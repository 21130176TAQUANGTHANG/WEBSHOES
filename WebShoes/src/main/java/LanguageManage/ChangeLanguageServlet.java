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

        // Nếu lang không hợp lệ, mặc định sẽ là tiếng Việt
        if (lang == null || (!lang.equals("vi") && !lang.equals("en"))) {
            lang = "vi";  // Ngôn ngữ mặc định là Tiếng Việt
        }

        // Lưu ngôn ngữ vào session
        Locale locale = new Locale(lang, lang.equals("vi") ? "VN" : "US");
        req.getSession().setAttribute("locale", locale);

        // Chuyển hướng lại về trang trước đó hoặc trang sản phẩm
        String redirectURL = req.getHeader("Referer");  // Lấy URL trang hiện tại
        if (redirectURL == null) {
            redirectURL = "product";  // Mặc định chuyển hướng đến trang sản phẩm
        }
        resp.sendRedirect(redirectURL);
    }
}
