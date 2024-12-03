package AdminManage;

import DBConnect.DBDAO;
import Order.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
@WebServlet("/admin_order")
public class AdminOrderManagementServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBDAO dbdao = new DBDAO();
        String pageParam = req.getParameter("page");
        int page = (pageParam != null) ? Integer.parseInt(pageParam) : 1; // Mặc định trang 1
        int size = 7; // Số lượng bản ghi trên mỗi trang

        List<Order> orderList = dbdao.getOrdersByPage(page, size);
        int totalOrders = dbdao.getTotalOrders();
        int totalPages = (int) Math.ceil((double) totalOrders / size);

        req.setAttribute("orderList", orderList);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);

        req.getRequestDispatcher("AdminOrderManagement.jsp").forward(req, resp);
    }
}
