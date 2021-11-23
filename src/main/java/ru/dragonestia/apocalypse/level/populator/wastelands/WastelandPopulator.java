package ru.dragonestia.apocalypse.level.populator.wastelands;

import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.math.NukkitRandom;

public interface WastelandPopulator {

    boolean checkPlace(int chunkX, int chunkZ, FullChunk chunk);

    void populate(ChunkManager chunkManager, int chunkX, int chunkZ, NukkitRandom nukkitRandom, FullChunk chunk);

}
