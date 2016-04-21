package db;


import java.sql.*;

public class DataBase {
    private static DataBase instance;
    private static Connection connection;
    private static String databaseUrl = "jdbc:sqlite:ManagerDB";

    private DataBase() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(databaseUrl);

            if (!isTableExist()) {
                Statement stmt = connection.createStatement();
                String createSql = readResoursel(DataBase.class, "create.sql");
                stmt.executeUpdate(createSql);
                stmt.close();

                String insertSql = readResoursel(DataBase.class, "insert.sql");
                stmt.executeUpdate(insertSql);
                stmt.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            closeConnection();
        }
    }

    private boolean isTableExist() throws Exception {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT count(*) FROM sqlite_master WHERE type='table' AND name='USERS';");
        boolean result = true;
        int count = rs.getInt(1);
        if (count == 0) {
            result = false;
        }
        rs.close();
        stmt.close();
        return result;
    }

    public void closeConnection() {
        closeResource(connection);
        connection = null;
    }

    public void closeResource(AutoCloseable res) {
        try {
            if (res != null) {
                res.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String readResoursel(Class cpHolder, String path) throws Exception {
        java.net.URL url = cpHolder.getResource(path);
        java.nio.file.Path resPath = java.nio.file.Paths.get(url.toURI());
        return new String(java.nio.file.Files.readAllBytes(resPath), "UTF-8");
    }

    public static DataBase init() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }

    public Connection getConn() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(databaseUrl);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

}
