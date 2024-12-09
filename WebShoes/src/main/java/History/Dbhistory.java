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

    public static void main(String[] args) {
        Dbhistory db = new Dbhistory();
        List<Order> orders = db.getOrdersByUserId("2");
        for (Order order : orders) {
            System.out.println(order);
        }
    }
}
