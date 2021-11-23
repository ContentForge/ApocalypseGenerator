package ru.dragonestia.apocalypse.item

import cn.nukkit.item.Item
import ru.dragonestia.expo.item.AdvItem

class ChipItem @JvmOverloads constructor(meta: Int? = 0, count: Int = 1) : Item(ApocalypseID.CHIP, 0, count, "Chip"), AdvItem {

    override fun getMaxStackSize() = 4

    override fun getRuName(): String = "Микросхема"

    override fun getIcon(): String = "textures/items/upgrade/chip"

}