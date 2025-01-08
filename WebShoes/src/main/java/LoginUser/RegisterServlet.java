package LoginUser;


import DBConnect.DBDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.Locale;
import java.util.ResourceBundle;

@WebServlet(name = "RegisterServlet", value = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String repeatPassword = request.getParameter("repeatPassword");
        String phoneStr = request.getParameter("phone");
        String address = request.getParameter("address");

        String errorMessage = null;

        // Kiểm tra mật khẩu
        if (!password.equals(repeatPassword)) {
            errorMessage = "Mật khẩu không khớp, vui lòng thử lại.";
        } else if (password.contains(" ") || password.length() < 6) {
            errorMessage = "Mật khẩu không hợp lệ. Vui lòng đảm bảo không có khoảng trắng và có ít nhất 6 ký tự.";
        }

        // Kiểm tra số điện thoại
        int phone = 0;
        try {
            phone = Integer.parseInt(phoneStr);
        } catch (NumberFormatException e) {
            errorMessage = "Số điện thoại không hợp lệ.";
        }

        // Kiểm tra thông tin khác
        if (username == null || username.isEmpty() || email == null || email.isEmpty() || address == null || address.isEmpty()) {
            errorMessage = "Vui lòng điền đầy đủ thông tin.";
        }

        // Kiểm tra email đã tồn tại
        DBDAO dbdao = new DBDAO();
        if (dbdao.isEmailExist(email)) {
            errorMessage = "Email đã được sử dụng. Vui lòng thử với email khác.";
        }

        if (errorMessage != null) {
            // Lưu thông báo lỗi vào attribute và chuyển hướng lại trang đăng ký
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return;
        }

        // Băm mật khẩu trước khi lưu vào database
        // Băm mật khẩu bằng BCrypt
        try {
            String hashedPassword = hashPassword(password); // Băm mật khẩu
            User user = new User(username, hashedPassword, email, phone, address);
            dbdao.registerUser(user); // Lưu người dùng vào DB
            response.sendRedirect("Login.jsp");
        } catch (Exception e) {
            errorMessage = "Có lỗi xảy ra khi đăng ký. Vui lòng thử lại sau.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("Register.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy tham số ngôn ngữ từ URL (nếu có)
        String lang = req.getParameter("lang");
        Locale locale;

        if (lang != null) {
            // Thiết lập locale dựa trên tham số lang
            switch (lang) {
                case "vi":
                    locale = new Locale("vi", "VN");
                    break;
                case "en":
                    locale = new Locale("en", "US");
                    break;
                default:
                    locale = new Locale("vi", "VN");
            }

            // Lưu ngôn ngữ vào session
            req.getSession().setAttribute("locale", locale);

            // **Lưu ngôn ngữ vào Cookie**
            Cookie langCookie = new Cookie("lang", lang);
            langCookie.setMaxAge(60 * 60 * 24 * 30); // Lưu trong 30 ngày
            resp.addCookie(langCookie);
        } else {
            // Nếu không có tham số `lang`, kiểm tra trong Cookie
            Cookie[] cookies = req.getCookies();
            String cookieLang = null;

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("lang")) {
                        cookieLang = cookie.getValue();
                        break;
                    }
                }
            }

            // Thiết lập locale từ Cookie nếu tồn tại
            if (cookieLang != null) {
                switch (cookieLang) {
                    case "vi":
                        locale = new Locale("vi", "VN");
                        break;
                    case "en":
                        locale = new Locale("en", "US");
                        break;
                    default:
                        locale = new Locale("vi", "VN");
                }
                req.getSession().setAttribute("locale", locale);
            } else {
                // Nếu không có Cookie hoặc Session, sử dụng mặc định
                locale = new Locale("vi", "VN");
                req.getSession().setAttribute("locale", locale);
            }
        }
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        req.getRequestDispatcher("Register.jsp").forward(req,resp);
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