package AdminManage;

import DBConnect.DBContext;
import LoginUser.User;
import Order.Order;
import Order.OrderItem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DbAdmin {
    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    public User addUser(User user) {
        String sql = "insert into login(username, password, email, phone,address, role) values(?,?,?,?,?,?)";
        try {
            con = new DBContext().getConnection();
            pst = con.prepareStatement(sql);
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getEmail());
            pst.setInt(4, user.getPhone());
            pst.setString(5, user.getAddress());
            pst.setInt(6, user.getRole());
            pst.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return user;
    }


    public Order getOrderById(int orderId) {
        String query = "SELECT o.order_id, o.order_date, o.notes, o.total_price, o.name, o.address, o.phone, o.status, " +
                "od.quantity, od.size, od.subtotal, p.productName, p.productPrice " +
                "FROM orders o " +
                "JOIN orderdetails od ON o.order_id = od.order_id " +
                "JOIN product p ON od.product_id = p.productid " +
                "WHERE o.order_id = ?";

        Order currentOrder = null;

        try {
            con = new DBContext().getConnection();
            pst = con.prepareStatement(query);
            pst.setInt(1, orderId);
            rs = pst.executeQuery();

            while (rs.next()) {
                int orderIds = rs.getInt("order_id");
                if (currentOrder == null || currentOrder.getOrderId() != orderIds) {
                    // Khởi tạo hóa đơn mới nếu chưa có
                    currentOrder = new Order(
                            orderIds,  // Sử dụng orderIds lấy từ query, không phải tham số orderId
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
                OrderItem item = new OrderItem(
                        rs.getString("productName"),  // Tên sản phẩm
                        rs.getInt("quantity"),        // Số lượng
                        rs.getInt("productPrice"),    // Giá sản phẩm
                        rs.getInt("subtotal"),        // Tổng tiền cho sản phẩm
                        rs.getString("size")          // Kích thước sản phẩm
                );
                currentOrder.addOrderItem(item);  // Thêm item vào order
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeResources();
        }

        // Trả về đối tượng currentOrder nếu có
        return currentOrder;
    }
    private void closeResources() {
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            throw new RuntimeException("Error closing database resources", e);
        }
    }
    public int updateOrders(int orderId, String name, String address, String phone, String notes, String status,
                           String[] productIds, String[] quantities, String[] sizes, String[] subtotals) {
        String queryUpdateOrder = "UPDATE orders SET name = ?, address = ?, phone = ?, notes = ?, status = ? WHERE order_id = ?";
        String queryUpdateDetails = "UPDATE orderdetails SET quantity = ?, size = ?, subtotal = ? WHERE order_id = ? AND product_id = ?";
        String queryUpdateTotalPrice = "UPDATE orders SET total_price = (SELECT SUM(subtotal) FROM orderdetails WHERE order_id = ?) WHERE order_id = ?";

        try {
            con = new DBContext().getConnection();
            con.setAutoCommit(false); // Bắt đầu transaction

            // Cập nhật thông tin chung của đơn hàng
            try (PreparedStatement pstOrder = con.prepareStatement(queryUpdateOrder)) {
                pstOrder.setString(1, name);
                pstOrder.setString(2, address);
                pstOrder.setString(3, phone);
                pstOrder.setString(4, notes);
                pstOrder.setString(5, status);
                pstOrder.setInt(6, orderId);

                int rowsUpdated = pstOrder.executeUpdate();
                if (rowsUpdated == 0) {
                    con.rollback();
                    return 0; // Không cập nhật được
                }
            }

            // Cập nhật thông tin chi tiết sản phẩm trong đơn hàng
            try (PreparedStatement pstDetails = con.prepareStatement(queryUpdateDetails)) {
                for (int i = 0; i < productIds.length; i++) {
                    pstDetails.setInt(1, Integer.parseInt(quantities[i])); // Số lượng
                    pstDetails.setString(2, sizes[i]); // Kích thước
                    pstDetails.setInt(3, Integer.parseInt(subtotals[i])); // Thành tiền
                    pstDetails.setInt(4, orderId); // ID đơn hàng
                    pstDetails.setInt(5, Integer.parseInt(productIds[i])); // ID sản phẩm

                    pstDetails.addBatch(); // Thêm vào batch
                }
                pstDetails.executeBatch(); // Thực thi batch

            }

            // Cập nhật tổng tiền của đơn hàng
            try (PreparedStatement pstTotal = con.prepareStatement(queryUpdateTotalPrice)) {
                pstTotal.setInt(1, orderId);
                pstTotal.setInt(2, orderId);

                pstTotal.executeUpdate();
            }

            con.commit(); // Hoàn thành transaction
            return 1; // Cập nhật thành công

        } catch (Exception e) {
            try {
                if (con != null) {
                    con.rollback(); // Rollback nếu có lỗi
                }
            } catch (Exception rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            return 0; // Có lỗi trong quá trình cập nhật
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true); // Bật lại AutoCommit
                    con.close();
                }
            } catch (Exception closeEx) {
                closeEx.printStackTrace();
            }
        }
    }


    public boolean updateOrderDetail(int orderId, int productId, int quantity, String size) {
        String query = "UPDATE orderdetails od " +
                "JOIN product p ON od.product_id = p.productId " +
                "SET od.quantity = ?, od.size = ?, od.subtotal = p.productPrice * ? " +
                "WHERE od.order_id = ? AND od.product_id = ?";
        try {
            con = new DBContext().getConnection();
            pst = con.prepareStatement(query);
            pst.setInt(1, quantity);       // Số lượng
            pst.setString(2, size);       // Kích thước
            pst.setInt(3, quantity);      // Số lượng (để tính subtotal)
            pst.setInt(4, orderId);       // ID đơn hàng
            pst.setInt(5, productId);     // ID sản phẩm

            int rowsUpdated = pst.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources();
        }
    }

    public void updateOrderDetails(int orderId, int productId, int quantity, double subtotal) {
        String queryUpdateDetails = "UPDATE orderdetails SET quantity = ?, subtotal = ? WHERE order_id = ? AND product_id = ?";

        try  {
            con = new DBContext().getConnection();
            pst = con.prepareStatement(queryUpdateDetails);
            pst.setInt(1, quantity);
            pst.setDouble(2, subtotal);
            pst.setInt(3, orderId);
            pst.setInt(4, productId);

            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public boolean updateOrder(Order order) {
        String query = "UPDATE orders SET name = ?, address = ?, phone = ?, notes = ?, status = ?, total_price = ? WHERE order_id = ?";

        try  {
            con = new DBContext().getConnection();
            pst = con.prepareStatement(query);
            pst.setString(1, order.getName());
            pst.setString(2, order.getAddress());
            pst.setString(3, order.getPhone());
            pst.setString(4, order.getNotes());
            pst.setString(5, order.getStatus());
            pst.setDouble(6, order.getTotalPrice());
            pst.setInt(7, order.getOrderId());

            int rowsUpdated = pst.executeUpdate();

            return rowsUpdated > 0; // Trả về true nếu có dòng được cập nhật
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi trong quá trình cập nhật
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        DbAdmin dbAdmin = new DbAdmin();


    }


}
