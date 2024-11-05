package LoginUser;

import DBConnect.DBDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LoginGGServlet", value = "/LoginGGServlet")
public class LoginGGServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        GoogleLogin gg = new GoogleLogin();
        String accessToken = gg.getToken(code);
        GoogleAccount user = gg.getUserInfo(accessToken);

        DBDAO dbdao = new DBDAO();
        GoogleAccount googleAccount = dbdao.checkGoogleAccount(user.getEmail());

        HttpSession session = request.getSession();

        if (googleAccount != null) {
            session.setAttribute("googleUser", googleAccount);
        } else {
            dbdao.saveGoogleAccount(user);
            session.setAttribute("googleUser", user);
        }

        response.sendRedirect("product");
    }
}
