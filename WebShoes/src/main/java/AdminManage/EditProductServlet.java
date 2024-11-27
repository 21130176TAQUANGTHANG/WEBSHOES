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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
@WebServlet("/EditProductServlet")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class EditProductServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Lấy thông tin từ form
        int productId = Integer.parseInt(req.getParameter("productID"));
        String productName = req.getParameter("productName");
        int productPrice = Integer.parseInt(req.getParameter("productPrice"));
        String productDescription = req.getParameter("productDescription");
        int productQuantity = Integer.parseInt(req.getParameter("productQuantity"));
        int productSize = Integer.parseInt(req.getParameter("productSize"));
        int productColor = Integer.parseInt(req.getParameter("productColor"));
        int productLogo = Integer.parseInt(req.getParameter("productLogo"));

        // Lấy tên file hiện tại từ CSDL
        DBDAO dbdao = new DBDAO();
        String currentImageFile = dbdao.getProductImageById(productId);

        // Kiểm tra và xử lý file upload mới
        Part filePart = req.getPart("productImage");
        String newImageFile = currentImageFile; // Mặc định giữ file hiện tại
        System.out.println("file hien tai:   "+newImageFile);
        if (filePart != null && filePart.getSize() > 0) {
            newImageFile = filePart.getSubmittedFileName();
            String uploadPath = "D:\\LTWEB\\WebShoes\\src\\main\\webapp\\image\\" + newImageFile;

            // Lưu file mới
            try (FileOutputStream fileOutputStream = new FileOutputStream(uploadPath);
                 InputStream inputStream = filePart.getInputStream()) {
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);
                fileOutputStream.write(buffer);
                fileOutputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Cập nhật sản phẩm với thông tin mới
        Product product = new Product(productId, productName, newImageFile, productPrice, productDescription, productQuantity, productSize, productColor, productLogo);
        dbdao.updateProduct(product);

        // Chuyển hướng lại trang quản lý
        resp.sendRedirect("AdminProduct");
    }
}
