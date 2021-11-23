package ru.dragonestia.apocalypse.level.populator.wastelands;

import cn.nukkit.item.Item;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.generator.populator.type.Populator;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector3;
import ru.dragonestia.apocalypse.item.ApocalypseID;
import ru.dragonestia.apocalypse.level.ApocalypseGenerator;

import java.util.ArrayList;
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

        boolean hasRoof = random.nextBoolean();
        for(int x = 7; x < 13; x++)
            for(int z = 5; z < 14; z++)
                for(int y = 0; y < 7; y++){
                    if(hasRoof && y == 6){
                        chunk.setBlockId(x, h + y, z, Item.COBBLE);
                        continue;
                    }
                    if(y == 3 && random.nextFloat() > 0.6f) continue;
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
                if(random.nextFloat() > 0.5f) continue;

                chunk.setBlock(x, h-1, z, Item.PLANK);
            }

        chunk.setBlock(8, h, 10, Item.CRAFTING_TABLE);
        if(random.nextBoolean()) chunk.setBlock(8, h, 9, Item.FURNACE);
        if(random.nextBoolean()) chunk.setBlock(8, h, 8, Item.FURNACE);

        ArrayList<Item> items = new ArrayList<>();
        if(random.nextFloat() < 0.35f) items.add(Item.get(Item.ROTTEN_FLESH, 0, random.nextInt(12) + 2));
        if(random.nextFloat() < 0.35f) items.add(Item.get(ApocalypseID.PLASTIC, 0, random.nextInt(10) + 2));
        if(random.nextFloat() < 0.35f) items.add(Item.get(ApocalypseID.YODADULIN));
        if(random.nextFloat() < 0.35f) items.add(Item.get(ApocalypseID.CLOTH, 0, random.nextInt(15) + 2));
        if(random.nextFloat() < 0.35f) items.add(Item.get(ApocalypseID.BATTERY, 0, 2));;
        if(random.nextFloat() < 0.35f) items.add(Item.get(ApocalypseID.SULFUR_DUST, 0, random.nextInt(5) + 1));
        if(random.nextFloat() < 0.35f) items.add(Item.get(Item.POISONOUS_POTATO, 0, random.nextInt(5) + 2));

        chunk.setBlock(11, h, 12, Item.CHEST);
        ApocalypseGenerator.fillChest(new Vector3(11, h, 12), chunk, items);
    }

}
