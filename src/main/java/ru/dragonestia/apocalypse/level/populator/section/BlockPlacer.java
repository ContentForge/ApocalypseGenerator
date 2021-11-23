package ru.dragonestia.apocalypse.level.populator.section;

public interface BlockPlacer {

    int setBlock(int x, int y, int z, int id, int meta);

    int setBlock(int x, int y, int z, int id);

    int getBlock(int x, int y, int z);

    int getBiome(int x, int z);

}
