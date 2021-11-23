package ru.dragonestia.apocalypse.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.scheduler.AsyncTask;
import lombok.SneakyThrows;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import ru.dragonestia.apocalypse.Apocalypse;
import ru.dragonestia.apocalypse.chat.Interference;
import ru.dragonestia.apocalypse.player.PlayerData;
import ru.dragonestia.apocalypse.player.submodules.Radio;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;

public class ChatListener implements Listener {

    private final Apocalypse main;

    public ChatListener(Apocalypse main){
        this.main = main;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(PlayerChatEvent event){
        Player player = event.getPlayer();
        String message = event.getMessage().trim().replaceAll("§.", "");

        event.setCancelled(true);

        if(message.startsWith("!") || message.startsWith(".")){
            PlayerData data = main.getPlayerManager().get(player);

            if(data.getRadio().isBroken()){
                player.sendMessage("§cВаше радио сломано. Почините его чтобы вновь пользоваться чатом.");
                return;
            }

            message = message.replaceAll("^[!.]", "");
            main.getPlayerManager().sendMessage(data, message);
            onRadio(player, data.getRadio(), message);
            return;
        }

        int maxDistance = player.getLevel().equals(main.getServer().getDefaultLevel())? 1000 : 10;
        for(Player p: player.getLevel().getPlayers().values()){
            double distance = player.distance(p);
            String msg;

            if(distance < maxDistance) msg = "§f§l" + player.getName() + "§r§f: §7" + message + "§к";
            else if(distance < maxDistance * 2) msg = "§7§l" + player.getName() + "§r§7: §8" + message + "§к";
            else if(distance < maxDistance * 3) msg = "§8§l" + player.getName() + "§r§8: §0" + message + "§к";
            else continue;

            p.sendMessage(msg);
        }

    }

    private void onRadio(Player player, Radio radio, String message){
        switch (radio.getChannel()){
            case 750:
                if(radio.isAnonymous()) return;
                main.getServer().getScheduler().scheduleAsyncTask(main, new LastDayRadioAsyncTask(player, radio, message));
                return;
        }
    }

    public static class LastDayRadioAsyncTask extends AsyncTask {

        private final Player player;
        private final Radio radio;
        private final String message;

        public LastDayRadioAsyncTask(Player player, Radio radio, String message){
            this.player = player;
            this.radio = radio;
            this.message = message;
        }

        @Override
        public void onRun() {
            String text = Interference.distort(message, 1 - radio.getPower());
            if(text == null || radio.getPower() < 0.9) return;

            try {
                Request.Post("https://api.vk.com/method/messages.send").bodyForm(Arrays.asList(
                        new BasicNameValuePair("access_token", "бля-бля-бля-бля"),
                        new BasicNameValuePair("v", "5.131"),
                        new BasicNameValuePair("random_id", "0"),
                        new BasicNameValuePair("peer_id", "2000000001"),
                        new BasicNameValuePair("message", player.getName() + ": " + text)
                ), StandardCharsets.UTF_8).execute().discardContent();
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

}
