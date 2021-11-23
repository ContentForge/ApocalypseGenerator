package ru.dragonestia.apocalypse.commands

import cn.nukkit.Player
import cn.nukkit.command.Command
import cn.nukkit.command.CommandSender
import cn.nukkit.item.Item
import cn.nukkit.item.ItemID
import ru.contentforge.formconstructor.form.CustomForm
import ru.contentforge.formconstructor.form.SimpleForm
import ru.contentforge.formconstructor.form.element.*
import ru.dragonestia.apocalypse.Apocalypse
import ru.dragonestia.apocalypse.item.ApocalypseID
import ru.dragonestia.apocalypse.player.submodules.Radio
import ru.nukkitx.ruitems.RuItems

class RadioCommand(private val main: Apocalypse) : Command("radio", "Радио", "/radio") {

    override fun execute(sender: CommandSender, label: String, args: Array<out String>): Boolean {
        if(sender is Player){
            sendMainForm(sender)
            return true
        }

        sender.sendMessage("Данную команду можно использовать только в игре!")
        return false
    }

    private fun sendMainForm(player: Player){
        val data = main.playerManager.get(player)
        val radio = data.radio

        val form = SimpleForm("Радио",
                "Заряд: §l§3${radio.charge}/${radio.maxCharge}EU§r\n" +
                        "Дальность передатчика: §l§3${radio.distance}м§r\n" +
                        "Максимальная дальность передатчика: §l§3${radio.maxDistance}м§r\n" +
                        "Эффективность передатчика: §l§g${(radio.power *100).toInt()} процентов§r\n" +
                        "Эффективная дальность передатчика: §l§e${(radio.distance * radio.power).toInt()}м§f/§g${(radio.maxDistance * radio.power).toInt()}м§r\n" +
                        "Выбранная волна: §l§2${radio.channel/10.0}МГц§r\n" +
                        "Состояние радио: §l§${if(radio.isBroken) "4Сломано" else "2В рабочем состоянии"}§r\n" +
                        "Авторизированное радио: §l§${if(radio.isNamed) "2Да" else "6Нет"}§r\n" +
                        "Анонимный режим: §l§e${if(radio.isAnonymous) "Да" else "Нет"}§r"
        )

        if(radio.isBroken){
            form.addButton("Починить радио", ImageType.PATH, "textures/ui/dragonestia/radio") { _, _ -> sendRepairForm(player, radio)}
                .send(player)

            return
        }
        form.addButton("Настройки", ImageType.PATH, "textures/ui/dragonestia/radio") {_, _ -> sendSettingsForm(player, radio)}

        if(player.inventory.contains(Item.get(ApocalypseID.BATTERY)) && radio.charge < radio.maxCharge){
            form.addButton("Зарядить радио", ImageType.PATH, "textures/ui/dragonestia/refuel") {_, _ ->
                player.inventory.removeItem(Item.get(ApocalypseID.BATTERY))

                radio.refuel(50)

                player.sendMessage("§eВы успешно зарядили радио!")
                sendMainForm(player)
            }
        }

        if(radio.maxCharge < Radio.MAX_CHARGE){
            form.addButton("Увеличить максимальный запас батареи", ImageType.PATH, "textures/ui/dragonestia/refuel") {_, _ -> sendUpdateBattery(player, radio)}
        }

        form.addButton("Улучшить дальность передатчика", ImageType.PATH, "textures/ui/dragonestia/signal_distance") {_, _ -> sendUpgradeDistance(player, radio)}
            .addButton("Улучшить эффективность передатчика", ImageType.PATH, "textures/ui/dragonestia/quality_upgrade") {_, _ -> sendUpgradePower(player, radio)}
            .send(player)
    }

