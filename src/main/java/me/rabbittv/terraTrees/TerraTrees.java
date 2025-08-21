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

import me.rabbittv.terraTrees.listeners.TreeGrowthListener;
import me.rabbittv.terraTrees.utils.SpawnStructure;
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
    public final ConfigurationSection settings = getConfig().getConfigurationSection("settings");
    public final ConfigurationSection structures = getConfig().getConfigurationSection("structures");


    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new TreeGrowthListener(this, new SpawnStructure(this)), this);
        saveDefaultConfig();
        if (this.settings.getBoolean("debug")) {
            World bukkitWorld = Bukkit.getWorld(settings.getString("debug-world-name", "world"));
            WritableWorld world = new BukkitServerWorld(bukkitWorld);
            ConfigPack pack = world.getPack();
            pack.getRegistry(Structure.class).keys().forEach((key) -> {
                this.getLogger().info("Available structure: " + key.toString());
            });
        }
    }

}
