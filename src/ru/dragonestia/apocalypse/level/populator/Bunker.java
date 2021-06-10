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
import ru.dragonestia.apocalypse.util.Pair;

import java.util.ArrayList;
import java.util.Random;

public class Bunker extends Populator {

    private final Random random;
    private final SimplexD cityNoise;
    private final Pair<Integer, Integer>[] chests = new Pair[]{
            new Pair(9, 4),
            new Pair(11, 10),
            new Pair(11, 1),
            new Pair(6, 12)
    };

    public Bunker(Random random, SimplexD cityNoise){
        this.random = random;
        this.cityNoise = cityNoise;
    }

    @Override
    public void populate(ChunkManager chunkManager, int chunkX, int chunkZ, NukkitRandom nukkitRandom, FullChunk chunk) {
        if(chunk.getBiomeId(7, 7) != ApocalypseGenerator.COMMON_BIOME || random.nextFloat() > 0.015f) return;
        int h = random.nextInt(5) + 20;

        for(int x = 0; x < 16; x++)
            for(int z = 0; z < 16; z++)
                for(int y = 0; y < 5; y++){
                    if(y == 0){ //Пол
                        if(random.nextFloat() > 0.6) chunk.setBlock(x, y + h, z, 1, 5);
                        else chunk.setBlock(x, y + h, z, 1, 0);
                        continue;
                    }
                    if(y == 4){ //Потолок
                        if(random.nextFloat() > 0.3) chunk.setBlock(x, y + h, z, 236, 8);
                        else chunk.setBlock(x, y + h, z, 13, 0);
                        continue;
                    }
                    if(x > 0 && x < 15 && z > 0 && z < 15){ //Пустота
                        chunk.setBlockId(x, y + h, z, 0);
                        continue;
                    }
                    if(Math.abs(x - 7.5) > 6 && Math.abs(z - 7.5) > 6){ //Опоры
                        chunk.setBlockId(x, y + h, z, Item.STONE_BRICKS);
                        continue;
                    }
                    if(y == 1){ //Стены
                        chunk.setBlock(x, y + h, z, 236, 13);
                        continue;
                    }
                    chunk.setBlock(x, y + h, z, 236, 9);
                }

        //Расстановка стен
        for(int z = 1; z < 15; z++){
            if(z == 2 || z == 3) continue;
            placeWall(5, h, z, chunk);
        }
        for(int z = 1; z < 11; z++){
            if(z == 7 || z == 8) continue;
            placeWall(10, h, z, chunk);
        }
        for(int x = 6; x < 15; x++){
            if(x == 7 || x == 8) continue;
            placeWall(x, h, 11, chunk);
        }
        for(int x = 6; x < 10; x++){
            if(x == 7 || x == 8) continue;
            placeWall(x, h, 5, chunk);
        }

        //Кирпичи
        for(int x = 6; x < 10; x++)
            for(int y = 1; y < 4; y++)
                chunk.setBlockId(x, y + h, 0, Item.BRICKS_BLOCK);

        //Верстак
        chunk.setBlockId(11, h+1, 5, Item.CRAFTING_TABLE);

        //Динамит
        for(int x = 10; x < 15; x++)
            for(int z = 12; z < 15; z++){
                if(random.nextFloat() > 0.3f) continue;

                chunk.setBlockId(x, h+1, z, Item.TNT);
            }

        //Колонны
        for(int z = 6; z < 11; z++){
            for(int y = 1; y < 4; y++) chunk.setBlockId(6, h+y, z, Item.STONE_WALL);
        }

        //Коробки
        for(int x = 1; x < 5; x++){
            for(int z = 5; z < 15; z++){
                if(random.nextFloat() > 0.15f) continue;
                chunk.setBlockId(x, h+1, z, Item.JUKEBOX);
            }
        }

        //Сундуки
        for(Pair<Integer, Integer> chest: chests){
            ArrayList<Item> items = new ArrayList<>();

            if(random.nextFloat() < 0.4) items.add(Item.get(ApocalypseID.ZELENKA));
            if(random.nextFloat() < 0.35) items.add(Item.get(Item.CHAIN_HELMET).setCustomName("§r§d§lЗащитный костюм"));
            if(random.nextFloat() < 0.35) items.add(Item.get(Item.CHAIN_CHESTPLATE).setCustomName("§r§d§lЗащитный костюм"));
            if(random.nextFloat() < 0.35) items.add(Item.get(Item.CHAIN_LEGGINGS).setCustomName("§r§d§lЗащитный костюм"));
            if(random.nextFloat() < 0.35) items.add(Item.get(Item.CHAIN_BOOTS).setCustomName("§r§d§lЗащитный костюм"));
            if(random.nextFloat() < 0.35) items.add(Item.get(373, 30).setCustomName("§r§6§lРенерган-Ф"));
            if(random.nextFloat() < 0.05) items.add(Item.get(373, 12).setCustomName("§r§6§lКакая-то жЫжа"));
            if(random.nextFloat() < 0.35) items.add(Item.get(Item.TNT, 0, 10 + random.nextInt(30)));
            if(random.nextFloat() < 0.35) items.add(Item.get(ApocalypseID.PLASTIC, 0, 3 + random.nextInt(15)));
            if(random.nextFloat() < 0.35) items.add(Item.get(ApocalypseID.CHIP, 0, 1 + random.nextInt(4)));

            chunk.setBlockId(chest.first, h+1, chest.second, Item.CHEST);
            ApocalypseGenerator.fillChest(new Vector3(chest.first, h+1, chest.second), chunk, items);
        }
    }

    private void placeWall(int x, int h, int z, FullChunk chunk){
        chunk.setBlock(x, h+1, z, 236, 13);
        chunk.setBlock(x, h+2, z, 236, 9);
        chunk.setBlock(x, h+3, z, 236, 9);
    }

}
