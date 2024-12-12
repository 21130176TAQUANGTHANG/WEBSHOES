package History;

import LoginUser.User;
import Order.Order;
import Order.OrderItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
@WebServlet("/downloadBill")
public class DownloadBillServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy thông tin đơn hàng và khách hàng từ session hoặc request attribute
        User user = (User) req.getSession().getAttribute("user");
        List<OrderItem> orderItems = (List<OrderItem>) req.getSession().getAttribute("orderItems"); // Danh sách sản phẩm
        Order order = (Order) req.getSession().getAttribute("order"); // Thông tin đơn hàng

        // Đặt header cho file tải xuống
        resp.setContentType("text/plain");
        resp.setHeader("Content-Disposition", "attachment;filename=bill.txt");

        // Viết dữ liệu vào file
        try (PrintWriter writer = resp.getWriter()) {
            writer.println("========== HÓA ĐƠN ==========");
            writer.println("Tên khách hàng: " + user.getUsername());
            writer.println("Email: " + user.getEmail());
            writer.println("Số điện thoại: " + user.getPhone());
            writer.println("Địa chỉ: " + user.getAddress());
            writer.println("----------------------------");

            writer.println("Ngày đặt hàng: " + order.getOrderDate());
            writer.println("Mã đơn hàng: " + order.getOrderId());
            writer.println("Ghi chú: " + order.getNotes());
            writer.println("----------------------------");

            writer.println("Danh sách sản phẩm:");
            writer.printf("%-20s %-10s %-10s %-10s\n", "Tên sản phẩm", "SL", "Đơn giá", "Thành tiền");
            double total = 0;
            for (OrderItem item : orderItems) {
                double itemTotal = item.getQuantity() * item.getPrice();
                writer.printf("%-20s %-10d %-10.2f %-10.2f\n",
                        item.getProductName(), item.getQuantity(), item.getPrice(), itemTotal);
                total += itemTotal;
            }
            writer.println("----------------------------");
            writer.printf("Tổng tiền: %.2f\n", total);
            writer.println("============================");
        }
    }
}
