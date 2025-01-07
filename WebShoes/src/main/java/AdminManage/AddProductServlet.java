package AdminManage;

import DBConnect.DBDAO;
import Product.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
@WebServlet("/AddProductServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class AddProductServlet extends HttpServlet {
    private static final String IMAGE_DIRECTORY = "D:\\ManageImage"; // Đường dẫn thư mục lưu ảnh

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Kiểm tra và lấy các tham số
            String productIDStr = req.getParameter("productID");
            int productID = (productIDStr != null && !productIDStr.trim().isEmpty()) ? Integer.parseInt(productIDStr) : 0;

            String productName = req.getParameter("productName");
            if (productName == null || productName.trim().isEmpty()) {
                productName = "Default Name";
            }

            String productPriceStr = req.getParameter("productPrice");
            int productPrice = (productPriceStr != null && !productPriceStr.trim().isEmpty()) ? Integer.parseInt(productPriceStr) : 0;

            String productDescription = req.getParameter("productDescription");
            if (productDescription == null || productDescription.trim().isEmpty()) {
                productDescription = "No description available";
            }

            String productQuantityStr = req.getParameter("productQuantity");
            int productQuantity = (productQuantityStr != null && !productQuantityStr.trim().isEmpty()) ? Integer.parseInt(productQuantityStr) : 0;

            String productSizeStr = req.getParameter("productSize");
            int productSize = (productSizeStr != null && !productSizeStr.trim().isEmpty()) ? Integer.parseInt(productSizeStr) : 0;

            String productColorStr = req.getParameter("productColor");
            int productColor = (productColorStr != null && !productColorStr.trim().isEmpty()) ? Integer.parseInt(productColorStr) : 0;

            String productLogoStr = req.getParameter("productLogo");
            int productLogo = (productLogoStr != null && !productLogoStr.trim().isEmpty()) ? Integer.parseInt(productLogoStr) : 0;

            // Kiểm tra và xử lý file upload
            Part filePart = req.getPart("productImage"); // Lấy phần file
            if (filePart == null) {
                resp.getWriter().println("No file uploaded or wrong input name!");
                return;
            }

            // Lấy tên file ảnh
            String imageFileFilename = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

            // Kiểm tra và tạo thư mục nếu chưa tồn tại
            String directoryPath = "D:\\ManageImage";
            File directory = new File(directoryPath);
            if (!directory.exists()) {
                if (directory.mkdirs()) {
                    System.out.println("Thư mục đã được tạo: " + directoryPath);
                } else {
                    System.out.println("Không thể tạo thư mục: " + directoryPath);
                }
            }

            // Đường dẫn đầy đủ để lưu ảnh
            String uploadPath = directoryPath + File.separator + imageFileFilename;

            try (FileOutputStream fileOutputStream = new FileOutputStream(uploadPath);
                 InputStream inputStream = filePart.getInputStream()) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                }
            }

            // Tạo đối tượng Product và thêm vào DB
            DBDAO dao = new DBDAO();
            Product product = new Product(productID, productName, imageFileFilename, productPrice, productDescription, productQuantity, productSize, productColor, productLogo);
            Product addedProduct = dao.addProduct(product);

            if (addedProduct != null) {
                resp.sendRedirect("AdminProduct?success=true");
            } else {
                resp.getWriter().println("Failed to add product.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("Error: " + e.getMessage());
        }
    }
}
