package ru.dragonestia.apocalypse.level.populator.section;

import cn.nukkit.level.ChunkManager;
import cn.nukkit.level.format.FullChunk;
import ru.dragonestia.apocalypse.level.ApocalypseGenerator;

import java.util.Random;

public abstract class HouseSection extends Section {

    public HouseSection(Random random, ChunkManager chunkManager) {
        super(random, chunkManager);
    }

    public final void generate(FullChunk chunk) {
        int r = random.nextInt(4);

        BlockPlacer blockPlacer = (x, y, z, id, meta) -> {
            int lastId;
            switch (r % 4) {
                case 0:
                    lastId = chunk.getBlockId(x, y, z);
                    chunk.setBlock(x, y, z, id, meta);
                    break;

                case 1:
                    lastId = chunk.getBlockId(15 - z, y, x);
                    chunk.setBlock(15 - z, y, x, id, meta);
                    break;

                case 2:
                    lastId = chunk.getBlockId(15 - x, y, 15 - z);
                    chunk.setBlock(15 - x, y, 15 - z, id, meta);
                    break;

                case 3:
                    lastId = chunk.getBlockId(z, y, 15 - x);
                    chunk.setBlock(z, y, 15 - x, id, meta);
                    break;
                default:
                    lastId = -1;
            }
            return lastId;
        };

        generate(blockPlacer, chunk, chunk.getBiomeId(5, 5) != ApocalypseGenerator.COMMON_BIOME);
    }

    protected abstract void generate(BlockPlacer placer, FullChunk chunk, boolean broken);


}
