package ru.dragonestia.apocalypse.workbench;

import cn.nukkit.Player;
import cn.nukkit.block.Block;
import cn.nukkit.block.BlockID;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.network.protocol.ContainerOpenPacket;
import ru.contentforge.formconstructor.form.SimpleForm;
import ru.contentforge.formconstructor.form.element.ImageType;
import ru.dragonestia.apocalypse.Apocalypse;
import ru.dragonestia.expo.player.PlayerData;
import ru.dragonestia.expo.workbench.Workbench;

public class ClassicWorkbench extends Workbench implements Listener {

    public static final String WORKBENCH_ID = "classic";

    public ClassicWorkbench(Apocalypse main) {
        super(WORKBENCH_ID, "Обычный верстак");

        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onTap(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.isCancelled() || !event.getAction().equals(PlayerInteractEvent.Action.RIGHT_CLICK_BLOCK) || event.getBlock().getId() != BlockID.CRAFTING_TABLE || player.isSneaking()) return;

        event.setCancelled(true);
        sendToPlayer(player, event.getBlock());
    }

    @Override
    public void open(PlayerData playerData, Block block) {
        Player player = playerData.getPlayer();

        SimpleForm form = new SimpleForm("Верстак")
                .addButton("Классическое создание предметов", ImageType.PATH, "textures/blocks/crafting_table_top", (p, b) -> {
                    if(block == null) return;

                    player.craftingType = 1;
                    ContainerOpenPacket pk = new ContainerOpenPacket();
                    pk.windowId = -1;
                    pk.type = 1;
                    pk.x = (int) block.x;
                    pk.y = (int) block.y;
                    pk.z = (int) block.z;
                    pk.entityId = player.getId();
                    player.dataPacket(pk);
                })
                .addButton("Создание сложных предметов", ImageType.PATH, "textures/ui/cartography_table_empty", (p, b) -> sendCraftForm(playerData))
                .addButton("Изучение новых рецептов", ImageType.PATH, "textures/ui/infobulb", (p, b) -> sendStudyForm(playerData));

        form.send(player.getPlayer());
    }

}
