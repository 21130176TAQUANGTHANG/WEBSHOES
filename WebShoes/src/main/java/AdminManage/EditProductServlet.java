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
        try {
            // Lấy thông tin từ form
            int productId = Integer.parseInt(req.getParameter("productID"));
            String productName = req.getParameter("productName");
            int productPrice = Integer.parseInt(req.getParameter("productPrice"));
            String productDescription = req.getParameter("productDescription");
            int productQuantity = Integer.parseInt(req.getParameter("productQuantity"));
            int productSize = Integer.parseInt(req.getParameter("productSize"));
            int productColor = Integer.parseInt(req.getParameter("productColor"));
            int productLogo = Integer.parseInt(req.getParameter("productLogo"));

            // Lấy file hình ảnh
            Part filePart = req.getPart("productImage");
            String uploadPath = "D:\\LTWEB\\WebShoes\\image\\";
            String newImageFile = null;

            if (filePart != null && filePart.getSize() > 0) {
                newImageFile = filePart.getSubmittedFileName();
                String fullPath = uploadPath + newImageFile;

                try (FileOutputStream fileOutputStream = new FileOutputStream(fullPath);
                     InputStream inputStream = filePart.getInputStream()) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        fileOutputStream.write(buffer, 0, bytesRead);
                    }
                }
            } else {
                // Nếu không chọn file mới, giữ lại file cũ
                newImageFile = new DBDAO().getProductImageById(productId);
            }

            // Cập nhật thông tin sản phẩm
            Product product = new Product(productId, productName, newImageFile, productPrice, productDescription, productQuantity, productSize, productColor, productLogo);
            new DBDAO().updateProduct(product);

            // Chuyển hướng lại trang quản lý
            resp.sendRedirect("AdminProduct");
        } catch (NumberFormatException | NullPointerException e) {
            req.setAttribute("errorMessage", "Dữ liệu không hợp lệ. Vui lòng kiểm tra lại!");
            req.getRequestDispatcher("/adminProduct.jsp").forward(req, resp);
        }
    }
}
