package airton4563.floresta.Variables;

import airton4563.floresta.Main;
import airton4563.floresta.Util;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Config {
    public static String World;
    public static Location Spawn = null;

    public static void setup() {
        Bukkit.getConsoleSender().sendMessage("§7[Floresta] §eCarregando §bconfig.yml§e...");

        World = GetYML.getString("World", "config.yml");

        if (GetYML.getString("Spawn.World", "config.yml") != null) {
            double x = Double.parseDouble(GetYML.getString("Spawn.X", "config.yml"));
            double y = Double.parseDouble(GetYML.getString("Spawn.Y", "config.yml"));
            double z = Double.parseDouble(GetYML.getString("Spawn.Z", "config.yml"));
            float yaw = Float.parseFloat(GetYML.getString("Spawn.Yaw", "config.yml"));
            float pitch = Float.parseFloat(GetYML.getString("Spawn.Pitch", "config.yml"));
            org.bukkit.World world = Bukkit.getWorld(GetYML.getString("Spawn.World", "config.yml"));
            Spawn = new Location(world, x, y, z, yaw, pitch);
        }

        Bukkit.getConsoleSender().sendMessage("§7[Floresta] §aArquivo §bconfig.yml§a carregado e salvo.");
    }
}
