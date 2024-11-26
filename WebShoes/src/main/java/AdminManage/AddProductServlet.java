package AdminManage;

import DBConnect.DBDAO;
import Product.Product;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
@WebServlet("/AddProductServlet")
public class AddProductServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Kiểm tra các tham số cơ bản
            int productID = Integer.parseInt(req.getParameter("productID"));
            String productName = req.getParameter("productName");
            int productPrice = Integer.parseInt(req.getParameter("productPrice"));
            String productDescription = req.getParameter("productDescription");
            int productQuantity = Integer.parseInt(req.getParameter("productQuantity"));
            int productSize = Integer.parseInt(req.getParameter("productSize"));
            int productColor = Integer.parseInt(req.getParameter("productColor"));
            int productLogo = Integer.parseInt(req.getParameter("productLogo"));

            // Kiểm tra và xử lý file upload
            Part filePart = req.getPart("productImage");
            String fileName = null;

            if (filePart != null && filePart.getSize() > 0) {
                fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                String uploadPath = getServletContext().getRealPath("") + "uploads";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();
                filePart.write(uploadPath + File.separator + fileName);
            } else {
                resp.getWriter().println("No file uploaded or file is empty.");
                return;
            }

            // Thêm sản phẩm vào DB
            DBDAO dao = new DBDAO();
            Product product = new Product(productID, productName, fileName, productPrice, productDescription, productQuantity, productSize, productColor, productLogo);
            Product addedProduct = dao.addProduct(product);

            if (addedProduct != null) {
                resp.getWriter().println("Product added successfully!");
            } else {
                resp.getWriter().println("Failed to add product.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.getWriter().println("Error: " + e.getMessage());
        }
    }

}
