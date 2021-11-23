package ru.dragonestia.apocalypse.weapon;

import cn.nukkit.block.BlockID;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityLiving;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector3;

public class Tracer {

    public static final int MAX_DISTANCE = 6 * 15;

    private final Position start;
    private final Vector3 direction;
    private final Entity sender;

    public Tracer(Position start, Vector3 direction, Entity sender){
        this.start = start;
        this.direction = direction;
        this.sender = sender;
    }

    public EntityLiving trace(){
        if((direction.x == 0 && direction.y == 0 && direction.z == 0) || start.getLevel() == null) return null;

        double wayPassed = 0;
        Level level = start.level;
        Vector3 ray = new Vector3(start.x, start.y, start.z);

        FullChunk chunk = null;
        while(true) {
            ray.x += direction.x;
            ray.y += direction.y;
            ray.z += direction.z;
            wayPassed += 1;

            if(chunk == null || (int) ray.x >> 4 != chunk.getX() || (int) ray.z >> 4 != chunk.getZ()){
                if(!level.isChunkLoaded((int) ray.x >> 4, (int) ray.z >> 4)) return null;
                chunk = level.getChunkIfLoaded((int) ray.x >> 4, (int) ray.z >> 4);
            }
            if(wayPassed > MAX_DISTANCE) return null;


            int blockId = chunk.getBlockId((int) ray.x & 0xF, (int) ray.y, (int) ray.z & 0xF);
            if (blockId != 0){
                playBlockStrikeSound(blockId, new Position(ray.x, ray.y, ray.z, level));
                if(!isIgnoreBlock(blockId)) return null;
            }

            for (Entity entity : chunk.getEntities().values()) {
                if (!(entity instanceof EntityLiving) || entity.equals(sender)) continue;

                if (entity.y > ray.y || ray.y - entity.y > entity.getHeight()) continue;
                double t = Math.abs(entity.getYaw() % 180 - 90) / 90.0;
                float al = Math.abs(entity.getLength() - entity.getLength() / 2f), aw = Math.abs(entity.getWidth() - entity.getWidth() / 2f);
                double l = al + aw * t, w = aw + al * t;

                if(Math.abs(entity.x - ray.x) > l || Math.abs(entity.z - ray.z) > w) continue;

                return (EntityLiving) entity;
            }
        }
    }

    public boolean isIgnoreBlock(int blockId){
        switch (blockId){
            case BlockID.IRON_BARS:
            case BlockID.GLASS_PANE:
            case BlockID.STAINED_GLASS_PANE:
                return true;

            default:
                return false;
        }
    }

    public void playBlockStrikeSound(int blockId, Position position){

    }

}
