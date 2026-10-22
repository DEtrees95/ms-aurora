/* No dependencies. Tests protection logic against a minimal WorldEdit API model. */
const fs = require('node:fs');
const vm = require('node:vm');
const assert = require('node:assert/strict');
const path = require('node:path');
const source = fs.readFileSync(path.join(__dirname,'../craftscripts/aurora.js'),'utf8').replace(/main\(\);\s*$/, '');
function fixture(options={}) {
  const blocks=new Map(), writes=[], messages=[];
  let sessions=0;
  const natural=k=>+k.split(',')[1]<=63?'minecraft:water':'minecraft:air';
  const world={id:()=>options.world||'aurora_minecraft:overworld',getBlock:k=>({getBlockType:()=>({getId:()=>blocks.get(k)||natural(k)})})};
  const env={Packages:{com:{sk89q:{worldedit:{math:{BlockVector3:{at:(x,y,z)=>[x,y,z].join(',')}}}}}},
    argv:['aurora','preview','fresh'],player:{getWorld:()=>world},context:{
      print:m=>messages.push(m),getSession:()=>({getBlockChangeLimit:()=>options.limit??-1}),getBlock:id=>id,
      remember:()=>{sessions++;return {setBlock:(k,id)=>{writes.push([k,id]);blocks.set(k,id);return true;}};}
    }};
  vm.createContext(env);vm.runInContext(source,env);
  env.CFG.worldId='aurora_minecraft:overworld';
  return {env,blocks,writes,messages,get sessions(){return sessions;},run:(mode='preview',action='fresh',backup='BACKUP')=>{
    env.argv=['aurora',mode,action,backup];return vm.runInContext('main()',env);
  }};
}
let passed=0;
function test(name,fn){fn();passed++;console.log('PASS '+name);}
test('preview creates no edit session and writes nothing',()=>{const f=fixture();f.run();assert.equal(f.sessions,0);assert.equal(f.writes.length,0);});
test('exact geometry, seven colors and crossing precedence',()=>{
  const f=fixture();f.run('apply');
  const counts={};for(const [k,id] of f.writes)counts[id]=(counts[id]||0)+1;
  assert.deepEqual(counts,{'minecraft:white_concrete':16200,'minecraft:light_blue_concrete':534,'minecraft:yellow_concrete':460,'minecraft:orange_concrete':60,'minecraft:gray_concrete':60,'minecraft:red_concrete':2,'minecraft:black_concrete':540});
  assert.equal(f.blocks.get('96,65,-1'),'minecraft:orange_concrete');
  assert.equal(f.blocks.get('-135,65,-1'),'minecraft:gray_concrete');
  assert.equal(f.blocks.get('134,65,0'),'minecraft:red_concrete');
  assert.equal(f.writes.length,17856);assert.equal(f.sessions,1);
  for(const [k] of f.writes){const [x,y,z]=k.split(',').map(Number);assert(x>=-135&&x<=134&&z>=-30&&z<=29);assert([52,64,65].includes(y));}
});
test('occupied block at end of scan aborts all writes',()=>{const f=fixture();f.blocks.set('134,52,0','minecraft:chest');assert.throws(()=>f.run('apply'),/ABORT/);assert.equal(f.sessions,0);assert.equal(f.writes.length,0);});
test('same color existing yacht block is protected in fresh mode',()=>{const f=fixture();f.blocks.set('0,64,0','minecraft:white_concrete');assert.throws(()=>f.run('apply'),/ABORT/);assert.equal(f.writes.length,0);});
test('migration is locked by default',()=>{const f=fixture();assert.throws(()=>f.run('apply','migrate'),/Migration locked/);assert.equal(f.writes.length,0);});
test('no writes without backup token or configured world',()=>{const f=fixture();assert.throws(()=>f.run('apply','fresh',''),/Backup/);f.env.CFG.worldId=null;assert.throws(()=>f.run('apply'),/worldId/);assert.equal(f.writes.length,0);});
test('wrong world rejected',()=>{const f=fixture({world:'other_minecraft:overworld'});assert.throws(()=>f.run('apply'),/Wrong world/);assert.equal(f.writes.length,0);});
test('change limit is checked before edit session',()=>{const f=fixture({limit:100});assert.throws(()=>f.run('apply'),/limit too low/);assert.equal(f.sessions,0);});
test('reviewed migration cleans only manifest union new plan',()=>{
  const f=fixture();f.env.CFG.oldReviewed=true;
  const old=f.env.plan(f.env.CFG.old);for(const k in old)f.blocks.set(k,old[k]);
  f.blocks.set('226,64,0','minecraft:white_concrete');
  f.blocks.set('0,53,0','minecraft:chest');
  f.run('apply','migrate');
  assert.equal(f.blocks.get('-224,64,-49'),'minecraft:air');
  assert.equal(f.blocks.get('226,64,0'),'minecraft:white_concrete');
  assert.equal(f.blocks.get('0,53,0'),'minecraft:chest');
  for(const [k] of f.writes)assert(k in old || k in f.env.NEW);
});
test('unexpected material inside old footprint aborts migration',()=>{const f=fixture();f.env.CFG.oldReviewed=true;f.blocks.set('-224,64,-49','minecraft:stone');assert.throws(()=>f.run('apply','migrate'),/ABORT/);assert.equal(f.writes.length,0);});
test('missing old marks allowed; black old keel restored to water',()=>{const f=fixture();f.env.CFG.oldReviewed=true;f.env.CFG.old.push([-224,52,0,-224,52,1,'black_concrete']);f.blocks.set('-224,52,0','minecraft:black_concrete');f.run('apply','migrate');assert.equal(f.blocks.get('-224,52,0'),'minecraft:water');assert.equal(f.blocks.has('-224,52,1'),false);});
test('invalid old manifest cannot broaden cleanup bounds',()=>{for(const b of [[-225,64,0,225,64,0,'white_concrete'],[0,52,0,0,65,0,'black_concrete'],[0,64,0,0,64,0,'stone'],[0.5,64,0,1,64,0,'white_concrete']]){const f=fixture();f.env.CFG.oldReviewed=true;f.env.CFG.old.push(b);assert.throws(()=>f.run('apply','migrate'));assert.equal(f.writes.length,0);}});
test('repeated fresh apply fails safely',()=>{const f=fixture();f.run('apply');const n=f.writes.length;assert.throws(()=>f.run('apply'),/ABORT/);assert.equal(f.writes.length,n);});
console.log(`${passed} safety/geometry tests passed. Minecraft/Rhino integration NOT tested.`);
