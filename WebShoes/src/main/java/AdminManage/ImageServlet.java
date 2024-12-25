package AdminManage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;

@WebServlet("/image/*")
public class ImageServlet extends HttpServlet {
    private static final String IMAGE_DIRECTORY = "D:\\LTWEB\\WebShoes\\image";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String imagePath = IMAGE_DIRECTORY + req.getPathInfo(); // Lấy đường dẫn ảnh
        File imageFile = new File(imagePath);

        if (!imageFile.exists() || imageFile.isDirectory()) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND); // Nếu không tìm thấy ảnh
            return;
        }

        // Đặt loại nội dung trả về
        String mimeType = getServletContext().getMimeType(imageFile.getName());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        resp.setContentType(mimeType);

        // Trả về dữ liệu hình ảnh
        try (InputStream in = new FileInputStream(imageFile);
             OutputStream out = resp.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}
