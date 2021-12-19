package ru.dragonestia.apocalypse.item;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import cn.nukkit.math.Vector3;
import cn.nukkit.potion.Effect;
import ru.dragonestia.apocalypse.Apocalypse;
import ru.dragonestia.apocalypse.player.PlayerData;
import ru.dragonestia.expo.item.AdvItem;

public class FirstAidKitItem extends Item implements AdvItem, UsableItem {

    public FirstAidKitItem(Integer meta, int count) {
        super(ApocalypseID.FIRST_AID_KIT, 0, count, "First aid kit");
    }

    @Override
    public String getRuName() {
        return "Аптечка";
    }

    @Override
    public String getIcon() {
        return "textures/items/med_kit"; //TODO
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean onClickAir(Player player, Vector3 directionVector) {
        use(player);
        if(!player.isCreative()) count--;
        return true;
    }

    @Override
    public void use(Player player){
        PlayerData playerData = Apocalypse.getInstance().getPlayerManager().get(player);

        player.addEffect(Effect.getEffect(Effect.HEALING).setDuration(20 * 60).setVisible(false));
        playerData.playSound("dragonestia.rad.yodadulin", 0.3f, 1f);
    }
    
}
