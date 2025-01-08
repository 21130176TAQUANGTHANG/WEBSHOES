package LanguageManage;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.Locale;

public class LocaleUtils {
    public static Locale getLocale(HttpServletRequest req) {
        HttpSession session = req.getSession();
        String lang = req.getParameter("lang");
        Locale locale;

        if (lang != null) {
            switch (lang) {
                case "vi":
                    locale = new Locale("vi", "VN");
                    break;
                case "en":
                    locale = new Locale("en", "US");
                    break;
                default:
                    locale = new Locale("vi", "VN");
            }
            session.setAttribute("locale", locale);
        } else {
            locale = (Locale) session.getAttribute("locale");
            if (locale == null) {
                locale = new Locale("vi", "VN");
                session.setAttribute("locale", locale);
            }
        }
        return locale;
    }
}
