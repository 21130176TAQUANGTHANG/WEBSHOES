package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDB {
    public static void main(String[] args) {
        var url = "jdbc:mysql://localhost:3306/test";
        var user = "root";
        var password = "";
        try(Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to database");

        } catch (SQLException e) {
             e.printStackTrace();
        }
    }
}
