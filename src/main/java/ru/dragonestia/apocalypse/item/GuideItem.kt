package ru.dragonestia.apocalypse.item

import cn.nukkit.Player
import cn.nukkit.item.Item
import cn.nukkit.math.Vector3
import ru.contentforge.formconstructor.form.SimpleForm
import ru.contentforge.formconstructor.form.element.ImageType
import ru.dragonestia.expo.item.AdvItem

class GuideItem @JvmOverloads constructor(meta: Int? = 0, count: Int = 1) : Item(ApocalypseID.GUIDE_BOOK, 0, count, "Guide"), AdvItem {

    override fun onClickAir(player: Player, directionVector: Vector3): Boolean {
        sendMainForm(player)
        return true
    }

    override fun getMaxStackSize() = 1

    private fun sendMainForm(player: Player){
        SimpleForm("Инструкция по выживанию",
        "Выберите раздел, о котором хотите узнать. Рекомендуется читать все полностью, иначе вы не сможете выжить, также информация пополняется со временем!\n\n" +
                "Группа сервера: §9vk.com/dragonestia")
                .addButton("О сервере", ImageType.PATH, "textures/ui/dragonestia/server") {_, _, -> sendAboutForm(player)}
                .addButton("Где добывать ресурсы?", ImageType.PATH, "textures/ui/dragonestia/mine") {_, _, -> sendMiningForm(player)}
                .addButton("Где добыть еду?", ImageType.PATH, "textures/ui/dragonestia/food") {_, _, -> sendFoodForm(player)}
                .addButton("О радио", ImageType.PATH, "textures/ui/dragonestia/radio") {_, _, -> sendRadioForm(player)}
                .addButton("Природные катаклизмы", ImageType.PATH, "textures/ui/dragonestia/cataclysm") {_, _, -> sendWeatherForm(player)}
                .addButton("Структуры", ImageType.PATH, "textures/ui/dragonestia/structures") {_, _, -> sendStructureForm(player)}
                .send(player)
    }

    private fun sendAboutForm(player: Player){
        SimpleForm("О сервере",
                "    Это §lхардкорный сервер§r проекта §l§cDragonestia§r. Здесь вы реально почувствуете насколько сложным может быть выживание в Minecraft.\n" +
                        "Смысл данного сервера - выживать сквозь боль и страдания, ведь в данном месте §lвыживает только сильнейший§r. Здесь нет приватов, нет плагинов на телепортацию, " +
                        "абсолютно нет доната и прочего, что упрощает игру. §7§lУмер - начни заново§r§f. После смерти вы появляетесь возле кровати, либо в случайном месте.\n\n" +
                        "    §l§dСюжет§r\n" +
                        "На землю упал астероид, последствия которого привели к умиранию планеты. Теперь на поверхности " +
                        "проходят метеоритные дожди, а также из-за воздействия солнечной радиации организм человека умирает за секунды. На планете осталось всего-то 1млн человек, " +
                        "но поскольку все §7ресурсы в дефиците§f, то §lвстреча с другими людьми будет всегда кровопролитной§r."
        ).addButton("Назад") {_, _ -> sendMainForm(player)}
                .send(player)
    }

    private fun sendMiningForm(player: Player){
        SimpleForm("Где добывать ресурсы?",
                "В разных биомах расположены разные ресурсы. В городской местности в домах можно найти ящики с ресурсами.\n\n" +
                        "§l[§bДревесина§f]§r\n" +
                        "   В городах в §lобычном биоме§r можно в домах встретить §3древесину§f. Тажке в пустошах есть разрушенные домики и если их откопать, то в полу можно найти §3древесину§f.\n\n" +
                        "§l[§bМеталлолом§f]§r\n" +
                        "   Из §3железных прутьев§f падает §3металлолом§f, который можно переплавить в печи в §3железо§f.\n\n" +
                        "§l[§bАнтирады§f]§r\n" +
                        "   Их можно найти в §3подземном хранилище§f, выбить из ящиков, либо же сделать самому на верстаке в разделе §eРасходники§7(для древесного угля можно сжечь доски в печи)§f.\n\n" +
                        "§l[§bРуды§f]§r\n" +
                        "   §2В обычном биоме§f руды не появляются, кроме §3угольной§f.\n" +
                        "- Из §eсерой руды§f может выпасть §3железный§f и §3оловянный кластер§f.\n" +
                        "- Из §eжелтой руды§f выпадает §3медный§f или §3серный кластер§f.\n" +
                        "- §3Красный камень§f можно добыть только в §2огненном биоме§f.\n" +
                        "- §3Алмазную руду§f можно добыть только в §2пепельном биоме§f.\n" +
                        "- §3Угольная руда§f появляется всегда только на поверхности.\n\n"
        ).addButton("Назад") {_, _ -> sendMainForm(player)}
                .send(player)
    }

