package ru.dragonestia.apocalypse.item;

import cn.nukkit.item.Item;
import ru.dragonestia.expo.item.AdvItem;

public class CopperIngotItem extends Item implements AdvItem {

    public CopperIngotItem(Integer meta, int count) {
        super(ApocalypseID.COPPER_INGOT, meta, count, "Copper ingot");
    }

    @Override
    public String getRuName() {
        return "Медный слиток";
    }

    @Override
    public String getIcon() {
        return "textures/items/ingot/copper";
    }

}
