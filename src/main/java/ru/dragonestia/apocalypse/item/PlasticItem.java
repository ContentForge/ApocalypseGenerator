package ru.dragonestia.apocalypse.item;

import cn.nukkit.item.Item;
import ru.dragonestia.expo.item.AdvItem;

public class PlasticItem extends Item implements AdvItem {

    public PlasticItem(Integer meta, int count) {
        super(ApocalypseID.PLASTIC, meta, count, "Plastic");
    }

    @Override
    public String getRuName() {
        return "Пластик";
    }

    @Override
    public String getIcon() {
        return "textures/items/plastic";
    }

}
