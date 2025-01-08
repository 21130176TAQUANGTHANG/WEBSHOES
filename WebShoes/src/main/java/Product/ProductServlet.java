package Product;

import DBConnect.DBDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import jakarta.servlet.http.Cookie;

import jakarta.servlet.http.Cookie;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBDAO dao = new DBDAO();

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

        // Tải ResourceBundle
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);

        // Lấy danh sách sản phẩm
        List<Product> allProducts = dao.getAllProducts();
        if (allProducts == null) {
            req.setAttribute("errorMessage", bundle.getString("noProductsFound"));
        } else {
            req.setAttribute("products", allProducts);
        }

        // Chuyển tiếp đến JSP
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
