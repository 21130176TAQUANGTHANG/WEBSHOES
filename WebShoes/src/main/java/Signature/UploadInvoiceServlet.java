package Signature;

import LoginUser.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Base64;

@WebServlet("/UploadInvoiceServlet")
@MultipartConfig(maxFileSize = 10 * 1024 * 1024) // Tối đa 10MB
public class UploadInvoiceServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy thông tin từ form
        String orderIdParam = req.getParameter("orderId");
        int orderId = Integer.parseInt(orderIdParam);
        HttpSession session = req.getSession();
        String userId = null;

        // Kiểm tra nếu người dùng đăng nhập
        User user = (User) session.getAttribute("user");
        if (user != null) {
            userId = user.getId();
        }

        // Kiểm tra orderId đã tồn tại trong database hay chưa
        DbSecurity dbSecurity = new DbSecurity();
        if (dbSecurity.isOrderHashExist(orderId)) {
            // Set thông báo kèm orderId
            req.setAttribute("uploadMessage", "Order ID " + orderId + " đã tồn tại. Không thể upload nữa.");
            req.setAttribute("errorOrderId", orderId);

            // Forward về trang JSP
            req.getRequestDispatcher("CustomerOrder.jsp").forward(req, resp);
            return;
        }


        // Lấy các tệp từ form
        Part hashFilePart = req.getPart("signatureFilehash");
        Part signatureFilePart = req.getPart("signatureFile");

        // Đọc và xử lý nội dung của file hash
        InputStream hashFileContent = hashFilePart.getInputStream();
        String hashFileContentStr = new String(hashFileContent.readAllBytes(), StandardCharsets.UTF_8);

        // Tính toán hash (SHA-256)
        String hash = calculateHash(hashFileContentStr);

        // Đọc và xử lý nội dung của file chữ ký
        InputStream signatureFileContent = signatureFilePart.getInputStream();
        String signatureFileContentStr = new String(signatureFileContent.readAllBytes(), StandardCharsets.UTF_8);

        // Tách chữ ký từ nội dung file chữ ký
        String signature = extractSignature(signatureFileContentStr);

        // Lưu hash và signature vào database
        try {
            dbSecurity.uploadInvoice(userId, orderId, hash, signature);
            req.setAttribute("uploadMessage", "Tệp đã được tải lên thành công cho Order ID: " + orderId);
            req.setAttribute("successOrderId", orderId);
            resp .sendRedirect("CustomerOrderServlet");
        } catch (Exception e) {
            req.setAttribute("uploadMessage", "Lỗi khi tải tệp lên: " + e.getMessage());
            req.getRequestDispatcher("CustomerOrder.jsp").forward(req, resp);
        }
    }

    // Hàm tính hash (SHA-256)
    private String calculateHash(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error calculating hash", e);
        }
    }

    // Hàm tách chữ ký từ nội dung file
    private String extractSignature(String fileContent) {
        final String SIGNATURE_PREFIX = "Chữ ký số:";
        int signatureIndex = fileContent.indexOf(SIGNATURE_PREFIX);
        if (signatureIndex != -1) {
            return fileContent.substring(signatureIndex + SIGNATURE_PREFIX.length()).trim();
        }
        return null; // Trả về null nếu không tìm thấy chữ ký
    }
}

