package Cart;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy thông tin sản phẩm và số lượng từ request
        String productIdParam = req.getParameter("productId");
        String quantityParam = req.getParameter("quantity");

        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        if (productIdParam != null && quantityParam != null) {
            try {
                int productId = Integer.parseInt(productIdParam);
                int quantity = Integer.parseInt(quantityParam);

                // Cập nhật giỏ hàng
                if (cart.update(productId, quantity)) {
                    session.setAttribute("cart", cart);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace(); // Xử lý ngoại lệ
            }
        }

        // Kiểm tra nếu có yêu cầu hoàn thành đơn hàng
        String completeOrder = req.getParameter("completeOrder");
        if ("true".equals(completeOrder)) {
            Integer userId = (Integer) session.getAttribute("userId"); // Giả sử userId đã lưu trong session khi đăng nhập
            if (userId != null) {
                // Nếu lưu đơn hàng thành công, chuyển hướng đến trang cảm ơn
                resp.sendRedirect("thankYou.jsp");
            } else {
                // Xử lý trường hợp lưu đơn hàng thất bại
                resp.sendRedirect("viewCart.jsp?error=true");
            }
        } else {
            // Chuyển hướng về giỏ hàng sau khi cập nhật
            resp.sendRedirect("viewCart.jsp");
        }
    }


}
