package me.rabbittv.terraTrees.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import me.rabbittv.terraTrees.TerraTrees;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;

@CommandAlias("terratrees")
public class ReloadCommand extends BaseCommand {

    private final TerraTrees plugin;

    public ReloadCommand(TerraTrees plugin) {
        this.plugin = plugin;
    }

    @Subcommand("reload")
    @Description("Reload the plugin.")
    @CommandPermission("terratrees.reload")
    public void reload(CommandSender sender) {
        plugin.loadConfig();
        sender.sendMessage(MiniMessage.miniMessage().deserialize(plugin.messages.getString("config-reloaded", "<pride><b>TerraTrees config reloaded!")));
    }

}
