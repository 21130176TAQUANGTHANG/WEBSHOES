package ListProduct;

import DBConnect.DBContext;
import Product.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
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
    public List<Product> getProductsByIds(List<Integer> ids) {
        List<Product> list = new ArrayList<>();
        if (ids == null || ids.isEmpty()) {
            return list; // Trả về danh sách rỗng nếu không có id
        }

        // Xây dựng câu truy vấn SQL
        StringBuilder SQL = new StringBuilder("SELECT * FROM product WHERE productId IN (");
        for (int i = 0; i < ids.size(); i++) {
            SQL.append("?");
            if (i < ids.size() - 1) {
                SQL.append(", ");
            }
        }
        SQL.append(")");

        try {
            conn = new DBContext().getConnection(); // Kết nối cơ sở dữ liệu
            ps = conn.prepareStatement(SQL.toString()); // Chuẩn bị câu truy vấn

            // Gán giá trị cho các tham số ?
            for (int i = 0; i < ids.size(); i++) {
                ps.setInt(i + 1, ids.get(i));
            }

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

        // Danh sách các id sản phẩm cần lấy
        List<Integer> ids = Arrays.asList(100, 102, 103, 104, 105);

        // Gọi phương thức để lấy danh sách sản phẩm
        List<Product> products = dao.getProductsByIds(ids);

        // Hiển thị danh sách sản phẩm
        for (Product product : products) {
            System.out.println(product.getProductName() + " - " + product.getProductPrice());
        }
    }
}
