package ru.dragonestia.apocalypse.level.populator.section;

import cn.nukkit.item.Item;
import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.format.FullChunk;
import ru.dragonestia.apocalypse.level.ApocalypseGenerator;
import ru.dragonestia.apocalypse.level.populator.section.filter.VoidFilter;

import java.util.Random;

public class BrickHouse2 extends HouseSection {

    public BrickHouse2(Random random, ChunkManager chunkManager) {
        super(random, chunkManager);
    }

    @Override
    protected void generate(BlockPlacer placer, FullChunk chunk, boolean broken) {
        int h = chunk.getHighestBlockAt(7, 7);
        int levels = 10 + random.nextInt(7);
        VoidFilter voidFilter = new VoidFilter(h, h + levels*4, (broken? 1 : 0) + random.nextInt(2), random);
        for(int level = 0; level < levels; level++){
            generateLevel(level*4 + h, voidFilter, placer, broken);
        }
        generateRoof(levels*4 + h, voidFilter, placer);
    }

    private void generateLevel(int y, VoidFilter voidFilter, BlockPlacer placer, boolean broken){
        //Балкон
        for(int x = 0; x < 7; x++){
            setBlock(x, y, 15, 1, 0, voidFilter, placer);
            placeBrick(x, y+1, 15, voidFilter, placer, broken);
            setBlock(x, y+2, 15, Item.SLAB, 0, voidFilter, placer);
        }
        for(int z = 10; z < 16; z++){
            setBlock(0, y, z, 1, 0, voidFilter, placer);
            placeBrick(0, y+1, z, voidFilter, placer, broken);
            setBlock(0, y+2, z, Item.SLAB, 0, voidFilter, placer);
        }
        for(int x = 1; x < 7; x++){
            for (int z = 10; z < 15; z++){
                if(x > 3 && z < 13) continue;
                placeWoodFloor(x, y, z, voidFilter, placer);
            }
        }

        //Контуры
        for(int x = 0; x < 4; x++) setBlock(x, y, 9, Item.STONE_BRICK, 0, voidFilter, placer);
        for(int x = 3; x < 7; x++) setBlock(x, y, 12, Item.STONE_BRICK, 0, voidFilter, placer);
        for(int x = 1; x < 16; x++) setBlock(x, y, 1, Item.STONE_BRICK, 0, voidFilter, placer);
        for(int x = 0; x < 4; x++) setBlock(x, y, 9, Item.STONE_BRICK, 0, voidFilter, placer);
        for(int x = 8; x < 15; x++) setBlock(x, y, 8, Item.STONE_BRICK, 0, voidFilter, placer);
        for(int x = 8; x < 15; x++) setBlock(x, y, 14, Item.STONE_BRICK, 0, voidFilter, placer);
        for(int x = 11; x < 16; x++) setBlock(x, y, 5, Item.STONE_BRICK, 0, voidFilter, placer);

        for(int z = 1; z < 10; z++) setBlock(1, y, z, Item.STONE_BRICK, 0, voidFilter, placer);
        for(int z = 1; z < 16; z++) setBlock(7, y, z, Item.STONE_BRICK, 0, voidFilter, placer);
        for(int z = 1; z < 6; z++) setBlock(11, y, z, Item.STONE_BRICK, 0, voidFilter, placer);
        for(int z = 1; z < 6; z++) setBlock(15, y, z, Item.STONE_BRICK, 0, voidFilter, placer);
        for(int z = 6; z < 15; z++) setBlock(14, y, z, Item.STONE_BRICK, 0, voidFilter, placer);

        //Пол
        for(int x = 8; x < 14; x++){
            for(int z = 9; z < 14; z++){
                setBlock(x, y, z, 24, 0, voidFilter, placer);
            }
        }
        for(int x = 2; x < 7; x++){
            for(int z = 2; z < 12; z++){
                if(x < 4 && z > 8) continue;
                setBlock(x, y, z, 159, 9, voidFilter, placer);
            }
        }
        for(int x = 8; x < 14; x++){
            for(int z = 2; z < 8; z++){
                if(x > 10 && z < 6) continue;
                placeStoneFloor(x, y, z, voidFilter, placer);
            }
        }

        //Лесница
        for(int z = 1; z < 5; z++) setBlock(12, y, z,1, 0, voidFilter, placer);
        setBlock(13, y+1, 4,1, 0, voidFilter, placer);
        for(int z = 1; z < 5; z++) setBlock(14, y+2, z,1, 0, voidFilter, placer);
        setBlock(13, y+3, 2,1, 0, voidFilter, placer);

        //Стены
        for(int x = 2; x < 14; x++)
            for(int dy = 1; dy < 4; dy++){
                if(dy != 1 && x > 2 && x < 6) continue;
                placeBrick(x, y + dy, 1, voidFilter, placer, broken);
            }
        for(int x = 8; x < 14; x++)
            for(int dy = 1; dy < 4; dy++){
                if(dy != 1 && x > 9 && x < 12) continue;
                placeBrick(x, y + dy, 14, voidFilter, placer, broken);
            }
        for(int x = 8; x < 14; x++)
            for(int dy = 1; dy < 4; dy++){
                placeBrick(x, y + dy, 8, voidFilter, placer, broken);
            }
        for(int z = 2; z < 9; z++)
            for(int dy = 1; dy < 4; dy++){
                if(dy != 1 && (z - 2) % 3 != 0) continue;
                placeBrick(1, y + dy, z, voidFilter, placer, broken);
            }
        for(int z = 6; z < 14; z++)
            for(int dy = 1; dy < 4; dy++){
                if(dy != 1 && z > 9 && z < 13) continue;
                placeBrick(14, y + dy, z, voidFilter, placer, broken);
            }
        for(int z = 2; z < 14; z++)
            for(int dy = 1; dy < 4; dy++){
                if(dy != 3 && (z == 6 || z == 10)) continue;
                placeBrick(7, y + dy, z, voidFilter, placer, broken);
            }

        //Стена балкона
        for(int x = 1; x < 3; x++){
            for(int dy = 1; dy < 4; dy++){
                setBlock(x, y + dy, 9, 4, 0, voidFilter, placer);
            }
        }
        for(int x = 4; x < 7; x++){
            for(int dy = 1; dy < 4; dy++){
                if(x == 5 && y == 2) continue;
                setBlock(x, y + dy, 12, 4, 0, voidFilter, placer);
            }
        }
        for(int z = 10; z < 12; z++){
            for(int dy = 1; dy < 4; dy++){
                if(dy != 3) continue;
                setBlock(3, y + dy, z, 4, 0, voidFilter, placer);
            }
        }
        for(int z = 13; z < 15; z++){
            for(int dy = 1; dy < 4; dy++){
                setBlock(7, y + dy, z, 4, 0, voidFilter, placer);
            }
        }

        //Каменная хрень
        for(int z = 1; z < 6; z++)
            for(int dy = 0; dy < 4; dy++){
                setBlock(15, y + dy, z, 1, 0, voidFilter, placer);
            }

        //Каменная кирпичная хрень
        placeStoneBricksLine(14, y, 1, voidFilter, placer);
        placeStoneBricksLine(11, y, 1, voidFilter, placer);
        placeStoneBricksLine(7, y, 1, voidFilter, placer);
        placeStoneBricksLine(1, y, 1, voidFilter, placer);
        placeStoneBricksLine(14, y, 5, voidFilter, placer);
        placeStoneBricksLine(14, y, 8, voidFilter, placer);
        placeStoneBricksLine(7, y, 8, voidFilter, placer);
        placeStoneBricksLine(3, y, 9, voidFilter, placer);
        placeStoneBricksLine(0, y, 9, voidFilter, placer);
        placeStoneBricksLine(7, y, 12, voidFilter, placer);
        placeStoneBricksLine(3, y, 12, voidFilter, placer);
        placeStoneBricksLine(14, y, 14, voidFilter, placer);
        placeStoneBricksLine(7, y, 15, voidFilter, placer);

        //Ящики
        if(random.nextFloat() < 0.05f) setBlock(5, y+1, 13, Item.JUKEBOX, 0, voidFilter, placer);
        if(random.nextFloat() < 0.05f) setBlock(8, y+1, 13, Item.JUKEBOX, 0, voidFilter, placer);
        if(random.nextFloat() < 0.05f) setBlock(6, y+1, 3, Item.JUKEBOX, 0, voidFilter, placer);
    }

