package Order;

import DBConnect.DBDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;
import javax.mail.Authenticator; // Import javax.mail.Authenticator instead
import javax.mail.PasswordAuthentication; // This is correct

@WebServlet("/ConfirmOrderServlet")
public class ConfirmOrderServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int orderId = Integer.parseInt(req.getParameter("orderId"));
        DBDAO dao = new DBDAO();
        boolean success = dao.updateOrderStatus(orderId, "Xác nhận thành công");

        if (success) {
            // Lấy thông tin đơn hàng và email khách hàng
            Order order = dao.getOrderById(orderId); // Hàm này trả về thông tin đơn hàng
            String customerEmail = dao.getCustomerEmailByUserId(order.getUserId()); // Truy vấn email theo userId

            // Gửi email
            try {
                sendEmail(customerEmail, order);
                resp.sendRedirect("admin_order?success=true");
            } catch (Exception e) {
                e.printStackTrace();
                resp.sendRedirect("admin_order?emailError=true");
            }
        } else {
            resp.sendRedirect("admin_order?error=true");
        }
    }

    private void sendEmail(String toEmail, Order order) throws Exception {
        // Cấu hình SMTP
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Tài khoản gửi email
        String fromEmail = "yuuwhisky@gmail.com";
        String password = "";

        // Thiết lập session
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        // Tạo nội dung email
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(fromEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
        message.setSubject("Thông báo đặt hàng thành công");
        message.setText(generateEmailContent(order)); // Nội dung email từ hàm `generateEmailContent`

        // Gửi email
        Transport.send(message);
    }

    private String generateEmailContent(Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append("Kính chào ").append(order.getName()).append(",\n\n");
        sb.append("Đơn hàng của bạn đã được xác nhận thành công.\n");
        sb.append("Thông tin hóa đơn:\n");
        sb.append("Mã đơn hàng: ").append(order.getOrderId()).append("\n");
        sb.append("Ngày đặt hàng: ").append(order.getOrderDate()).append("\n");
        sb.append("Ghi chú: ").append(order.getNotes()).append("\n");
        sb.append("Tổng cộng: ").append(order.getTotalPrice()).append(" VND\n");
        sb.append("\nDanh sách sản phẩm:\n");
        sb.append(String.format("%-20s %-10s %-10s %-10s\n", "Tên sản phẩm", "SL", "Giá/SP", "Thành tiền"));
        for (OrderItem item : order.getOrderItems()) {
            sb.append(String.format("%-20s %-10d %-10.2f %-10.2f\n",
                    item.getProductName(),
                    item.getQuantity(),
                    (double) item.getPrice(),
                    (double) item.getSubtotal()));
        }
        sb.append("\nCảm ơn bạn đã mua hàng tại cửa hàng của chúng tôi.\n");
        sb.append("\n Nếu gặp bất cứ vấn đề gì liên hệ admin@gmail.com, \n");
        sb.append("Trân trọng,\nĐội ngũ hỗ trợ.");
        return sb.toString();
    }
}

