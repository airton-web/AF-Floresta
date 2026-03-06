package airton4563.floresta.Variables;

import airton4563.floresta.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class GetYML {

    public static String getString(String path, String file) {
        File fileConfig = new File(Main.pluginFolder, file);
        FileConfiguration config = YamlConfiguration.loadConfiguration(fileConfig);
        return config.getString(path);
    }

    public static void setInt(String path, int value, String file) {
        File fileConfig = new File(Main.pluginFolder, file);
        FileConfiguration config = YamlConfiguration.loadConfiguration(fileConfig);
        config.set(path, value);
        try {
            config.save(fileConfig);
        } catch (IOException ignored) {}
    }

    public static void setString(String path, String value, String file) {
        File fileConfig = new File(Main.pluginFolder, file);
        FileConfiguration config = YamlConfiguration.loadConfiguration(fileConfig);
        config.set(path, value);
        try {
            config.save(fileConfig);
        } catch (IOException ignored) {}
    }

    public static int getInt(String path, String file) {
        File fileConfig = new File(Main.pluginFolder, file);
        FileConfiguration config = YamlConfiguration.loadConfiguration(fileConfig);
        return config.getInt(path);
    }

    public static List<String> getList(String path, String file) {
        File fileConfig = new File(Main.pluginFolder, file);
        FileConfiguration config = YamlConfiguration.loadConfiguration(fileConfig);
        return config.getStringList(path);
    }

    public static FileConfiguration getConfiguration(String file) {
        File fileConfig = new File(Main.pluginFolder, file);
        return YamlConfiguration.loadConfiguration(fileConfig);
    }

    public static boolean getBoolean(String path, String file) {
        File fileConfig = new File(Main.pluginFolder, file);
        FileConfiguration config = YamlConfiguration.loadConfiguration(fileConfig);
        return config.getBoolean(path);
    }
}
