package ru.dragonestia.apocalypse.commands

import cn.nukkit.Player
import cn.nukkit.command.Command
import cn.nukkit.command.CommandSender
import ru.dragonestia.apocalypse.Apocalypse

class SendRadioCommand(private val main: Apocalypse) : Command("r", "Отправить сообщение по радио", "/r <Сообщение>") {

    override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {
        if(sender !is Player) return false
        if(args.isEmpty()) return false

        val message = args.joinToString(" ")
        main.chatManager.sendMessage(main.chatManager.get(sender), message.replace(Regex("§."), ""))

        return true
    }

}