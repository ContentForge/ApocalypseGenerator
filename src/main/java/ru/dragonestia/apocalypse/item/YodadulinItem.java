package ru.dragonestia.apocalypse.item;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.potion.Effect;
import ru.dragonestia.apocalypse.Apocalypse;
import ru.dragonestia.apocalypse.player.PlayerData;
import ru.dragonestia.expo.item.AdvItem;

public class YodadulinItem extends Item implements AdvItem, UsableItem {

    public YodadulinItem(Integer meta, int count) {
        super(ApocalypseID.YODADULIN, meta, count, "Yodadulin");
    }

    @Override
    public String getRuName() {
        return "Йодадулин";
    }

    @Override
    public String getIcon() {
        return "textures/items/rad/yodadulin";
    }

    @Override
    public void use(Player player) {
        PlayerData playerData = Apocalypse.getInstance().getPlayerManager().get(player);

        playerData.reduceRadiationLevel(70000);
        playerData.playSound("dragonestia.rad.yodadulin", 0.3f, 1f);

        Effect[] effects = new Effect[]{
                Effect.getEffect(Effect.WEAKNESS).setDuration(20 * 70).setVisible(false),
                Effect.getEffect(Effect.SLOWNESS).setDuration(20 * 30).setVisible(false),
                Effect.getEffect(Effect.HUNGER).setDuration(20 * 10).setVisible(false),
        };
        for(Effect effect: effects) player.addEffect(effect);
    }

}
