package ru.dragonestia.apocalypse.item;

import cn.nukkit.item.Item;
import ru.dragonestia.expo.item.AdvItem;

public class BatteryItem extends Item implements AdvItem {

    public BatteryItem(Integer meta, int count) {
        super(ApocalypseID.BATTERY, 0, count, "Battery");
    }

    @Override
    public String getRuName() {
        return "Батарейка";
    }

    @Override
    public String getIcon() {
        return "textures/items/upgrade/battery";
    }

}
