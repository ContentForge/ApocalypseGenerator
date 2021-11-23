package ru.dragonestia.apocalypse.item

import cn.nukkit.item.Item
import ru.dragonestia.expo.item.AdvItem

class SulfurClusterItem @JvmOverloads constructor(meta: Int? = 0, count: Int = 1) : Item(ApocalypseID.SULFUR_CLUSTER, 0, count, "Sulfur cluster"), AdvItem {

    override fun getRuName(): String = "Серная руда"

    override fun getIcon(): String = "textures/items/cluster/sulfur"

}