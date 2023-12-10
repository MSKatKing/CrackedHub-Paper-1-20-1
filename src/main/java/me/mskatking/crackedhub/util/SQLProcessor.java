package me.mskatking.crackedhub.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.mskatking.crackedhub.CrackedHub;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class SQLProcessor {

    public static void printTable(String table) {
        HikariConfig hConfig = new HikariConfig();
        hConfig.setJdbcUrl((String) CrackedHub.config.get("sql.address"));
        hConfig.setUsername((String) CrackedHub.config.get("sql.username"));
        hConfig.setPassword((String) CrackedHub.config.get("sql.password"));

        HikariDataSource dataSource = new HikariDataSource(hConfig);
        try(Connection conn = dataSource.getConnection()) {
            PreparedStatement sql = conn.prepareStatement("SQL");

            sql.execute("USE s318_SERVER_DATA");
            ResultSet rs = sql.executeQuery("SELECT * FROM " + table);
            while (rs.next()) {
                Console.info("UUID: " + rs.getString("UUID") + " | Rank: " + rs.getString("RANK") + " | Playtime: " + rs.getInt("PLAYTIME"));
            }
        } catch (Exception e) {
            Console.error("Unable to connect to the sql database!");
        }
        dataSource.close();
    }

    public static void execute(String query) {
        HikariConfig hConfig = new HikariConfig();
        hConfig.setJdbcUrl((String) CrackedHub.config.get("sql.address"));
        hConfig.setUsername((String) CrackedHub.config.get("sql.username"));
        hConfig.setPassword((String) CrackedHub.config.get("sql.password"));

        HikariDataSource dataSource = new HikariDataSource(hConfig);
        try(Connection conn = dataSource.getConnection()) {
            PreparedStatement sql = conn.prepareStatement("SQL");

            sql.execute("USE s318_SERVER_DATA");
            sql.execute(query);
        } catch (Exception e) {
            Console.error("Unable to connect to the sql database!");
            e.printStackTrace();
        }
        dataSource.close();
    }

    public static ResultSet getResult(String query) {
        HikariConfig hConfig = new HikariConfig();
        hConfig.setJdbcUrl(CrackedHub.config.getString("sql.address"));
        hConfig.setUsername(CrackedHub.config.getString("sql.username"));
        hConfig.setPassword(CrackedHub.config.getString("sql.password"));

        HikariDataSource dataSource = new HikariDataSource(hConfig);
        try(Connection conn = dataSource.getConnection();
            PreparedStatement sql = conn.prepareStatement(query);) {
            sql.execute("USE s318_SERVER_DATA");
            ResultSet out = sql.executeQuery();
            dataSource.close();
            return out;
        } catch (Exception e) {
            Console.error("Unable to connect to the sql database!");
        }
        dataSource.close();
        return null;
    }

    public static boolean contains(String uuid) {
        HikariConfig hConfig = new HikariConfig();
        hConfig.setJdbcUrl((String) CrackedHub.config.get("sql.address"));
        hConfig.setUsername((String) CrackedHub.config.get("sql.username"));
        hConfig.setPassword((String) CrackedHub.config.get("sql.password"));

        HikariDataSource dataSource = new HikariDataSource(hConfig);
        try(Connection conn = dataSource.getConnection()) {
            PreparedStatement sql = conn.prepareStatement("SELECT UUID FROM PLAYER_DATA;");

            sql.execute("USE s318_SERVER_DATA");

            ResultSet rs = sql.executeQuery();
            while(rs.next()) {
                if(rs.getString("UUID").equals(uuid)) {
                    dataSource.close();
                    return true;
                }
            }
        } catch (Exception e) {
            Console.error("Unable to connect to the sql database!");
            e.printStackTrace();
        }
        dataSource.close();
        return false;
    }

    public static boolean contains(String uuid, String table) {
        HikariConfig hConfig = new HikariConfig();
        hConfig.setJdbcUrl((String) CrackedHub.config.get("sql.address"));
        hConfig.setUsername((String) CrackedHub.config.get("sql.username"));
        hConfig.setPassword((String) CrackedHub.config.get("sql.password"));

        HikariDataSource dataSource = new HikariDataSource(hConfig);
        try(Connection conn = dataSource.getConnection()) {
            PreparedStatement sql = conn.prepareStatement("SELECT UUID FROM " + table + ";");

            sql.execute("USE s318_SERVER_DATA");

            ResultSet rs = sql.executeQuery();
            while(rs.next()) {
                if(rs.getString("UUID").equals(uuid)) {
                    dataSource.close();
                    return true;
                }
            }
        } catch (Exception e) {
            Console.error("Unable to connect to the sql database!");
            e.printStackTrace();
        }
        dataSource.close();
        return false;
    }

    public static boolean contains(String uuid, String column, String table) {
        HikariConfig hConfig = new HikariConfig();
        hConfig.setJdbcUrl((String) CrackedHub.config.get("sql.address"));
        hConfig.setUsername((String) CrackedHub.config.get("sql.username"));
        hConfig.setPassword((String) CrackedHub.config.get("sql.password"));

        HikariDataSource dataSource = new HikariDataSource(hConfig);
        try(Connection conn = dataSource.getConnection()) {
            PreparedStatement sql = conn.prepareStatement("SQL");

            sql.execute("USE s318_SERVER_DATA");

            ResultSet rs = sql.executeQuery("SELECT * FROM " + table + " WHERE " + column + "='" + uuid + "';");
            while(rs.next()) {
                if(rs.getString(column).equals(uuid)) {
                    dataSource.close();
                    return true;
                }
            }
        } catch (Exception e) {
            Console.error("Unable to connect to the sql database!");
            e.printStackTrace();
        }
        dataSource.close();
        return false;
    }

    public static boolean isStaff(String uuid) {
        HikariConfig hConfig = new HikariConfig();
        hConfig.setJdbcUrl((String) CrackedHub.config.get("sql.address"));
        hConfig.setUsername((String) CrackedHub.config.get("sql.username"));
        hConfig.setPassword((String) CrackedHub.config.get("sql.password"));

        HikariDataSource dataSource = new HikariDataSource(hConfig);
        try(Connection conn = dataSource.getConnection()) {
            PreparedStatement sql = conn.prepareStatement("SELECT UUID, STAFF FROM PLAYER_DATA WHERE UUID = '" + uuid + "';");

            sql.execute("USE s318_SERVER_DATA");

            ResultSet rs = sql.executeQuery();
            dataSource.close();
            rs.next();
            return rs.getBoolean("STAFF");
        } catch (Exception e) {
            Console.error("Unable to connect to the sql database!");
            e.printStackTrace();
        }
        dataSource.close();
        return false;
    }

    public enum Tables {
        PLAYER_DATA("PLAYER_DATA"),
        DUPE_LIFESTEAL_DATA("DUPE_LIFESTEAL_DATA"),
        DISCORD_LINKED("DISCORD_LINKED");

        private final String value;
        Tables(String value) {this.value = value;}
        public String toString() {return value;}
    }

    public static void saveBackup(String folder) {
        Console.info("Beginning backup of the SQL Database");
        System.gc();
        String date = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
        FileConfiguration backup = ConfigHelper.getConfig("backups/" + folder, date + "_sqlbackup.yml");
        File f = ConfigHelper.getFile("backups/" + folder, date + "_sqlbackup.yml");
        try {
            HikariConfig hConfig = new HikariConfig();
            hConfig.setJdbcUrl(CrackedHub.config.getString("sql.address"));
            hConfig.setUsername(CrackedHub.config.getString("sql.username"));
            hConfig.setPassword(CrackedHub.config.getString("sql.password"));

            HikariDataSource source = new HikariDataSource(hConfig);
            PreparedStatement sql = source.getConnection().prepareStatement("SELECT * FROM PLAYER_DATA;");
            sql.execute("USE s318_SERVER_DATA");
            ResultSet playerData = sql.executeQuery();
            ArrayList<String> player = new ArrayList<>();
            while (playerData.next()) {
                player.add(playerData.getString("UUID") + "/" + playerData.getString("RANK") + "/" + playerData.getInt("PLAYTIME") + "/" + playerData.getString("USER") + "/" + playerData.getInt("STAFF") + "/" + playerData.getInt("XP"));
            }
            playerData.close();
            backup.set("playerdata", player);
            player = null;
            playerData = null;
            System.gc();
            sql = source.getConnection().prepareStatement("SELECT * FROM DUPE_LIFESTEAL_DATA;");
            sql.execute("USE s318_SERVER_DATA");
            ResultSet dupeLifestealData = sql.executeQuery();
            ArrayList<String> dupeLifesteal = new ArrayList<>();
            while (dupeLifestealData.next()) {
                dupeLifesteal.add(dupeLifestealData.getString("UUID") + "/" + dupeLifestealData.getString("HEALTH"));
            }
            dupeLifestealData.close();
            backup.set("dupelifesteal", dupeLifesteal);
            source.close();
            dupeLifesteal = null;
            dupeLifestealData = null;
            System.gc();
        } catch (SQLException e) {
            Console.error("Couldn't pull data from sql database!");
            e.printStackTrace();
        }
        ConfigHelper.saveConfig(backup, f);
        Console.info("Finished backing up database!");
    }
}
