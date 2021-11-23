package ru.dragonestia.apocalypse.item

import cn.nukkit.item.Item
import ru.dragonestia.expo.item.AdvItem

class SulfurDustItem @JvmOverloads constructor(meta: Int? = 0, count: Int = 1) : Item(ApocalypseID.SULFUR_DUST, 0, count, "Sulfur dust"), AdvItem {

    override fun getRuName(): String = "Сера"

    override fun getIcon(): String = "textures/items/dust/sulfur"

}