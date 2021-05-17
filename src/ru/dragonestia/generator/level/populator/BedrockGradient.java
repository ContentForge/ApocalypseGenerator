package ru.dragonestia.generator.level.populator;

import cn.nukkit.item.Item;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.generator.populator.type.Populator;
import cn.nukkit.math.NukkitRandom;

public class BedrockGradient extends Populator {

    @Override
    public void populate(ChunkManager chunkManager, int chunkX, int chunkZ, NukkitRandom nukkitRandom, FullChunk chunk) {
        for(int x = 0; x < 16; x++)
            for(int z = 0; z < 16; z++)
                for(int y = 0; y < 7; y++){
                    if(y == 0 || nukkitRandom.nextFloat() < y / 7.1F) chunk.setBlockId(x, y, z, Item.BEDROCK);
                }
    }

}
