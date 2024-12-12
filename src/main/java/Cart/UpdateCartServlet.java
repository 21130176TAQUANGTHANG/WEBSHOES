package Cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;


import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/UpdateCartServlet")
public class UpdateCartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        String productIdParam = req.getParameter("productId");
        String quantityParam = req.getParameter("quantity");

        JSONObject jsonResponse = new JSONObject();

        if (productIdParam != null && quantityParam != null) {
            try {
                int productId = Integer.parseInt(productIdParam);
                int quantity = Integer.parseInt(quantityParam);

                if (cart.update(productId, quantity)) {
                    jsonResponse.put("status", "success");
                    jsonResponse.put("totalPrice", cart.getTotalPrice());
                    jsonResponse.put("productQuantity", cart.getProduct(productId).getQuantity());
                } else {
                    jsonResponse.put("status", "error");
                    jsonResponse.put("message", "Cập nhật không thành công");
                }
            } catch (NumberFormatException e) {
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "Dữ liệu không hợp lệ");
            }
        } else {
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "Thiếu thông tin sản phẩm");
        }

        resp.setContentType("application/json");
        resp.getWriter().write(jsonResponse.toString());
    }
}
