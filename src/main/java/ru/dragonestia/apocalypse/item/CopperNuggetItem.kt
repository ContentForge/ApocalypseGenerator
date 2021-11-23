package ru.dragonestia.apocalypse.item

import cn.nukkit.item.Item
import ru.dragonestia.expo.item.AdvItem

class CopperNuggetItem @JvmOverloads constructor(meta: Int? = 0, count: Int = 1) : Item(ApocalypseID.COPPER_NUGGET, 0, count, "Copper nugget"), AdvItem {

    override fun getRuName(): String = "Медный самородок"

    override fun getIcon(): String = "textures/items/nugget/copper"

}