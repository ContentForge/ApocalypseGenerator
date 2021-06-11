package ru.dragonestia.apocalypse.level.populator;

import cn.nukkit.item.Item;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.generator.noise.nukkit.d.SimplexD;
import cn.nukkit.level.generator.populator.type.Populator;
import cn.nukkit.math.NukkitRandom;
import cn.nukkit.math.Vector3;
import ru.dragonestia.apocalypse.item.ApocalypseID;
import ru.dragonestia.apocalypse.level.ApocalypseGenerator;

import java.util.ArrayList;
import java.util.Random;

public class RadioTower extends Populator {

    private final Random random;
    private final SimplexD cityNoise;

    public RadioTower(Random random, SimplexD cityNoise){
        this.random = random;
        this.cityNoise = cityNoise;
    }

    @Override
    public void populate(ChunkManager chunkManager, int chunkX, int chunkZ, NukkitRandom nukkitRandom, FullChunk chunk) {
        if(cityNoise.getNoise2D(chunkX / 100.0, chunkZ / 100.0) > 0 || chunk.getBiomeId(5, 5) != ApocalypseGenerator.COMMON_BIOME || random.nextFloat() > 0.01f) return;
        int x = 4 + random.nextInt(5);
        int z = 4 + random.nextInt(5);
        int y = chunk.getHighestBlockAt(x, z) - 3;

        for(int level = 0, lastLevel = 12 + random.nextInt(10); level < lastLevel; level++){
            placeFloor(x, y++, z, chunk);
            for(int dy = 0, yMax = 1 + random.nextInt(5); dy < yMax; dy++, y++){
                placeWall(x, y, z, chunk);
                if(level == lastLevel - 1) placeBars(x, y, z, chunk);
            }
        }
        placeTop(x, y, z, chunk);
    }

    private void placeWall(int x, int y, int z, FullChunk chunk){
        if(chunk.getBlockId(x-1, y, z+1) == 0) chunk.setBlockId(x-1, y, z-1, Item.STONE_WALL);
        if(chunk.getBlockId(x-1, y, z+1) == 0) chunk.setBlockId(x-1, y, z+1, Item.STONE_WALL);
        if(chunk.getBlockId(x+1, y, z-1) == 0) chunk.setBlockId(x+1, y, z-1, Item.STONE_WALL);
        if(chunk.getBlockId(x+1, y, z+1) == 0) chunk.setBlockId(x+1, y, z+1, Item.STONE_WALL);
    }

    private void placeFloor(int x, int y, int z, FullChunk chunk){
        chunk.setBlock(x-1, y, z-1, 159, 9);
        chunk.setBlock(x-1, y, z+1, 159, 9);
        chunk.setBlock(x+1, y, z-1, 159, 9);
        chunk.setBlock(x+1, y, z+1, 159, 9);

        chunk.setBlockId(x+1, y, z, 44);
        chunk.setBlockId(x-1, y, z, 44);
        chunk.setBlockId(x, y, z+1, 44);
        chunk.setBlockId(x, y, z-1, 44);
    }

    private void placeBars(int x, int y, int z, FullChunk chunk){
        chunk.setBlockId(x-2, y, z, Item.IRON_BARS);
        chunk.setBlockId(x-2, y, z+1, Item.IRON_BARS);
        chunk.setBlockId(x-2, y, z-1, Item.IRON_BARS);

        chunk.setBlockId(x+2, y, z, Item.IRON_BARS);
        chunk.setBlockId(x+2, y, z+1, Item.IRON_BARS);
        chunk.setBlockId(x+2, y, z-1, Item.IRON_BARS);

        chunk.setBlockId(x+1, y, z-2, Item.IRON_BARS);
        chunk.setBlockId(x, y, z-2, Item.IRON_BARS);
        chunk.setBlockId(x-1, y, z-2, Item.IRON_BARS);

        chunk.setBlockId(x+1, y, z+2, Item.IRON_BARS);
        chunk.setBlockId(x, y, z+2, Item.IRON_BARS);
        chunk.setBlockId(x-1, y, z+2, Item.IRON_BARS);
    }

    private void placeTop(int x, int y, int z, FullChunk chunk){
        for(int dx = 0; dx < 3; dx++)
            for(int dz = 0; dz < 3; dz++){
                if(dx == dz && dx == 1) continue;
                chunk.setBlockId(x + dx - 1, y, z + dz - 1, Item.SLAB);
            }

        chunk.setBlock(x-2, y, z, 236, 8);
        chunk.setBlock(x-2, y, z+1, 236, 8);
        chunk.setBlock(x-2, y, z-1, 236, 8);

        chunk.setBlock(x+2, y, z, 236, 8);
        chunk.setBlock(x+2, y, z+1, 236, 8);
        chunk.setBlock(x+2, y, z-1, 236, 8);

        chunk.setBlock(x+1, y, z-2, 236, 8);
        chunk.setBlock(x, y, z-2, 236, 8);
        chunk.setBlock(x-1, y, z-2, 236, 8);

        chunk.setBlock(x+1, y, z+2, 236, 8);
        chunk.setBlock(x, y, z+2, 236, 8);
        chunk.setBlock(x-1, y, z+2, 236, 8);
        y++;

        chunk.setBlockId(x + 2, y, z, Item.CHEST);
        ArrayList<Item> items = new ArrayList<>();
        if(random.nextFloat() < 0.35) items.add(Item.get(ApocalypseID.CHIP, 0, 1 + random.nextInt(2)));
        if(random.nextFloat() < 0.35) items.add(Item.get(ApocalypseID.COPPER_WIRE, 0, 1 + random.nextInt(2)));
        if(random.nextFloat() < 0.35) items.add(Item.get(ApocalypseID.BATTERY, 0, 1 + random.nextInt(2)));
        if(items.size() == 0) items.add(Item.get(ApocalypseID.PLASTIC, 0, 1 + random.nextInt(2)));
        ApocalypseGenerator.fillChest(new Vector3(x+2, y, z), chunk, items);

        placeTower(x+2, y, z+1, chunk);
        placeTower(x+1, y, z-2, chunk);
        placeTower(x-2, y, z, chunk);
    }

    private void placeTower(int x, int y, int z, FullChunk chunk){
        for(int i = 0, iMax = 1 + random.nextInt(4); i < iMax; i++, y++){
            chunk.setBlock(x, y, z, 236, 8);
        }
        for(int i = 0, iMax = 10 + random.nextInt(6); i < iMax; i++, y++){
            chunk.setBlockId(x, y, z, Item.COBBLE_WALL);
        }
        chunk.setBlockId(x, y++, z, Item.IRON_BLOCK);
        for(int i = 0, iMax = 10 + random.nextInt(6); i < iMax; i++, y++){
            chunk.setBlockId(x, y, z, Item.IRON_BARS);
        }
    }

}
