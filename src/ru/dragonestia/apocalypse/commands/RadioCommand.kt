package ru.dragonestia.apocalypse.commands

import cn.nukkit.Player
import cn.nukkit.command.Command
import cn.nukkit.command.CommandSender
import cn.nukkit.item.Item
import ru.dragonestia.apocalypse.Apocalypse
import ru.dragonestia.apocalypse.player.PlayerData
import ru.nukkitx.forms.elements.CustomForm
import ru.nukkitx.forms.elements.ImageType
import ru.nukkitx.forms.elements.SimpleForm

class RadioCommand(private val main: Apocalypse) : Command("radio", "Радио", "/radio") {

    private val channelsRange = 1000..5000

    override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {
        if(sender is Player){
            sendMainForm(sender)
            return true
        }

        sender.sendMessage("Данную команду можно использовать только в игре!")
        return false
    }

    private fun sendMainForm(player: Player){
        val radio = main.playerManager.get(player)

        SimpleForm("Радио",
                "Заряд: §l§3${radio.radioCharge}/200EU§r\n" +
                        "Дальность передатчика: §l§3${radio.radioDistance}м§r\n" +
                        "Максимальная дальность передатчика: §l§3${radio.maxRadioDistance}м§r\n" +
                        "Эффективность передатчика: §l§g${(radio.radioQuality *100).toInt()} процентов§r\n" +
                        "Эффективная дальность передатчика: §l§e${(radio.radioDistance *radio.radioQuality).toInt()}м§f/§g${(radio.maxRadioDistance *radio.radioQuality).toInt()}м§r\n" +
                        "Выбранная волна: §l§2${radio.radioChannel/10.0}МГц§r"
        )
                .addButton("Настройки", ImageType.PATH, "textures/ui/dragonestia/radio")
                .addButton("Зарядить", ImageType.PATH, "textures/ui/dragonestia/refuel")
                .addButton("Улучшить дальность передатчика", ImageType.PATH, "textures/ui/dragonestia/signal_distance")
                .addButton("Улучшить эффективность передатчика", ImageType.PATH, "textures/ui/dragonestia/quality_upgrade")
                .send(player) { _, _, data ->
                    val inv = player.inventory
                    when(data){
                        0 -> sendSettingsForm(player, radio)
                        1 -> {
                            val item = Item.get(10001)
                            if(!inv.contains(item)){
                                player.sendMessage("§cУ вас нет батареек для подзарядки.")
                                return@send
                            }
                            if(radio.radioCharge >= 200){
                                player.sendMessage("§cРадио заряжено полностью.")
                                return@send
                            }
                            var newCharge = radio.radioCharge + 50
                            if(newCharge > 200) newCharge = 200

                            radio.radioCharge = newCharge.toShort()
                            inv.removeItem(item)

                            sendMainForm(player)
                        }
                        2 -> {
                            val item = Item.get(10003)
                            if(!inv.contains(item)){
                                player.sendMessage("§cУ вас нет медной проволоки для улучшения.")
                                return@send
                            }

                            radio.maxRadioDistance = (radio.maxRadioDistance + 1000).toShort()
                            inv.removeItem(item)

                            sendMainForm(player)
                        }
                        3 -> {
                            val item = Item.get(10002)
                            if(!inv.contains(item)){
                                player.sendMessage("§cУ вас нет микросхемы для улучшения.")
                                return@send
                            }

                            val newQuality = radio.radioQuality + 0.1f
                            if(newQuality >= 1f){
                                player.sendMessage("§cЭффективная дальность радио на максимальном уровне.")
                                return@send
                            }

                            radio.radioQuality = newQuality
                            inv.removeItem(item)

                            sendMainForm(player)
                        }
                        else -> return@send
                    }
                }
    }

    private fun sendSettingsForm(player: Player, playerData: PlayerData){
        val maxDistance = playerData.maxRadioDistance
        val minDistance = 100
        CustomForm("Настройка радио")
                .addLabel("Выберите волну вещания и прослушки радио. Допустимые значения: §b${channelsRange.first}-${channelsRange.last}§f(§2${channelsRange.first / 10.0}-${channelsRange.last / 10.0}Мгц§f).\n" +
                        "Стандартная частота - §31000§f(§2100.0МГц)")
                .addInput("Волна вещания", playerData.radioChannel.toString(), playerData.radioChannel.toString())
                .addLabel("Для того чтобы регулировать дальность вашего вещания измените поле ниже. Чем ниже дальность вещания по сравнению с максимальной дальностью, тем меньше идет расход заряда радиопередатчика.\n" +
                        "Допустимое значение: §b${minDistance}-${maxDistance}§f(метров).")
                .addInput("Дальность отправляемого сигнала", playerData.radioDistance.toString(), playerData.radioDistance.toString())
                .send(player) { _, _, data ->
                    if(data == null) return@send

                    val channel: Int
                    val distance: Int
                    try {
                        channel = data[1].toString().toInt()
                        distance = data[3].toString().toInt()
                    } catch (ex: NumberFormatException){
                        player.sendMessage("§cВведено неверное значение.")
                        return@send
                    }

                    if(channel !in channelsRange){
                        player.sendMessage("§cВведена неверная частота.")
                        return@send
                    }

                    if(distance !in minDistance..maxDistance){
                        player.sendMessage("§cВведена неверная дальность.")
                        return@send
                    }

                    playerData.radioChannel = channel.toShort()
                    playerData.radioDistance = distance.toShort()

                    player.sendMessage("§eВы успешно изменили настройки радио!")
                }
    }

}