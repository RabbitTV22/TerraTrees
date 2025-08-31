package me.rabbittv.terraTrees

import co.aikar.commands.PaperCommandManager
import com.dfsek.terra.api.config.ConfigPack
import com.dfsek.terra.api.structure.Structure
import com.dfsek.terra.api.world.WritableWorld
import com.dfsek.terra.bukkit.world.BukkitServerWorld
import me.rabbittv.terraTrees.commands.BiomeListCommand
import org.bukkit.Bukkit
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.plugin.java.JavaPlugin

class TerraTrees : JavaPlugin(){
    lateinit var settings: ConfigurationSection
    lateinit var structures: ConfigurationSection

    override fun onEnable() {
        val manager: PaperCommandManager = PaperCommandManager(this)
        saveDefaultConfig()
        manager.registerCommand(BiomeListCommand(this))
        settings = config.getConfigurationSection("settings")!!
        structures = config.getConfigurationSection("structures")!!
        if (settings.getBoolean("debug")) {
            val bukkitWorld = Bukkit.getWorld(settings.getString("debug-world-name", "world").toString())!!
            val world: WritableWorld = BukkitServerWorld(bukkitWorld)
            val pack: ConfigPack = world.pack
            pack.getRegistry(Structure::class.java).keys().forEach { key ->
                logger.info("Available Structure: $key")
            }
        }
    }
}
