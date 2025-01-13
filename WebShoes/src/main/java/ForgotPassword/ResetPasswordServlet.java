package ForgotPassword;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.security.MessageDigest;

@WebServlet("/ResetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        DBforgot dao = new DBforgot();

        HttpSession session = req.getSession();
        String email = (String) session.getAttribute("email");

        if (email == null) {
            req.setAttribute("error", "Email không hợp lệ. Vui lòng thử lại.");
            req.getRequestDispatcher("forgotPassword.jsp").forward(req, resp);
            return;
        }

        if (password.equals(confirmPassword)) {
            // Băm mật khẩu
            String hashedPassword = hashPassword(password);

            // Cập nhật mật khẩu
            boolean update = dao.updateNewPassword(email, hashedPassword);

            if (update) {
                session.removeAttribute("email");
                resp.sendRedirect("Login.jsp");
            } else {
                req.setAttribute("error", "Cập nhật mật khẩu thất bại. Vui lòng thử lại.");
                req.getRequestDispatcher("resetPassword.jsp").forward(req, resp);
            }
        } else {
            req.setAttribute("error", "Mật khẩu không khớp!");
            req.getRequestDispatcher("resetPassword.jsp").forward(req, resp);
        }
    }

    public String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
