package History;

import LoginUser.AccountFF;
import LoginUser.GoogleAccount;
import LoginUser.User;
import Order.Order;
import Order.OrderItem;
import Signature.DbSecurity;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/downloadBill")
public class DownloadBillServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy orderId từ tham số URL hoặc session
        String orderIdParam = req.getParameter("orderId"); // lấy orderid từ URL hoặc request parameter
        int orderId = Integer.parseInt(orderIdParam); // Chuyển đổi thành kiểu int

        resp.setContentType("text/plain");
        resp.setHeader("Content-Disposition", "attachment;filename=bill.txt");

//        try (PrintWriter writer = resp.getWriter()) {
//            DbSecurity dbSecurity = new DbSecurity();
//            dbSecurity.getOrdersByUserId(orderId, writer); // Truyền orderId vào phương thức
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
}
