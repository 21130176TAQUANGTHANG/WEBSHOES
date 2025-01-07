package History;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/cancelOrder")
public class CancelOrderServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy orderId từ form
        String orderIdParam = req.getParameter("orderId");

        try {
            int orderId = Integer.parseInt(orderIdParam);
            // Gọi DAO để cập nhật trạng thái đơn hàng
            Dbhistory dao = new Dbhistory();
            boolean isCancelled = dao.cancelOrder(orderId);

            // Kiểm tra kết quả và phản hồi
            if (isCancelled) {
                req.setAttribute("message", "Đơn hàng đã được hủy thành công!");
            } else {
                req.setAttribute("error", "Không thể hủy đơn hàng. Vui lòng thử lại.");
            }
        } catch (NumberFormatException e) {
            req.setAttribute("error", "ID đơn hàng không hợp lệ.");
        }

        // Chuyển hướng về trang quản lý đơn hàng
        req.getRequestDispatcher("CustomerOrder.jsp").forward(req, resp);
    }
}
