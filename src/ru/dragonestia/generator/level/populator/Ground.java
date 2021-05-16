package ru.dragonestia.generator.level.populator;

import cn.nukkit.item.Item;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.generator.noise.nukkit.d.SimplexD;
import cn.nukkit.level.generator.populator.type.Populator;
import cn.nukkit.math.NukkitRandom;
import ru.dragonestia.generator.util.Pair;

import java.util.Random;

public class Ground extends Populator {

    private final SimplexD simplex;
    private final Random random;
    private final Pair<Integer, Integer>[] blocks;

    public Ground(NukkitRandom nukkitRandom, Random random){
        this.random = random;
        simplex = new SimplexD(nukkitRandom, 1F, 1F);
        blocks = new Pair[]{
                new Pair<>(Item.COBBLE, 0),
                new Pair<>(Item.GRAVEL, 0),
                new Pair<>(Item.DIRT, 0),
                new Pair<>(Item.DIRT, 1),
                new Pair<>(Item.SOUL_SAND, 0),
                new Pair<>(Item.CONCRETE_POWDER, 7),
                new Pair<>(Item.COAL_ORE, 0),
                new Pair<>(Item.CONCRETE_POWDER, 8)
        };
    }

    @Override
    public void populate(ChunkManager chunkManager, int chunkX, int chunkZ, NukkitRandom nukkitRandom, FullChunk chunk) {
        for(int x = 0; x < 16; x++)
            for(int z = 0; z < 16; z++){
                int globalX = x + chunkX*16, globalZ = z + chunkZ*16;
                int y = chunk.getHighestBlockAt(x, z);

                double noise = (simplex.getNoise2D(globalX / 100.0, globalZ / 100.0) + 1) / 2;
                for(int ty = y, dy = y - random.nextInt(3) - 3; ty > dy; ty--){
                    if(noise < random.nextFloat()) continue;

                    Pair<Integer, Integer> block = blocks[random.nextInt(blocks.length)];
                    chunk.setBlock(x, ty, z, block.first, block.second);
                }
            }
    }

}
