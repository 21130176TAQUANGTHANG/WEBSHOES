package jar;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String filePath = getServletContext().getRealPath("/resources/files/Chữ ký điện tử.jar");
        File file = new File(filePath);

        if (file.exists()) {
            response.setContentType("application/java-archive");
            response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
            response.setContentLength((int) file.length());

            try (FileInputStream fis = new FileInputStream(file);
                 OutputStream os = response.getOutputStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            }
        } else {
            response.getWriter().write("File not found!");
        }
    }
}
