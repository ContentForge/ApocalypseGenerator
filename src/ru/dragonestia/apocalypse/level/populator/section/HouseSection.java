package ru.dragonestia.apocalypse.level.populator.section;

import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.format.FullChunk;
import ru.dragonestia.apocalypse.level.ApocalypseGenerator;
import ru.dragonestia.apocalypse.level.populator.section.filter.Rotator;

import java.util.Random;

public abstract class HouseSection extends Section {

    public HouseSection(Random random, ChunkManager chunkManager) {
        super(random, chunkManager);
    }

    public final void generate(FullChunk chunk) {
        BlockPlacer blockPlacer = new Rotator(chunk, random.nextInt(4));

        generate(blockPlacer, chunk, chunk.getBiomeId(5, 5) != ApocalypseGenerator.COMMON_BIOME);
    }

    protected abstract void generate(BlockPlacer placer, FullChunk chunk, boolean broken);


}
