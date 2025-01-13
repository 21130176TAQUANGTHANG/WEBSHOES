package ListProduct;

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
@WebServlet("/search")

public class SearchProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBlistproductDAO dao = new DBlistproductDAO();

        String productName = req.getParameter("name");
        String logoParam = req.getParameter("logo");
        int logo = 0;
        if (logoParam != null) {
            try {
                logo = Integer.parseInt(logoParam);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        List<Product> filteredProducts = dao.getProductsByName(productName);

        Locale locale = (Locale) req.getSession().getAttribute("locale");
        if (locale == null) {
            locale = new Locale("vi", "VN");
        }
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);

        if (filteredProducts == null || filteredProducts.isEmpty()) {
            req.setAttribute("errorMessage", bundle.getString("noProductsFound"));
        } else {
            req.setAttribute("listproducts", filteredProducts);
        }

        req.getRequestDispatcher("listproduct.jsp").forward(req, resp);
    }

}
