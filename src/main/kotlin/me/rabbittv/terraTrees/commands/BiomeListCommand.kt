package me.rabbittv.terraTrees.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Description
import co.aikar.commands.annotation.Subcommand
import com.dfsek.terra.api.world.biome.Biome
import com.dfsek.terra.bukkit.world.BukkitServerWorld
import me.rabbittv.terraTrees.TerraTrees
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import java.io.File
import java.io.FileWriter
import java.util.Collections

@CommandAlias("terra")
class BiomeListCommand(private val plugin: TerraTrees) : BaseCommand() {
   @Subcommand("listbiomes")
   @Description("List all terra biomes in the current world. Also prints to plugins/TerraTrees/biome_list.txt")
   @CommandPermission("terratrees.listbiomes")
   fun listBiomes(sender: CommandSender) {
       val world = BukkitServerWorld(Bukkit.getWorld(plugin.settings.getString("debug-world-name", "world")!!))
       val pack = world.pack
       val biome_names = ArrayList<String>()
       val biome_names_raw = ArrayList<String>()
       pack.getRegistry(Biome::class.java).forEach { biome ->
           val biome_name = biome.toString().lowercase()
               .replace("{biome:", "")
               .replace("}", "")
           biome_names.add("terra:${pack.id.lowercase()}/${pack.id.lowercase()}$biome_name")
           biome_names_raw.add(biome_name)
       }
       Collections.sort(biome_names)
       Collections.sort(biome_names_raw)
       val writer = FileWriter(File(plugin.dataFolder, "biome_list.txt"))
       val writer_raw = FileWriter(File(plugin.dataFolder, "biome_list_raw.txt"))
       for (biome_name in biome_names_raw) {
           sender.sendMessage(biome_name)
           writer.write(biome_name + System.lineSeparator())
       }
       writer.close()
       for (biome_name in biome_names_raw) {
           writer_raw.write(biome_name + System.lineSeparator())
       }
       writer_raw.close()
   }
}