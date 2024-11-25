package Order;

import Cart.Cart;
import Cart.CartProduct;
import DBConnect.DBDAO;
import LoginUser.AccountFF;
import LoginUser.GoogleAccount;
import LoginUser.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/ProcessCheckout")
public class ProcessCheckoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy thông tin người dùng từ form
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String phone = req.getParameter("phone");
        String notes = req.getParameter("notes");

        // Kiểm tra nếu thiếu thông tin nào
        if (name == null || address == null || phone == null || name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            resp.sendRedirect("checkout.jsp?error=missingInfo");
            return;
        }

        // Lấy giỏ hàng từ session
        HttpSession session = req.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null || cart.getData().isEmpty()) {
            resp.sendRedirect("checkout.jsp?error=emptyCart");
            return;
        }

        // Kiểm tra phương thức đăng nhập và lấy userId
        String userId = null;

        // Kiểm tra nếu người dùng đăng nhập bằng tài khoản thông thường
        User user = (User) session.getAttribute("user");
        if (user != null) {
            userId = user.getId();
            System.out.println("userId = " + userId);
        }

        // Kiểm tra nếu người dùng đăng nhập bằng Google
        GoogleAccount googleUser = (GoogleAccount) session.getAttribute("googleUser");
        if (googleUser != null) {
            String googleId = googleUser.getId(); // Lấy ID ở dạng String
            System.out.println("Google ID: " + googleId); // In ra ID
            if (googleId != null && !googleId.isEmpty()) {
                userId = googleId; // Lưu googleId mà không cần chuyển đổi
                System.out.println("userIdGG = " + userId);
            } else {
                resp.sendRedirect("checkout.jsp?error=invalidGoogleId");
                return;
            }
        }

        // Kiểm tra nếu người dùng đăng nhập bằng Facebook
        AccountFF facebookUser = (AccountFF) session.getAttribute("facebookUser");
        if (facebookUser != null) {
            try {
                userId = facebookUser.getId();
                System.out.println("userIdFF = " + userId);
            } catch (NumberFormatException e) {
                resp.sendRedirect("checkout.jsp?error=invalidFacebookId");
                return;
            }
        }

        // Đảm bảo rằng userId không phải là null
        if (userId == null) {
            resp.sendRedirect("checkout.jsp?error=noUser");
            return;
        }

        // Lưu đơn hàng vào cơ sở dữ liệu
        DBDAO dao = new DBDAO();
        int totalPrice = cart.getTotalPrice();
        int orderId = dao.saveOrder(userId, totalPrice, name, address, phone, notes);

        // Lưu chi tiết đơn hàng và cập nhật số lượng còn lại trong kho
        if (orderId > 0) {
            // Map lưu thông tin số lượng còn lại
            Map<Integer, Integer> remainingQuantities = new HashMap<>();

            for (CartProduct cartProduct : cart.getData().values()) {
                int productId = cartProduct.getProduct().getProductId();
                int quantityOrdered = cartProduct.getQuantity();
                String size = cartProduct.getSize();
                int subtotal = cartProduct.getSubtotal();

                // Lưu chi tiết đơn hàng
                dao.saveOrderDetails(orderId, productId, quantityOrdered, size, subtotal);

                // Cập nhật số lượng sản phẩm còn lại trong kho
                int remainingQuantity = dao.updateProductAfterOrder(productId, quantityOrdered);
                remainingQuantities.put(productId, remainingQuantity);
            }

            // Đưa remainingQuantities vào request để hiển thị trong JSP
            req.setAttribute("remainingQuantities", remainingQuantities);

            // Sau khi lưu xong, xóa giỏ hàng và chuyển hướng tới trang thông báo thành công
            session.removeAttribute("cart");
            req.getRequestDispatcher("orderSuccess.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("checkout.jsp?error=orderFailed");
        }
    }
}
