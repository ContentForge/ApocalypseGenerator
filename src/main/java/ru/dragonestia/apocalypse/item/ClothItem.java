package ru.dragonestia.apocalypse.item;

import cn.nukkit.item.Item;
import ru.dragonestia.expo.item.AdvItem;

public class ClothItem extends Item implements AdvItem {

    public ClothItem(Integer meta, int count) {
        super(ApocalypseID.CLOTH, meta, count, "Cloth");
    }

    @Override
    public String getRuName() {
        return "Ткань";
    }

    @Override
    public String getIcon() {
        return "textures/items/cloth";
    }

}
