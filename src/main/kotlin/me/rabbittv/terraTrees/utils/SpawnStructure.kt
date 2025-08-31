package me.rabbittv.terraTrees.utils

import com.dfsek.terra.api.registry.key.RegistryKey
import com.dfsek.terra.api.structure.Structure
import com.dfsek.terra.api.util.Rotation
import com.dfsek.terra.api.util.vector.Vector3Int
import com.dfsek.terra.bukkit.world.BukkitServerWorld
import me.rabbittv.terraTrees.TerraTrees
import org.bukkit.Location
import org.bukkit.World
import java.util.Random

class SpawnStructure(private val plugin: TerraTrees) {


    fun spawnTerraStructure(world: World, location: Location, StructureID: String?) {
        val TerraWorld = BukkitServerWorld(world)
        val vector = Vector3Int.of(location.blockX, location.blockY, location.blockZ)

        val TerraPack = TerraWorld.pack
        if (TerraPack == null) {
            plugin.logger.warning("The current world does not have a Terra pack")
        } else {
            val StructureKey = RegistryKey.parse(StructureID)
            val StructureOptional = TerraPack.getRegistry(Structure::class.java).get(StructureKey)
            if (StructureOptional.isEmpty) {
                plugin.logger.warning("Structure $StructureID not found.")
            }
            val structure = StructureOptional.get()
            val random = Random()
            val rotation = Rotation.NONE
            val generated = structure.generate(vector, TerraWorld, random, rotation)

            if (generated && plugin.settings.getBoolean("debug")){
                plugin.logger.info("Successfully spawned structure $StructureID at ${location.blockX}, ${location.blockY}, ${location.blockZ}")
            } else if (plugin.settings.getBoolean("debug")) {
                plugin.logger.warning("Failed to spawn structure $StructureID at ${location.blockX}, ${location.blockY}, ${location.blockZ}.")
            }
        }
    }

}