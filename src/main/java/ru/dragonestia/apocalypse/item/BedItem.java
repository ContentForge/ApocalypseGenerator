package ru.dragonestia.apocalypse.item;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.math.Vector3;
import ru.contentforge.formconstructor.form.SimpleForm;
import ru.dragonestia.apocalypse.Apocalypse;
import ru.dragonestia.expo.item.AdvItem;

public class BedItem extends Item implements AdvItem {

    public BedItem(Integer meta, int count) {
        super(ApocalypseID.BED, 0, count, "Bed");
    }

    @Override
    public String getRuName() {
        return "Спальный мешок";
    }

    @Override
    public String getIcon() {
        return "textures/items/d_bed";
    }

    @Override
    public boolean onClickAir(Player player, Vector3 directionVector) {
        new SimpleForm("Спальный мешок", "Спальный мешок является точкой возрождения и он может размещен только один. Если у вас уже есть спальный мешок и вы поставите новый, то вы потеряете старый.")
                .addButton("Установить точку возрождения", (p, b) -> {
                    Apocalypse.getInstance().getPlayerManager().get(player).placeBed(player);
                    player.getInventory().removeItem(Item.get(ApocalypseID.BED));
                }).send(player);
        return true;
    }

}
