package me.rabbittv.terraTrees.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class AlignTree {

    public static Location alignSouth(Location origin, Material saplingType) {
        World world = origin.getWorld();
        if (world == null) return null;

        int[][] offsets = {
                {0, 0}, {-1, 0}, {0, -1}, {-1, -1}
        };

        for (int[] offset : offsets) {
            int dx = offset[0];
            int dz = offset[1];

            Location sw = origin.clone().add(dx, 0, dz);
            Location se = sw.clone().add(1, 0, 0);
            Location nw = sw.clone().add(0, 0, 1);
            Location ne = sw.clone().add(1, 0, 1);

            if (world.getBlockAt(sw).getType() == saplingType &&
                    world.getBlockAt(se).getType() == saplingType &&
                    world.getBlockAt(nw).getType() == saplingType &&
                    world.getBlockAt(ne).getType() == saplingType) {
                return sw;
            }
        }

        return null;
    }

}
