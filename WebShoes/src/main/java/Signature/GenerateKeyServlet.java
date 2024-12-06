package Signature;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
@WebServlet("/GenerateKeyServlet")
public class GenerateKeyServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Tạo đối tượng ChuKyDientu
            ChuKyDientu chuKyDientu = new ChuKyDientu("RSA", "SHA1PRNG", "BC");
            chuKyDientu.genKey();

            // Lấy public key và private key dưới dạng Base64
            String publicKey = chuKyDientu.getPublicKeyAsString();
            String privateKey = chuKyDientu.getPrivateKeyAsString();

            // Gửi public key và private key về giao diện
            req.setAttribute("publicKey", publicKey);
            req.setAttribute("privateKey", privateKey);


            // Điều hướng về trang checkout.jsp (hoặc trang khác nếu cần)
            req.getRequestDispatcher("checkout.jsp").forward(req, resp);
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating keys: " + e.getMessage());
        }
    }

}
