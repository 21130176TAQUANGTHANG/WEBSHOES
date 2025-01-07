package History;

import DBConnect.DBContext;
import LoginUser.User;
import Order.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Dbhistory {
    Connection conn;
    PreparedStatement  ps;
    ResultSet rs;

    public User getUserById(String userId) {
        User user = null;
        String sql = "SELECT * FROM login WHERE id = ?";

        try {
            conn= new DBContext().getConnection();
            ps  = conn.prepareStatement(sql);
            ps.setString(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                user = new User(rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getInt("phone"),
                        rs.getString("address"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public List<Order> getOrdersByUserId(String userId) {
        List<Order> orders = new ArrayList<Order>();
        String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getString("user_id"));
                order.setTotalPrice(rs.getInt("total_price"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setNotes(rs.getString("notes"));
                order.setStatus(rs.getString("status"));

                orders.add(order);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return orders;
    }
    // chua xac nhan
    public List<Order> getPendingOrdersByUserId(String userId) {
        List<Order> pendingOrders  = new ArrayList<Order>();
        String sql = "SELECT * FROM orders WHERE status = 'Chờ xác nhận' AND user_id = ? ORDER BY order_date DESC";
        try {
            conn= new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getString("user_id"));
                order.setTotalPrice(rs.getInt("total_price"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setNotes(rs.getString("notes"));
                order.setStatus(rs.getString("status"));

                pendingOrders.add(order);
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return pendingOrders;
    }

    public List<Order> getConfirmedOrdersByUserId(String userId) {
        List<Order> confirmedOrders  = new ArrayList<Order>();
        String sql = "SELECT * FROM orders WHERE status = 'Xác nhận thành công' AND user_id = ? ORDER BY order_date DESC";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setUserId(rs.getString("user_id"));
                order.setTotalPrice(rs.getInt("total_price"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setNotes(rs.getString("notes"));
                order.setStatus(rs.getString("status"));
                confirmedOrders.add(order);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return confirmedOrders;
    }
    public boolean cancelOrder(int orderId) {
        String sql = "UPDATE orders SET status = 'Đã hủy' WHERE order_id = ? AND status = 'Chờ xác nhận'";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu có dòng bị ảnh hưởng
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        Dbhistory db = new Dbhistory();
        List<Order> orders = db.getPendingOrdersByUserId("2");
        for (Order order : orders) {
            System.out.println(order);
        }
    }
}
