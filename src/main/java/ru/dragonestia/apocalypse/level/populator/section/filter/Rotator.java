package ru.dragonestia.apocalypse.level.populator.section.filter;

import cn.nukkit.level.format.FullChunk;
import ru.dragonestia.apocalypse.level.populator.section.BlockPlacer;

public class Rotator implements BlockPlacer {

    private final FullChunk chunk;
    private final int rotate;

    public Rotator(FullChunk chunk, int rotate){
        this.chunk = chunk;
        this.rotate = rotate;
    }

    @Override
    public int setBlock(int x, int y, int z, int id, int meta) {
        int lastId;
        switch (rotate % 4) {
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
    }

    @Override
    public int setBlock(int x, int y, int z, int id) {
        return setBlock(x, y, z, id, 0);
    }

    @Override
    public int getBlock(int x, int y, int z) {
        switch (rotate % 4) {
            case 0:
                return chunk.getBlockId(x, y, z);
            case 1:
                return chunk.getBlockId(15 - z, y, x);

            case 2:
                return chunk.getBlockId(15 - x, y, 15 - z);

            case 3:
                return chunk.getBlockId(z, y, 15 - x);
        }
        return 0;
    }

    @Override
    public int getBiome(int x, int z) {
        switch (rotate % 4) {
            case 0:
                return chunk.getBiomeId(x, z);
            case 1:
                return chunk.getBiomeId(15 - z, x);

            case 2:
                return chunk.getBiomeId(15 - x, 15 - z);

            case 3:
                return chunk.getBiomeId(z, 15 - x);
        }
        return 0;
    }

}
