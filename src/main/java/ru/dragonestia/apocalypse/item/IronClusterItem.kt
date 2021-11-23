package ru.dragonestia.apocalypse.item

import cn.nukkit.item.Item
import ru.dragonestia.expo.item.AdvItem

class IronClusterItem @JvmOverloads constructor(meta: Int? = 0, count: Int = 1) : Item(ApocalypseID.IRON_CLUSTER, 0, count, "Iron cluster"), AdvItem {

    override fun getRuName(): String = "Железная руда"

    override fun getIcon(): String = "textures/items/cluster/iron"

}