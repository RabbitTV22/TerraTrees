package me.rabbittv.terraTrees;

import com.dfsek.terra.api.config.ConfigPack;
import com.dfsek.terra.api.registry.key.RegistryKey;
import com.dfsek.terra.api.structure.Structure;
import com.dfsek.terra.api.util.Rotation;
import com.dfsek.terra.api.util.vector.Vector3Int;
import com.dfsek.terra.api.world.WritableWorld;
import com.dfsek.terra.bukkit.world.BukkitServerWorld;

import java.util.Optional;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class TerraTrees extends JavaPlugin implements Listener {
    private final ConfigurationSection config = getConfig().getConfigurationSection("settings");
    private final ConfigurationSection structures = getConfig().getConfigurationSection("structures");

    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        saveDefaultConfig();
        if (this.config.getBoolean("debug")) {
            World bukkitWorld = Bukkit.getWorld(config.getString("debug-world-name", "world"));
            BukkitServerWorld world = new BukkitServerWorld(bukkitWorld);
            ConfigPack pack = world.getPack();
            pack.getRegistry(Structure.class).keys().forEach((key) -> {
                this.getLogger().info("Available structure: " + key.toString());
            });
        }
    }

    public void spawnTerraStructure(WritableWorld world, Vector3Int location, String structureId) {
        ConfigPack pack = world.getPack();
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

            boolean generated = structure.generate(location, world, random, rotation);

            if (generated && config.getBoolean("debug")) {
                getLogger().info("Successfully spawned structure '" + structureId + "' at " + location.toString());
            } else if (config.getBoolean("debug")) {
                getLogger().warning("Failed to spawn structure '" + structureId + "' at " + location.toString());
            }
        }
    }

    @EventHandler
    public void onSaplingGrow(StructureGrowEvent e) {
        if (e.getSpecies() == TreeType.TREE) {
            Material type = e.getLocation().getBlock().getType();
            if (type == Material.OAK_SAPLING) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                BukkitServerWorld w = new BukkitServerWorld(world);
                Vector3Int l = Vector3Int.of((int)Math.floor(location.getX()), (int)Math.floor(location.getY()), (int)Math.floor(location.getZ()));

                this.spawnTerraStructure(w, l, structures.getString("oak-tree", "structure-terrascript-loader:oak_tree_procedural"));
            }
        } else if (e.getSpecies() == TreeType.BIRCH) {
            Material type = e.getLocation().getBlock().getType();
            if (type == Material.BIRCH_SAPLING) {
                e.setCancelled(true);
                Location location = e.getLocation();
                World world = location.getWorld();
                BukkitServerWorld w = new BukkitServerWorld(world);
                Vector3Int l = Vector3Int.of((int)Math.floor(location.getX()), (int)Math.floor(location.getY()), (int)Math.floor(location.getZ()));

                this.spawnTerraStructure(w, l, structures.getString("birch-tree", "structure-terrascript-loader:birch_tree_procedural"));

            }
        }

    }

    public void onDisable() {
    }
}