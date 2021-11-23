package ru.dragonestia.apocalypse.entity;


import cn.nukkit.entity.data.Skin;
import ru.dragonestia.apocalypse.Apocalypse;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.stream.Collectors;

public enum Skins {

    ;

    public final static String TEXTURES_PATH = "textures/";

    private final Skin skin;

    Skins(Skin skin){
        this.skin = skin;
    }

    public Skin getSkin(){
        return skin;
    }

    public static Skin generateSkin(String skinId){
        InputStream textureStream = Apocalypse.class.getResourceAsStream(TEXTURES_PATH + skinId + ".png");
        if(textureStream == null) return null;

        InputStream modelStream = Apocalypse.class.getResourceAsStream(TEXTURES_PATH + skinId + ".png");
        if(modelStream == null) return null;

        try {
            Skin skin = new Skin();

            skin.setSkinId(skinId);
            skin.setTrusted(true);
            skin.setSkinData(ImageIO.read(textureStream));
            skin.setGeometryName("geometry." + skinId);
            skin.setGeometryData(new BufferedReader(new InputStreamReader(modelStream)).lines().collect(Collectors.joining()));

            return skin;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
