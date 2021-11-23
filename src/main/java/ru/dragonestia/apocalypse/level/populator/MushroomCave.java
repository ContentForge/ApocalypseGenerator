package ru.dragonestia.apocalypse.level.populator;

import cn.nukkit.item.Item;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.generator.populator.type.Populator;
import cn.nukkit.math.NukkitRandom;
import ru.dragonestia.apocalypse.level.ApocalypseGenerator;

import java.util.Random;

public class MushroomCave extends Populator {

    private final Random random;

    public MushroomCave(Random random){
        this.random = random;
    }

    @Override
    public void populate(ChunkManager chunkManager, int chunkX, int chunkZ, NukkitRandom nukkitRandom, FullChunk chunk) {
        if(random.nextFloat() > 0.15f || chunk.getBiomeId(7, 7) != ApocalypseGenerator.COMMON_BIOME) return;
        int y = random.nextInt(4) + 25;

        for(int x = 0; x < 16; x++)
            for(int z = 0; z < 16; z++){
                if(Math.pow(x - 7, 2) + Math.pow(z - 8, 2) > 49) continue;
                int h = random.nextInt(4) + 1;
                for(int ty = y - h; ty < y + h + 1; ty++) chunk.setBlockId(x, ty, z, 0);
                if(random.nextFloat() < 0.7f) chunk.setBlockId(x, y-h-1, z, Item.MYCELIUM);
                if(random.nextFloat() < 0.15f) chunk.setBlockId(x, y-h, z, random.nextBoolean()? Item.RED_MUSHROOM : Item.BROWN_MUSHROOM);
                if(random.nextFloat() < 0.02f) chunk.setBlockId(x, y+h+1, z, Item.GLOWSTONE);
            }
    }

}
