package ListProduct;

import DBConnect.DBContext;
import Product.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DBlistproductDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    //Phương thức in ra tất cả sản phẩm
    public List<Product> getAllProduct() {
        List<Product> list = new ArrayList<Product>();
        String SQL = "SELECT * FROM `product`";
        try {
            conn = new DBContext().getConnection(); // Kết nối cơ sở dữ liệu
            ps = conn.prepareStatement(SQL); // Chuẩn bị câu truy vấn
            rs = ps.executeQuery(); // Thực thi truy vấn

            // Duyệt qua kết quả và thêm vào danh sách sản phẩm
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("productId"), // Cột productId
                        rs.getString("productName"), // Cột productName
                        rs.getString("productImage"), // Cột productImage
                        rs.getInt("productPrice"), // Cột productPrice
                        rs.getString("productDescription"), // Cột productDescription
                        rs.getInt("productQuantity"), // Cột productQuantity
                        rs.getInt("productSize"), // Cột productSize
                        rs.getInt("productColor"), // Cột productColor
                        rs.getInt("productLogo") // Cột productLogo
                );
                list.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng tài nguyên
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List<Product> getProductsByLogo(int logo) {
        List<Product> list = new ArrayList<Product>();
        String SQL = "SELECT * FROM product WHERE productLogo = ?"; // Truy vấn theo logo
        try {
            conn = new DBContext().getConnection(); // Kết nối cơ sở dữ liệu
            ps = conn.prepareStatement(SQL); // Chuẩn bị câu truy vấn
            ps.setInt(1, logo); // Gán giá trị logo vào câu truy vấn
            rs = ps.executeQuery(); // Thực thi truy vấn

            // Duyệt qua kết quả và thêm vào danh sách sản phẩm
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("productId"), // Cột productId
                        rs.getString("productName"), // Cột productName
                        rs.getString("productImage"), // Cột productImage
                        rs.getInt("productPrice"), // Cột productPrice
                        rs.getString("productDescription"), // Cột productDescription
                        rs.getInt("productQuantity"), // Cột productQuantity
                        rs.getInt("productSize"), // Cột productSize
                        rs.getInt("productColor"), // Cột productColor
                        rs.getInt("productLogo") // Cột productLogo
                );
                list.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng tài nguyên
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static void main(String[] args) {
        DBlistproductDAO dao = new DBlistproductDAO();

        // Lấy danh sách sản phẩm của Nike (logo = 0)
        System.out.println("Danh sách sản phẩm Nike:");
        List<Product> nikeProducts = dao.getProductsByLogo(0);
        for (Product product : nikeProducts) {
            System.out.println(product);
        }
    }
}
