package airton4563.floresta.Database;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.sql.*;

public class Account {
    private String database = "floresta_account";
    private Player player;

    public Account(Player player) {
        this.player = player;
    }

    public void create() {
        try {
            String checkSql = "SELECT COUNT(*) FROM " + database + " WHERE uuid = ?";
            Connection conn = MySQL.getConnection();
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, player.getUniqueId().toString());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) == 0) {
                String insertSql = "INSERT INTO " + database + " (uuid, last_axe, lenhas, time) VALUES (?, ?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setString(1, player.getUniqueId().toString());
                insertStmt.setString(2, "wood");
                insertStmt.setString(3, "0");
                insertStmt.setString(4, "0");
                insertStmt.executeUpdate();
            }
            conn.close();
        } catch (SQLException e) {
            Bukkit.getConsoleSender().sendMessage(e.getMessage());
        }
    }

    public void setColumn(String columnName, Object value) {
        try {
            Connection conn = MySQL.getConnection();
            String sql = "UPDATE " + database + " SET " + columnName + " = ? WHERE uuid = ?";
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
            Connection conn = MySQL.getConnection();
            String sql = "SELECT " + column + " FROM " + database + " WHERE uuid = ?";
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

    public int getLenhas() {
        return Integer.valueOf(getColumn("lenhas"));
    }

    public int getTime() {
        return Integer.valueOf(getColumn("time"));
    }

    public String getLastAxe() {
        return getColumn("last_axe");
    }

    public void addLenhas(int amount) {
        String nowString = getColumn("lenhas");
        int now = 0;
        if (nowString != null) {
            now = Integer.parseInt(nowString);
        }
        int nowValue = now + amount;
        setColumn("lenhas", String.valueOf(nowValue));
    }

    public void removeLenhas(int amount) {
        String nowString = getColumn("lenhas");
        int now = 0;
        if (nowString != null) {
            now = Integer.parseInt(nowString);
        }
        int nowValue = now - amount;
        setColumn("lenhas", String.valueOf(nowValue));
    }

    public void setLenhas(int amount) {
        setColumn("lenhas", String.valueOf(amount));
    }
}