    private void generateRoof(int y, VoidFilter voidFilter, BlockPlacer placer){
        for(int x = 1; x < 15; x++)
            for(int z = 1; z < 15; z++){
                if(z < 6 && x > 10) continue;
                if(x > 1 && x < 14 && z > 1 && z < 14){
                    setBlock(x, y, z, 1, 0, voidFilter, placer);
                    continue;
                }
                setBlock(x, y, z, Item.STONE_BRICK, 0, voidFilter, placer);
                setBlock(x, y+1, z, Item.SLAB, 0, voidFilter, placer);
            }
        for(int x = 11; x < 16; x++)
            for(int z = 1; z < 6; z++){
                if(x > 11 && x < 15 && z > 1 && z < 5) continue;
                setBlock(x, y, z, Item.STONE_BRICK, 0, voidFilter, placer);
                setBlock(x, y+1, z, Item.SLAB, 0, voidFilter, placer);
            }
        for(int z = 1; z < 5; z++) setBlock(12, y, z, 1, 0, voidFilter, placer);
    }

    private void setBlock(int x, int y, int z, int id, int meta, VoidFilter voidFilter, BlockPlacer placer){
        if(voidFilter.check(x, y, z)) return;
        placer.setBlock(x, y, z, id, meta);
    }

    private void placeBrick(int x, int y, int z, VoidFilter voidFilter, BlockPlacer placer, boolean broken){
        if(voidFilter.check(x, y, z)) return;
        switch (random.nextInt(5)){
            case 1:
                placer.setBlock(x, y, z, 172, 0);
                break;
            case 2:
                placer.setBlock(x, y, z, 1, 1);
                break;

            default:
                placer.setBlock(x, y, z, (broken && random.nextFloat() > 0.7f)? Item.IRON_BARS : Item.BRICKS_BLOCK, 0);
        }
    }

    private void placeWoodFloor(int x, int y, int z, VoidFilter voidFilter, BlockPlacer placer){
        int id, damage = 0;
        switch (random.nextInt(7)){
            case 0:
                if(placer.getBiome(x, z) != ApocalypseGenerator.COMMON_BIOME){
                    id = 0;
                    break;
                }
                id = Item.PLANK;
                break;
            case 2:
            case 1:
                id = Item.SLAB;
                damage = 3;
                break;
            case 3:
            case 4:
                id = Item.GRAVEL;
                break;
            default:
                id = Item.COBBLE;
        }
        setBlock(x, y, z, id, damage, voidFilter, placer);
    }

    private void placeStoneFloor(int x, int y, int z, VoidFilter voidFilter, BlockPlacer placer){
        if(random.nextFloat() > 0.5f) setBlock(x, y, z, 1, 5, voidFilter, placer);
        else setBlock(x, y, z, 4, 0, voidFilter, placer);
    }

    private void placeStoneBricksLine(int x, int y, int z, VoidFilter voidFilter, BlockPlacer placer){
        for(int dy = 0; dy < 4; dy++)
            setBlock(x, y+dy, z, Item.STONE_BRICK, 0, voidFilter, placer);
    }

}
