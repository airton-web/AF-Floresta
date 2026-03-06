package airton4563.floresta.Database;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQL {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:plugins/Floresta/database.db";
            File file = new File("plugins/Floresta/database.db");
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            conn = DriverManager.getConnection(url);
            return conn;
        } catch (SQLException | ClassNotFoundException ignored) {
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    public static List<String> getTop10Players() throws SQLException {
        ArrayList<String> allStats = new ArrayList<>();
        try {
            String sql = "SELECT uuid, lenhas FROM floresta_account ORDER BY lenhas DESC LIMIT 10";
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String playerUUID = rs.getString("uuid");
                String playerLenhas = rs.getString("lenhas");
                String add = playerUUID + ";" + playerLenhas;
                allStats.add(add);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allStats;
    }

    public static void checkDB() {
        try {
            Connection conn = getConnection();

            String accountSQL = "CREATE TABLE IF NOT EXISTS floresta_account ("
                    + "uuid VARCHAR(255) PRIMARY KEY, "
                    + "last_axe VARCHAR(255), "
                    + "lenhas VARCHAR(255), "
                    + "time VARCHAR(255) "
                    + ")";
            Statement accountSTMT = conn.createStatement();
            accountSTMT.executeUpdate(accountSQL);

            String woodSQL = "CREATE TABLE IF NOT EXISTS floresta_wood ("
                    + "uuid VARCHAR(255) PRIMARY KEY, "
                    + "axe VARCHAR(10000), "
                    + "fortuna VARCHAR(255), "
                    + "eficiencia VARCHAR(255), "
                    + "derrubador VARCHAR(255), "
                    + "motoserra VARCHAR(255) "
                    + ")";
            Statement woodSTMT = conn.createStatement();
            woodSTMT.executeUpdate(woodSQL);

            String stoneSQL = "CREATE TABLE IF NOT EXISTS floresta_stone ("
                    + "uuid VARCHAR(255) PRIMARY KEY, "
                    + "axe VARCHAR(10000), "
                    + "fortuna VARCHAR(255), "
                    + "eficiencia VARCHAR(255), "
                    + "derrubador VARCHAR(255), "
                    + "motoserra VARCHAR(255) "
                    + ")";
            Statement stoneSTMT = conn.createStatement();
            stoneSTMT.executeUpdate(stoneSQL);

            String ironSQL = "CREATE TABLE IF NOT EXISTS floresta_iron ("
                    + "uuid VARCHAR(255) PRIMARY KEY, "
                    + "axe VARCHAR(10000), "
                    + "fortuna VARCHAR(255), "
                    + "eficiencia VARCHAR(255), "
                    + "derrubador VARCHAR(255), "
                    + "motoserra VARCHAR(255) "
                    + ")";
            Statement ironSTMT = conn.createStatement();
            ironSTMT.executeUpdate(ironSQL);

            String goldSQL = "CREATE TABLE IF NOT EXISTS floresta_gold ("
                    + "uuid VARCHAR(255) PRIMARY KEY, "
                    + "axe VARCHAR(10000), "
                    + "fortuna VARCHAR(255), "
                    + "eficiencia VARCHAR(255), "
                    + "derrubador VARCHAR(255), "
                    + "motoserra VARCHAR(255) "
                    + ")";
            Statement goldSTMT = conn.createStatement();
            goldSTMT.executeUpdate(goldSQL);

            String diamondSQL = "CREATE TABLE IF NOT EXISTS floresta_diamond ("
                    + "uuid VARCHAR(255) PRIMARY KEY, "
                    + "axe VARCHAR(10000), "
                    + "fortuna VARCHAR(255), "
                    + "eficiencia VARCHAR(255), "
                    + "derrubador VARCHAR(255), "
                    + "motoserra VARCHAR(255) "
                    + ")";
            Statement diamondSTMT = conn.createStatement();
            diamondSTMT.executeUpdate(diamondSQL);

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
