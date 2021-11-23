package ru.dragonestia.apocalypse.item

import cn.nukkit.item.Item
import ru.dragonestia.expo.item.AdvItem

class CopperClusterItem @JvmOverloads constructor(meta: Int? = 0, count: Int = 1) : Item(ApocalypseID.COPPER_CLUSTER, 0, count, "Copper cluster"), AdvItem {

    override fun getRuName(): String = "Медная руда"

    override fun getIcon(): String = "textures/items/cluster/copper"

}