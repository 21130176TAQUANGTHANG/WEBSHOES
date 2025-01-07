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
import java.security.Security;
import java.sql.Timestamp;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

@WebServlet("/KeyGenerationServlet")
public class KeyGenerationServlet extends HttpServlet {

    static {
        // Đảm bảo BouncyCastle được thêm khi servlet được tải
        Security.addProvider(new BouncyCastleProvider());
    }

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
            resp.sendRedirect("login.jsp"); // Điều hướng đến trang đăng nhập nếu chưa đăng nhập
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

            // Sử dụng lớp ChuKyDientu để tạo cặp key
            ElectronicSignature electronicSignature = new ElectronicSignature("RSA", "SHA1PRNG", "SUN");
            boolean keyGenerated = electronicSignature.genKey();

            if (!keyGenerated) {
                throw new Exception("Không thể tạo cặp khóa.");
            }

            // Lấy Public Key và Private Key ở dạng Base64
            String encodedPublicKey = electronicSignature.getPublicKeyAsString();
            String encodedPrivateKey = electronicSignature.getPrivateKeyAsString();

            // Gán Public Key vào request và session
            req.setAttribute("publicKey", encodedPublicKey);
            session.setAttribute("publicKey", electronicSignature.publicKey);

            // Gán Private Key vào request và session
            req.setAttribute("privateKey", encodedPrivateKey);
            session.setAttribute("privateKey", electronicSignature.privateKey);

            // Thời gian hiện tại làm createTime
            Timestamp createTime = new Timestamp(System.currentTimeMillis());
            Timestamp endTime = null; // Ban đầu là null

            // Lưu Public Key vào Database
            db.savePublicKeyToDatabase(userId, encodedPublicKey, createTime, endTime);

            // Chuyển hướng đến trang hiển thị kết quả
            req.getRequestDispatcher("keyResult.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi xử lý cơ sở dữ liệu hoặc tạo cặp khóa.");
        }
    }
}
