package Cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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

        // Lấy thông tin từ request
        String productIdParam = req.getParameter("productId");
        String quantityParam = req.getParameter("quantity");

        if (productIdParam != null && quantityParam != null) {
            try {
                int productId = Integer.parseInt(productIdParam);
                int quantity = Integer.parseInt(quantityParam);

                // Cập nhật số lượng sản phẩm trong giỏ hàng
                cart.update(productId, quantity);

                // Tính lại tổng giá trị giỏ hàng
                int totalPriceForAllProducts = cart.getTotalPrice();

                // Trả về tổng giá trị giỏ hàng cho AJAX
                PrintWriter out = resp.getWriter();
                out.println(totalPriceForAllProducts);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }
}
