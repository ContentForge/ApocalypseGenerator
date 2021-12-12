package ru.dragonestia.apocalypse.item;

import cn.nukkit.item.Item;
import ru.dragonestia.expo.item.AdvItem;

public class CopperWireItem extends Item implements AdvItem {

    public CopperWireItem(Integer meta, int count) {
        super(ApocalypseID.COPPER_WIRE, meta, count, "Copper Wire");
    }

    @Override
    public String getRuName() {
        return "Медная катушка";
    }

    @Override
    public String getIcon() {
        return "textures/items/upgrade/copper_wire";
    }

    @Override
    public int getMaxStackSize() {
        return 4;
    }
}
