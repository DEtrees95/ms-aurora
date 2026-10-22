/* MS Aurora — ES5 / WorldEdit 7.3 CraftScript. See docs/SICHERHEIT.md. */
var V = Packages.com.sk89q.worldedit.math.BlockVector3;
var CFG = {
    // ONLY after inspecting every old marker and excluding all real builds:
    oldReviewed: false,
    // Exact WorldEdit world.id() from preview, not a vanilla dimension name.
    worldId: null,
    // The four known legacy marks. Complete marks 5–7 locally before migration.
    // Format: [x1,y1,z1,x2,y2,z2,material]
    old: [
        [-224,64,-49,225,64,50,"white_concrete"],
        [-224,65,0,225,65,1,"light_blue_concrete"],
        [-224,65,-49,-224,65,50,"gray_concrete"],
        [225,65,0,225,65,1,"red_concrete"]
    ]
};
function plan(boxes) {
    var out = {}, i,x,y,z,b;
    for(i=0;i<boxes.length;i++) {
        b=boxes[i];
        if(b[0]>b[3] || b[1]>b[4] || b[2]>b[5]) throw "Invalid box";
        for(x=b[0];x<=b[3];x++) for(y=b[1];y<=b[4];y++) for(z=b[2];z<=b[5];z++)
            out[x+","+y+","+z]="minecraft:"+b[6];
    }
    return out;
}
function validateOld(boxes) {
    var allowed={white_concrete:1,light_blue_concrete:1,gray_concrete:1,red_concrete:1,orange_concrete:1,yellow_concrete:1,black_concrete:1};
    for(var i=0;i<boxes.length;i++) {
        var b=boxes[i];
        if(b.length!==7 || !allowed[b[6]]) throw "Invalid old material/box";
        for(var j=0;j<6;j++) if(typeof b[j]!=="number" || !isFinite(b[j]) || Math.floor(b[j])!==b[j]) throw "Coordinates must be finite integers";
        if(b[0]<-224 || b[3]>225 || b[2]<-49 || b[5]>50 || b[0]>b[3] || b[2]>b[5]) throw "Old plan exceeds legacy footprint";
        if(b[1]!==b[4] || (b[1]!==52 && b[1]!==64 && b[1]!==65)) throw "Old plan must stay on individual marker levels";
        if((b[1]===52)!==(b[6]==="black_concrete")) throw "Only black keel markers permitted at Y52";
    }
}
// Later entries win: orange/gray/red remain readable at intersections.
var NEW = plan([
    [-135,64,-30,134,64,29,"white_concrete"],
    [-135,65,-1,134,65,0,"light_blue_concrete"],
    [-135,65,-30,95,65,-30,"yellow_concrete"],
    [-135,65,29,95,65,29,"yellow_concrete"],
    [96,65,-30,96,65,29,"orange_concrete"],
    [-135,65,-30,-135,65,29,"gray_concrete"],
    [134,65,-1,134,65,0,"red_concrete"],
    [-135,52,-1,134,52,0,"black_concrete"]
]);
function vec(k) { var a=k.split(","); return V.at(+a[0],+a[1],+a[2]); }
function natural(id,k) {
    var y=+k.split(",")[1];
    // Never overwrite water at/above the building plane, or air at keel depth.
    return y<=63 ? id==="minecraft:water" : id==="minecraft:air";
}
function main() {
    var mode=String(argv.length>1?argv[1]:"preview");
    var action=String(argv.length>2?argv[2]:"fresh");
    if((mode!=="preview" && mode!=="apply") || (action!=="fresh" && action!=="migrate"))
        throw "Usage: /cs aurora preview|apply fresh|migrate [BACKUP]";
    var worldId=String(player.getWorld().id());
    context.print("WorldEdit world ID: "+worldId);
    if(CFG.worldId!==null && worldId!==CFG.worldId) throw "Wrong world/dimension";
    if(mode==="apply" && CFG.worldId===null) throw "Set CFG.worldId to the reviewed preview ID before apply";
    if(action==="migrate" && !CFG.oldReviewed)
        throw "Migration locked: complete CFG.old and inspect the entire footprint, then set oldReviewed=true.";
    if(mode==="apply" && String(argv.length>3?argv[3]:"")!=="BACKUP") throw "Backup confirmation missing";
    if(action==="migrate") validateOld(CFG.old);
    var old=action==="migrate"?plan(CFG.old):{}, all={}, k, id, target, changes=[], conflicts=[];
    var world=player.getWorld();
    for(k in old) all[k]=true;
    for(k in NEW) all[k]=true;
    // Read everything before opening an edit session. Unknown blocks abort ALL edits.
    for(k in all) {
        id=String(world.getBlock(vec(k)).getBlockType().getId());
        if(!natural(id,k) && !(old[k] && id===old[k])) {
            if(conflicts.length<10) conflicts.push(k+"="+id);
            continue;
        }
        target=NEW[k] || (+k.split(",")[1]<=63?"minecraft:water":"minecraft:air");
        if(target!==id) changes.push([k,target]);
    }
    if(conflicts.length) throw "ABORT: occupied/unexpected blocks (first 10): "+conflicts.join("; ");
    context.print("Plan "+action+": "+changes.length+" changes. No broad volume clearing.");
    if(mode!=="apply") {context.print("Preview only. No blocks changed."); return;}
    var limit=context.getSession().getBlockChangeLimit();
    if(limit>=0 && changes.length>limit) throw "WorldEdit block limit too low; no changes made";
    var edit=context.remember(), cache={}, i;
    for(i=0;i<changes.length;i++) {
        target=changes[i][1];
        if(!cache[target]) cache[target]=context.getBlock(target);
    }
    for(i=0;i<changes.length;i++) edit.setBlock(vec(changes[i][0]),cache[changes[i][1]]);
    context.print("Done. Check visually. Immediate rollback: //undo (same player/session).");
}
main();
