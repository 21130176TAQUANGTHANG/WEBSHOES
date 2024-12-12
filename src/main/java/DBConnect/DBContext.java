package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBContext {

    private final String serverName = "localhost";
    private final String serverPort = "3306";
    private final String DBName = "test";
    private final String userID = "root";
    private final String password = "";

    public Connection getConnection() throws Exception {
        String url = "jdbc:mysql://" + serverName + ":" + serverPort + "/" + DBName;
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, userID, password);
    }

    public static void main(String[] args) {
        DBContext dbContext = new DBContext();
        try (Connection connection = dbContext.getConnection()) {
            System.out.println("Kết nối thành công!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Kết nối thất bại: " + e.getMessage());
        }
    }
}
