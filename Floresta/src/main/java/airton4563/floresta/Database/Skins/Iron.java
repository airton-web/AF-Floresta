package airton4563.floresta.Database.Skins;

import airton4563.floresta.Database.MySQL;
import airton4563.floresta.Floresta.Machados;
import airton4563.floresta.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class Iron {
    private String database = "floresta_iron";
    private Player player;

    public Iron(Player player) {
        this.player = player;
    }

    public void create() {
        try {
            Connection conn = MySQL.getConnection();
            String checkSql = "SELECT COUNT(*) FROM " + database + " WHERE uuid = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, player.getUniqueId().toString());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) == 0) {
                String insertSql = "INSERT INTO " + database + " (uuid, axe, fortuna, eficiencia, derrubador, motoserra) VALUES (?, ?, ?, ?, ?, ?)";

                List<String> lore = Arrays.asList(Machados.IRON.getLore().split("\\|"));

                ItemStack item = Util.createItem(Machados.IRON.getMaterial(), Machados.IRON.getName(), lore);
                String code = Util.cryptItem(item);

                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setString(1, player.getUniqueId().toString());
                insertStmt.setString(2, code);
                insertStmt.setString(3, "0");
                insertStmt.setString(4, "0");
                insertStmt.setString(5, "0");
                insertStmt.setString(6, "false");
                insertStmt.executeUpdate();
            }
            conn.close();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setColumn(String columnName, Object value) {
        try {
            String sql = "UPDATE " + database + " SET " + columnName + " = ? WHERE uuid = ?";
            Connection conn = MySQL.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setObject(1, value);
            preparedStatement.setString(2, player.getUniqueId().toString());
            preparedStatement.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(e.getMessage());
        }
    }

    public String getColumn(String column) {
        String value = null;
        try {
            String sql = "SELECT " + column + " FROM " + database + " WHERE uuid = ?";
            Connection conn = MySQL.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, player.getUniqueId().toString());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                value = rs.getString(column);
            }
            conn.close();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(e.getMessage());
        }
        return value;
    }

    public ItemStack getAxe() {
        String code = getColumn("axe");
        if (code == null) {
            return null;
        }
        try {
            return Util.encryptItem(code);
        } catch (IOException ignored) {}
        return null;
    }

    public int getFortune() {
        return Integer.valueOf(getColumn("fortuna"));
    }

    public int getEfficiency() {
        return Integer.valueOf(getColumn("eficiencia"));
    }

    public int getDerrubador() {
        return Integer.valueOf(getColumn("derrubador"));
    }

    public void addFortune(int amount) {
        int now = getFortune();
        if (amount > 0) {
            setColumn("fortuna", String.valueOf(amount + now));
        }
    }

    public String getMotoserra() {
        return getColumn("motoserra");
    }

    public void addEfficiency(int amount) {
        int now = getEfficiency();
        if (amount > 0) {
            setColumn("eficiencia", String.valueOf(amount + now));
        }
    }

    public void addDerrubador(int amount) {
        int now = getDerrubador();
        if (amount > 0) {
            setColumn("derrubador", String.valueOf(amount + now));
        }
    }

    public void setMotoserra(boolean status) {
        setColumn("motoserra", String.valueOf(status));
    }
}