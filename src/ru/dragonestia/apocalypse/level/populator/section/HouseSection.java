package ru.dragonestia.apocalypse.level.populator.section;

import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.format.FullChunk;

import java.util.Random;

public abstract class HouseSection extends Section {

    public HouseSection(Random random, ChunkManager chunkManager) {
        super(random, chunkManager);
    }

    public final void generate(FullChunk chunk) {
        int r = random.nextInt(4);

        BlockPlacer blockPlacer = (x, y, z, id, meta) -> {
            switch (r % 4) {
                case 0:
                    chunk.setBlock(x, y, z, id, meta);
                    return;

                case 1:
                    chunk.setBlock(15 - z, y, x, id, meta);
                    return;

                case 2:
                    chunk.setBlock(15 - x, y, 15 - z, id, meta);
                    return;

                case 3:
                    chunk.setBlock(z, y, 15 - x, id, meta);
                    break;
            }
        };

        generate(blockPlacer, chunk);
    }

    protected abstract void generate(BlockPlacer placer, FullChunk chunk);


}
