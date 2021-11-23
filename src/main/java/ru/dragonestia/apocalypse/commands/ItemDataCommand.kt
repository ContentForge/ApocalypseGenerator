package ru.dragonestia.apocalypse.commands

import cn.nukkit.Player
import cn.nukkit.command.Command
import cn.nukkit.command.CommandSender
import cn.nukkit.item.Item

fun debugItem(item: Item) = "[name=${item.name}, id=${item.id}, damage=${item.damage}, count=${item.count}, nbt=${item.namedTag}]"

class ItemDataCommand() : Command("itemdata", "Просмотр информации о предмете", "/itemdata") {

    override fun execute(sender: CommandSender?, label: String?, args: Array<out String>): Boolean {
        if(sender !is Player) return false
        val player: Player = sender
        val item = player.inventory.itemInHand

        player.sendMessage(debugItem(item))
        if(args.isNotEmpty()){
            if(args[0] == "clear") player.inventory.clearAll()
        }
        return true
    }
}