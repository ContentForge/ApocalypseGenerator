package ru.dragonestia.apocalypse.item

import cn.nukkit.item.Item

class ChipItem @JvmOverloads constructor(meta: Int? = 0, count: Int = 1) : Item(ApocalypseID.CHIP, 0, count, "Chip") {

    override fun getMaxStackSize() = 4

}