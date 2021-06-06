package ru.dragonestia.apocalypse.level.populator.section;

import cn.nukkit.item.Item;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.Vector3;
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

                    if(x == 2 && z == 10) placeColumn(x, y, z, chunk, 0);
                    if(x == 14 && z == 5) placeColumn(x, y, z, chunk, 2);
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

                    if(z == 2 && x == 10) placeColumn(x, y, z, chunk, 1);
                    if(z == 14 && x == 5) placeColumn(x, y, z, chunk, 3);
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

    private void placeColumn(int x, int y, int z, FullChunk chunk, int rotation){
        y++;
        chunk.setBlock(x, y++, z, 159, 9);
        chunk.setBlock(x, y++, z, 159, 9);
        chunk.setBlock(x, y++, z, 159, 9);
        for(int i = 0; i < 6; i++){
            chunk.setBlock(x, y++, z, 139, 4);
        }

        for(int i = 0; i < 4; i++){
            chunk.setBlock(x, y, z, Item.SLAB);
            switch (rotation % 4){
                case 0:
                    x++;
                    break;
                case 1:
                    z++;
                    break;
                case 2:
                    x--;
                    break;
                case 3:
                    z--;
                    break;
            }
        }

    }

    public enum Type {
        RIGHT,
        LEFT,
        FORWARD,
        HORIZONTAL
    }

}
