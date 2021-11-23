package ru.dragonestia.apocalypse.item

import cn.nukkit.Player
import cn.nukkit.item.Item
import cn.nukkit.math.Vector3
import cn.nukkit.potion.Effect
import ru.dragonestia.apocalypse.Apocalypse
import ru.dragonestia.expo.item.AdvItem

class ZelenkaItem @JvmOverloads constructor(meta: Int? = 0, count: Int = 1) : Item(ApocalypseID.ZELENKA, 0, count, "Zelenka"), AdvItem {

    override fun onClickAir(player: Player, directionVector: Vector3): Boolean {
        val playerData = Apocalypse.getInstance().playerManager[player]

        playerData.reduceRadiationLevel(500000)
        playerData.playSound("dragonestia.rad.zelenka", 0.3f, 1f)

        player.addEffect(Effect.getEffect(Effect.NIGHT_VISION).setDuration(20 * 5).setVisible(false))

        if (player.isSurvival) this.count--
        return true
    }

    override fun getRuName(): String = "Радиопротектор Зеленка"

    override fun getIcon(): String = "textures/items/rad/zelenka"

}