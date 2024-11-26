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

            Part fileName = req.getPart("productImage");
            String imageFileFilename = fileName.getSubmittedFileName();
            System.out.println(imageFileFilename);

            String uploadPath = "D:\\LTWEB\\WebShoes\\src\\main\\webapp\\image\\" + imageFileFilename;
            System.out.println( "duong dan" +uploadPath);

            try {
                FileOutputStream fileOutputStream = new FileOutputStream(uploadPath);
                InputStream inputStream = filePart.getInputStream();
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);
                fileOutputStream.write(buffer);
                fileOutputStream.flush();
                fileOutputStream.close();
            }catch (Exception e) {
                e.printStackTrace();
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
            resp.getWriter().println("Error: " + e.getMessage()) ;
        }
    }


}
