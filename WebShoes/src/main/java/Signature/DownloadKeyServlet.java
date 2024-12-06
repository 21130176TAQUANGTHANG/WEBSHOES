package Signature;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
@WebServlet("/DownloadKeyServlet")
public class DownloadKeyServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy loại key và nội dung key từ request
        String keyType = req.getParameter("keyType");
        String keyContent = req.getParameter("keyContent");

        // Đặt tên file
        String fileName = (keyType.equals("public") ? "publicKey.txt" : "privateKey.txt");

        // Cấu hình response cho việc tải file
        resp.setContentType("text/plain");
        resp.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        // Ghi nội dung key vào response
        PrintWriter writer = resp.getWriter();
        writer.write(keyContent);
        writer.flush();
        writer.close();
    }
}
