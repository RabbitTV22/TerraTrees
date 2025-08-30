package me.rabbittv.terraTrees

import co.aikar.commands.PaperCommandManager
import com.dfsek.terra.api.structure.Structure
import com.dfsek.terra.bukkit.world.BukkitServerWorld
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class TerraTreesKotlin : JavaPlugin() {
    public final val settings = config.getConfigurationSection("settings")!!
    public final val structures = config.getConfigurationSection("structures")!!

    override fun onEnable() {
        val manager: PaperCommandManager = PaperCommandManager(this)
      //  manager.registerCommand()
        if (settings.getBoolean("debug")) {
            val bukkitWorld = Bukkit.getWorld(settings.getString("debug-world-name", "world").toString())!!
            val world = BukkitServerWorld(bukkitWorld)
            val pack = world.pack
            pack.getRegistry(Structure::class.java).keys().forEach { key ->
                logger.info("Available Structure: $key")
            }
        }
    }
}
