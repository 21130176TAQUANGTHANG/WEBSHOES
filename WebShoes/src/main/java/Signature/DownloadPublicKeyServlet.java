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
        Object publicKeyObj = req.getSession().getAttribute("publicKey");
        if (!(publicKeyObj instanceof PublicKey)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Không tìm thấy hoặc Public Key không hợp lệ!");
            return;
        }

        PublicKey publicKey = (PublicKey) publicKeyObj;

        // Lấy dữ liệu Public Key ở dạng nhị phân (encoded)
        byte[] publicKeyData = publicKey.getEncoded();

        // Đặt tiêu đề tải về, đổi tên file thành publicKey.der
        resp.setContentType("application/octet-stream");  // Kiểu MIME cho tệp nhị phân
        resp.setHeader("Content-Disposition", "attachment;filename=publicKey.der");

        // Ghi dữ liệu ra file tải về
        try (OutputStream out = resp.getOutputStream()) {
            out.write(publicKeyData);  // Ghi dữ liệu nhị phân trực tiếp
            out.flush();
        } catch (IOException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi ghi dữ liệu Public Key!");
        }
    }
}


