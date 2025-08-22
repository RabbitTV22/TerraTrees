package me.rabbittv.terraTrees.commands;


import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.dfsek.terra.api.config.ConfigPack;
import com.dfsek.terra.api.world.WritableWorld;
import com.dfsek.terra.api.world.biome.Biome;
import com.dfsek.terra.bukkit.world.BukkitServerWorld;
import me.rabbittv.terraTrees.TerraTrees;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CommandAlias("terra")
public class BiomeListCommand extends BaseCommand {

    private TerraTrees plugin;

    public BiomeListCommand(TerraTrees plugin) {
        this.plugin = plugin;
    }

    @Subcommand("listbiomes")
    @Description("List all terra biomes in the current world. Also prints to plugins/TerraTrees/biome_list.txt")
    @CommandPermission("terratrees.listbiomes")
    public void listBiomes(CommandSender sender) throws IOException {
        WritableWorld world = new BukkitServerWorld(Bukkit.getWorld(plugin.settings.getString("debug-world-name", "world")));
        ConfigPack pack = world.getPack();
        List<String> biome_names = new ArrayList<>();
        List<String> biome_names_raw = new ArrayList<>();
        pack.getRegistry(Biome.class).forEach((biome) -> {
           String biome_name = biome.toString().toLowerCase()
                   .replace("{biome:", "")
                   .replace("}", "");
           biome_names.add("terra:" + pack.getID().toLowerCase() + "/" + pack.getID().toLowerCase() + "/" + biome_name);
           biome_names_raw.add(biome_name);
        });
        Collections.sort(biome_names);
        Collections.sort(biome_names_raw);
        FileWriter writer = new FileWriter(new File(plugin.getDataFolder(), "biome_list.txt"));
        FileWriter writer_raw = new FileWriter(new File(plugin.getDataFolder(), "biome_list_raw.txt"));
        for (String biome_name : biome_names) {
            sender.sendMessage(biome_name);
            writer.write(biome_name + System.lineSeparator());
        }
        writer.close();
        for (String biome_name : biome_names_raw) {
            writer_raw.write(biome_name + System.lineSeparator());
        }
        writer_raw.close();
    }

}
