package me.rabbittv.terraTrees;

import com.dfsek.terra.api.config.ConfigPack;
import com.dfsek.terra.api.registry.key.RegistryKey;
import com.dfsek.terra.api.structure.Structure;
import com.dfsek.terra.api.util.vector.Vector3Int;
import com.dfsek.terra.api.world.WritableWorld;
import com.dfsek.terra.bukkit.world.BukkitServerWorld;
import java.util.Optional;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class TerraTrees extends JavaPlugin implements Listener {
    public boolean debug = false;

    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
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
                ConfigPack pack = w.getPack();
                if (this.debug) {
                    pack.getRegistry(Structure.class).keys().forEach((key) -> {
                        this.getLogger().info("Available structure: " + key.toString());
                    });
                }

                this.spawnTerraStructure(w, l, "structure-terrascript-loader:oak_tree_procedural");
            }
        }

    }

    public void onDisable() {
    }
}