    private fun sendRepairForm(player: Player, radio: Radio){
        val items = arrayOf(
            Item.get(ApocalypseID.COPPER_WIRE),
            Item.get(ApocalypseID.TIN_NUGGET, 0, 6),
            Item.get(ApocalypseID.COPPER_NUGGET, 0, 3),
            Item.get(ItemID.IRON_NUGGET)
        )
        var canRepair = true
        val needItems = StringBuilder()
        for(item in items){
            needItems.append("\n§f- §")
            if(player.inventory.contains(item)){
                needItems.append('2')
            }else{
                canRepair = false;
                needItems.append('4')
            }
            needItems.append("${RuItems.get(item)} ${item.count}шт§f")
        }
        val form = SimpleForm(
            "Починка радио",
            "Пока у вас сломано радио вы не можете использовать его. Вы не можете как отправлять сообщения, так и получать их. Для его починки вам требуется: $needItems"
        )

        if(canRepair){
            form.addButton("Починить радио", ImageType.PATH, "texture/ui/") {_, _ ->
                for(item in items){
                    player.inventory.removeItem(item)
                }
                radio.repair()
                player.sendMessage("§eРадио было спешно починено!")
                main.playerManager.get(player).playSound("dragonestia.radio.found", 1f, 1f)
            }
        }else{
            form.addButton("Починить радио\n§l§4(Недостаточно компонентов)", ImageType.PATH, "textures/ui/lock_color") {_, _ ->
                player.sendMessage("§cНедостаточно компонентов для починки радио.")
            }
        }

        form.addButton("Назад") {_, _ -> sendMainForm(player)}
            .send(player)
    }

    private fun sendSettingsForm(player: Player, radio: Radio){
        val maxDistance = radio.maxDistance
        val minDistance = 100
        val form = CustomForm("Настройка радио")
                .addElement("Выберите волну вещания и прослушки радио. Допустимые значения: §b${Radio.MIN_CHANNEL_RANGE}-${Radio.MAX_CHANNEL_RANGE}§f(§2${Radio.MIN_CHANNEL_RANGE / 10.0}-${Radio.MAX_CHANNEL_RANGE / 10.0}Мгц§f).\n" +
                        "Стандартная частота - §3${Radio.DEFAULT_CHANNEL}§f(§2${Radio.DEFAULT_CHANNEL / 10.0}МГц)")
                .addElement("channel", Input.builder().setName("Волна вещания").setDefaultValue(radio.channel.toString()).setPlaceholder(radio.channel.toString()).setTrim(true).build())
                .addElement("Для того чтобы регулировать дальность вашего вещания измените поле ниже. Чем ниже дальность вещания по сравнению с максимальной дальностью, тем меньше идет расход заряда радиопередатчика.\n" +
                        "Допустимое значение: §b${minDistance}-${maxDistance}§f(метров).")
                .addElement("distance", Input.builder().setName("Дальность отправляемого сигнала").setTrim(true).setPlaceholder(radio.distance.toString()).setDefaultValue(radio.distance.toString()).build())

        if(radio.isNamed) {
            form.addElement("Вы можете отправлять именованные сообщения и игроки с улучшенными радио-приемниками будут видеть что эти пишите именно вы, но это увеличивает расход энергии радио-передатчика. Вы также можете отправлять анонимные сообщения и потребление энергии будет пониженное.")
                .addElement("anonymous", Toggle("Отправлять анонимные сообщения", radio.isAnonymous))
        }

            form.setHandler() form@{ _, response ->
                val channel: Int
                val distance: Int
                val anonymous = if(radio.isNamed) response.getToggle("anonymous").value else false

                try {
                    channel = response.getInput("channel").value.toInt()
                    distance = response.getInput("distance").value.toInt()
                } catch (ex: NumberFormatException) {
                    player.sendMessage("§cВведено неверное значение.")
                    return@form
                }

                if(channel !in Radio.MIN_CHANNEL_RANGE..Radio.MAX_CHANNEL_RANGE){
                    player.sendMessage("§cВведена неверная частота.")
                    return@form
                }

                if(distance !in minDistance..maxDistance){
                    player.sendMessage("§cВведена неверная дальность.")
                    return@form
                }

                radio.channel = channel.toShort()
                radio.distance = distance
                radio.isAnonymous = anonymous

                player.sendMessage("§eВы успешно изменили настройки радио!")
            }.send(player)
    }

