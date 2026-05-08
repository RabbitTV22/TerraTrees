package me.rabbittv.terraTrees.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.annotation.*;
import me.rabbittv.terraTrees.TerraTrees;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;

@CommandAlias("terratrees|tt")
public class RootCommand extends BaseCommand {

    private final TerraTrees plugin;

    public RootCommand(TerraTrees plugin) {
        this.plugin = plugin;
    }

    @Default
    @Description("Base command for TerraTrees")
    public void TerraTreesCommand(CommandSender sender) {
        sender.sendMessage(MiniMessage.miniMessage().deserialize("<gradient:#357E3D:#56F454>This server is running TerraTrees version " + plugin.getPluginMeta().getVersion() + "!"));
    }

    @HelpCommand
    public void TerraTreesHelpCommand(CommandSender sender, CommandHelp help) {
        help.showHelp();
    }
}
