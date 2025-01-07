package AdminManage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/CalculateSubtotalServlet")
public class CalculateSubtotalServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String priceParam = req.getParameter("price");
        String quantityParam = req.getParameter("quantity");

        try {
            double price = Double.parseDouble(priceParam);
            int quantity = Integer.parseInt(quantityParam);

            // Tính toán tổng tiền
            double subtotal = price * quantity;

            // Trả về JSON chứa subtotal
            resp.setContentType("application/json");
            resp.getWriter().write("{\"subtotal\": " + subtotal + "}");
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Invalid input\"}");
        }
    }
}
