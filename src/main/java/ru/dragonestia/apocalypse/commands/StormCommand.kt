package ru.dragonestia.apocalypse.commands

import cn.nukkit.Player
import cn.nukkit.command.Command
import cn.nukkit.command.CommandSender
import ru.dragonestia.apocalypse.storms.GlobalEvents

class StormCommand(private val globalEvents: GlobalEvents) : Command("storm", "Управление катаклизмами", "/storm") {

    override fun execute(sender: CommandSender?, label: String?, args: Array<out String>?): Boolean {
        if(sender !is Player) return false
        if(!sender.isOp) return false

        if(args!!.isEmpty()){
            sender.sendMessage("/storm set <id> - Установить шторм")
            sender.sendMessage("/storm settime <time> - Установить время")
            return true
        }

        when(args[0].toLowerCase()){
            "set" -> {
                if(args.size < 2) return false
                try{
                    val num = args[1].toInt()
                    if(num !in 0..(globalEvents.events.size)) return false
                    globalEvents.nextEvent = globalEvents.events[num]
                    sender.sendMessage("Установлено")
                }catch (ex: NumberFormatException){
                    sender.sendMessage("Неверное значение.")
                }
            }
            "settime" -> {
                if(args.size < 2) return false
                try{
                    val num = args[1].toInt()
                    globalEvents.time = num
                    sender.sendMessage("Успех!")
                }catch (ex: NumberFormatException){
                    sender.sendMessage("Неверное значение.")
                }
            }
            else -> sender.sendMessage("Неизвестный аргумент команды.")
        }

        return true
    }
}