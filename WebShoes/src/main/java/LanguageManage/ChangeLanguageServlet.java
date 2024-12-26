package LanguageManage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Locale;

@WebServlet("/changeLanguage")
public class ChangeLanguageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lang = req.getParameter("lang");

        Locale locale;
        if ("en".equals(lang)) {
            locale = new Locale("en", "US");
        } else {
            locale = new Locale("vi", "VN");
        }

        // Lưu locale vào session
        req.getSession().setAttribute("locale", locale);

        // Chuyển hướng về trang trước đó hoặc trang chính
        String referer = req.getHeader("Referer");
        if (referer != null) {
            resp.sendRedirect(referer);
        } else {
            resp.sendRedirect("product");
        }
    }
}


