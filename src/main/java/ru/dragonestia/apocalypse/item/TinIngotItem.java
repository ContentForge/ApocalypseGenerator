package ru.dragonestia.apocalypse.item;

import cn.nukkit.item.Item;
import ru.dragonestia.expo.item.AdvItem;

public class TinIngotItem extends Item implements AdvItem {

    public TinIngotItem(Integer meta, int count) {
        super(ApocalypseID.TIN_INGOT, meta, count, "Tin ingot");
    }

    @Override
    public String getRuName() {
        return "Оловянный слиток";
    }

    @Override
    public String getIcon() {
        return "textures/items/ingot/tin";
    }

}
