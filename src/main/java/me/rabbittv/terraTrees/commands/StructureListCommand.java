package me.rabbittv.terraTrees.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import com.dfsek.terra.api.config.ConfigPack;
import com.dfsek.terra.api.structure.Structure;
import com.dfsek.terra.api.world.WritableWorld;
import com.dfsek.terra.bukkit.world.BukkitServerWorld;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import me.rabbittv.terraTrees.TerraTrees;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CommandAlias("terratrees")
public class StructureListCommand extends BaseCommand {

    private TerraTrees plugin;

    public StructureListCommand(TerraTrees plugin) {
        this.plugin = plugin;
    }

    @Subcommand("liststructures")
    @Description("List all Terra structures in the world set in config.yml. Also prints to plugins/TerraTrees/structure_list.txt")
    @CommandPermission("terratrees.liststructures")
    public void listStructures(CommandSender sender) throws IOException {
        WritableWorld world = new BukkitServerWorld(Bukkit.getWorld(plugin.settings.getString("debug-world-name", "world")));
        ConfigPack pack = world.getPack();

        List<String> structures = new ArrayList<>();

        pack.getRegistry(Structure.class).keys().forEach((structure_key) -> {
            String structure = structure_key.toString().toLowerCase();
            structures.add(structure);
            });
        Collections.sort(structures);
        FileWriter writer = new FileWriter(new File(plugin.getDataFolder(), "structure_list.txt"));
        for (String structure : structures) {
            sender.sendMessage(structure);
            writer.write(structure + System.lineSeparator());
        }
        writer.close();
    }

}
