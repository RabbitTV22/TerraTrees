package me.rabbittv.terraTrees;

import co.aikar.commands.PaperCommandManager;
import com.dfsek.terra.api.config.ConfigPack;
import com.dfsek.terra.api.structure.Structure;
import com.dfsek.terra.bukkit.world.BukkitServerWorld;
import com.dfsek.terra.api.world.WritableWorld;
import me.rabbittv.terraTrees.commands.BiomeListCommand;
import me.rabbittv.terraTrees.commands.ReloadCommand;
import me.rabbittv.terraTrees.commands.SpawnStructureCommand;
import me.rabbittv.terraTrees.commands.StructureListCommand;
import me.rabbittv.terraTrees.listeners.TreeGrowthListener;
import me.rabbittv.terraTrees.utils.SpawnStructure;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;

public final class TerraTrees extends JavaPlugin {
    public ConfigurationSection settings = getConfig().getConfigurationSection("settings");
    public ConfigurationSection structures = getConfig().getConfigurationSection("structures");


    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new TreeGrowthListener(this, new SpawnStructure(this)), this);
        saveDefaultConfig();
        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new BiomeListCommand(this));
        manager.registerCommand(new ReloadCommand(this));
        manager.registerCommand(new StructureListCommand(this));
        manager.registerCommand(new SpawnStructureCommand(this));
        if (this.settings.getBoolean("debug")) {
            World bukkitWorld = Bukkit.getWorld(settings.getString("debug-world-name", "world"));
            WritableWorld world = new BukkitServerWorld(bukkitWorld);
            ConfigPack pack = world.getPack();
            pack.getRegistry(Structure.class).keys().forEach((key) -> {
                this.getLogger().info("Available structure: " + key.toString());
            });
        }
    }

    public void loadConfig() {
        reloadConfig();

        this.settings = getConfig().getConfigurationSection("settings");
        this.structures = getConfig().getConfigurationSection("structures");
    }

    public ConfigurationSection getSettings() {
        return settings;
    }

    public ConfigurationSection getStructures() {
        return structures;
    }

}
