package ru.dragonestia.apocalypse.item;

import cn.nukkit.item.Item;
import ru.dragonestia.expo.item.AdvItem;

public class SulfurClusterItem extends Item implements AdvItem {

    public SulfurClusterItem(Integer meta, int count) {
        super(ApocalypseID.SULFUR_CLUSTER, meta, count, "Sulfur cluster");
    }

    @Override
    public String getRuName() {
        return "Серная руда";
    }

    @Override
    public String getIcon() {
        return "textures/items/cluster/sulfur";
    }

}
