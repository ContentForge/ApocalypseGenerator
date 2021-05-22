package ru.dragonestia.apocalypse.level.populator;

import cn.nukkit.item.Item;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.generator.noise.nukkit.d.SimplexD;
import cn.nukkit.level.generator.populator.type.Populator;
import cn.nukkit.math.NukkitRandom;
import ru.dragonestia.apocalypse.level.ApocalypseGenerator;

import java.util.Random;

public class LavaPit extends Populator {

    private final SimplexD simplex;
    private final Random random;

    public LavaPit(SimplexD simplex, Random random){
        this.simplex = simplex;
        this.random = random;
    }

    @Override
    public void populate(ChunkManager chunkManager, int chunkX, int chunkZ, NukkitRandom nukkitRandom, FullChunk chunk) {
        for(int x = 0; x < 16; x++)
            for(int z = 0; z < 16; z++) {
                int y = chunk.getHighestBlockAt(x, z);
                double p = simplex.getNoise2D((x + chunkX * 16) / 200.0, (z + chunkZ * 16) / 200.0);
                p -= chunk.getBiomeId(x, z) == ApocalypseGenerator.FIRE_BIOME ? 0.3 : 0;

                if (p > -0.76) continue;
                for (int ty = y; ty >= 45; ty--)
                    if (random.nextFloat() < 0.01f) chunk.setBlock(x, ty, z, Item.OBSIDIAN);

                if (p > -0.78 || random.nextFloat() > 0.6f) {
                    for (int ty = y, h = (y*3 + 49*2) / 5; y > h; y--)
                        chunk.setBlock(x, ty, z, 0);
                    continue;
                }

                for (int ty = y; ty >= 46; ty--) {
                    chunk.setBlock(x, ty, z, ty == 46 ? Item.LAVA : 0);
                }
            }
    }

}
