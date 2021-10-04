package ru.dragonestia.apocalypse.network;

import cn.nukkit.network.protocol.DataPacket;

public class CameraShakePacket extends DataPacket {

    public static final byte NETWORK_ID = (byte) 0x9f;

    public static final byte TYPE_POSITIONAL = 0;
    public static final byte TYPE_ROTATIONAL = 1;
    public static final byte ACTION_ADD = 0;
    public static final byte ACTION_STOP = 1;

    public float intensity;
    public float duration;
    public byte shakeType;
    public byte shakeAction;

    @Override
    public byte pid() {
        return NETWORK_ID;
    }

    @Override
    public void decode() {
        intensity = getLFloat();
        duration = getLFloat();
        shakeType = (byte) getByte();
        shakeAction = (byte) getByte();
    }

    @Override
    public void encode() {
        reset();
        putLFloat(intensity);
        putLFloat(duration);
        putByte(shakeType);
        putByte(shakeAction);
    }

}
