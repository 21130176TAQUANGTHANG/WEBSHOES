package LoginUser;

import DBConnect.DBDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            DBDAO dbdao = new DBDAO();
            User user = dbdao.checkLogin(email, password);
            HttpSession session = request.getSession();

            String error = "Sai tài khoản hoặc mật khẩu";
            if (user != null) {
                // Đăng nhập thành công
                session.setAttribute("user", user);
                response.sendRedirect("product");
            } else {
                // Đăng nhập thất bại
                request.setAttribute("error", error);
                request.getRequestDispatcher("Login.jsp").forward(request, response); // Sử dụng forward thay vì redirect
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
