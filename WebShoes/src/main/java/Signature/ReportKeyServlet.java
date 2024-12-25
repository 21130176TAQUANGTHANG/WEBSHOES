
package Signature;

import LoginUser.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Base64;

@WebServlet("/ReportKeyServlet")
public class ReportKeyServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String userId = null;

        User user = (User) session.getAttribute("user");
        if (user != null) {
            userId = user.getId();
        }

        if (userId == null) {
            resp.sendRedirect("login.jsp"); // Chuyển hướng nếu chưa đăng nhập
            return;
        }

        try {
            DbSecurity db = new DbSecurity();
            KeyDeletionScheduler scheduler = new KeyDeletionScheduler();

            // Cập nhật thời gian endTime
            db.reportLostKey(userId);

            // Tính toán thời gian xóa key
            long currentTime = System.currentTimeMillis();
            long deletionTime = currentTime + 60000; // 1 phút sau
            Timestamp deletionTimestamp = new Timestamp(deletionTime);

            // Lập lịch xóa key
            scheduler.scheduleKeyDeletion(userId);

            // Gửi thông tin thời gian xóa đến JSP
            req.setAttribute("deletionTime", deletionTimestamp);
            req.getRequestDispatcher("userProfile.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi báo mất khóa.");
        }
    }
}
