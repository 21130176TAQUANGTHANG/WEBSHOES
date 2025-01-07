package Order;

import ListProduct.DBlistproductDAO;
import Product.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/productlist")
public class BestSellingProduct extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Danh sách ID sản phẩm cần lấy (ví dụ từ tham số request hoặc mã hóa cứng)
        List<Integer> ids = Arrays.asList(100,101, 103, 105, 106,107,108,109);

        // Gọi phương thức để lấy danh sách sản phẩm
        DBlistproductDAO bsp = new DBlistproductDAO();
        List<Product> products = bsp.getProductsByIds(ids);

        // Gửi danh sách sản phẩm đến JSP
        req.setAttribute("BestSellingProducts", products);
        req.getRequestDispatcher("index.jsp").forward(req, resp);
    }
}
