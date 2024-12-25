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

@WebServlet("/uploadKey")
@MultipartConfig
public class UploadKeyServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Kiểm tra đăng nhập
        HttpSession session = req.getSession();

        String userId = null;

        // Kiểm tra nếu người dùng đăng nhập bằng tài khoản thông thường
        User user = (User) session.getAttribute("user");
        if (user != null) {
            userId = user.getId();
        }

        // Lấy file Public Key từ form
        var publicKeyFile = req.getPart("publicKeyFile");
        if (publicKeyFile == null) {
            req.setAttribute("errorMessage", "Vui lòng tải lên Public Key!");
            req.getRequestDispatcher("userProfile.jsp").forward(req, resp);
            return;
        }

        // Đọc nội dung Public Key từ file tải lên
        String uploadedPublicKey;
        try (InputStream publicKeyInputStream = publicKeyFile.getInputStream()) {
            uploadedPublicKey = new String(publicKeyInputStream.readAllBytes()).trim();
        }

        // Lấy Public Key từ database
        DbSecurity db = new DbSecurity();
        String storedPublicKey = db.getPublicKey(userId); // Hàm này lấy Public Key từ CSDL

        if (storedPublicKey == null) {
            req.setAttribute("errorMessage", "Không tìm thấy Public Key trong hệ thống!");
            req.getRequestDispatcher("keyUpload.jsp").forward(req, resp);
            return;
        }

        // So sánh Public Key
        if (uploadedPublicKey.equals(storedPublicKey)) {
            // Lưu Public Key vào session
            session.setAttribute("publicKey", storedPublicKey);
            req.setAttribute("successMessage", "Public Key hợp lệ! Đã lưu vào hệ thống.");
        } else {
            req.setAttribute("errorMessage", "Public Key không khớp. Vui lòng tải lên lại.");
        }

        // Quay lại trang profile
        req.getRequestDispatcher("keyUpload.jsp").forward(req, resp);
    }


}


