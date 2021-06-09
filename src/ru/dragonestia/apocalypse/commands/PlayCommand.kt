package ru.dragonestia.apocalypse.commands

import cn.nukkit.Player
import cn.nukkit.command.Command
import cn.nukkit.command.CommandSender
import cn.nukkit.item.Item
import cn.nukkit.level.Position
import cn.nukkit.scheduler.PluginTask
import ru.dragonestia.apocalypse.Apocalypse
import ru.nukkitx.forms.elements.SimpleForm

class PlayCommand(private val main: Apocalypse) : Command("play", "Начать игру", "/play") {

    private val random = main.playerManager.random

    override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {
        if(sender is Player) execute(sender)
        return true
    }

    private fun execute(player: Player){
        if(!player.level.equals(player.server.defaultLevel)){
            player.sendMessage("§cДанную команду можно использовать только в лобби.")
            return
        }

        SimpleForm("Играть", "Здесь будет позже написан какой-то текст...")
                .addButton("Случайное появление")
                .send(player) { _, _, data ->
                    when(data){
                        0 -> spawn(player)
                    }
                }
    }

    private fun spawn(player: Player){
        val x = random.nextInt(1000)
        val z = random.nextInt(1000)
        val pos = Position(x + 0.5, 35.0, z + 0.5, main.gameLevel)
        player.teleport(pos)
        player.setSpawn(pos)
        player.isImmobile = true
        player.noDamageTicks = 60
        player.isNameTagAlwaysVisible = false
        player.extinguish()
        player.setGamemode(0)
        player.sendMessage("§eТелепортирование...")

        main.server.scheduler.scheduleDelayedTask(PlayerSpawnTask(main, player), 60)
    }

}

class PlayerSpawnTask(owner: Apocalypse, val player: Player) : PluginTask<Apocalypse>(owner) {

    override fun onRun(tick: Int) {
        player.isImmobile = false
        player.extinguish()

        player.level.setBlockAt(player.floorX, player.floorY - 1, player.floorZ, Item.COBBLESTONE)
        player.level.setBlockAt(player.floorX, player.floorY, player.floorZ, 0)
        player.level.setBlockAt(player.floorX, player.floorY + 1, player.floorZ, 0)

        val inv = player.inventory
        inv.addItem(Item.get(274, 0, 3))
        inv.addItem(Item.get(282, 0, 4))
    }

}