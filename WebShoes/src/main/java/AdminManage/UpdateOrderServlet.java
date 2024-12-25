package AdminManage;

import LoginUser.User;
import Order.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/UpdateOrderServlet")
public class UpdateOrderServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(UpdateOrderServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String orderIdParam = req.getParameter("orderId");
        int orderId = Integer.parseInt(orderIdParam);
        DbAdmin dbAdmin = new DbAdmin();

        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String phone = req.getParameter("phone");
        String notes = req.getParameter("notes");
        String status = req.getParameter("status");

        String[] productIds = req.getParameterValues("productIds");
        String[] quantities = req.getParameterValues("quantities");
        String[] sizes = req.getParameterValues("sizes");
        String[] subtotals = req.getParameterValues("subtotals");

        // Tính lại tổng tiền
        int totalPrice = 0;
        for (int i = 0; i < subtotals.length; i++) {
            totalPrice += Double.parseDouble(subtotals[i]);
        }

        // Cập nhật tổng tiền vào đơn hàng
        Order order = dbAdmin.getOrderById(orderId);
        order.setTotalPrice(totalPrice);
        dbAdmin.updateOrder(order);

        // Lưu nhật ký cập nhật đơn hàng

        // Lấy thông tin người dùng từ session
        User user = (User) req.getSession().getAttribute("user");
        String userId = null;
        if (user != null) {
            userId = user.getId();
        }
        String logMessage = String.format("Tài khoản có Id: %s cập nhật đơn hàng (ID: %d). Tổng giá mới: %d, Trạng thái: %s, Products: %d",
                userId, orderId, totalPrice, status, productIds.length);
        logger.info(logMessage);



        // Lưu thông tin người dùng và nhật ký vào request để truyền sang JSP
        req.setAttribute("logMessage", logMessage);
        req.setAttribute("userId", userId);

        // Cập nhật chi tiết đơn hàng
        for (int i = 0; i < productIds.length; i++) {
            int productId = Integer.parseInt(productIds[i]);
            int quantity = Integer.parseInt(quantities[i]);
            double subtotal = Double.parseDouble(subtotals[i]);

            // Cập nhật chi tiết sản phẩm
            dbAdmin.updateOrderDetails(orderId, productId, quantity, subtotal);

            // Ghi nhật ký cho từng sản phẩm
            logger.info("Tài khoản có Id {}, sản phẩm có id (ID: {}) đơn hàng có id (ID: {}). số lượng: {}, tổng tiền: {}",
                    userId, productId, orderId, quantity, subtotal);
        }

        // Chuyển tiếp đến trang JSP và hiển thị nhật ký và thông tin tài khoản
        req.getRequestDispatcher("VerifySignatureServlet").forward(req, resp);
    }
}
