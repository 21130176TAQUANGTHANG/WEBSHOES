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
        int phone = Integer.parseInt(request.getParameter("phone"));
        String address = request.getParameter("address");

        if (!password.equals(repeatPassword)) {
            // Xử lý trường hợp mật khẩu và repeat password không khớp
            response.getWriter().println("Mật khẩu không khớp, vui lòng thử lại.");
            return;
        }
        if (username == null || username.isEmpty() ||
                email == null || email.isEmpty() ||
                password == null || password.isEmpty() ||
                phone == 0 ||
                address == null || address.isEmpty()) {

            response.getWriter().println("Vui lòng điền đầy đủ thông tin.");
            return;
        }


        DBDAO dbdao = new DBDAO();
        User user = new User(username, password, email, phone, address);
        dbdao.registerUser(user);

        // Sau khi đăng ký thành công, bạn có thể chuyển hướng về trang login hoặc trang chính
        response.sendRedirect("Login.jsp");

    }
}