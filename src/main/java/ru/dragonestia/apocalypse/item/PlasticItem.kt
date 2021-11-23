package ru.dragonestia.apocalypse.item

import cn.nukkit.item.Item
import ru.dragonestia.expo.item.AdvItem

class PlasticItem @JvmOverloads constructor(meta: Int? = 0, count: Int = 1) : Item(ApocalypseID.PLASTIC, 0, count, "Plastic"), AdvItem {

    override fun getRuName(): String = "Пластик"

    override fun getIcon(): String = "textures/items/plastic"

}