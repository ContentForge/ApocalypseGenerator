package ru.dragonestia.apocalypse.storms;

import cn.nukkit.Player;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.Sound;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.PlaySoundPacket;
import cn.nukkit.potion.Effect;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

abstract public class GlobalEventBase {

    protected final Random random = new Random();

    private final static HashSet<Integer> transparentBlocks = new HashSet<>(Arrays.asList(0, 8, 9, 18, 20, 102, 50, 101, 85, 65, 107));

    public abstract void init();

    public abstract String getName();

    public abstract int getPeriod();

    public abstract String generateTitleMessage(Player player, int lastTime);

    public abstract String getNoticeStartMessage();

    public abstract String getStartMessage();

    public abstract String getNoticeEndMessage();

    public abstract String getEndMessage();

    public abstract boolean condition();
    public abstract void handle(Player player);

    protected boolean checkUp(Vector3 pos, Level level) {
        int x = (int) pos.x;
        int posY = (int) pos.y + 1;
        int z = (int) pos.z;

        for(int y = 127; y >= posY; y--){
            if(!transparentBlocks.contains(level.getBlockIdAt(x, y, z))) return false;
        }
        return true;
    }

    protected void playSound(Player player, Vector3 pos, Sound sound){
        playSound(player, pos, sound, 1F, 1F);
    }

    protected void playSound(Player player, Vector3 pos, Sound sound, float volume, float pitch){
        PlaySoundPacket packet = new PlaySoundPacket();
        packet.name = sound.getSound();
        packet.volume = volume;
        packet.pitch = pitch;
        packet.x = pos.getFloorX();
        packet.y = pos.getFloorY();
        packet.z = pos.getFloorZ();
        player.dataPacket(packet);
    }

    protected void checkSunBurn(Player player){
        if(!player.isSurvival()) return;

        //Защитный костюм
        PlayerInventory inv = player.getInventory();
        if(inv.getBoots().getId() == Item.CHAIN_BOOTS &&
                inv.getLeggings().getId() == Item.CHAIN_LEGGINGS &&
                inv.getChestplate().getId() == Item.CHAIN_CHESTPLATE &&
                inv.getHelmet().getId() == Item.CHAIN_HELMET) return;

        if(!checkUp(player, player.level)) return;

        player.addEffect(Effect.getEffect(Effect.WITHER)
                .setDuration(20 * 5)
                .setAmplifier(2)
                .setVisible(false));
        player.addEffect(Effect.getEffect(Effect.BLINDNESS)
                .setDuration(20 * 5)
                .setVisible(false));
        player.sendTitle("§g§l⚠ §cСолнечная радиация §g⚠", "§6СРОЧНО НАЙДИТЕ УКРЫТИЕ!", 10, 10, 3*20);
    }

}
