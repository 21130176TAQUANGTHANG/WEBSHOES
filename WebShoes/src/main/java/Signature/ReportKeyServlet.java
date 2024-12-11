package Signature;

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
import java.security.*;
import java.util.Base64;

@WebServlet("/ReportKeyServlet")
public class ReportKeyServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String userId = (String) session.getAttribute("userId");

        if (userId == null) {
            resp.sendRedirect("login.jsp?error=notLoggedIn");
            return;
        }

        try {
            DbSecurity db = new DbSecurity();

            // Kiểm tra nếu Public Key tồn tại
            if (db.isPublicKeyExist(userId)) {
                // Cập nhật endTime của key hiện tại
                db.updateKeyEndTime(userId);

                // Gửi thông báo thành công
                req.setAttribute("message", "Key của bạn đã được báo mất thành công.");
            } else {
                // Không tìm thấy key
                req.setAttribute("errorMessage", "Không tìm thấy key để báo mất.");
            }

            // Chuyển hướng về trang kết quả
            req.getRequestDispatcher("reportResult.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi xử lý yêu cầu.");
        }
    }
}
