package ru.dragonestia.apocalypse.item

import cn.nukkit.item.Item
import ru.dragonestia.expo.item.AdvItem

class ScrapItem @JvmOverloads constructor(meta: Int? = 0, count: Int = 1) : Item(ApocalypseID.SCRAP, 0, count, "Scrap"), AdvItem {

    override fun getRuName(): String = "Металлолом"

    override fun getIcon(): String = "textures/items/scrap"

}