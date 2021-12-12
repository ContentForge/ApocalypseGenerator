package ru.dragonestia.apocalypse.item;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.potion.Effect;
import ru.dragonestia.apocalypse.Apocalypse;
import ru.dragonestia.apocalypse.player.PlayerData;
import ru.dragonestia.expo.item.AdvItem;

public class TabletItem extends Item implements AdvItem, UsableItem {

    public TabletItem(Integer meta, int count) {
        super(ApocalypseID.TABLET, meta, count, "Tablets");
    }

    @Override
    public String getRuName() {
        return "Самодельный радиопротектор";
    }

    @Override
    public String getIcon() {
        return "textures/items/rad/tablet";
    }

    @Override
    public void use(Player player) {
        PlayerData playerData = Apocalypse.getInstance().getPlayerManager().get(player);

        playerData.reduceRadiationLevel(15000);
        playerData.playSound("dragonestia.rad.tablet", 0.3f, 1f);

        Effect[] effects = new Effect[]{
                Effect.getEffect(Effect.WEAKNESS).setDuration(20 * 120).setVisible(false),
                Effect.getEffect(Effect.SLOWNESS).setDuration(20 * 40).setVisible(false),
                Effect.getEffect(Effect.HUNGER).setDuration(20 * 20).setVisible(false),
                Effect.getEffect(Effect.FATIGUE).setDuration(20 * 70).setVisible(false),
                Effect.getEffect(Effect.POISON).setDuration(20 * 3).setVisible(false),
        };
        for(Effect effect: effects) player.addEffect(effect);
    }

}
