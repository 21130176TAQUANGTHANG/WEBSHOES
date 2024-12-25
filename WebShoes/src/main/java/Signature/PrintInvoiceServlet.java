package Signature;

import Order.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/PrintInvoiceServlet")
public class PrintInvoiceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy orderId từ tham số URL hoặc session
        String orderIdParam = req.getParameter("orderId"); // lấy orderid từ URL hoặc request parameter
        int orderId = Integer.parseInt(orderIdParam); // Chuyển đổi thành kiểu int

        resp.setContentType("text/plain");
        resp.setHeader("Content-Disposition", "attachment;filename=bill.txt");

        try (PrintWriter writer = resp.getWriter()) {
            DbSecurity dbSecurity = new DbSecurity();
            dbSecurity.getOrdersByUserId(orderId, writer); // Truyền orderId vào phương thức
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
