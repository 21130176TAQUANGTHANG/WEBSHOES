package Signature;

import LoginUser.AccountFF;
import LoginUser.GoogleAccount;
import LoginUser.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;

@WebServlet("/UploadKeyServlet")
@MultipartConfig
public class UploadKeyServlet extends HttpServlet {
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            InputStream privateKeyStream = req.getPart("privateKey").getInputStream();
            InputStream publicKeyStream = req.getPart("publicKey").getInputStream();

            // Đọc dữ liệu từ file
            byte[] privateKeyBytes = privateKeyStream.readAllBytes();
            byte[] publicKeyBytes = publicKeyStream.readAllBytes();

            String privateKey = new String(privateKeyBytes);
            String publicKey = new String(publicKeyBytes);

            // Lưu publicKey vào database
            if (publicKey == null || publicKey.isEmpty()) {
                resp.sendRedirect("checkout.jsp?error=invalidKey");
                return;
            }

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
                resp.sendRedirect("keyUpload.jsp?error=noUser");
                return;
            }
            try {
                DbSecurity db = new DbSecurity();
                // Thời gian hiện tại làm createTime
                Timestamp createTime = new Timestamp(System.currentTimeMillis());
                Timestamp endTime = null; // ban đầu là null
//                db.savePublicKeyToDatabase(userId, publicKey );
                resp.getWriter().write("Cặp key đã được tải lên thành công!");
            } catch (Exception e) {
                e.printStackTrace();
                resp.sendRedirect("keyUpload.jsp?error=uploadFailed");
            }
        }
    }


