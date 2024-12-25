package Signature;

import AdminManage.DbAdmin;
import DBConnect.DBDAO;
import Order.Order;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.*;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;

@WebServlet("/VerifySignatureServlet")
public class VerifySignatureServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int orderId;
        DbSecurity dbSecurity = new DbSecurity();
        try {
            orderId = Integer.parseInt(req.getParameter("orderId"));
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Invalid Order ID.");
            req.getRequestDispatcher("verifySignature.jsp").forward(req, resp);
            return;
        }

        try {
            DbSecurity db = new DbSecurity();
            // Lấy thông tin hóa đơn từ cơ sở dữ liệu
            Invoice invoice = db.getInvoiceByOrderId(orderId);
            if (invoice == null) {
                req.setAttribute("error", "Invoice not found.");
                req.getRequestDispatcher("verifySignature.jsp").forward(req, resp);
                return;
            }

            // Lấy public key của user từ cơ sở dữ liệu
            String publicKeyString = db.getPublicKeyByUserId(invoice.getUserId());
            if (publicKeyString == null || publicKeyString.isEmpty()) {
                req.setAttribute("error", "Public key not found for the user.");
                req.getRequestDispatcher("verifySignature.jsp").forward(req, resp);
                return;
            }

            // Giải mã chữ ký và so sánh hash
            PublicKey publicKey = getPublicKeyFromBase64(publicKeyString);
            boolean isValid = verifySignature(publicKey, invoice.getHashFromContent(), invoice.getSignature());

            // In ra kết quả so sánh
            if (isValid) {
                System.out.println("The invoice signature is VALID.");
            } else {
                System.out.println("The invoice signature is INVALID.");
            }


            // Nạp lại danh sách đơn hàng
            DBDAO dbdao = new DBDAO();
            String pageParam = req.getParameter("page");
            int page = (pageParam != null) ? Integer.parseInt(pageParam) : 1;
            int size = 7;

            List<Order> orderList = dbdao.getOrdersByPage(page, size);
            int totalOrders = dbdao.getTotalOrders();
            int totalPages = (int) Math.ceil((double) totalOrders / size);

            req.setAttribute("orderList_verify", orderList);
            req.setAttribute("currentPage_verify", page);
            req.setAttribute("totalPages_verify", totalPages);

            // Gửi thông tin sang JSP
            req.setAttribute("order", invoice); // Thông tin đơn hàng
            req.setAttribute("verifyResult", new VerifyResult(isValid)); // Kết quả kiểm tra
            req.getRequestDispatcher("verifySignature.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Error verifying signature: " + e.getMessage());
            req.getRequestDispatcher("verifySignature.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBDAO dbdao = new DBDAO();
        String pageParam = req.getParameter("page");
        int page = (pageParam != null) ? Integer.parseInt(pageParam) : 1; // Mặc định trang 1
        int size = 7; // Số lượng bản ghi trên mỗi trang

        List<Order> orderList = dbdao.getOrdersByPage(page, size);
        int totalOrders = dbdao.getTotalOrders();
        int totalPages = (int) Math.ceil((double) totalOrders / size);

        req.setAttribute("orderList_verify", orderList);
        req.setAttribute("currentPage_verify", page);
        req.setAttribute("totalPages_verify", totalPages);
        req.getRequestDispatcher("verifySignature.jsp").forward(req, resp);

    }

    // Hàm lấy PublicKey từ Base64
    private PublicKey getPublicKeyFromBase64(String base64PublicKey) throws Exception {
        byte[] keyBytes = Base64.getDecoder().decode(base64PublicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    // Hàm kiểm tra chữ ký
    private boolean verifySignature(PublicKey publicKey, String hashFromContent, String signature) {
        try {

            // Decode chữ ký từ Base64
            byte[] decodedSignature = Base64.getDecoder().decode(signature);

            // Tạo đối tượng Signature với thuật toán SHA256withRSA
            Signature sig = Signature.getInstance("SHA256withRSA");
            sig.initVerify(publicKey);

            // Cập nhật dữ liệu là nội dung gốc (hashFromContent)
            sig.update(hashFromContent.getBytes("UTF-8"));

            // Kiểm tra chữ ký và in kết quả
            boolean isValid = sig.verify(decodedSignature);
            System.out.println("Signature verification result: " + isValid);

            return isValid;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}

