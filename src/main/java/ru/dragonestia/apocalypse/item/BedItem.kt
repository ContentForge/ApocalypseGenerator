package ru.dragonestia.apocalypse.item

import cn.nukkit.Player
import cn.nukkit.item.Item
import cn.nukkit.math.Vector3
import ru.contentforge.formconstructor.form.SimpleForm
import ru.dragonestia.apocalypse.Apocalypse
import ru.dragonestia.expo.item.AdvItem

class BedItem @JvmOverloads constructor(meta: Int? = 0, count: Int = 1) : Item(ApocalypseID.BED, 0, count, "Bed"), AdvItem {

    override fun getMaxStackSize(): Int {
        return 1
    }

    override fun onClickAir(player: Player, directionVector: Vector3): Boolean {
        sendMainForm(player)
        return true
    }

    private fun sendMainForm(player: Player){
        SimpleForm("Спальный мешок", "Спальный мешок является точкой возрождения и он может размещен только один. Если у вас уже есть спальный мешок и вы поставите новый, то вы потеряете старый.")
            .addButton("Установить точку возрождения") {_, _ ->
                Apocalypse.getInstance().playerManager.get(player).placeBed(player)
                count--
            }.send(player)
    }

    override fun getRuName(): String = "Спальный мешок"

    override fun getIcon(): String = "textures/items/d_bed"

}