package AdminManage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@WebServlet("/ShowLogServlet")
public class ShowLogServlet extends HttpServlet {
//    private static final Logger logger = LoggerFactory.getLogger(ShowLogServlet.class);
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        // Đọc file nhật ký
//        String logFilePath = "C:\\Users\\thang\\OneDrive\\Desktop\\server.log"; // Đường dẫn đến file nhật ký
//        List<String> logs = Files.readAllLines(Paths.get(logFilePath)); // Đọc tất cả dòng trong file
//
//        // Truyền dữ liệu nhật ký vào JSP
//        req.setAttribute("logs", logs);
//        req.getRequestDispatcher("viewLogs.jsp").forward(req, resp);
//    }
}
