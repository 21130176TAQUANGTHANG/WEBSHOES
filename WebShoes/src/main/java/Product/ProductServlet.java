package Product;

import DBConnect.DBDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@WebServlet("/product")
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBDAO dao = new DBDAO();

        List<Integer> bestSellingProductIds = Arrays.asList(100, 101);
        List<Product> products = dao.getProductsByIds(bestSellingProductIds);
        if (products == null || products.isEmpty()) {
            req.setAttribute("errorMessage", "Không có sản phẩm nào được tìm thấy.");
        } else {
            req.setAttribute("products", products);
        }

        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
