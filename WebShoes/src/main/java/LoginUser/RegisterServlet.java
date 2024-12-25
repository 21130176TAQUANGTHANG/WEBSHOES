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

        String errorMessage = null;

        // Kiểm tra mật khẩu
        if (!password.equals(repeatPassword)) {
            errorMessage = "Mật khẩu không khớp, vui lòng thử lại.";
        } else if (password.contains(" ") || password.length() < 6) {
            errorMessage = "Mật khẩu không hợp lệ. Vui lòng đảm bảo không có khoảng trắng và có ít nhất 6 ký tự.";
        }

        // Kiểm tra số điện thoại
        int phone = 0;
        try {
            phone = Integer.parseInt(phoneStr);
        } catch (NumberFormatException e) {
            errorMessage = "Số điện thoại không hợp lệ.";
        }

        // Kiểm tra thông tin khác
        if (username == null || username.isEmpty() || email == null || email.isEmpty() || address == null || address.isEmpty()) {
            errorMessage = "Vui lòng điền đầy đủ thông tin.";
        }

        if (errorMessage != null) {
            // Lưu thông báo lỗi vào attribute và chuyển hướng lại trang đăng ký
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return;
        }

        // Đăng ký người dùng
        try {
            DBDAO dbdao = new DBDAO();
            User user = new User(username, password, email, phone, address);
            dbdao.registerUser(user);
            response.sendRedirect("Login.jsp");
        } catch (Exception e) {
            // Nếu xảy ra lỗi trong quá trình đăng ký
            errorMessage = "Có lỗi xảy ra khi đăng ký. Vui lòng thử lại sau.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("Register.jsp").forward(request, response);
        }
    }


}