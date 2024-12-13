package Signature;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.security.PublicKey;
import java.util.Base64;

@WebServlet("/downloadPublicKey")
public class DownloadPublicKeyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy PublicKey từ session
        PublicKey publicKey = (PublicKey) req.getSession().getAttribute("publicKey");
        if (publicKey == null) {
            resp.getWriter().write("Không tìm thấy Public Key!");
            return;
        }

        // Chuyển đổi PublicKey sang Base64
        String encodedPublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());

        // Đặt tiêu đề tải về, đổi tên file thành publicKey.txt
        resp.setContentType("text/plain");
        resp.setHeader("Content-Disposition", "attachment;filename=publicKey.txt");

        // Ghi dữ liệu ra file tải về
        try (OutputStream out = resp.getOutputStream()) {
            out.write(encodedPublicKey.getBytes());
            out.flush();
        }
    }
}
