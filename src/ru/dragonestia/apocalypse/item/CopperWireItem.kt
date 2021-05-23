package ru.dragonestia.apocalypse.item

import cn.nukkit.item.Item

class CopperWireItem @JvmOverloads constructor(meta: Int? = 0, count: Int = 1) : Item(ApocalypseID.COPPER_WIRE, 0, count, "Copper Wire") {

    override fun getMaxStackSize() = 4

}
