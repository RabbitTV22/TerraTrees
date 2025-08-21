package me.rabbittv.terraTrees.utils;

import com.dfsek.terra.api.config.ConfigPack;
import com.dfsek.terra.api.registry.key.RegistryKey;
import com.dfsek.terra.api.structure.Structure;
import com.dfsek.terra.api.util.Rotation;
import com.dfsek.terra.api.util.vector.Vector3Int;
import com.dfsek.terra.bukkit.world.BukkitServerWorld;
import me.rabbittv.terraTrees.TerraTrees;
import org.bukkit.Location;
import org.bukkit.World;
import java.util.Optional;
import java.util.Random;

public class SpawnStructure {

    private final TerraTrees plugin;

    public SpawnStructure(TerraTrees plugin) {
        this.plugin = plugin;
    }

    public void spawnTerraStructure(World world, Location location, String structureId) {
        BukkitServerWorld TerraWorld = new BukkitServerWorld(world);
        Vector3Int vector = Vector3Int.of((int)Math.floor(location.getX()), (int)Math.floor(location.getY()), (int)Math.floor(location.getZ()));

        ConfigPack pack = TerraWorld.getPack();
        if (pack == null) {
            plugin.getLogger().warning("Could not find a config pack for the current world.");
        } else {
            RegistryKey structureKey = RegistryKey.parse(structureId);
            Optional<Structure> structureOptional = pack.getRegistry(Structure.class).get(structureKey);
            if (structureOptional.isEmpty()) {
                plugin.getLogger().warning("Structure with ID '" + structureId + "' not found in config pack.");
            }
            Structure structure = structureOptional.get();

            Random random = new Random();
            Rotation rotation = Rotation.NONE;

            boolean generated = structure.generate(vector, TerraWorld, random, rotation);

            if (generated && plugin.settings.getBoolean("debug")) {
                plugin.getLogger().info("Successfully spawned structure '" + structureId + "' at " + location.toString());
            } else if (plugin.settings.getBoolean("debug")) {
                plugin.getLogger().warning("Failed to spawn structure '" + structureId + "' at " + location.toString());
            }
        }
    }

}
