package Product;

import DBConnect.DBDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/productDetail")
public class ProductDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String productId = req.getParameter("productId");

        if (productId != null && !productId.isEmpty()) {
            DBDAO dao = new DBDAO();
            Product product = dao.getProductById(Integer.parseInt(productId));

            if (product != null) {
                req.setAttribute("product", product);
                req.getRequestDispatcher("productDetail.jsp").forward(req, resp);
            } else {
                resp.sendRedirect("error.jsp");
            }
        } else {
            resp.sendRedirect("error.jsp");
        }
    }
}
