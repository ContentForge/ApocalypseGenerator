package ru.dragonestia.apocalypse.item

import cn.nukkit.item.Item

class BatteryItem @JvmOverloads constructor(meta: Int? = 0, count: Int = 1) : Item(ApocalypseID.BATTERY, 0, count, "Battery"){

    override fun getMaxStackSize() = 4

}