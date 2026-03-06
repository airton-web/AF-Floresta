package airton4563.floresta;

import airton4563.floresta.Commands.*;
import airton4563.floresta.Database.MySQL;
import airton4563.floresta.Enchantments.Derrubador;
import airton4563.floresta.Enchantments.Efficiency;
import airton4563.floresta.Enchantments.Fortune;
import airton4563.floresta.Enchantments.Motoserra;
import airton4563.floresta.Events.Events;
import airton4563.floresta.Variables.Config;
import airton4563.floresta.Variables.GetYML;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.SQLException;

public final class Main extends JavaPlugin {

    public static File pluginFolder;
    public static Plugin plugin;

    @Override
    public void onEnable() {
        pluginFolder = getDataFolder();
        plugin = this;
        saveDefaultConfig();
        setup();
        commands();
        events();
    }

    public void commands() {
        getCommand("floresta").setExecutor(new Floresta());
        getCommand("lenhas").setExecutor(new Lenhas());
        getCommand("motoserra").setExecutor(new Encantamento());
        getCommand("machado").setExecutor(new Machado());
    }
    public void events() {
        Bukkit.getPluginManager().registerEvents(new Events(), this);
        Bukkit.getPluginManager().registerEvents(new Listener(), this);
    }
    public void setup() {
        Config.setup();
        MySQL.checkDB();
        resourcesSave("encantamentos/Fortuna.yml", false);
        resourcesSave("encantamentos/Eficiencia.yml", false);
        resourcesSave("encantamentos/Derrubador.yml", false);
        resourcesSave("encantamentos/Motoserra.yml", false);
        Derrubador.load();
        Efficiency.load();
        Fortune.load();
        try {
            TopUpdate();
        } catch (SQLException ignored) {}
        Motoserra.price = GetYML.getInt("price", "encantamentos/Motoserra.yml");
    }
    private void resourcesSave(String resourcePath, boolean replace) {
        File resourceFile = new File(getDataFolder(), resourcePath);
        if (!resourceFile.exists() || replace) {
            saveResource(resourcePath, replace);
        }
    }
    public void TopUpdate() throws SQLException {
        Lenhas.TopLenhas = MySQL.getTop10Players();
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                try {
                    Lenhas.TopLenhas = MySQL.getTop10Players();
                } catch (SQLException ignored) {}
            }
        }, 6000L, 6000L);
    }
}
