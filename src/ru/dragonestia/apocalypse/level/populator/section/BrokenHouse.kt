package ru.dragonestia.apocalypse.level.populator.section

import cn.nukkit.item.Item
import cn.nukkit.level.ChunkManager
import cn.nukkit.level.format.FullChunk
import java.util.*

class BrokenHouse(random: Random, chunkManager: ChunkManager) : HouseSection(random, chunkManager) {

    override fun generate(placer: BlockPlacer, chunk: FullChunk, broken: Boolean) {
        val h = chunk.getHighestBlockAt(5, 5) - 2 - random.nextInt(3)

        val walls = Array(16) { BooleanArray(16) }
        if(random.nextFloat() > 0.5f){ walls[0][15] = true } else { walls[1][14] = true }
        if(random.nextFloat() > 0.5f){ walls[0][0] = true } else { walls[1][1] = true }
        if(random.nextFloat() > 0.5f){ walls[15][15] = true } else { walls[14][14] = true }
        if(random.nextFloat() > 0.5f){ walls[15][0] = true } else { walls[14][1] = true }

        for(x in 3..11){
            for(z in 3..11){
                if(x in 4..10 && z in 4..10) continue

                if(random.nextFloat() < 0.6f) walls[x][z] = true
            }
        }
        val void = if(broken && random.nextFloat() < 0.6f) random.nextInt(40) + 60 else -1

        var tempY = h - 1
        while(true) {
            var next = false
            for (x in 0..15)
                for (z in 0..15) {
                    if(!walls[x][z]) continue

                    if(placeWall(x, tempY, z, placer, broken, void) == 0) next = true
                }
            tempY--
            if(!next) break
        }

        for(l in 0..(2 + random.nextInt(15))){
            generateLevel(placer, h + l * 4, chunk, broken, walls, void)
        }
    }

    private fun generateLevel(placer: BlockPlacer, dy: Int, chunk: FullChunk, broken: Boolean, walls: Array<BooleanArray>, void: Int) {
        for(y in 0..3){
            for(x in 0..15){
                for(z in 0..15){
                    if(x in 1..14 && z in 1..14 && y == 0){
                        placeFloor(x, y + dy, z, placer, broken, void)
                        continue
                    }

                    if(y == 0){
                        placeBorder(x, y + dy, z, placer, broken, void)
                        continue
                    }

                    if(walls[x][z]) placeWall(x, y + dy, z, placer, broken, void)
                }
            }
        }
    }

    private fun placeFloor(x: Int, y: Int, z: Int, placer: BlockPlacer, broken: Boolean, void: Int){
        if(void != -1 && inVoid(x, y, z, void)) return
        val block = when(random.nextInt(2 + (if(broken) 1 else 0))){
            0 -> Pair(1, 0)
            1 -> Pair(Item.SLAB, 3)
            else -> Pair(0, 0)
        }
        placer.setBlock(x, y, z, block.first, block.second)
    }

    private fun placeBorder(x: Int, y: Int, z: Int, placer: BlockPlacer, broken: Boolean, void: Int){
        if(void != -1 && inVoid(x, y, z, void)) return
        val block = when(random.nextInt(4 + (if(broken) 1 else 0))){
            0 -> Pair(Item.STONE_BRICKS, 0)
            1 -> Pair(Item.STONE_BRICKS, 2)
            2 -> Pair(Item.COBBLESTONE, 0)
            else -> Pair(Item.IRON_BARS, 0)
        }
        placer.setBlock(x, y, z, block.first, block.second)
    }

    private fun placeWall(x: Int, y: Int, z: Int, placer: BlockPlacer, broken: Boolean, void: Int): Int {
        if(void != -1 && inVoid(x, y, z, void)) return -1
        val block = when(random.nextInt(5 + (if(broken) 1 else 0))){
            0 -> Pair(Item.STONE_BRICKS, 0)
            1 -> Pair(Item.STONE_BRICKS, 2)
            2 -> Pair(Item.COBBLESTONE, 0)
            in(3..4) -> Pair(Item.STONE_WALL, 0)
            else -> Pair(0, 0)
        }
        return placer.setBlock(x, y, z, block.first, block.second)
    }

    private fun inVoid(x: Int, y: Int, z: Int, dy: Int): Boolean {
        val dX = (x - 15) / (if(dy > 70) 3 else 2)
        val dY = (y - dy) / (if(dy > 70) 6 else 5)
        val dZ = z - 15 / (if(dy > 70) 2 else 1)

        return dX*dX + dY*dY + dZ*dZ <= 10
    }

}