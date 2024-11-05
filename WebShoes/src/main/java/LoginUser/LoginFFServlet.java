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
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        System.out.println(code);
        FaceLogin faceLogin = new FaceLogin();
        String accessToken = faceLogin.getToken(code);
        HttpSession session = request.getSession();


        AccountFF ff = faceLogin.getUserInfo(accessToken);
        System.out.println(ff);

        // Lưu thông tin vào cơ sở dữ liệu
        DBDAO dbdao = new DBDAO();
        AccountFF accountFF = dbdao.checkFacebookAccount(ff.getName());

        if(accountFF != null) {
            session.setAttribute("facebookUser", ff);
        }else{
            dbdao.saveFaceAccount(ff);
            session.setAttribute("facebookUser", ff);

        }
        response.sendRedirect("product");


    }
}