package ru.dragonestia.apocalypse.item;

import cn.nukkit.item.Item;
import ru.dragonestia.expo.item.AdvItem;

public class CopperClusterItem extends Item implements AdvItem {

    public CopperClusterItem(Integer meta, int count) {
        super(ApocalypseID.COPPER_CLUSTER, meta, count, "Copper cluster");
    }

    @Override
    public String getRuName() {
        return "Медная руда";
    }

    @Override
    public String getIcon() {
        return "textures/items/cluster/copper";
    }

}
