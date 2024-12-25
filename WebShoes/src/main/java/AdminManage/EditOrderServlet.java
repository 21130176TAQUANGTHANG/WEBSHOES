package AdminManage;

import Order.Order;
import Order.OrderItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/EditOrderServlet")
public class EditOrderServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderIdParam = request.getParameter("orderId");
        int orderId = Integer.parseInt(orderIdParam);

        DbAdmin dbAdmin = new DbAdmin();
        Order order = dbAdmin.getOrderById(orderId);

        request.setAttribute("order", order);
        request.getRequestDispatcher("editOrder.jsp").forward(request, response);
    }
}

