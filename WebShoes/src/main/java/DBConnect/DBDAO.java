package DBConnect;

import LoginUser.AccountFF;
import LoginUser.GoogleAccount;
import LoginUser.User;
import Order.Order;
import Product.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DBDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    // Phương thức kiểm tra đăng nhập
    public User checkLogin(String email, String password) {
        try {
            String query = "SELECT * FROM login WHERE email=? AND password=?";
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            while (rs.next()) {
                User a = new User(
                        rs.getString("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getInt("phone"),
                        rs.getString("address"),
                        rs.getInt("role")
                );
                return a;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public AccountFF checkFacebookAccount(String name) {
        try {
            String query = "SELECT * FROM faceaccount WHERE  name=?";
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, name);
            rs = ps.executeQuery();
            while (rs.next()) {
                AccountFF a = new AccountFF(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3)
                );
                return a;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public GoogleAccount checkGoogleAccount(String email) {
        try {
            String query = "SELECT * FROM googleaccount WHERE email=?";
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            while (rs.next()) {
                GoogleAccount a = new GoogleAccount(
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7)
                );
                return a;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // đăng ký
    public User registerUser(User user) {
        try {
            String query = "INSERT INTO login (username, email, password, phone, address, role) VALUES (?, ?, ?, ?, ?, 0)";
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setInt(4, user.getPhone());
            ps.setString(5, user.getAddress());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return user;
    }
    public void saveGoogleAccount(GoogleAccount googleAccount) {
        try {
            String query = "INSERT INTO googleaccount (email, name, first_name, given_name, family_name, picture, verified_email) VALUES (?, ?, ?, ?, ?, ?, ?)";
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, googleAccount.getEmail());
            ps.setString(2, googleAccount.getName());
            ps.setString(3, googleAccount.getFirst_name());
            ps.setString(4, googleAccount.getGiven_name());
            ps.setString(5, googleAccount.getFamily_name());
            ps.setString(6, googleAccount.getPicture());
            ps.setBoolean(7, googleAccount.isVerified_email());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Phương thức lưu thông tin tài khoản Facebook
    public void saveFaceAccount(AccountFF account) {
        try {
            String query = "INSERT INTO faceaccount (email, name) VALUES (?, ?)";
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, account.getEmail());
            ps.setString(2, account.getName());
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<User> getAllUsers() {
        List<User>users = new ArrayList<>();
        String query = "SELECT * FROM login";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                User a = new User(rs.getString("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getInt("phone"),
                        rs.getString("address"),
                        rs.getInt("role"));
                users.add(a);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return users;
    }
    public List<AccountFF> getAllFacebook() {
        List<AccountFF>ff = new ArrayList<>();
        String query = "SELECT * FROM faceaccount";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                AccountFF accountFF = new AccountFF(rs.getString("id"),
                        rs.getString("email"),
                        rs.getString("name"));
                ff.add(accountFF);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ff;
    }
    public List<GoogleAccount> getAllGoogle() {
        List<GoogleAccount>gg = new ArrayList<>();
        String query = "SELECT * FROM googleaccount";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                GoogleAccount googleAccount = new GoogleAccount(rs.getString("id"),
                        rs.getString("email"),
                         rs.getString("name"),
                        rs.getString("first_name"),
                        rs.getString("given_name"),
                        rs.getString("family_name"),
                        rs.getString("picture"),
                        rs.getBoolean("verified_email"));
                gg.add(googleAccount);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return gg;
    }
    public List<Product>getAllProducts() {
        List<Product> products = new ArrayList<Product>();
        String query = "SELECT * FROM product";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product(rs.getInt("productId"),
                        rs.getString("productName"),
                        rs.getString("productImage"),
                        rs.getInt("productPrice"),
                        rs.getString("productDescription"),
                        rs.getInt("productquantity"),
                        rs.getInt("productSize"),
                        rs.getInt("productColor"),
                        rs.getInt("productLogo"));

                products.add(product);

            }
        }catch (Exception e) {
            e.printStackTrace(); // Hiển thị stack trace đầy đủ
            throw new RuntimeException("Error occurred in getAllProducts: " + e.getMessage(), e);
        }

        finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return products;
    }




    // hiển thị chi tiết sản phẩm theo id
    public Product getProductById(int productId) {
        String query = "SELECT * FROM product WHERE productId = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, productId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new Product(
                        rs.getInt("productId"),
                        rs.getString("productName"),
                        rs.getString("productImage"),
                        rs.getInt("productPrice"),
                        rs.getString("productDescription"),
                        rs.getInt("productquantity")

                );
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }



    // lấy sản phẩm theo id
    public List<Product> getProductsByIds(List<Integer> productIds) {
        List<Product> products = new ArrayList<>();
        if (productIds == null || productIds.isEmpty()) {
            return products; // Trả về danh sách rỗng nếu không có ID nào
        }

        // Tạo câu truy vấn SQL sử dụng IN
        String query = "SELECT * FROM product WHERE productId IN (";
        query += productIds.stream().map(id -> "?").collect(Collectors.joining(","));
        query += ")";

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);

            // Gán các giá trị ID vào câu truy vấn
            for (int i = 0; i < productIds.size(); i++) {
                ps.setInt(i + 1, productIds.get(i));
            }

            rs = ps.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("productId"),
                        rs.getString("productName"),
                        rs.getString("productImage"),
                        rs.getInt("productPrice"),
                        rs.getString("productDescription"),
                        rs.getInt("productquantity")
                );
                products.add(product);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return products;
    }
//--------------------------------------------
    // lưu thông tin đăt hàng
// Lưu thông tin vào bảng Orders
    public int saveOrder(String userId, int totalPrice, String name, String address, String phone, String notes) {
        String query = "INSERT INTO orders (user_id, total_price, order_date, notes, name, address, phone, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, userId);
            ps.setInt(2, totalPrice);
            ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            ps.setString(4, notes);
            ps.setString(5, name);
            ps.setString(6, address);
            ps.setString(7, phone);
            ps.setString(8, "Chờ xác nhận");
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // ID đơn hàng mới
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
    public boolean updateOrderStatus(int orderId, String status){
        String query = "UPDATE orders SET status = ? WHERE order_id = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, status); // Trạng thái sẽ được truyền vào
            ps.setInt(2, orderId);
            ps.executeUpdate();
            return true; // Cập nhật thành công
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false; // Cập nhật thất bại
    }


    public void saveOrderDetails(int orderId, int productId, int quantity, String size, int subtotal) {
        String query = "INSERT INTO orderdetails (order_id, product_id, quantity, size, subtotal) VALUES (?, ?, ?, ?, ?)";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, orderId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            ps.setString(4, size);
            ps.setInt(5, subtotal);
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
// lich su
    public List<Order> getOrderHistoryByUserId(String userId) {
        List<Order> orderList = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE user_id = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getString("user_id"));
                order.setTotalPrice(rs.getInt("total_price"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setNotes(rs.getString("notes"));
                order.setName(rs.getString("name"));
                order.setAddress(rs.getString("address"));
                order.setPhone(rs.getString("phone"));
                orderList.add(order);

            }
        }catch (SQLException  e){
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return orderList;
    }

    // Cập nhật số lượng sản phẩm sau khi đặt hàng
    public int updateProductAfterOrder(int productId, int quantityOrdered) {
        int remainingQuantity = checkProductQuantity(productId);
        if (remainingQuantity < quantityOrdered) {
            System.out.println("Không đủ số lượng sản phẩm trong kho.");
            return -1; // Trả về -1 để báo lỗi
        }

        String query = "UPDATE product SET productquantity = productquantity - ? WHERE productId = ?";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, quantityOrdered);
            ps.setInt(2, productId);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Sản phẩm đã được cập nhật thành công.");
                remainingQuantity = checkProductQuantity(productId);
            } else {
                System.out.println("Cập nhật thất bại. Có thể sản phẩm không tồn tại.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return remainingQuantity;
    }


    // Kiểm tra số lượng sản phẩm còn lại
    public int checkProductQuantity(int productId) {
        String query = "SELECT productquantity FROM product WHERE productId = ?";
        int availableQuantity = 0;

        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, productId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    availableQuantity = rs.getInt("productquantity");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return availableQuantity;
    }

    public String getProductImageById(int productId) {
        String imageFile = "";
        String query = "SELECT productImage FROM product WHERE productId = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, productId);
            rs = ps.executeQuery();
            if (rs.next()) {
                imageFile = rs.getString("productImage");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return imageFile;
    }


    public Product addProduct(Product product) {
        String query = "INSERT INTO product (productId, productName, productImage, productPrice, productDescription, productQuantity, productSize, productColor, productLogo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, product.getProductId());
            ps.setString(2, product.getProductName());
            ps.setString(3, product.getProductImage());
            ps.setDouble(4, product.getProductPrice());
            ps.setString(5, product.getProductDescription());
            ps.setInt(6, product.getProductQuantity());
            ps.setInt(7, product.getProductSize());
            ps.setInt(8, product.getProductColor());
            ps.setInt(9, product.getProductLogo());
            ps.executeUpdate();
            return product; // Trả về đối tượng vừa thêm
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Trả về null nếu có lỗi
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void updateProduct(Product product) {
        try {
            conn = new DBContext().getConnection();  // Giả sử bạn có lớp kết nối DB
            String sql = "UPDATE product SET productName = ?, productImage = ?,productPrice = ?, productDescription = ?, productquantity = ?, productSize = ?, productColor = ?, productLogo = ? WHERE productId = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, product.getProductName());
            ps.setString(2, product.getProductImage());
            ps.setInt(3, product.getProductPrice());
            ps.setString(4, product.getProductDescription());
            ps.setInt(5, product.getProductQuantity());
            ps.setInt(6, product.getProductSize());
            ps.setInt(7, product.getProductColor());
            ps.setInt(8, product.getProductLogo());
            ps.setInt(9, product.getProductId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // Đóng kết nối
        }
    }

    public List<Order> getAllOrder(){
        List<Order> orderList = new ArrayList<Order>();

        try {
            String query = "SELECT * FROM orders";

            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("order_id"),
                        rs.getString("user_id"),
                        rs.getInt("total_price"),
                        rs.getTimestamp("order_date"),
                        rs.getString("notes"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("status")
                );
                orderList.add(order);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return orderList;
    }
    public List<Order> getOrdersByPage(int page, int size) {
        List<Order> orderList = new ArrayList<>();
        int offset = (page - 1) * size; // Tính offset cho SQL

        try {
            String query = "SELECT * FROM orders ORDER BY order_date DESC LIMIT ? OFFSET ?";
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, size);  // Giới hạn số lượng bản ghi mỗi trang
            ps.setInt(2, offset); // Bắt đầu từ vị trí offset
            rs = ps.executeQuery();

            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("order_id"),
                        rs.getString("user_id"),
                        rs.getInt("total_price"),
                        rs.getTimestamp("order_date"),
                        rs.getString("notes"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("status")
                );
                orderList.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return orderList;
    }

    // Đếm tổng số đơn hàng
    public int getTotalOrders() {
        int total = 0;
        try {
            String query = "SELECT COUNT(*) FROM orders";
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return total;
    }



    // Hàm main để kiểm tra phương thức getAllUsers
    public static void main(String[] args) {
        DBDAO dbdao = new DBDAO();

    }

}
