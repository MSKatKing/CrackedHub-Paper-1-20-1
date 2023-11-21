package me.mskatking.crackedhub.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.mskatking.crackedhub.CrackedHub;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
        }
        dataSource.close();
    }

    public static ResultSet getResult(String query) {
        HikariConfig hConfig = new HikariConfig();
        hConfig.setJdbcUrl((String) CrackedHub.config.get("sql.address"));
        hConfig.setUsername((String) CrackedHub.config.get("sql.username"));
        hConfig.setPassword((String) CrackedHub.config.get("sql.password"));

        HikariDataSource dataSource = new HikariDataSource(hConfig);
        try(Connection conn = dataSource.getConnection()) {
            PreparedStatement sql = conn.prepareStatement("SQL");

            sql.execute("USE s318_SERVER_DATA");
            ResultSet out = sql.executeQuery(query);
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
            PreparedStatement sql = conn.prepareStatement("SQL");

            sql.execute("USE s318_SERVER_DATA");

            ResultSet rs = sql.executeQuery("SELECT UUID FROM PLAYER_DATA;");
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

    public static boolean isStaff(String uuid) {
        HikariConfig hConfig = new HikariConfig();
        hConfig.setJdbcUrl((String) CrackedHub.config.get("sql.address"));
        hConfig.setUsername((String) CrackedHub.config.get("sql.username"));
        hConfig.setPassword((String) CrackedHub.config.get("sql.password"));

        HikariDataSource dataSource = new HikariDataSource(hConfig);
        try(Connection conn = dataSource.getConnection()) {
            PreparedStatement sql = conn.prepareStatement("SQL");

            sql.execute("USE s318_SERVER_DATA");

            ResultSet rs = sql.executeQuery("SELECT UUID, STAFF FROM PLAYER_DATA WHERE UUID = '" + uuid + "';");
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
        DUPE_LIFESTEAL_DATA("DUPE_LIFESTEAL_DATA");

        private String value;
        Tables(String value) {this.value = value;}
        public String toString() {return value;}
    }
}
