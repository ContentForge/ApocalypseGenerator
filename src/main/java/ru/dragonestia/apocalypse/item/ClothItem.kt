package ru.dragonestia.apocalypse.item

import cn.nukkit.item.Item
import ru.dragonestia.expo.item.AdvItem

class ClothItem @JvmOverloads constructor(meta: Int? = 0, count: Int = 1) : Item(ApocalypseID.CLOTH, 0, count, "Cloth"), AdvItem {

    override fun getRuName(): String = "Ткань"

    override fun getIcon(): String = "textures/items/cloth"

}