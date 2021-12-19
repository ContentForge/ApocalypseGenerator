package ru.dragonestia.apocalypse.expo.button;

import cn.nukkit.Player;
import cn.nukkit.inventory.PlayerInventory;
import cn.nukkit.item.Item;
import cn.nukkit.level.Position;
import cn.nukkit.potion.Effect;
import cn.nukkit.scheduler.PluginTask;
import ru.contentforge.formconstructor.form.SimpleForm;
import ru.contentforge.formconstructor.form.element.ImageType;
import ru.dragonestia.apocalypse.Apocalypse;
import ru.dragonestia.apocalypse.item.ApocalypseID;
import ru.dragonestia.expo.dialogue.Dialogue;
import ru.dragonestia.expo.dialogue.DialogueError;
import ru.dragonestia.expo.dialogue.DialogueSender;
import ru.dragonestia.expo.dialogue.instance.ButtonInstance;
import ru.dragonestia.expo.player.PlayerData;

import java.util.Random;

public class PlayButtonInstance extends ButtonInstance {

    private final Apocalypse main;
    private final Random random = new Random();

    public PlayButtonInstance(Apocalypse main) {
        super("play");

        this.main = main;
    }

    @Override
    public void handle(PlayerData playerData, DialogueSender sender, Dialogue dialogue, String text, String[] args) throws DialogueError {
        Player player = playerData.getPlayer();
        ru.dragonestia.apocalypse.player.PlayerData data = main.getPlayerManager().get(player);

        SimpleForm form = new SimpleForm("Играть",
                "Если вы выберите пункт §6Случайное появление§f, то вы появитесь глубоко под землей. " +
                        "Вы получите стартовый набор из §33 каменных кирок§f и §34 чашек с грибным супом§f. " +
                        "Еще у вас будет §3книга§f с маленьким описанием всего того, что ждет вас впереди и §lлучше ее прочесть - там полезная информация§r.\n\n" +
                        "§l§eПомните, что после конца света §gначала процветать анархия§e и любой другой игрок представляет огромную опасность для вас.\n Удачи!\n\n" +
                "§r§7Для возвращения обратно на стартовую локацию используйте команду §e/spawn§7."
                ).addButton("Случайное появление", ImageType.PATH, "textures/ui/dragonestia/server", (p, b) -> spawn(p));

        if(data.hasBed()){
            form.addButton("Появление на спальном мешке", ImageType.PATH, "textures/ui/dragonestia/server", (p, b) -> spawnToBed(data));
        }

        form.send(player);
    }

    private void spawnToBed(ru.dragonestia.apocalypse.player.PlayerData data){
        Player player = data.getPlayer();

        player.teleport(data.getBed().getPosition());

        player.noDamageTicks = 60;
        player.setNameTagAlwaysVisible(false);
        player.extinguish();
        player.setGamemode(0);

        data.removeBed();
        main.getLobbyMusicTask().leaveFromLobby(player);

        player.addEffect(Effect.getEffect(Effect.NIGHT_VISION).setDuration(600).setVisible(false));
        player.addEffect(Effect.getEffect(Effect.BLINDNESS).setDuration(600).setVisible(false));
    }

    private void spawn(Player player){
        int x = random.nextInt(1000);
        int z = random.nextInt(1000);
        Position pos = new Position(x + 0.5, 35.0, z + 0.5, main.getGameLevel());
        player.teleport(pos);
        player.setSpawn(pos);
        player.setImmobile(true);
        player.noDamageTicks = 60;
        player.setNameTagAlwaysVisible(false);
        player.extinguish();
        player.setGamemode(0);
        player.addEffect(Effect.getEffect(Effect.NIGHT_VISION).setDuration(600).setVisible(false));
        player.addEffect(Effect.getEffect(Effect.BLINDNESS).setDuration(600).setVisible(false));

        main.getLobbyMusicTask().leaveFromLobby(player);

        main.getServer().getScheduler().scheduleDelayedTask(new PlayerSpawnTask(main, player), 60);
    }

    public static class PlayerSpawnTask extends PluginTask<Apocalypse> {

        private final Player player;

        private PlayerSpawnTask(Apocalypse owner, Player player){
            super(owner);

            this.player = player;
        }

        @Override
        public void onRun(int tick) {
            player.setImmobile(false);
            player.extinguish();

            player.level.setBlockAt(player.getFloorX(), player.getFloorY() - 1, player.getFloorZ(), Item.COBBLESTONE);
            player.level.setBlockAt(player.getFloorX(), player.getFloorY(), player.getFloorZ(), 0);
            player.level.setBlockAt(player.getFloorX(), player.getFloorY() + 1, player.getFloorZ(), 0);

            player.removeAllEffects();
            PlayerInventory inv = player.getInventory();

            ru.dragonestia.apocalypse.player.PlayerData data = owner.getPlayerManager().get(player);
            if(data.isTakenBonus()) return;

            inv.addItem(Item.get(274, 0, 2));
            inv.addItem(Item.get(282, 0, 4));
            inv.addItem(Item.get(ApocalypseID.GUIDE_BOOK));
            data.takeBonus();
        }

    }

}
