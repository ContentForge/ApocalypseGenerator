package ru.dragonestia.apocalypse.item

import cn.nukkit.Player
import cn.nukkit.item.Item
import cn.nukkit.math.Vector3
import cn.nukkit.potion.Effect
import ru.dragonestia.apocalypse.Apocalypse
import ru.dragonestia.expo.item.AdvItem

class YodadulinItem @JvmOverloads constructor(meta: Int? = 0, count: Int = 1) : Item(ApocalypseID.YODADULIN, 0, count, "Yodadulin"), AdvItem {

    override fun onClickAir(player: Player, directionVector: Vector3): Boolean {
        val playerData = Apocalypse.getInstance().playerManager[player]

        playerData.reduceRadiationLevel(70000)
        playerData.playSound("dragonestia.rad.yodadulin", 0.3f, 1f)

        val effects = arrayOf(
                Effect.getEffect(Effect.WEAKNESS).setDuration(20 * 70).setVisible(false),
                Effect.getEffect(Effect.SLOWNESS).setDuration(20 * 30).setVisible(false),
                Effect.getEffect(Effect.HUNGER).setDuration(20 * 10).setVisible(false),
        )
        for(effect in effects) player.addEffect(effect)

        if (player.isSurvival) this.count--
        return true
    }

    override fun getRuName(): String = "Йодадулин"

    override fun getIcon(): String = "textures/items/rad/yodadulin"

}