package ru.dragonestia.apocalypse.item

import cn.nukkit.Player
import cn.nukkit.item.Item
import cn.nukkit.math.Vector3
import cn.nukkit.potion.Effect
import ru.dragonestia.apocalypse.Apocalypse

class TabletItem @JvmOverloads constructor(meta: Int? = 0, count: Int = 1) : Item(ApocalypseID.TABLET, 0, count, "Zelenka"){

    override fun onClickAir(player: Player, directionVector: Vector3): Boolean {
        val playerData = Apocalypse.getInstance().playerManager[player]

        playerData.reduceRadiationLevel(15000)
        playerData.playSound("dragonestia.rad.tablet", 0.3f, 1f)

        val effects = arrayOf(
                Effect.getEffect(Effect.WEAKNESS).setDuration(20 * 120).setVisible(false),
                Effect.getEffect(Effect.SLOWNESS).setDuration(20 * 40).setVisible(false),
                Effect.getEffect(Effect.HUNGER).setDuration(20 * 20).setVisible(false),
                Effect.getEffect(Effect.FATIGUE).setDuration(20 * 70).setVisible(false),
                Effect.getEffect(Effect.POISON).setDuration(20 * 3).setVisible(false),
        )
        for(effect in effects) player.addEffect(effect)

        if (player.isSurvival) this.count--
        return true
    }

}