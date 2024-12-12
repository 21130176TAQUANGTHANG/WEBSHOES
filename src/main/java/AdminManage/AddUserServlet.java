package AdminManage;

import LoginUser.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet("/AddUserServlet")
public class AddUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        int phone = Integer.parseInt(req.getParameter("phone"));
        String address = req.getParameter("address");
        int role = Integer.parseInt(req.getParameter("role"));

        DbAdmin dbAdmin = new DbAdmin();
        User user = new User(username, password, email, phone, address, role);
        dbAdmin.addUser(user);
        resp.sendRedirect("AccountADServlet");

    }
}
