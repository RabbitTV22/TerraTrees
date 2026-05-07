package me.rabbittv.terraTrees.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import me.rabbittv.terraTrees.TerraTrees;
import me.rabbittv.terraTrees.utils.SpawnStructure;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;

@CommandAlias("Terratrees")
public class SpawnStructureCommand extends BaseCommand {

    private TerraTrees plugin;

    public SpawnStructureCommand(TerraTrees plugin) {
        this.plugin = plugin;
    }

    @Subcommand("spawnstructure")
    @Syntax("<Structure Name> <World Name> <Player or X <Y> Z>")
    @Description("Generate a Terra structure at a specified location")
    @CommandPermission("terratrees.spawnstructure")
    public void spawnStructure(CommandSender sender, String[] args) {
        if  (args.length > 5 || args.length < 3) {
            sender.sendMessage(MiniMessage.miniMessage().deserialize("<#dd600d>You did not follow the correct syntax!"));
        }

        SpawnStructure generate = new SpawnStructure(plugin);
        World world = Bukkit.getWorld(args[1]);

        if (args.length == 5) {
            Location location = new Location(world, Double.parseDouble(args[3]), Double.parseDouble(args[4]), Double.parseDouble(args[5]));
            generate.spawnTerraStructure(world, location, args[1]);
        } else if (args.length == 4 && sender instanceof Entity e) {
            double ypos = e.getLocation().getBlockY();
            Location location = new Location(world, Double.parseDouble(args[3]), ypos, Double.parseDouble(args[4]));
            generate.spawnTerraStructure(world, location, args[1]);
        } else {
            Location location = Bukkit.getPlayer(args[3]).getLocation();
            generate.spawnTerraStructure(world, location, args[1]);
        }

        sender.sendMessage(MiniMessage.miniMessage().deserialize("<gradient:#F454CF:#5457B6>Structure spawned!"));

    }
}
