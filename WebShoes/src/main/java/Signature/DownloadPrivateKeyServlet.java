package Signature;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;
import java.security.PrivateKey;
import java.util.Base64;

@WebServlet("/downloadPrivateKey")
public class DownloadPrivateKeyServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy PrivateKey từ session
        PrivateKey privateKey = (PrivateKey) req.getSession().getAttribute("privateKey");
        if (privateKey == null) {
            resp.getWriter().write("Không tìm thấy Private Key!");
            return;
        }

        // Chuyển đổi PrivateKey sang Base64
        String encodedPrivateKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());

        // Đặt tiêu đề tải về, đổi tên file thành privateKey.txt
        resp.setContentType("text/plain");
        resp.setHeader("Content-Disposition", "attachment;filename=privateKey.txt");

        // Ghi dữ liệu ra file tải về
        try (OutputStream out = resp.getOutputStream()) {
            out.write(encodedPrivateKey.getBytes());
            out.flush();
        }
    }
}
