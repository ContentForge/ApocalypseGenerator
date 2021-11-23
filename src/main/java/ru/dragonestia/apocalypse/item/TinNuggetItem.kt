package ru.dragonestia.apocalypse.item

import cn.nukkit.item.Item
import ru.dragonestia.expo.item.AdvItem

class TinNuggetItem @JvmOverloads constructor(meta: Int? = 0, count: Int = 1) : Item(ApocalypseID.TIN_NUGGET, 0, count, "Tin nugget"), AdvItem {

    override fun getRuName(): String = "Оловянный самородок"

    override fun getIcon(): String = "textures/items/nugget/tin"

}