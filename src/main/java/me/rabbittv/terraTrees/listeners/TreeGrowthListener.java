package me.rabbittv.terraTrees.listeners;

import me.rabbittv.terraTrees.TerraTrees;
import me.rabbittv.terraTrees.utils.AlignTree;
import me.rabbittv.terraTrees.utils.SpawnStructure;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.StructureGrowEvent;

public class TreeGrowthListener implements Listener {

    private final TerraTrees plugin;
    private final SpawnStructure structure;


    public TreeGrowthListener(TerraTrees plugin, SpawnStructure structure) {
        this.plugin = plugin;
        this.structure = structure;
    }
    @EventHandler
    public void onSaplingGrow(StructureGrowEvent e) {
        Material type = e.getLocation().getBlock().getType();
        if (e.getSpecies() == TreeType.TREE && plugin.structures.getConfigurationSection("oak-tree").getBoolean("enabled")) {

            if (type == Material.OAK_SAPLING) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                structure.spawnTerraStructure(world, location, plugin.structures.getConfigurationSection("oak-tree").getString("structure", "structure-terrascript-loader:oak_tree_procedural"));
            }
        } else if (e.getSpecies() == TreeType.BIG_TREE && plugin.structures.getConfigurationSection("big-oak-tree").getBoolean("enabled")) {
            if (type == Material.OAK_SAPLING) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                structure.spawnTerraStructure(world, location, plugin.structures.getConfigurationSection("oak-tree").getString("structure", "structure-terrascript-loader:oak_tree_procedural"));

            }
        } else if (e.getSpecies() == TreeType.BIRCH) {
            if (type == Material.BIRCH_SAPLING && plugin.structures.getConfigurationSection("birch-tree").getBoolean("enabled")) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                structure.spawnTerraStructure(world, location, plugin.structures.getConfigurationSection("birch-tree").getString("structure", "structure-terrascript-loader:birch_tree_procedural"));

            }
        } else if (e.getSpecies() == TreeType.ACACIA && plugin.structures.getConfigurationSection("acacia-tree").getBoolean("enabled")) {
            if (type == Material.ACACIA_SAPLING) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                structure.spawnTerraStructure(world, location, plugin.structures.getConfigurationSection("acacia-tree").getString("structure", "structure-terrascript-loader:acacia_tree_procedural"));

            }
        } else if (e.getSpecies() == TreeType.CHERRY && plugin.structures.getConfigurationSection("cherry-tree").getBoolean("enabled")) {
            if (type == Material.CHERRY_SAPLING) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                structure.spawnTerraStructure(world, location, plugin.structures.getConfigurationSection("cherry-tree").getString("structure", "structure-terrascript-loader:cherry_blossom_tree_procedural"));

            }
        } else if (e.getSpecies() == TreeType.DARK_OAK && plugin.structures.getConfigurationSection("dark-oak-tree").getBoolean("enabled")) {
            if (type == Material.DARK_OAK_SAPLING) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                Location swCorner = AlignTree.alignSouth(location, Material.DARK_OAK_SAPLING);
                if (swCorner == null) return;
                structure.spawnTerraStructure(world, swCorner, plugin.structures.getConfigurationSection("dark-oak-tree").getString("structure", "structure-terrascript-loader:large_dark_oak_tree_procedural"));

            }
        } else if (e.getSpecies() == TreeType.JUNGLE && plugin.structures.getConfigurationSection("tall-jungle-tree").getBoolean("enabled")) {
            if (type == Material.JUNGLE_SAPLING) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                structure.spawnTerraStructure(world, location, plugin.structures.getConfigurationSection("tall-jungle-tree").getString("structure", "structure-terrascript-loader:large_jungle_tree_procedural"));

            }
        } else if (e.getSpecies() == TreeType.SMALL_JUNGLE && plugin.structures.getConfigurationSection("small-jungle-tree").getBoolean("enabled")) {
            if (type == Material.JUNGLE_SAPLING) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                structure.spawnTerraStructure(world, location, plugin.structures.getConfigurationSection("small-jungle-tree").getString("structure", "structure-terrascript-loader:medium_jungle_tree_procedural"));

            }
        }else if (e.getSpecies() == TreeType.MANGROVE && plugin.structures.getConfigurationSection("mangrove-tree").getBoolean("enabled")) {
            if (type == Material.MANGROVE_PROPAGULE) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                Block propagule = location.getBlock();
                propagule.setType(Material.AIR);
                structure.spawnTerraStructure(world, location, plugin.structures.getConfigurationSection("mangrove-tree").getString("structure", "structure-terrascript-loader:mangrove_tree_procedural"));

            }
        } else if (e.getSpecies() == TreeType.PALE_OAK && plugin.structures.getConfigurationSection("pale-oak-tree").getBoolean("enabled")) {
            if (type == Material.PALE_OAK_SAPLING) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                Location swCorner = AlignTree.alignSouth(location, Material.PALE_OAK_SAPLING);
                if (swCorner == null) return;
                structure.spawnTerraStructure(world, swCorner, plugin.structures.getConfigurationSection("pale-oak-tree").getString("structure", "structure-terrascript-loader:large_pale_oak_tree_procedural"));

            }
        } else if (e.getSpecies() == TreeType.REDWOOD && plugin.structures.getConfigurationSection("spruce-tree").getBoolean("enabled")) {
            if (type == Material.SPRUCE_SAPLING) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                structure.spawnTerraStructure(world, location, plugin.structures.getConfigurationSection("spruce-tree").getString("structure", "structure-terrascript-loader:spruce_tree_procedural"));

            }
        }  else if ((e.getSpecies() == TreeType.MEGA_REDWOOD || e.getSpecies() == TreeType.MEGA_PINE) && plugin.structures.getConfigurationSection("tall-spruce-tree").getBoolean("enabled")) {
            if (type == Material.SPRUCE_SAPLING) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                Location swCorner = AlignTree.alignSouth(location, Material.SPRUCE_SAPLING);
                if (swCorner == null) return;
                structure.spawnTerraStructure(world, swCorner, plugin.structures.getConfigurationSection("tall-spruce-tree").getString("structure", "structure-terrascript-loader:giant_redwood"));

            }
        } else if (e.getSpecies() == TreeType.BROWN_MUSHROOM && plugin.structures.getConfigurationSection("brown-mushroom").getBoolean("enabled")) {
            if (type == Material.BROWN_MUSHROOM) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                structure.spawnTerraStructure(world, location, plugin.structures.getConfigurationSection("brown-mushroom").getString("structure", "structure-terrascript-loader:large_mixed_mushroom_procedural"));

            }
        } else if (e.getSpecies() == TreeType.RED_MUSHROOM && plugin.structures.getConfigurationSection("red-mushroom").getBoolean("enabled")) {
            if (type == Material.RED_MUSHROOM) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                structure.spawnTerraStructure(world, location, plugin.structures.getConfigurationSection("red-mushroom").getString("structure", "structure-terrascript-loader:large_mixed_mushroom_procedural"));

            }
        } else if (e.getSpecies() == TreeType.WARPED_FUNGUS && plugin.structures.getConfigurationSection("warped-fungus").getBoolean("enabled")) {
            if (type == Material.WARPED_FUNGUS) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                structure.spawnTerraStructure(world, location, plugin.structures.getConfigurationSection("warped-fungus").getString("structure", "structure-terrascript-loader:huge_warped_fungus"));

            }
        } else if (e.getSpecies() == TreeType.CRIMSON_FUNGUS && plugin.structures.getConfigurationSection("crimson-fungus").getBoolean("enabled")) {
            if (type == Material.CRIMSON_FUNGUS) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                structure.spawnTerraStructure(world, location, plugin.structures.getConfigurationSection("crimson-fungus").getString("structure", "structure-terrascript-loader:huge_crimson_fungus"));

            }
        }

    }
}
