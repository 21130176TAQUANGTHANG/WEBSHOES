package AdminManage;

import DBConnect.DBDAO;
import LoginUser.AccountFF;
import LoginUser.GoogleAccount;
import LoginUser.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/AccountADServlet")
public class AccountADServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBDAO dbdao = new DBDAO();
        List<User> users  = dbdao.getAllUsers();
        List<AccountFF> ff = dbdao.getAllFacebook();
        List<GoogleAccount> gg = dbdao.getAllGoogle();
        req.setAttribute("adminGoogle", gg);
        req.setAttribute("adminFacebook", ff);
        req.setAttribute("adminUsers", users);
        req.getRequestDispatcher("adminaccount.jsp").forward(req, resp);
    }
}
