package ru.dragonestia.apocalypse.item

import cn.nukkit.item.Item
import ru.dragonestia.expo.item.AdvItem

class CopperIngotItem @JvmOverloads constructor(meta: Int? = 0, count: Int = 1) : Item(ApocalypseID.COPPER_INGOT, 0, count, "Copper ingot"), AdvItem {

    override fun getRuName(): String = "Медный слиток"

    override fun getIcon(): String = "textures/items/ingot/copper"

}