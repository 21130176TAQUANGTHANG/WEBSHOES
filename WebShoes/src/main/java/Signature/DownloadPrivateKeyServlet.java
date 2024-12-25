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
        Object privateKeyObj = req.getSession().getAttribute("privateKey");
        if (!(privateKeyObj instanceof PrivateKey)) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Không tìm thấy hoặc Private Key không hợp lệ!");
            return;
        }

        PrivateKey privateKey = (PrivateKey) privateKeyObj;

        // Lấy dữ liệu private key ở dạng nhị phân (encoded)
        byte[] privateKeyData = privateKey.getEncoded();

        // Đặt tiêu đề tải về, đổi tên file thành privateKey.der
        resp.setContentType("application/octet-stream");  // Kiểu MIME cho tệp nhị phân
        resp.setHeader("Content-Disposition", "attachment;filename=privateKey.der");

        // Ghi dữ liệu ra file tải về
        try (OutputStream out = resp.getOutputStream()) {
            out.write(privateKeyData);  // Ghi dữ liệu nhị phân trực tiếp
            out.flush();
        } catch (IOException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi khi ghi dữ liệu Private Key!");
        }
    }
}

