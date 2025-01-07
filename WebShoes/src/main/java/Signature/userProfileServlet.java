package Signature;

import LoginUser.AccountFF;
import LoginUser.GoogleAccount;
import LoginUser.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
@WebServlet("/userProfileServlet")
public class userProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String userId = getUserIdFromSession(session);

        if (userId == null) {
            resp.sendRedirect("login.jsp"); // Chuyển hướng đến trang đăng nhập nếu không có người dùng
            return;
        }

        DbSecurity db = new DbSecurity();
        boolean userHasKey = db.hasKey(userId);

        req.setAttribute("userHasKey", userHasKey);
        RequestDispatcher dispatcher = req.getRequestDispatcher("userProfile.jsp");
        dispatcher.forward(req, resp);
    }

    private String getUserIdFromSession(HttpSession session) {
        // Kiểm tra người dùng thông thường
        User user = (User) session.getAttribute("user");
        if (user != null) {
            return user.getId();
        }

        // Kiểm tra người dùng Google
        GoogleAccount googleUser = (GoogleAccount) session.getAttribute("googleUser");
        if (googleUser != null) {
            return googleUser.getId();
        }

        // Kiểm tra người dùng Facebook
        AccountFF facebookUser = (AccountFF) session.getAttribute("facebookUser");
        if (facebookUser != null) {
            return facebookUser.getId();
        }

        return null; // Không có người dùng đăng nhập
    }

}
