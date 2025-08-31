package me.rabbittv.terraTrees.listeners

import me.rabbittv.terraTrees.TerraTrees
import me.rabbittv.terraTrees.utils.AlignTree
import me.rabbittv.terraTrees.utils.SpawnStructure
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.TreeType.*
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.StructureGrowEvent

class TreeGrowthListener(private val plugin: TerraTrees, private val structure: SpawnStructure, private val alignTree: AlignTree): Listener {
    @EventHandler
    fun onSaplingGrowth(e: StructureGrowEvent) {
        val type = e.location.block.type
        val location = e.location
        val world = location.world
        if (e.species == TREE && plugin.structures.getConfigurationSection("oak-tree")?.getBoolean("enabled") == true) {
            e.isCancelled = true
            structure.spawnTerraStructure(
                world,
                location,
                plugin.structures.getConfigurationSection("oak-tree")?.getString("structure")
            )
        } else if (e.species == BIG_TREE && plugin.structures.getConfigurationSection("big-oak-tree")?.getBoolean("enabled") == true) {
            e.isCancelled = true
            structure.spawnTerraStructure(
                world,
                location,
                plugin.structures.getConfigurationSection("big-oak-tree")?.getString("structure")
            )
        } else if (e.species == BIRCH && plugin.structures.getConfigurationSection("birch-tree")?.getBoolean("enabled") == true) {
            e.isCancelled = true
            structure.spawnTerraStructure(
                world,
                location,
                plugin.structures.getConfigurationSection("birch-tree")?.getString("structure")
            )
        } else if (e.species == ACACIA && plugin.structures.getConfigurationSection("acacia-tree")?.getBoolean("enabled") == true) {
            e.isCancelled = true
            structure.spawnTerraStructure(
                world,
                location,
                plugin.structures.getConfigurationSection("acacia-tree")?.getString("structure")
            )
        } else if (e.species == CHERRY && plugin.structures.getConfigurationSection("cherry-tree")?.getBoolean("enabled") == true) {
            e.isCancelled = true
            structure.spawnTerraStructure(
                world,
                location,
                plugin.structures.getConfigurationSection("cherry-tree")?.getString("structure")
            )
        } else if (e.species == DARK_OAK && plugin.structures.getConfigurationSection("dark-oak-tree")?.getBoolean("enabled") == true) {
            if (type == Material.DARK_OAK_SAPLING) {
                val swCorner = alignTree.alignSouth(location, Material.DARK_OAK_SAPLING)
                if (swCorner != null) {
                    e.isCancelled = true
                    structure.spawnTerraStructure(
                        world,
                        swCorner,
                        plugin.structures.getConfigurationSection("dark-oak-tree")?.getString("structure")
                    )
                }
            }
        } else if (e.species == JUNGLE && plugin.structures.getConfigurationSection("tall-jungle-tree")?.getBoolean("enabled") == true) {
            if (type == Material.JUNGLE_SAPLING) {
                val swCorner = alignTree.alignSouth(location, Material.JUNGLE_SAPLING)
                if (swCorner != null) {
                    e.isCancelled = true
                    structure.spawnTerraStructure(
                        world,
                        swCorner,
                        plugin.structures.getConfigurationSection("tall-jungle-tree")?.getString("structure")
                    )
                }
            }
        } else if (e.species == SMALL_JUNGLE && plugin.structures.getConfigurationSection("small-jungle-tree")?.getBoolean("enabled") == true) {
            e.isCancelled = true
            structure.spawnTerraStructure(
                world,
                location,
                plugin.structures.getConfigurationSection("small-jungle-tree")?.getString("structure")
            )
        } else if (e.species == MANGROVE && plugin.structures.getConfigurationSection("mangrove-tree")?.getBoolean("enabled") == true) {
            e.isCancelled = true
            val propagule = location.block
            propagule.type = Material.AIR
            structure.spawnTerraStructure(
                world,
                location,
                plugin.structures.getConfigurationSection("mangrove-tree")?.getString("structure")
            )
        } else if (e.species == PALE_OAK && plugin.structures.getConfigurationSection("pale-oak-tree")?.getBoolean("enabled") == true) {
            if (type == Material.PALE_OAK_SAPLING) {
                val swCorner = alignTree.alignSouth(location, Material.PALE_OAK_SAPLING)
                if (swCorner != null) {
                    e.isCancelled = true
                    structure.spawnTerraStructure(
                        world,
                        swCorner,
                        plugin.structures.getConfigurationSection("pale-oak-tree")?.getString("structure")
                    )
                }
            }
        } else if (e.species == REDWOOD && plugin.structures.getConfigurationSection("spruce-tree")?.getBoolean("enabled") == true) {
            e.isCancelled = true
            structure.spawnTerraStructure(
                 world,
                 location,
                 plugin.structures.getConfigurationSection("spruce-tree")?.getString("structure")
                )
        } else if ((e.species == MEGA_PINE || e.species == MEGA_REDWOOD) && plugin.structures.getConfigurationSection("tall-spruce-tree")?.getBoolean("enabled") == true) {
            if (type == Material.SPRUCE_SAPLING) {
                val swCorner = alignTree.alignSouth(location, Material.SPRUCE_SAPLING)
                if (swCorner != null) {
                    e.isCancelled = true
                    structure.spawnTerraStructure(
                        world,
                        swCorner,
                        plugin.structures.getConfigurationSection("tall-spruce-tree")?.getString("structure")
                    )
                }
            }
        } else if (e.species == BROWN_MUSHROOM && plugin.structures.getConfigurationSection("brown-mushroom")?.getBoolean("enabled") == true) {
            e.isCancelled = true
            structure.spawnTerraStructure(
                world,
                location,
                plugin.structures.getConfigurationSection("brown-mushroom")?.getString("structure")
            )
        } else if (e.species == RED_MUSHROOM && plugin.structures.getConfigurationSection("red-mushroom")?.getBoolean("enabled") == true) {
            e.isCancelled = true
            structure.spawnTerraStructure(
                world,
                location,
                plugin.structures.getConfigurationSection("red-mushroom")?.getString("structure")
            )
        } else if (e.species == WARPED_FUNGUS && plugin.structures.getConfigurationSection("warped-fungus")?.getBoolean("enabled") == true) {
            e.isCancelled = true
            structure.spawnTerraStructure(
                world,
                location,
                plugin.structures.getConfigurationSection("warped-fungus")?.getString("structure")
            )
        } else if (e.species == CRIMSON_FUNGUS && plugin.structures.getConfigurationSection("crimson-fungus")?.getBoolean("enabled") == true) {
            e.isCancelled = true
            structure.spawnTerraStructure(
                world,
                location,
                plugin.structures.getConfigurationSection("crimson-fungus")?.getString("structure")
            )
        }
    }
}