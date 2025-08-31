package me.rabbittv.terraTrees.utils

import org.bukkit.Material
import org.bukkit.Location

class AlignTree {

    fun alignSouth(origin: Location, saplingType: Material): Location? {
        val world = origin.getWorld() ?: return null

        val offsets = arrayOf(
            intArrayOf(0, 0),
            intArrayOf(-1, 0),
            intArrayOf(0, -1),
            intArrayOf(-1, -1)
        )
        for (offset in offsets) {
            val dx = offset[0]
            val dz = offset[1]
            val dx_double = dx.toDouble()
            val dz_double = dz.toDouble()

            val sw = origin.clone().add(dx_double, 0.0, dz_double)
            val se = sw.clone().add(1.0, 0.0, 0.0)
            val nw = sw.clone().add(0.0, 0.0, 1.0)
            val ne = se.clone().add(1.0, 0.0, 1.0)

            if (world.getBlockAt(sw).type == saplingType &&
                world.getBlockAt(se).type == saplingType &&
                world.getBlockAt(nw).type == saplingType &&
                world.getBlockAt(ne).type == saplingType
            ) {
                return sw
            }
        }
        return null
    }

}