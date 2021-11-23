package ru.dragonestia.apocalypse.level.populator.section.filter;

import java.util.Random;

public class VoidFilter {

    private final Void[] voids;

    public VoidFilter(int yMin, int yMax, int count, Random random){
        this.voids = new Void[count];

        for(int i = 0; i < count; i++){
            voids[i] = new Void((random.nextBoolean()? 0 : 11) + random.nextInt(4), yMin + random.nextInt(yMax), random.nextInt(16), random);
        }
    }

    public boolean check(int x, int y, int z){
        for(Void v: voids){
            if(v.check(x, y, z)) return true;
        }
        return false;
    }

    public static class Void {

        private final int x, y, z, dx, dy, dz;

        public Void(int x, int y, int z, Random random){
            this.x = x;
            this.y = y;
            this.z = z;

            dx = 1 + random.nextInt(3);
            dy = (1 + random.nextInt(3)) * 2;
            dz = 1 + random.nextInt(2);
        }

        public boolean check(int x, int y, int z){
            x = (x - this.x) / dx;
            y = (y - this.y) / dy;
            z = (z - this.z) / dz;
            return x*x + y*y + z*z <= 10;
        }
    }

}
