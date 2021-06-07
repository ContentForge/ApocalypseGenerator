package ru.dragonestia.apocalypse.storms;

import cn.nukkit.Player;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.level.Level;
import cn.nukkit.level.Sound;
import cn.nukkit.level.biome.Biome;
import cn.nukkit.math.Vector3;
import cn.nukkit.network.protocol.PlaySoundPacket;
import cn.nukkit.potion.Effect;
import ru.dragonestia.apocalypse.level.biome.ApocalypseBiome;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

abstract public class GlobalEventBase {

    protected final Random random = new Random();

    public abstract void init();

    public abstract String getName();

    public abstract int getPeriod();

    public abstract String generateTitleMessage(Player player, int lastTime);

    public abstract String getNoticeStartMessage();

    public abstract String getStartMessage();

    public abstract String getNoticeEndMessage();

    public abstract String getEndMessage();

    public abstract boolean condition();

    public void handle(Player player){
        checkSunBurn(player);
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
        Biome biome = Biome.getBiome(player.getLevel().getBiomeId(player.getFloorX(), player.getFloorZ()));
        if(!(biome instanceof ApocalypseBiome)) return;
        ApocalypseBiome apocalypseBiome = (ApocalypseBiome) biome;

        if(apocalypseBiome.hasProtection(player)) return;
        apocalypseBiome.giveNegativeEffect(player, 1);
    }

}
