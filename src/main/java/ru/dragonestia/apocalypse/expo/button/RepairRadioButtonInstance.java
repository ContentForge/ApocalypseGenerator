package ru.dragonestia.apocalypse.expo.button;

import cn.nukkit.Player;
import ru.contentforge.formconstructor.form.SimpleForm;
import ru.contentforge.formconstructor.form.element.ImageType;
import ru.dragonestia.apocalypse.Apocalypse;
import ru.dragonestia.apocalypse.expo.shop.TicketEconomyInstance;
import ru.dragonestia.expo.dialogue.Dialogue;
import ru.dragonestia.expo.dialogue.DialogueError;
import ru.dragonestia.expo.dialogue.DialogueSender;
import ru.dragonestia.expo.dialogue.instance.ButtonInstance;
import ru.dragonestia.expo.player.PlayerData;
import ru.dragonestia.expo.shop.EconomyInstance;

public class RepairRadioButtonInstance extends ButtonInstance {

    public static final int REPAIR_PRICE = 5;

    private final Apocalypse main;

    public RepairRadioButtonInstance(Apocalypse main) {
        super("repair_radio");

        this.main = main;
    }

    @Override
    public void handle(PlayerData playerData, DialogueSender sender, Dialogue dialogue, String text, String[] args) throws DialogueError {
        Player player = playerData.getPlayer();
        EconomyInstance eco = playerData.getMain().getEconomyManager().getEconomyInstance(TicketEconomyInstance.ID);
        SimpleForm form = new SimpleForm("Починка радио", "Данный техник может почнить ваше радио, но стоить это будет "+eco.getMoneyFormat(REPAIR_PRICE)+ ". У вас сейчас есть " + eco.getMoneyFormat(eco.getMoney(player)) + ".");

        if(eco.hasMoney(player, REPAIR_PRICE)){
            form.addButton("Починить радио", ImageType.PATH, "textures/ui/dragonestia/radio", (p, b) -> {
                eco.takeMoney(p, REPAIR_PRICE);
                main.getPlayerManager().get(p).getRadio().repair();
                p.sendMessage("§eВаше радио было успешно отремонтировано!");
            });
        } else {
            form.addButton("Починить радио\n§l§4(Недостаточно средств)", ImageType.PATH, "textures/ui/lock_color", (p, b) -> {});
        }
        form.send(player);
    }

    @Override
    public String getButtonIcon(PlayerData playerData) {
        return "textures/ui/dragonestia/radio";
    }

}
