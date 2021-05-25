package ru.dragonestia.apocalypse.level.populator;

import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.level.generator.populator.type.Populator;
import cn.nukkit.math.NukkitRandom;
import ru.dragonestia.apocalypse.level.populator.section.HouseSection;
import ru.dragonestia.apocalypse.level.populator.section.RedBrickHouse;
import ru.dragonestia.apocalypse.level.populator.section.RoadSection;

import java.util.Random;

public class SectionPopulator extends Populator {

    private final Random random;
    private final ChunkManager chunkManager;
    private final HouseSection[] houses;

    public SectionPopulator(Random random, ChunkManager chunkManager){
        this.random = random;
        this.chunkManager = chunkManager;

        houses = new HouseSection[]{
            new RedBrickHouse(random, chunkManager)
        };
    }

    @Override
    public void populate(ChunkManager chunkManager, int chunkX, int chunkZ, NukkitRandom nukkitRandom, FullChunk chunk) {
        onGround:
        if(!generateRoad(chunkX, chunkZ, chunk) && random.nextFloat() < 0.3f){
            if(houses.length == 0) break onGround;

            houses[random.nextInt(houses.length)].generate(chunk);
        }

        //TODO: Генерация канализации
    }

    private boolean generateRoad(int chunkX, int chunkZ , FullChunk chunk){
        int sectionX = chunkX / 3, sectionZ = chunkZ / 3;
        int sX = chunkX % 3, sZ = chunkZ % 3;
        int bX = sectionX % 3, bZ = sectionZ % 2;

        if((sX == 2 || sX == 0 || (sX == 1 && sZ == 1 && bX == 1)) && sZ == 1){
            new RoadSection(random, chunkManager).generate(chunk, RoadSection.Type.FORWARD);
            return true;
        }

        if((bX == 0 && sX == 1 && ((bZ == 0 && sZ == 0) || (bZ == 1 && sZ == 2))) || (bX == 2 && sX == 1 && ((bZ == 0 && sZ == 2) || (bZ == 1 && sZ == 0)))){
            new RoadSection(random, chunkManager).generate(chunk, RoadSection.Type.HORIZONTAL);
            return true;
        }

        if((bX == 0 && bZ == 1 && sX == 1 && sZ == 1) || (bZ == 0 && bX == 2 && sX == 1 && sZ == 1)){
            new RoadSection(random, chunkManager).generate(chunk, RoadSection.Type.RIGHT);
            return true;
        }

        if((bZ == 0 && bX == 0 && sX == 1 && sZ == 1) || (bZ == 1 && bX == 2 && sX == 1 && sZ == 1)){
            new RoadSection(random, chunkManager).generate(chunk, RoadSection.Type.LEFT);
            return true;
        }

        return false;
    }

}
