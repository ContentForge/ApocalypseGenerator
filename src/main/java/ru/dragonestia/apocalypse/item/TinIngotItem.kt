package ru.dragonestia.apocalypse.item

import cn.nukkit.item.Item
import ru.dragonestia.expo.item.AdvItem

class TinIngotItem @JvmOverloads constructor(meta: Int? = 0, count: Int = 1) : Item(ApocalypseID.TIN_INGOT, 0, count, "Tin ingot"), AdvItem {

    override fun getRuName(): String = "Оловянный слиток"

    override fun getIcon(): String = "textures/items/ingot/tin"

}