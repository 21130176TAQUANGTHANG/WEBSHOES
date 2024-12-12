package AdminManage;

import DBConnect.DBDAO;
import Product.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
@WebServlet("/AdminProduct")
public class AdminProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBDAO dbdao= new DBDAO();
        List<Product> products = dbdao.getAllProducts();
        req.setAttribute("adminproducts", products);
        req.getRequestDispatcher("adminProduct.jsp").forward(req, resp);
    }
}
