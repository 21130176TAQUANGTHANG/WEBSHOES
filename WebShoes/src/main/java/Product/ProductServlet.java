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

@WebServlet("/product")
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBDAO dao = new DBDAO();

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

        // Lấy danh sách sản phẩm
        List<Product> allProducts = dao.getAllProducts();
        if (allProducts == null) {
            req.setAttribute("errorMessage", bundle.getString("noProductsFound")); // Thông báo nếu không có sản phẩm
        } else {
            req.setAttribute("products", allProducts);

            // Lấy tỷ giá và ký hiệu tiền tệ từ ResourceBundle
            try {
                double exchangeRate = Double.parseDouble(bundle.getString("exchangeRate"));
                String currencySymbol = bundle.getString("currencySymbol");

                req.setAttribute("exchangeRate", exchangeRate);
                req.setAttribute("currencySymbol", currencySymbol);
            } catch (NumberFormatException | MissingResourceException e) {
                req.setAttribute("exchangeRate", 1.0); // Tỷ giá mặc định
                req.setAttribute("currencySymbol", ""); // Ký hiệu tiền mặc định
            }
        }

        // Chuyển tiếp đến trang JSP
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
