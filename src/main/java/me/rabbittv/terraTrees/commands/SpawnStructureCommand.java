package me.rabbittv.terraTrees.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.dfsek.terra.api.config.ConfigPack;
import com.dfsek.terra.api.structure.Structure;
import com.dfsek.terra.api.world.WritableWorld;
import com.dfsek.terra.bukkit.world.BukkitServerWorld;
import me.rabbittv.terraTrees.TerraTrees;
import me.rabbittv.terraTrees.utils.SpawnStructure;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.UUID;

@CommandAlias("terratrees")
public class SpawnStructureCommand extends BaseCommand {

    private final TerraTrees plugin;

    public SpawnStructureCommand(TerraTrees plugin) {
        this.plugin = plugin;
    }

    private Entity getEntity(String input) {
        Player p = Bukkit.getPlayer(input);

        if  (p != null) {
            return p;
        }
        try  {
            UUID uuid = UUID.fromString(input);
            return Bukkit.getEntity(uuid);
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }

    private String getStructure(String input, World w) {
        WritableWorld world = new BukkitServerWorld(w);
        ConfigPack pack = world.getPack();

        return pack.getRegistry(Structure.class)
                .keys()
                .stream()
                .map(key -> key.toString().toLowerCase())
                .filter(name -> name.equalsIgnoreCase(input))
                .findFirst()
                .orElse(null);
    }


    @Subcommand("spawnstructure")
    @Syntax("<Structure Name> <World Name> <Player or X <Y> Z>")
    @Description("Generate a Terra structure at a specified location")
    @CommandPermission("terratrees.spawnstructure")
    public void spawnStructure(CommandSender sender, String[] args) {

        if  (args.length > 5 || args.length < 1) {
            sender.sendMessage(MiniMessage.miniMessage().deserialize("<#dd600d>You did not follow the correct syntax!"));
            return;
        }

        SpawnStructure generate = new SpawnStructure(plugin);

        if (Bukkit.getWorld(args[1]) == null) {
            sender.sendMessage(MiniMessage.miniMessage().deserialize("<#dd600d>World not found!"));
            return;
        }

        // /terratrees spawnstructure <structure> <world> <x> <y> <z>
        if (args.length == 5) {

            String structure = getStructure(args[0], Bukkit.getWorld(args[1]));
            if (structure == null) {
                sender.sendMessage(MiniMessage.miniMessage().deserialize("<#dd600d>Structure not found!"));
                return;
            }
            try {
                double x = Double.parseDouble(args[2]);
                double y = Double.parseDouble(args[3]);
                double z = Double.parseDouble(args[4]);
                Location location = new Location(Bukkit.getWorld(args[1]), x, y, z);
                generate.spawnTerraStructure(Bukkit.getWorld(args[1]), location, structure);
            } catch (NumberFormatException ignored) {
                sender.sendMessage(MiniMessage.miniMessage().deserialize("<#dd600d>Invalid coordinates!"));
                return;
            }


        // /terratrees spawnstructure <structure> <world> <x> <z>
        } else if (args.length == 4) {

            if (sender instanceof Entity e) {
                String structure = getStructure(args[0], Bukkit.getWorld(args[1]));
                if (structure == null) {
                    sender.sendMessage(MiniMessage.miniMessage().deserialize("<#dd600d>Structure not found!"));
                    return;
                }
                try {
                    double x = Double.parseDouble(args[2]);
                    double z = Double.parseDouble(args[3]);
                    double y = e.getLocation().getBlockY();
                    Location location = new Location(Bukkit.getWorld(args[1]), x, y, z);
                    generate.spawnTerraStructure(Bukkit.getWorld(args[1]), location, structure);
                } catch (NumberFormatException ignored) {
                    sender.sendMessage(MiniMessage.miniMessage().deserialize("<#dd600d>Invalid coordinates!"));
                    return;
                }
            } else {
                sender.sendMessage(MiniMessage.miniMessage().deserialize("<#dd600d>This command has to be run by an entity!"));
                return;
            }

        // /terratrees spawnstructure <structure> <world> <entity|player>
        } else if (args.length == 3) {
            Entity e = getEntity(args[2]);
            String structure = getStructure(args[0], Bukkit.getWorld(args[1]));
            if (structure == null) {
                sender.sendMessage(MiniMessage.miniMessage().deserialize("<#dd600d>Structure not found!"));
                return;
            }
            if (e != null) {
                Location location = e.getLocation();
                generate.spawnTerraStructure(Bukkit.getWorld(args[1]), location, structure);
            } else {
                sender.sendMessage(MiniMessage.miniMessage().deserialize("<#dd600d>Player or entity not found!"));
                return;
            }

        // /terratrees spawnstructure <structure> <entity|player>
        } else if (args.length == 2) {
            Entity e = getEntity(args[0]);

            if (e != null) {
                Location location = e.getLocation();
                World w = e.getWorld();

                String structure = getStructure(args[0], w);
                if (structure == null) {
                    sender.sendMessage(MiniMessage.miniMessage().deserialize("<#dd600d>Structure not found!"));
                    return;
                }

                generate.spawnTerraStructure(w, location, structure);
            } else {
                sender.sendMessage(MiniMessage.miniMessage().deserialize("<#dd600d>Player or entity not found!"));
                return;
            }

        // /terratrees spawnstructure <structure>
        } else {

            if (sender instanceof Entity e) {
                String structure = getStructure(args[0], e.getWorld());
                if (structure == null) {
                    sender.sendMessage(MiniMessage.miniMessage().deserialize("<#dd600d>Structure not found!"));
                    return;
                }
                generate.spawnTerraStructure(e.getWorld(), e.getLocation(), structure);
            }
        }

        sender.sendMessage(MiniMessage.miniMessage().deserialize("<gradient:#F454CF:#5457B6>Structure spawned!"));

    }
}
