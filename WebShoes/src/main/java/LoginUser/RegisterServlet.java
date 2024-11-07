package LoginUser;


import DBConnect.DBDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "RegisterServlet", value = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String repeatPassword = request.getParameter("repeatPassword");
        String phoneStr = request.getParameter("phone");
        String address = request.getParameter("address");

        if (!password.equals(repeatPassword)) {
            response.getWriter().println("Mật khẩu không khớp, vui lòng thử lại.");
            return;
        }

        if (password.contains(" ") || password.length() < 6) {
            response.getWriter().println("Mật khẩu không hợp lệ. Vui lòng đảm bảo không có khoảng trắng và có ít nhất 6 ký tự.");
            return;
        }

        int phone = 0;
        try {
            phone = Integer.parseInt(phoneStr);
        } catch (NumberFormatException e) {
            response.getWriter().println("Số điện thoại không hợp lệ.");
            return;
        }

        if (username == null || username.isEmpty() || email == null || email.isEmpty() || address == null || address.isEmpty()) {
            response.getWriter().println("Vui lòng điền đầy đủ thông tin.");
            return;
        }

        DBDAO dbdao = new DBDAO();
        User user = new User(username, password, email, phone, address);
        dbdao.registerUser(user);
        response.sendRedirect("Login.jsp");
    }

}