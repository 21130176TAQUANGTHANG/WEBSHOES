package History;

import DBConnect.DBDAO;
import LoginUser.AccountFF;
import LoginUser.GoogleAccount;
import LoginUser.User;
import Order.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
@WebServlet("/CustomerOrderServlet")
public class CustomerOrderServlet  extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
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
        Dbhistory dao = new Dbhistory();
        User userhis = dao.getUserById(userId); // Lấy thông tin cá nhân
        List<Order> orders = dao.getOrdersByUserId(userId); // Lấy danh sách đơn hàng
        req.setAttribute("user", userhis);
        req.setAttribute("orders", orders);

        req.getRequestDispatcher("CustomerOrder.jsp").forward(req, resp);
    }
}
