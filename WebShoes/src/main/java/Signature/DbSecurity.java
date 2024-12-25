package Signature;

import DBConnect.DBContext;
import Order.Order;
import Order.OrderItem;
import java.io.PrintWriter;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DbSecurity {
    static Connection conn;
    PreparedStatement ps;
    ResultSet rs;

    public Order getOrderByIdss(int orderId) {
        Order order = null;
        try  {
            String sql = "SELECT * FROM orders WHERE order_id = ?";
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                order = new Order(
                        rs.getInt("order_id"),
                        rs.getString("user_id"),
                        rs.getInt("total_price"),
                        rs.getTimestamp(    "order_date"),
                        rs.getString("notes"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getString("status"),
                        rs.getString("signature")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }

    public boolean hasKey(String userId) {
        String query = "SELECT publicKey FROM users WHERE userId=?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, userId);
            rs = ps.executeQuery();
            // Kiểm tra nếu có public_key
            if (rs.next() && rs.getString("publicKey") != null) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error checking key in database.", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    public void savePublicKeyToDatabase(String userId, String publicKey, Timestamp createTime, Timestamp endTime) {
        try {
            String query = "INSERT INTO users (userId, publicKey, createTime, endTime) VALUES (?, ?, ?, ?)";
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, userId);
            ps.setString(2, publicKey);
            ps.setTimestamp(3, createTime);
            ps.setTimestamp(4, endTime); // ban đầu là null
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isPublicKeyExist(String userId) {
        // Thay bằng truy vấn thực tế của bạn
        String query = "SELECT COUNT(*) FROM users WHERE userId = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    public String getPublicKeyFromDatabase(String userId) {
        String query = "SELECT publicKey FROM users WHERE userId = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("publicKey");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void updateKeyEndTime(String userId) {
        String query = "UPDATE users SET endTime = CURRENT_TIMESTAMP WHERE userId = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, userId);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeResources();
        }
    }

    // Phương thức in hóa đơn chứa thông tin sản phẩm
    public void getOrdersByUserId(int orderid, PrintWriter writer) {
        String query = "SELECT o.order_id, o.order_date, o.notes, o.total_price, o.name, o.address, o.phone, o.status, " +
                "od.quantity, od.size, od.subtotal, p.productName, p.productPrice " +
                "FROM orders o " +
                "JOIN orderdetails od ON o.order_id = od.order_id " +
                "JOIN product p ON od.product_id = p.productid " +
                "WHERE o.order_id = ?";

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, orderid);
            rs = ps.executeQuery();

            Order currentOrder = null;
            while (rs.next()) {
                int orderId = rs.getInt("order_id");
                if (currentOrder == null || currentOrder.getOrderId() != orderId) {
                    // Nếu chuyển sang hóa đơn mới, in hóa đơn cũ
                    if (currentOrder != null) {
                        printOrder(currentOrder, writer);
                    }

                    // Khởi tạo hóa đơn mới
                    currentOrder = new Order(
                            orderId,
                            rs.getTimestamp("order_date"),
                            rs.getString("notes"),
                            rs.getInt("total_price"),
                            rs.getString("name"),
                            rs.getString("address"),
                            rs.getString("phone"),
                            rs.getString("status")
                    );
                }

                // Thêm sản phẩm vào hóa đơn
                currentOrder.addOrderItem(new OrderItem(
                        rs.getString("productName"),  // Tên sản phẩm
                        rs.getInt("quantity"),       // Số lượng
                        rs.getInt("productPrice"),// Giá sản phẩm
                        rs.getInt("subtotal"),    // Tổng tiền (subtotal)
                        rs.getString("size")         // Kích thước (size)
                ));
            }

            // In hóa đơn cuối cùng
            if (currentOrder != null) {
                printOrder(currentOrder, writer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }
    }
    public void deleteKey(String userId) {
        String query = "DELETE FROM users WHERE userId = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);

            ps.setString(1, userId);
            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public boolean isOrderHashExist(int orderId) {
        boolean exists = false;
        String query = "SELECT COUNT(*) FROM invoices WHERE order_id = ?";

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();

            if (rs.next()) {
                exists = rs.getInt(1) > 0; // Nếu số lượng > 0, thì đã tồn tại
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exists;
    }

    private void printOrder(Order order, PrintWriter writer) {
        writer.println("========== INVOICE ==========");
        writer.println("Customer Name: " + order.getName());
        writer.println("Address: " + order.getAddress());
        writer.println("Phone Number: " + order.getPhone());
        writer.println("----------------------------");
        writer.println("Order Date: " + order.getOrderDate());
        writer.println("Order ID: " + order.getOrderId());
        writer.println("Notes: " + order.getNotes());
        writer.println("----------------------------");
        writer.println("Product List:");
        writer.printf("%-20s %-10s %-10s %-10s\n", "Product Name", "Qty", "Unit Price", "Total Price");
        for (OrderItem item : order.getOrderItems()) {
            writer.printf("%-20s %-10d %-10.2f %-10.2f\n",
                    item.getProductName(),
                    item.getQuantity(),
                    (double) item.getPrice(), // Cast the price to double
                    (double) item.getSubtotal()); // Cast subtotal to double
        }
        writer.println("----------------------------");
        writer.printf("Total Price: %d\n", order.getTotalPrice()); // Use %d for int type
        writer.println("============================");
        writer.println();
    }


    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error closing database resources", e);
        }
    }

    public String getPublicKey(String userId) {
        String query = "SELECT publicKey FROM users WHERE userId = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("publicKey");
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy Public Key từ CSDL", e);
        }
        return null;
    }


    // Kiểm tra xem dữ liệu đã tồn tại hay chưa
    public boolean isInvoiceExists(int orderId, String userId) throws Exception {
        String sql = "SELECT COUNT(*) FROM invoices WHERE user_id = ? AND order_id = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            ps.setInt(2, orderId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Trả về true nếu có dữ liệu
            }
            return false;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }
    }
    public void reportLostKey(String userId) {
        String query = "UPDATE users SET endTime = ? WHERE userId = ? AND endTime IS NULL";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);

            // Thời điểm hiện tại
            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            ps.setTimestamp(1, currentTime); // Gán endTime = thời điểm hiện tại
            ps.setString(2, userId);

            ps.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public boolean cancelOrder(String orderId) {
        String query = "UPDATE orders SET status = 'Đã hủy' WHERE order_id = ? AND status = 'Chờ xác nhận'";
        try (Connection conn = new DBContext().getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, orderId);
            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0; // Nếu có ít nhất 1 hàng được cập nhật, trả về true.
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi hủy đơn hàng.", e);
        }
    }
    public void uploadInvoice(String userId, int orderId, String hash, String signature) {
        String checkSql = "SELECT hash, signature FROM invoices WHERE user_id = ? AND order_id = ?";
        String updateSql = "UPDATE invoices SET hash = ?, signature = ?, created_at = ? WHERE user_id = ? AND order_id = ?";
        String insertSql = "INSERT INTO invoices (user_id, order_id, hash, signature, created_at) VALUES (?, ?, ?, ?, ?)";

        try {
            conn = new DBContext().getConnection();

            // Kiểm tra xem bản ghi đã tồn tại chưa
            ps = conn.prepareStatement(checkSql);
            ps.setString(1, userId);
            ps.setInt(2, orderId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Nếu bản ghi tồn tại, kiểm tra giá trị hash và signature
                String existingHash = rs.getString("hash");
                String existingSignature = rs.getString("signature");

                if ((existingHash == null && hash != null) || (existingSignature == null && signature != null)) {
                    // Cập nhật hash hoặc signature nếu một trong hai đang là null
                    ps = conn.prepareStatement(updateSql);
                    ps.setString(1, hash); // Ghi đúng vào cột hash
                    ps.setString(2, signature); // Ghi đúng vào cột signature
                    ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                    ps.setString(4, userId);
                    ps.setInt(5, orderId);
                    ps.executeUpdate();
                } else {
                    System.out.println("Both hash and signature already exist.");
                }
            } else {
                // Nếu bản ghi chưa tồn tại, chèn bản ghi mới
                ps = conn.prepareStatement(insertSql);
                ps.setString(1, userId);
                ps.setInt(2, orderId);
                ps.setString(3, hash); // Ghi đúng vào cột hash
                ps.setString(4, signature); // Ghi đúng vào cột signature
                ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
                ps.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }



    // Lấy public key từ bảng users
    public String getPublicKeyByUserId(String userId) throws Exception {
        String sql = "SELECT publicKey FROM users WHERE userId = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("publicKey");
            }
            return null;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }
    }
    // Lấy thông tin hóa đơn từ bảng invoices
    public Invoice getInvoiceByOrderId(int orderId) throws Exception {
        String sql = "SELECT * FROM invoices WHERE order_id = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();
            if (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setUserId(rs.getString("user_id"));
                invoice.setOrderId(rs.getInt("order_id"));
                invoice.setHashFromContent(rs.getString("hash"));
                invoice.setSignature(rs.getString("signature"));
                return invoice;
            }
            return null;
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        }
    }

    public static void main(String[] args) {
        DbSecurity dbSecurity = new DbSecurity();
        Order order = dbSecurity.getOrderByIdss(28);
        System.out.println(order);

    }
}