    private fun sendUpdateBattery(player: Player, radio: Radio){
        val items = arrayOf(
            Item.get(ApocalypseID.BATTERY),
            Item.get(ApocalypseID.PLASTIC),
            Item.get(ApocalypseID.TIN_NUGGET, 0, 4)
        )
        val info = handleCraftingList(player, items)
        CustomForm("Улучшение радио")
            .addElement("Данная модификация увеличивает максимальный запас заряда батареи радио. Разовое улучшение радио увеличивает максимальный запас на §320EU§f. \nДля улучшения потребуется: ${info.first}")
            .addElement("count", StepSlider("Множитель улучшения", info.second))
            .setHandler() form@{_, response ->
                val selected = response.getStepSlider("count").value
                if(selected.value == null){
                    player.sendMessage("§cНедостаточно компонентов для улучшения запаса батареи радио.")
                    return@form
                }
                val count = selected.value as Int

                for(item in items){
                    player.inventory.removeItem(Item.get(item.id, item.damage, item.count * count))
                }
                radio.upgradeCharge((20 * count).toShort())
                radio.refuel((10 * count).toShort())

                player.sendMessage("§eВы успешно улучшили свое радио!")
            }.send(player)
    }

    private fun sendUpgradeDistance(player: Player, radio: Radio){
        val items = arrayOf(
            Item.get(ApocalypseID.COPPER_WIRE),
            Item.get(ApocalypseID.TIN_INGOT),
        )
        val info = handleCraftingList(player, items)
        CustomForm("Улучшение радио")
            .addElement("Данная модификация позволяет отправлять ваше сообщения намного дальше. Разовое улучшение радио позволяет отправлять на §3100 метров§f дальше ваши сообщения. \nДля улучшения потребуется: ${info.first}")
            .addElement("count", StepSlider("Множитель улучшения", info.second))
            .setHandler() form@{_, response ->
                val selected = response.getStepSlider("count").value
                if(selected.value == null){
                    player.sendMessage("§cНедостаточно компонентов для улучшения радио.")
                    return@form
                }
                val count = selected.value as Int

                for(item in items){
                    player.inventory.removeItem(Item.get(item.id, item.damage, item.count * count))
                }
                radio.upgradeDistance(count * 100)

                player.sendMessage("§eВы успешно улучшили свое радио!")
            }.send(player)
    }

    private fun sendUpgradePower(player: Player, radio: Radio){
        val items = arrayOf(
            Item.get(ApocalypseID.CHIP),
            Item.get(ApocalypseID.TIN_NUGGET, 0, 6),
        )
        val info = handleCraftingList(player, items)
        CustomForm("Улучшение радио")
            .addElement("Данная модификация улучшает качество сигнала, убирая определенное количество помех, а также позволяет отправлять более четкий сигнал при отправке сообщения. Разовое улучшение радио увеличивает эффективность радио на §310 процентов§f. \nДля улучшения потребуется: ${info.first}")
            .addElement("count", StepSlider("Множитель улучшения", info.second))
            .setHandler() form@{_, response ->
                val selected = response.getStepSlider("count").value
                if(selected.value == null){
                    player.sendMessage("§cНедостаточно компонентов для улучшения эффективности радио.")
                    return@form
                }
                val count = selected.value as Int

                for(item in items){
                    player.inventory.removeItem(Item.get(item.id, item.damage, item.count * count))
                }
                radio.upgradePower(count * 0.1f)

                player.sendMessage("§eВы успешно улучшили свое радио!")
            }.send(player)
    }

    private fun handleCraftingList(player: Player, items: Array<Item>): Pair<StringBuilder, ArrayList<SelectableElement>> {
        val counts = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 15, 20, 30)
        val list = ArrayList<SelectableElement>()
        var canCraftNext = true
        var canCraft = true
        val needItems = StringBuilder()
        for(item in items){
            needItems.append("\n§f- §")
            if(player.inventory.contains(item)){
                needItems.append('2')
            }else{
                canCraft = false;
                needItems.append('4')
            }
            needItems.append("${RuItems.get(item)} ${item.count}шт§f")
        }
        for(i in counts){
            if(canCraft && canCraftNext){
                checking@for(item in items){
                    val temp = Item.get(item.id, item.damage, item.count * i)
                    if(!player.inventory.contains(temp)){
                        canCraftNext = false
                        break@checking
                    }
                }
            }
            if(!canCraft || !canCraftNext){
                list.add(SelectableElement("§l§4x${i}", null))
                continue
            }
            list.add(SelectableElement("§l§2x${i}", i))
        }
        return Pair(needItems, list)
    }

}