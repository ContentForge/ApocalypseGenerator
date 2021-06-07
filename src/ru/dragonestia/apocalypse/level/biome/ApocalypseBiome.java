package ru.dragonestia.apocalypse.level.biome;

import cn.nukkit.Player;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.biome.Biome;
import cn.nukkit.math.Vector3;
import cn.nukkit.potion.Effect;
import ru.dragonestia.apocalypse.factor.RadioactiveLevel;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public abstract class ApocalypseBiome extends Biome {

    protected final static HashSet<Integer> transparentBlocks = new HashSet<>(Arrays.asList(0, 8, 9, 18, 20, 102, 50, 101, 85, 65, 107));

    public boolean isNegativeEffectBySun(){
        return true;
    }

    public static boolean checkUp(Vector3 pos, Level level) {
        int x = (int) pos.x;
        int posY = (int) pos.y + 1;
        int z = (int) pos.z;

        for(int y = 127; y >= posY; y--){
            if(!transparentBlocks.contains(level.getBlockIdAt(x, y, z))) return false;
        }
        return true;
    }

    public boolean giveNegativeEffect(Player player, int level){
        if(isNegativeEffectBySun()){

            if(!checkUp(player, player.level)) return false;

            player.addEffect(Effect.getEffect(Effect.WITHER)
                    .setDuration(20 * 5)
                    .setAmplifier(2)
                    .setVisible(false));
            player.addEffect(Effect.getEffect(Effect.BLINDNESS)
                    .setDuration(20 * 5)
                    .setVisible(false));
            player.sendTitle("§g§l⚠ §cСолнечная радиация §g⚠", "§6СРОЧНО НАЙДИТЕ УКРЫТИЕ!", 10, 10, 3*20);
        }
        return true;
    }

    public boolean hasProtection(Player player){
        if(!player.isSurvival()) return true;

        PlayerInventory inv = player.getInventory();
        return inv.getBoots().getId() == Item.CHAIN_BOOTS &&
                inv.getLeggings().getId() == Item.CHAIN_LEGGINGS &&
                inv.getChestplate().getId() == Item.CHAIN_CHESTPLATE &&
                inv.getHelmet().getId() == Item.CHAIN_HELMET;
    }

    public abstract RadioactiveLevel getRadioactiveLevel();

    public int getHeightInterpolate(Random random, int y){
        return y;
    }

    public double getNoiseInterpolate(Random random, double noise){
        return noise;
    }

}
