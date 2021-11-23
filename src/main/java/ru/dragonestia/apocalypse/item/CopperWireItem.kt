package ru.dragonestia.apocalypse.item

import cn.nukkit.item.Item
import ru.dragonestia.expo.item.AdvItem

class CopperWireItem @JvmOverloads constructor(meta: Int? = 0, count: Int = 1) : Item(ApocalypseID.COPPER_WIRE, 0, count, "Copper Wire"), AdvItem {

    override fun getMaxStackSize() = 4

    override fun getRuName(): String = "Медная катушка"

    override fun getIcon(): String = "textures/items/upgrade/copper_wire"

}
