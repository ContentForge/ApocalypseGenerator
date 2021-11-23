package ru.dragonestia.apocalypse.level.populator;

import cn.nukkit.item.Item;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.generator.populator.type.Populator;
import cn.nukkit.math.NukkitRandom;
import ru.dragonestia.apocalypse.level.ApocalypseGenerator;

import java.util.Random;

public class UndergroundLava extends Populator {

    private final Random random;

    public UndergroundLava(Random random){
        this.random = random;
    }

    @Override
    public void populate(ChunkManager chunkManager, int chunkX, int chunkZ, NukkitRandom nukkitRandom, FullChunk chunk) {
        for(int i = 0, n = random.nextInt(15) + 15; i < n; i++){
            int x = random.nextInt(16), z = random.nextInt(16);
            if(chunk.getBiomeId(x, z) != ApocalypseGenerator.FIRE_BIOME) continue;

            chunk.setBlockId(x, random.nextInt(40) + 7, z, Item.LAVA);
        }
    }

}
