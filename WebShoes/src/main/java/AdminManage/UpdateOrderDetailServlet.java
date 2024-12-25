package AdminManage;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/UpdateOrderDetailServlet")
public class UpdateOrderDetailServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int orderId = Integer.parseInt(req.getParameter("orderId"));
        int productId = Integer.parseInt(req.getParameter("productId"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        String size = req.getParameter("size");

        DbAdmin dbAdmin = new DbAdmin();
        boolean result = dbAdmin.updateOrderDetail(orderId, productId, quantity, size);

        resp.setContentType("application/json");
        if (result) {
            resp.getWriter().write("{\"success\": true}");
        } else {
            resp.getWriter().write("{\"success\": false, \"message\": \"Cập nhật không thành công.\"}");
        }
    }
}

