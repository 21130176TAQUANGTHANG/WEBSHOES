package Signature;

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
import java.security.*;
import java.util.Base64;

@WebServlet("/KeyGenerationServlet")
public class KeyGenerationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String userId = null;

        // Kiểm tra nếu người dùng đăng nhập bằng tài khoản thông thường
        User user = (User) session.getAttribute("user");
        if (user != null) {
            userId = user.getId();
        }

        // Kiểm tra nếu người dùng đăng nhập bằng Google
        GoogleAccount googleUser = (GoogleAccount) session.getAttribute("googleUser");
        if (googleUser != null) {
            userId = googleUser.getId();
        }

        // Kiểm tra nếu người dùng đăng nhập bằng Facebook
        AccountFF facebookUser = (AccountFF) session.getAttribute("facebookUser");
        if (facebookUser != null) {
            userId = facebookUser.getId();
        }

        if (userId == null) {
            resp.sendRedirect("checkout.jsp?error=noUser");
            return;
        }

        try {
            DbSecurity db = new DbSecurity();

            // Kiểm tra nếu người dùng đã có Public Key
            if (db.isPublicKeyExist(userId)) {
                String existingPublicKey = db.getPublicKeyFromDatabase(userId);
                req.setAttribute("publicKey", existingPublicKey);
                req.setAttribute("errorMessage", "Người dùng đã có public key. Đây là public key của bạn.");
                req.getRequestDispatcher("keyResult.jsp").forward(req, resp);
                return;
            }

            // Tạo cặp key mới
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair pair = keyGen.generateKeyPair();

            PrivateKey privateKey = pair.getPrivate();
            PublicKey publicKey = pair.getPublic();

            // Chuyển đổi Public Key sang Base64
            String encodedPublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            req.setAttribute("publicKey", encodedPublicKey);

            // Mã hóa Private Key và lưu vào session
            String encodedPrivateKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());
            req.setAttribute("privateKey", encodedPrivateKey);
            req.getSession().setAttribute("privateKey", privateKey);

            // Lưu Public Key vào Database
            db.savePublicKeyToDatabase(userId, encodedPublicKey);

            System.out.println("Public Key: " + encodedPublicKey);
            System.out.println("Private Key: " + encodedPrivateKey);
            System.out.println("Private Key lưu vào session: " + privateKey);


            // Chuyển hướng đến trang hiển thị kết quả
            req.getRequestDispatcher("keyResult.jsp").forward(req, resp);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi tạo cặp key.");
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi xử lý cơ sở dữ liệu.");
        }
    }

}
