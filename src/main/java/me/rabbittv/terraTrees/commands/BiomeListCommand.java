package me.rabbittv.terraTrees.commands;


import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import com.dfsek.terra.api.config.ConfigPack;
import com.dfsek.terra.api.world.WritableWorld;
import com.dfsek.terra.api.world.biome.Biome;
import com.dfsek.terra.bukkit.world.BukkitServerWorld;
import me.rabbittv.terraTrees.TerraTrees;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

@CommandAlias("terra")
public class BiomeListCommand extends BaseCommand {

    private TerraTrees plugin;

    public BiomeListCommand(TerraTrees plugin) {
        this.plugin = plugin;
    }

    @Subcommand("listbiomes")
    @CommandPermission("terratrees.listbiomes")
    public void listBiomes(CommandSender sender) {
        WritableWorld world = new BukkitServerWorld(Bukkit.getWorld(plugin.settings.getString("debug-world-name", "world")));
        ConfigPack pack = world.getPack();
        pack.getRegistry(Biome.class).forEach((biome) -> {
           sender.sendMessage(biome.toString());
        });
    }

}
