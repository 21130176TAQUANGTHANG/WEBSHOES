package LoginUser;


import DBConnect.DBDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LoginFFServlet", value = "/LoginFFServlet")
public class LoginFFServlet extends HttpServlet { // login with facebook
    // LoginFFServlet.java - Facebook Login Example
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code == null) {
            response.sendRedirect("Login.jsp?error=facebookAuthFailed");
            return;
        }

        FaceLogin faceLogin = new FaceLogin();
        String accessToken = faceLogin.getToken(code);

        if (accessToken == null) {
            response.sendRedirect("Login.jsp?error=facebookAuthFailed");
            return;
        }

        HttpSession session = request.getSession();
        AccountFF ff = faceLogin.getUserInfo(accessToken);

        DBDAO dbdao = new DBDAO();
        AccountFF accountFF = dbdao.checkFacebookAccount(ff.getName());

        if (accountFF != null) {
            session.setAttribute("facebookUser", accountFF);
        } else {
            dbdao.saveFaceAccount(ff);
            session.setAttribute("facebookUser", ff);
        }
        response.sendRedirect("product");
    }

}