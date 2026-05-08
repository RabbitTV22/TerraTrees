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
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class TerraTrees extends JavaPlugin {
    public ConfigurationSection settings = getConfig().getConfigurationSection("settings");
    public ConfigurationSection structures = getConfig().getConfigurationSection("structures");
    private File messagesFile;
    public YamlConfiguration messages;

    public void onEnable() {
        int pluginId = 31191;
        Metrics metrics = new Metrics(this, pluginId);
        saveResource("messages.yml", false);
        messagesFile = new File(this.getDataFolder(), "messages.yml");
        messages = YamlConfiguration.loadConfiguration(messagesFile);
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new TreeGrowthListener(this, new SpawnStructure(this)), this);
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
        this.messages = YamlConfiguration.loadConfiguration(messagesFile);
        this.settings = getConfig().getConfigurationSection("settings");
        this.structures = getConfig().getConfigurationSection("structures");
    }

    public ConfigurationSection getSettings() {
        return settings;
    }

    public ConfigurationSection getStructures() {
        return structures;
    }

    public YamlConfiguration getMessages() {
        return messages;
    }

}
