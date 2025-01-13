package Dashboard;

import DBConnect.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DbDashBoard {
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    public List<ProductStats> bestSellingProducts() {
        List<ProductStats> products = new ArrayList<>();
        String query = "SELECT product_id, SUM(quantity) AS total_quantity " +
                "FROM orderdetails " +
                "GROUP BY product_id " +
                "ORDER BY total_quantity DESC " +
                "LIMIT 5"; // Nếu dùng SQL Server, thay bằng: TOP 5

        try  {
            con = new DBContext().getConnection();
            pst = con.prepareStatement(query);
            rs = pst.executeQuery();
            while (rs.next()) {
                int productId = rs.getInt("product_id");
                int totalQuantity = rs.getInt("total_quantity");

                products.add(new ProductStats(productId, totalQuantity));
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching best-selling products", e);
        }

        return products;
    }

    public static void main(String[] args) {
        DbDashBoard dashboard = new DbDashBoard();
        List<ProductStats> products = dashboard.bestSellingProducts();
        for (ProductStats ps : products) {
            System.out.println(ps);
        }
    }
}
