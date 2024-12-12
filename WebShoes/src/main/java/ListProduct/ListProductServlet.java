package ListProduct;

import DBConnect.DBDAO;
import Product.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@WebServlet("/listproduct")
public class ListProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBlistproductDAO dao = new DBlistproductDAO();

        // Lấy locale từ session
        Locale locale = (Locale) req.getSession().getAttribute("locale");
        if (locale == null) {
            locale = new Locale("vi", "VN"); // Mặc định là Tiếng Việt
        }

        // Đọc ResourceBundle theo locale
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
        List<Product> allProducts = dao.getAllProduct();

        if (allProducts == null) {
            req.setAttribute("errorMessage", bundle.getString("noProductsFound")); // Dùng ResourceBundle cho thông báo
        } else {
            req.setAttribute("listproducts", allProducts);

            // Lấy tỷ giá và ký hiệu tiền
            double exchangeRate = Double.parseDouble(bundle.getString("exchangeRate"));
            String currencySymbol = bundle.getString("currencySymbol");

            req.setAttribute("exchangeRate", exchangeRate);
            req.setAttribute("currencySymbol", currencySymbol);
        }

        req.getRequestDispatcher("listproduct.jsp").forward(req, resp);
    }
}
