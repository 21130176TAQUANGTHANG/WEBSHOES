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
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;


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

    public void savePublicKeyToDatabase(String userId, String publicKey) {
        try {
            String query = "INSERT INTO users (userId, publicKey) VALUES (?, ?)";
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, userId);
            ps.setString(2, publicKey);
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

    private void printOrder(Order order, PrintWriter writer) {
        writer.println("========== HÓA ĐƠN ==========");
        writer.println("Tên khách hàng: " + order.getName());
        writer.println("Địa chỉ: " + order.getAddress());
        writer.println("Số điện thoại: " + order.getPhone());
        writer.println("----------------------------");
        writer.println("Ngày đặt hàng: " + order.getOrderDate());
        writer.println("Mã đơn hàng: " + order.getOrderId());
        writer.println("Ghi chú: " + order.getNotes());
        writer.println("----------------------------");
        writer.println("Danh sách sản phẩm:");
        writer.printf("%-20s %-10s %-10s %-10s\n", "Tên sản phẩm", "SL", "Đơn giá", "Thành tiền");
        for (OrderItem item : order.getOrderItems()) {
            writer.printf("%-20s %-10d %-10.2f %-10.2f\n",
                    item.getProductName(),
                    item.getQuantity(),
                    (double) item.getPrice(), // Ép kiểu giá thành sang double
                    (double) item.getSubtotal()); // Ép kiểu subtotal sang double

        }
        writer.println("----------------------------");
        writer.printf("Tổng tiền: %d\n", order.getTotalPrice()); // Dùng %d cho kiểu int
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


}
