package ru.dragonestia.apocalypse.level.populator.section

import cn.nukkit.item.Item
import cn.nukkit.level.ChunkManager
import cn.nukkit.level.format.FullChunk
import ru.dragonestia.apocalypse.level.populator.section.filter.VoidFilter
import java.util.*

class VeryBrokenHouse(random: Random, chunkManager: ChunkManager) : HouseSection(random, chunkManager)  {

    override fun generate(placer: BlockPlacer, chunk: FullChunk, broken: Boolean) {
        val h = chunk.getHighestBlockAt(5, 5) - 10 - random.nextInt(5)
        var yOffset = h

        val levels = 4 + random.nextInt(7)
        val void = VoidFilter(h, h + levels*4, random.nextInt(2), random)

        for(level in 4..levels){
            for(y in 0..3){
                for(x in 0..14){
                    for(z in 0..14){
                        if(void.check(x, yOffset, z)) continue

                        if(x in 1..13 && z in 1..13) continue

                        if(y == 0){
                            placeBorder(x, yOffset, z, placer, broken)
                            continue
                        }

                        if(x % 2 == 0 && z % 2 == 0){
                            placeWall(x, yOffset, z, placer, broken)
                            continue
                        }
                    }
                }
                yOffset++
            }
        }

        for(y in 0..(4 + random.nextInt(9))){
            for(x in 0..14){
                for(z in 0..14){
                    if(void.check(x, yOffset, z)) continue

                    if(x in 1..13 && z in 1..13) continue

                    if(x % 2 == 0 && z % 2 == 0 && chunk.getBlockId(x, yOffset-1, z) != 0 && random.nextFloat() < 0.7){
                        placeWall(x, yOffset, z, placer, broken)
                        continue
                    }
                }
            }
            yOffset++
        }
    }

    private fun placeBorder(x: Int, y: Int, z: Int, placer: BlockPlacer, broken: Boolean){
        val block = when(random.nextInt(4 + (if(broken) 1 else 0))){
            0 -> Pair(Item.STONE_BRICKS, 0)
            1 -> Pair(Item.STONE_BRICKS, 2)
            2 -> Pair(Item.COBBLESTONE, 0)
            else -> Pair(Item.IRON_BARS, 0)
        }
        placer.setBlock(x, y, z, block.first, block.second)
    }

    private fun placeWall(x: Int, y: Int, z: Int, placer: BlockPlacer, broken: Boolean): Int {
        val block = when(random.nextInt(5 + (if(broken) 1 else 0))){
            0 -> Pair(Item.STONE_BRICKS, 0)
            1 -> Pair(Item.STONE_BRICKS, 2)
            2 -> Pair(Item.COBBLESTONE, 0)
            in(3..4) -> Pair(Item.STONE_WALL, 0)
            else -> Pair(0, 0)
        }
        return placer.setBlock(x, y, z, block.first, block.second)
    }

}