    private fun sendFoodForm(player: Player){
        SimpleForm("Где добыть еду?",
                "Добыть еду можно в специальных §3подземных хранилищах§f, либо же найти §3грибные пещеры§f §lглубоко под землей§r(20-30 по Y) в обычном биоме. " +
                        "Также еду можно §7отобрать у других игроков§f, но знайте, что они точно не захотят ей делиться."
        ).addButton("Назад") {_, _ -> sendMainForm(player)}
                .send(player)
    }

    private fun sendRadioForm(player: Player){
        SimpleForm("О радио",
                "  Отправляя обычное сообщение в чате вы просто 'говорите'. Ваше сообщение видно на расстоянии 20 блоков и чем дальше - тем сложнее прочитать ваш текст. \n" +
                        "  Для этого на сервере присутствует радио. Это устройтсво позволяет посылать сообщения на §lнесколько тысяч блоков§r. Все сообщения, передаваемые через радио §3анонимны§f и вы никак не " +
                        "сможете узнать кто отправил сигнал. Чем выше эффективность передатчика, тем дальше " +
                        "он сможет посылать сигнал. Но дальность отправляемого сообщения можно регулировать командой §3/radio§f. Также у радио есть такой параметр, как §lчастота передаваемого сигнала§r. " +
                        "Это что-то подобия канала чата. Настроить частоту вещания можно настроить с помощью команды §3/radio§f. Радио имеет свойство разряжаться! Для подзарядки требуются батарейки. " +
                        "Все необходимые улучшения и материалы дял радио хранятся в сундуках на радиовышках. \n" +
                        "Для того чтобы использовать радио, то просто перед сообщением §7поставьте восклицательный знак§f, либо использовать команду §3/r <Сообщение>§f. Пример: §e§l!Hello world§r\n\n" +
                        "Во время геомагнитного шторма эффективность радио уменьшается в 70 раз."
        ).addButton("Назад") {_, _ -> sendMainForm(player)}
                .send(player)
    }

    private fun sendWeatherForm(player: Player){
        SimpleForm("Природные катаклизмы",
                "После конца света на планете происходят различные катаклизмы. Они могут как и упрощать, так и усложнять выживание." +
                        "По радио часто идут оповещения по различным событиям. Они транслируются на всех волнах.\n\n" +
                        "  §l§b[Затмение]§r\n" +
                        "В это время солнечная активность снижается до безопасного состояния для человека. " +
                        "Во время этого события §lможно спокойно ходить по поверхности§r без специальной защиты.\n\n" +
                        "  §l§b[Экстремальная буря]§r\n" +
                        "Во время этого события лучше закапываться глубоко под землю, иначе §lвас просто снесет безумным потоком ветра§r. Спастись от этого явления на поверхности крайне трудно.\n\n" +
                        "  §l§b[Землетрясение]§r\n" +
                        "Всем ясно что во время этого явления происходит. Находится глубоко под землей и на хлипких зданиях очень опасно. Укрыться можно возле поверхности.\n\n" +
                        "  §l§b[Геомагнитный шторм]§r\n" +
                        "Во время данного события эффективность радио уменьшается в 70 раз, а также увеличивается радиоактивное облучение."
        ).addButton("Назад") {_, _ -> sendMainForm(player)}
                .send(player)
    }

    private fun sendStructureForm(player: Player){
        SimpleForm("Структуры",
                "На сервере присутствуют различные структуры, которые генерируются в мире. Структуры есть наземные и есть подземные. Из полезных структур можно выделить парочку: \n\n" +
                        "§6§lРадиовышка.§r Высокое сроение, которое похоже на радиовышку(логично). На самом верху можно найти §lсундук с улучшениями для радио§r, а именно §3батарейки§f, §3микросхемы§f и §3медную проволоку§f. Еще там можно найти §3пластик§f. Также на антене присутствует §33 железных блока§f. Расположены вне городской местности.\n\n" +
                        "§6§lПодземное хранилище.§r Редкая подземная структура, которая появляется на высоте §l25Y§r. Там можно найти §3еду§f, §3защитный костюм§f, §3медикаменты§f и §3динамит§f. Появляется только под огненным биомов.\n\n" +
                        "§6§lГородские дома.§r Бывшие жилища того времени, где раньше жили люди. Сейчас они сильно разрушены. В некоторых можно найти деревянные предметы и мебель. Также в них можно найти ящики, в которых лежат различные предметы.\n\n"
        ).addButton("Назад") {_, _ -> sendMainForm(player)}
                .send(player)
    }

    override fun getRuName(): String = "Книга выживальщика"

    override fun getIcon(): String = "textures/items/book/guide"

}