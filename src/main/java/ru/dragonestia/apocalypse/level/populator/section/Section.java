package ru.dragonestia.apocalypse.level.populator.section;

import cn.nukkit.level.ChunkManager;

import java.util.Random;

public abstract class Section {

    protected final Random random;
    protected final ChunkManager chunkManager;

    public Section(Random random, ChunkManager chunkManager){
        this.random = random;
        this.chunkManager = chunkManager;
    }

}
