package ru.dragonestia.apocalypse.commands

import cn.nukkit.Player
import cn.nukkit.command.Command
import cn.nukkit.command.CommandSender
import ru.dragonestia.apocalypse.player.PlayerManager

class PlayCommand(private val playerManager: PlayerManager) : Command("play", "Начать игру", "/play") {

    override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {
        if(sender is Player) execute(sender)
        return true
    }

    private fun execute(player: Player){
        if(!player.level.equals(player.server.defaultLevel)){
            player.sendMessage("§cДанную команду можно использовать только в лобби.")
            return
        }


    }

}