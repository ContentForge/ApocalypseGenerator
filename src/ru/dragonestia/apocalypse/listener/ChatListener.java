package ru.dragonestia.apocalypse.listener;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import ru.dragonestia.apocalypse.Apocalypse;

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
            main.getChatManager().sendMessage(main.getChatManager().get(player), message);
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

}
