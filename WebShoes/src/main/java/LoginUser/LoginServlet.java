package LoginUser;

import DBConnect.DBDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        DBDAO dbdao = new DBDAO();
        User user = dbdao.checkLogin(email, password); // Gọi phương thức checkLogin

        String error = "Sai tài khoản hoặc mật khẩu";
        if (user != null) {
            // Đăng nhập thành công
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            response.sendRedirect("product");
        } else {
            // Đăng nhập thất bại
            request.setAttribute("error", error);
            request.getRequestDispatcher("Login.jsp").forward(request, response);
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
        req.getRequestDispatcher("Login.jsp").forward(req, resp);
    }
}
