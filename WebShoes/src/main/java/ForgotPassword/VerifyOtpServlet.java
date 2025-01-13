package ForgotPassword;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
@WebServlet("/VerifyOtpServlet")
public class VerifyOtpServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userOtp = req.getParameter("otp");

        HttpSession session = req.getSession();
        String sessionOtp = (String) session.getAttribute("otp");

        if (sessionOtp != null && sessionOtp.equals(userOtp)) {
            // Xóa OTP khỏi session để tránh sử dụng lại
            session.removeAttribute("otp");

            // Chuyển đến trang đặt lại mật khẩu
            resp.sendRedirect("resetPassword.jsp");
        } else {
            req.setAttribute("error", "Mã OTP không chính xác!");
            req.getRequestDispatcher("verifyOtp.jsp").forward(req, resp);
        }
    }
}
