package ru.dragonestia.apocalypse.level.populator;

import cn.nukkit.item.Item;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.generator.noise.nukkit.d.SimplexD;
import cn.nukkit.level.generator.populator.type.Populator;
import cn.nukkit.math.NukkitRandom;

import java.util.Random;

public class Cave extends Populator {

    private final SimplexD simplexH;
    private final SimplexD simplexC;
    private final Random random;
    private static final int Y = 53;

    public Cave(NukkitRandom nukkitRandom, Random random){
        this.random = random;
        simplexH = new SimplexD(nukkitRandom, 1F, 1F);
        simplexC = new SimplexD(nukkitRandom, 1F, 1F);
    }

    @Override
    public void populate(ChunkManager chunkManager, int chunkX, int chunkZ, NukkitRandom nukkitRandom, FullChunk chunk) {
        for(int dx = 0; dx < 16; dx++){
            for(int dz = 0; dz < 16; dz++){
                int x = dx + chunkX * 16, z = dz + chunkZ * 16;

                int y = (int) (simplexH.getNoise2D(x / 50.0, z / 50.0) * 10) + Y;
                for(int dy = -1; dy < 3; dy++){
                    if(simplexC.getNoise3D(x / 30.0, (y + dy) / 30.0, z / 30.0) > 0.01) continue;

                    chunk.setBlockId(dx, y + dy, dz, 0);
                }

                int yMin = 0, yMax = 0;
                for(int dy = -1; dy < 3; dy++){
                    if(chunk.getBlockId(dx, y + dy, dz) != 0) continue;
                    if(yMin == 0) yMin = y + dy;
                    yMax = y + dy;
                }

                if(yMin == 0 || yMin >= chunk.getHighestBlockAt(dx, dz) - 5) continue;

                if(random.nextFloat() < 0.03f) chunk.setBlockId(dx, yMin, dz, Item.STONE_WALL);
                if(random.nextFloat() < 0.03f) chunk.setBlockId(dx, yMax, dz, Item.STONE_WALL);
                if(random.nextFloat() < 0.03f) chunk.setBlockId(dx, yMin, dz, Item.COBBLE);
                if(random.nextFloat() < 0.07f) chunk.setBlockId(dx, yMin - 1, dz, Item.DIRT);
                if(random.nextFloat() < 0.03f) chunk.setBlockId(dx, yMax, dz, Item.COBBLE);

                if(random.nextFloat() < 0.001f) chunk.setBlockId(dx, yMax, dz, Item.GLOWSTONE);
                if(chunk.getBlockId(dx, yMin - 2, dz) != 0 && random.nextFloat() < 0.01f){
                    chunk.setBlockId(dx, yMin-1, dz, Item.STILL_LAVA);
                }
            }
        }
    }

}
