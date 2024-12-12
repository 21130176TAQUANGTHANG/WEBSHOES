package Order;

import DBConnect.DBDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet("/ConfirmOrderServlet")

public class ConfirmOrderServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int orderId = Integer.parseInt(req.getParameter("orderId"));
        DBDAO dao = new DBDAO();
        boolean success = dao.updateOrderStatus(orderId, "Xác nhận thành công");

        if (success) {
            resp.sendRedirect("admin_order?success=true");
        } else {
            resp.sendRedirect("admin_order?error=true");
        }
    }
}
