package ru.dragonestia.apocalypse.item

import cn.nukkit.item.Item
import ru.dragonestia.expo.item.AdvItem

class TinClusterItem @JvmOverloads constructor(meta: Int? = 0, count: Int = 1) : Item(ApocalypseID.TIN_CLUSTER, 0, count, "Tin cluster"), AdvItem {

    override fun getRuName(): String = "Оловянная руда"

    override fun getIcon(): String = "textures/items/cluster/tin"

}