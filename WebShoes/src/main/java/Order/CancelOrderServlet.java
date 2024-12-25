package Order;

import Signature.DbSecurity;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "CancelOrderServlet", urlPatterns = {"/CancelOrderServlet"})
public class CancelOrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String orderId = request.getParameter("orderId");
        DbSecurity orderDAO = new DbSecurity();

        try {
            boolean result = orderDAO.cancelOrder(orderId);

            if (result) {
                request.getSession().setAttribute("message", "Đơn hàng đã được hủy thành công.");
            } else {
                request.getSession().setAttribute("error", "Không thể hủy đơn hàng. Vui lòng thử lại.");
            }

            response.sendRedirect("admin_order");
        } catch (Exception e) {
            throw new ServletException("Lỗi khi hủy đơn hàng.", e);
        }
    }
}
