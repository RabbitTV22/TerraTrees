package me.rabbittv.terraTrees.listeners

import me.rabbittv.terraTrees.TerraTrees
import me.rabbittv.terraTrees.utils.AlignTree
import me.rabbittv.terraTrees.utils.SpawnStructure
import org.bukkit.Material
import org.bukkit.TreeType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.StructureGrowEvent

class TreeGrowthListener(private val plugin: TerraTrees, private val structure: SpawnStructure, private val alignTree: AlignTree): Listener {
    @EventHandler
    fun onSaplingGrowth(e: StructureGrowEvent) {
        val type = e.location.block.type
        if (e.species == TreeType.TREE && plugin.structures.getConfigurationSection("oak-tree")?.getBoolean("enabled") == true){
            if (type == Material.OAK_SAPLING){
                    e.isCancelled = true
                    val location = e.location
                    val world = location.world
                    structure.spawnTerraStructure(world, location, plugin.structures.getConfigurationSection("oak-tree")?.getString("structure")) }
        }
    }
}