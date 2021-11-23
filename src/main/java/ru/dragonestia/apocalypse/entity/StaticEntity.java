package ru.dragonestia.apocalypse.entity;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.EntityHuman;
import cn.nukkit.item.Item;
import cn.nukkit.level.format.FullChunk;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.network.protocol.AddPlayerPacket;
import cn.nukkit.utils.Utils;
import lombok.NonNull;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public abstract class StaticEntity extends Entity {

    private UUID uuid;

    public StaticEntity(FullChunk chunk, CompoundTag nbt) {
        super(chunk, nbt);
    }

    @Override
    protected void initEntity() {
        super.initEntity();

        uuid = Utils.dataToUUID(String.valueOf(id).getBytes(StandardCharsets.UTF_8), (id + "").getBytes(StandardCharsets.UTF_8), getNameTag().getBytes(StandardCharsets.UTF_8));

        setNameTagAlwaysVisible(false);

        spawnToAll();
    }

    @Override
    public void spawnTo(Player player) {
        if (!this.hasSpawned.containsKey(player.getLoaderId())) {
            this.hasSpawned.put(player.getLoaderId(), player);

            this.server.updatePlayerListData(uuid, this.getId(), this.getName(), getSkinInfo().getSkin(), new Player[]{player});

            AddPlayerPacket pk = new AddPlayerPacket();
            pk.uuid = uuid;
            pk.username = getName();
            pk.entityUniqueId = id;
            pk.entityRuntimeId = id;
            pk.x = (float) x;
            pk.y = (float) y;
            pk.z = (float) z;
            pk.speedX = (float) motionX;
            pk.speedY = (float) motionY;
            pk.speedZ = (float) motionZ;
            pk.yaw = (float) yaw;
            pk.pitch = (float) pitch;
            pk.item = Item.get(0);
            pk.metadata = this.dataProperties;
            player.dataPacket(pk);

            server.removePlayerListData(uuid, new Player[]{player});

            super.spawnTo(player);
        }
    }

    @Override
    public int getNetworkId() {
        return EntityHuman.NETWORK_ID;
    }

    @Override
    public boolean onInteract(Player player, Item item) {
        onTap(player);
        return true;
    }

    @NonNull
    protected abstract Skins getSkinInfo();

    protected abstract void onTap(Player player);

}
