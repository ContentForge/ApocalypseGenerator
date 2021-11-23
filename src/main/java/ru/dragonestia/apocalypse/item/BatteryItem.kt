package ru.dragonestia.apocalypse.item

import cn.nukkit.item.Item
import ru.dragonestia.expo.item.AdvItem

class BatteryItem @JvmOverloads constructor(meta: Int? = 0, count: Int = 1) : Item(ApocalypseID.BATTERY, 0, count, "Battery"), AdvItem {

    override fun getMaxStackSize() = 4

    override fun getRuName(): String = "Батарейка"

    override fun getIcon(): String = "textures/items/upgrade/battery"

}