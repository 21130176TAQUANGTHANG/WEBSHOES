package Dashboard;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
@WebServlet("/admin")
public class TopSellingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DbDashBoard dbDashBoard = new DbDashBoard();
        List<ProductStats>productStats = dbDashBoard.bestSellingProducts();
        req.setAttribute("bestSellingProducts", productStats);
        req.getRequestDispatcher("admin.jsp").forward(req, resp);
    }
}
