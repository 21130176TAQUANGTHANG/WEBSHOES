package Order;

import ListProduct.DBlistproductDAO;
import Product.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

@WebServlet("/productlist")
public class BestSellingProduct extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Danh sách ID sản phẩm cần lấy (ví dụ từ tham số request hoặc mã hóa cứng)
        List<Integer> ids = Arrays.asList(100,101, 103, 105, 106,107,108,109);

        // Lấy tham số lang từ URL (nếu có) và cập nhật locale vào session
        String lang = req.getParameter("lang");
        Locale locale;

        if (lang != null) {
            switch (lang) {
                case "vi":
                    locale = new Locale("vi", "VN");
                    break;
                case "en":
                    locale = new Locale("en", "US");
                    break;
                default:
                    locale = new Locale("vi", "VN"); // Ngôn ngữ mặc định
            }
            req.getSession().setAttribute("locale", locale);
        }

        // Lấy locale từ session, hoặc sử dụng mặc định
        locale = (Locale) req.getSession().getAttribute("locale");
        if (locale == null) {
            locale = new Locale("vi", "VN");
            req.getSession().setAttribute("locale", locale);
        }
        // Tải ResourceBundle theo locale
        ResourceBundle bundle;
        try {
            bundle = ResourceBundle.getBundle("messages", locale);
        } catch (MissingResourceException e) {
            bundle = ResourceBundle.getBundle("messages", new Locale("vi", "VN")); // Fallback về Tiếng Việt
        }


        // Gọi phương thức để lấy danh sách sản phẩm
        DBlistproductDAO bsp = new DBlistproductDAO();
        List<Product> products = bsp.getProductsByIds(ids);
        double exchangeRate = Double.parseDouble(bundle.getString("exchangeRate"));
        String currencySymbol = bundle.getString("currencySymbol");
        req.setAttribute("exchangeRate", exchangeRate);
        req.setAttribute("currencySymbol", currencySymbol);
        // Gửi danh sách sản phẩm đến JSP
        req.setAttribute("BestSellingProducts", products);
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
