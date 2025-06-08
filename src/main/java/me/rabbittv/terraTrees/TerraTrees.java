package me.rabbittv.terraTrees;

import com.dfsek.terra.api.config.ConfigPack;
import com.dfsek.terra.api.registry.key.RegistryKey;
import com.dfsek.terra.api.structure.Structure;
import com.dfsek.terra.api.util.Rotation;
import com.dfsek.terra.api.util.vector.Vector3Int;
import com.dfsek.terra.bukkit.world.BukkitServerWorld;
import com.dfsek.terra.api.world.WritableWorld;

import java.util.Optional;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class TerraTrees extends JavaPlugin implements Listener {
    private final ConfigurationSection config = getConfig().getConfigurationSection("settings");
    private final ConfigurationSection structures = getConfig().getConfigurationSection("structures");


    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        saveDefaultConfig();
        if (this.config.getBoolean("debug")) {
            World bukkitWorld = Bukkit.getWorld(config.getString("debug-world-name", "world"));
            WritableWorld world = new BukkitServerWorld(bukkitWorld);
            ConfigPack pack = world.getPack();
            pack.getRegistry(Structure.class).keys().forEach((key) -> {
                this.getLogger().info("Available structure: " + key.toString());
            });
        }
    }

    public void spawnTerraStructure(World world, @NotNull Location location, String structureId) {
        BukkitServerWorld TerraWorld = new BukkitServerWorld(world);
        Vector3Int vector = Vector3Int.of((int)Math.floor(location.getX()), (int)Math.floor(location.getY()), (int)Math.floor(location.getZ()));

        ConfigPack pack = TerraWorld.getPack();
        if (pack == null) {
            this.getLogger().warning("Could not find a config pack for the current world.");
        } else {
            RegistryKey structureKey = RegistryKey.parse(structureId);
            Optional<Structure> structureOptional = pack.getRegistry(Structure.class).get(structureKey);
            if (structureOptional.isEmpty()) {
                this.getLogger().warning("Structure with ID '" + structureId + "' not found in config pack.");
            }
            Structure structure = structureOptional.get();

            Random random = new Random();
            Rotation rotation = Rotation.NONE;

            boolean generated = structure.generate(vector, TerraWorld, random, rotation);

            if (generated && config.getBoolean("debug")) {
                getLogger().info("Successfully spawned structure '" + structureId + "' at " + location.toString());
            } else if (config.getBoolean("debug")) {
                getLogger().warning("Failed to spawn structure '" + structureId + "' at " + location.toString());
            }
        }
    }

    private Location alingTree(Location origin, Material saplingType) {
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



    @EventHandler
    public void onSaplingGrow(StructureGrowEvent e) {
        Material type = e.getLocation().getBlock().getType();
        if (e.getSpecies() == TreeType.TREE && structures.getConfigurationSection("oak-tree").getBoolean("enabled")) {

            if (type == Material.OAK_SAPLING) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                this.spawnTerraStructure(world, location, structures.getConfigurationSection("oak-tree").getString("structure", "structure-terrascript-loader:oak_tree_procedural"));
            }
        } else if (e.getSpecies() == TreeType.BIG_TREE && structures.getConfigurationSection("big-oak-tree").getBoolean("enabled")) {
            if (type == Material.OAK_SAPLING) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                this.spawnTerraStructure(world, location, structures.getConfigurationSection("oak-tree").getString("structure", "structure-terrascript-loader:oak_tree_procedural"));

            }
        } else if (e.getSpecies() == TreeType.BIRCH) {
            if (type == Material.BIRCH_SAPLING && structures.getConfigurationSection("birch-tree").getBoolean("enabled")) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                this.spawnTerraStructure(world, location, structures.getConfigurationSection("birch-tree").getString("structure", "structure-terrascript-loader:birch_tree_procedural"));

            }
        } else if (e.getSpecies() == TreeType.ACACIA && structures.getConfigurationSection("acacia-tree").getBoolean("enabled")) {
            if (type == Material.ACACIA_SAPLING) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                this.spawnTerraStructure(world, location, structures.getConfigurationSection("acacia-tree").getString("structure", "structure-terrascript-loader:acacia_tree_procedural"));

            }
        } else if (e.getSpecies() == TreeType.CHERRY && structures.getConfigurationSection("cherry-tree").getBoolean("enabled")) {
            if (type == Material.CHERRY_SAPLING) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                this.spawnTerraStructure(world, location, structures.getConfigurationSection("cherry-tree").getString("structure", "structure-terrascript-loader:cherry_blossom_tree_procedural"));

            }
        } else if (e.getSpecies() == TreeType.DARK_OAK && structures.getConfigurationSection("dark-oak-tree").getBoolean("enabled")) {
            if (type == Material.DARK_OAK_SAPLING) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                Location swCorner = alingTree(location, Material.DARK_OAK_SAPLING);
                if (swCorner == null) return;
                this.spawnTerraStructure(world, swCorner, structures.getConfigurationSection("dark-oak-tree").getString("structure", "structure-terrascript-loader:large_dark_oak_tree_procedural"));

            }
        } else if (e.getSpecies() == TreeType.JUNGLE && structures.getConfigurationSection("tall-jungle-tree").getBoolean("enabled")) {
            if (type == Material.JUNGLE_SAPLING) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                this.spawnTerraStructure(world, location, structures.getConfigurationSection("tall-jungle-tree").getString("structure", "structure-terrascript-loader:large_jungle_tree_procedural"));

            }
        } else if (e.getSpecies() == TreeType.SMALL_JUNGLE && structures.getConfigurationSection("small-jungle-tree").getBoolean("enabled")) {
            if (type == Material.JUNGLE_SAPLING) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                this.spawnTerraStructure(world, location, structures.getConfigurationSection("small-jungle-tree").getString("structure", "structure-terrascript-loader:medium_jungle_tree_procedural"));

            }
        }else if (e.getSpecies() == TreeType.MANGROVE && structures.getConfigurationSection("mangrove-tree").getBoolean("enabled")) {
            if (type == Material.MANGROVE_PROPAGULE) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                Block propagule = location.getBlock();
                propagule.setType(Material.AIR);
                this.spawnTerraStructure(world, location, structures.getConfigurationSection("mangrove-tree").getString("structure", "structure-terrascript-loader:mangrove_tree_procedural"));

            }
        } else if (e.getSpecies() == TreeType.PALE_OAK && structures.getConfigurationSection("pale-oak-tree").getBoolean("enabled")) {
            if (type == Material.PALE_OAK_SAPLING) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                Location swCorner = alingTree(location, Material.PALE_OAK_SAPLING);
                if (swCorner == null) return;
                this.spawnTerraStructure(world, swCorner, structures.getConfigurationSection("pale-oak-tree").getString("structure", "structure-terrascript-loader:large_pale_oak_tree_procedural"));

            }
        } else if (e.getSpecies() == TreeType.REDWOOD && structures.getConfigurationSection("spruce-tree").getBoolean("enabled")) {
            if (type == Material.SPRUCE_SAPLING) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                this.spawnTerraStructure(world, location, structures.getConfigurationSection("spruce-tree").getString("structure", "structure-terrascript-loader:spruce_tree_procedural"));

            }
        }  else if ((e.getSpecies() == TreeType.MEGA_REDWOOD || e.getSpecies() == TreeType.MEGA_PINE) && structures.getConfigurationSection("tall-spruce-tree").getBoolean("enabled")) {
            if (type == Material.SPRUCE_SAPLING) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                Location swCorner = alingTree(location, Material.SPRUCE_SAPLING);
                if (swCorner == null) return;
                this.spawnTerraStructure(world, swCorner, structures.getConfigurationSection("tall-spruce-tree").getString("structure", "structure-terrascript-loader:giant_redwood"));

            }
        } else if (e.getSpecies() == TreeType.BROWN_MUSHROOM && structures.getConfigurationSection("brown-mushroom").getBoolean("enabled")) {
            if (type == Material.BROWN_MUSHROOM) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                this.spawnTerraStructure(world, location, structures.getConfigurationSection("brown-mushroom").getString("structure", "structure-terrascript-loader:large_mixed_mushroom_procedural"));

            }
        } else if (e.getSpecies() == TreeType.RED_MUSHROOM && structures.getConfigurationSection("red-mushroom").getBoolean("enabled")) {
            if (type == Material.RED_MUSHROOM) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                this.spawnTerraStructure(world, location, structures.getConfigurationSection("red-mushroom").getString("structure", "structure-terrascript-loader:large_mixed_mushroom_procedural"));

            }
        } else if (e.getSpecies() == TreeType.WARPED_FUNGUS && structures.getConfigurationSection("warped-fungus").getBoolean("enabled")) {
            if (type == Material.WARPED_FUNGUS) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                this.spawnTerraStructure(world, location, structures.getConfigurationSection("warped-fungus").getString("structure", "structure-terrascript-loader:huge_warped_fungus"));

            }
        } else if (e.getSpecies() == TreeType.CRIMSON_FUNGUS && structures.getConfigurationSection("crimson-fungus").getBoolean("enabled")) {
            if (type == Material.CRIMSON_FUNGUS) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                this.spawnTerraStructure(world, location, structures.getConfigurationSection("crimson-fungus").getString("structure", "structure-terrascript-loader:huge_crimson_fungus"));

            }
        }

    }

}
