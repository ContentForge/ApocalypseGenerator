package ru.dragonestia.apocalypse.item;

import cn.nukkit.item.Item;
import ru.dragonestia.expo.item.AdvItem;

public class SulfurDustItem extends Item implements AdvItem {

    public SulfurDustItem(Integer meta, int count) {
        super(ApocalypseID.SULFUR_DUST, meta, count, "Sulfur dust");
    }

    @Override
    public String getRuName() {
        return "Сера";
    }

    @Override
    public String getIcon() {
        return "textures/items/dust/sulfur";
    }

}
