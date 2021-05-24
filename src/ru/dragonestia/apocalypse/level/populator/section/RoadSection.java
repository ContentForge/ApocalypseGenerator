package ru.dragonestia.apocalypse.level.populator.section;

import cn.nukkit.item.Item;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.format.FullChunk;
import ru.dragonestia.apocalypse.level.ApocalypseGenerator;

import java.util.Random;

public class RoadSection extends Section {

    public RoadSection(Random random, ChunkManager chunkManager) {
        super(random, chunkManager);
    }

    public void generate(FullChunk chunk, Type type){
        for(int x = 0; x < 16; x++)
            for(int z = 0; z < 16; z++){
                if(chunk.getBiomeId(x, z) == ApocalypseGenerator.ASH_BIOME) continue;
                int y = chunk.getHighestBlockAt(x, z);

                if(type == Type.HORIZONTAL){
                    if (x < 4 || x > 11) placeNotRoad(x, y, z, chunk);
                    else placeRoad(x, y, z, chunk);
                }

                if(type == Type.RIGHT){
                    if (z < 4 || (z > 11 && (x < 4 || x > 11))) placeNotRoad(x, y, z, chunk);
                    else placeRoad(x, y, z, chunk);
                }

                if(type == Type.LEFT){
                    if (z > 11 || (z < 4 && (x < 4 || x > 11))) placeNotRoad(x, y, z, chunk);
                    else placeRoad(x, y, z, chunk);
                }

                if(type == Type.FORWARD){
                    if (z < 4 || z > 11) placeNotRoad(x, y, z, chunk);
                    else placeRoad(x, y, z, chunk);
                }
            }
    }

    private void placeRoad(int x, int y, int z, FullChunk chunk){
        if(random.nextFloat() > 0.6f) return;

        chunk.setBlock(x, y, z, Item.CONCRETE_POWDER, 7);
        chunk.setBlock(x, y - 1, z, Item.GRAVEL, 1);
        chunk.setBlock(x, y - 2, z, Item.GRAVEL, 1);
    }

    private void placeNotRoad(int x, int y, int z, FullChunk chunk){
        if(random.nextFloat() > 0.4f) return;

        chunk.setBlock(x, y+1, z, Item.SLAB);
        chunk.setBlock(x, y, z, Item.STONE_BRICK);
        chunk.setBlock(x, y-1, z, Item.STONE_BRICK);
    }

    public enum Type {
        RIGHT,
        LEFT,
        FORWARD,
        HORIZONTAL
    }

}
