package ru.dragonestia.apocalypse.level.populator.wastelands;

import cn.nukkit.item.Item;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.generator.populator.type.Populator;
import cn.nukkit.math.NukkitRandom;
import ru.dragonestia.apocalypse.level.ApocalypseGenerator;

import java.util.Random;

public class StoneHouse extends Populator implements WastelandPopulator {

    private final Random random;

    public StoneHouse(Random random){
        this.random = random;
    }

    @Override
    public boolean checkPlace(int chunkX, int chunkZ, FullChunk chunk) {
        return !(chunk.getBiomeId(5, 5) == ApocalypseGenerator.ASH_BIOME || random.nextFloat() > 0.01f);
    }

    @Override
    public void populate(ChunkManager chunkManager, int chunkX, int chunkZ, NukkitRandom nukkitRandom, FullChunk chunk) {
        boolean fireBiome = chunk.getBiomeId(5, 5) == ApocalypseGenerator.FIRE_BIOME;
        int h = chunk.getHighestBlockAt(5, 5) - 1;

        for(int x = 7; x < 13; x++)
            for(int z = 5; z < 14; z++)
                for(int y = 0; y < 4; y++){
                    if(z == 5 || z == 13){
                        chunk.setBlockId(x, h + y, z, Item.BRICKS);
                        continue;
                    }
                    if(x != 7 && x != 12) continue;
                    chunk.setBlock(x, h+y, z, Item.COBBLE);
                }

        if(fireBiome) return;
        for(int x = 8; x < 12; x++)
            for(int z = 6; z < 13; z++){
                if(random.nextFloat() > 0.3f) continue;

                chunk.setBlock(x, h-1, z, Item.PLANK);
            }
    }

}
