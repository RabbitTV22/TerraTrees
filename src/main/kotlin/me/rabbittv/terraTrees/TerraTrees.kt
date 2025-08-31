package me.rabbittv.terraTrees

import co.aikar.commands.PaperCommandManager
import com.dfsek.terra.api.config.ConfigPack
import com.dfsek.terra.api.structure.Structure
import com.dfsek.terra.api.world.WritableWorld
import com.dfsek.terra.bukkit.world.BukkitServerWorld
import me.rabbittv.terraTrees.commands.BiomeListCommand
import me.rabbittv.terraTrees.listeners.TreeGrowthListener
import me.rabbittv.terraTrees.utils.AlignTree
import me.rabbittv.terraTrees.utils.SpawnStructure
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin


class TerraTrees : JavaPlugin(){
    val settings = config.getConfigurationSection("settings")!!
    val structures = config.getConfigurationSection("structures")!!

    override fun onEnable() {
        val manager = PaperCommandManager(this)
        saveDefaultConfig()
        Bukkit.getPluginManager().registerEvents(TreeGrowthListener(this, SpawnStructure(this), AlignTree()), this)
        manager.registerCommand(BiomeListCommand(this))
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
