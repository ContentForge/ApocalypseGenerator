package ru.dragonestia.apocalypse.item;

import cn.nukkit.item.Item;
import ru.dragonestia.expo.item.AdvItem;

public class IronClusterItem extends Item implements AdvItem {

    public IronClusterItem(Integer meta, int count) {
        super(ApocalypseID.IRON_CLUSTER, meta, count, "Iron cluster");
    }

    @Override
    public String getRuName() {
        return "Железная руда";
    }

    @Override
    public String getIcon() {
        return "textures/items/cluster/iron";
    }

}
