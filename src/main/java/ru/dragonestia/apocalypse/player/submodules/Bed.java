package ru.dragonestia.apocalypse.player.submodules;

import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import ru.dragonestia.apocalypse.Apocalypse;

public class Bed {

    private int x;
    private int y;
    private int z;

    public Bed(Vector3 pos){
        x = pos.getFloorX();
        y = pos.getFloorY();
        z = pos.getChunkZ();
    }

    public Position getPosition(){
        return new Position(x + 0.5, y + 0.5, z + 0.5, Apocalypse.getInstance().getGameLevel());
    }

    public String getCode(){
        return x+":"+y+":"+z;
    }

}
