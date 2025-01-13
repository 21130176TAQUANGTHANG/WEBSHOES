package ForgotPassword;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;


@WebServlet("/ForgotPasswordServlet")
public class ForgotPasswordServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");

        // Kiểm tra email có tồn tại trong database
        DBforgot dao = new DBforgot();
        boolean emailExists = dao.checkEmailExists(email);

        if (!emailExists) {
            req.setAttribute("error", "Email không tồn tại trong hệ thống!");
            req.getRequestDispatcher("forgotPassword.jsp").forward(req, resp);
        } else {
            // Tạo mã OTP ngẫu nhiên
            String otp = generateOtp();

            // Lưu OTP vào session (hoặc database)
            HttpSession session = req.getSession();
            session.setAttribute("otp", otp);
            session.setAttribute("email", email);

            // Gửi OTP qua email
            try {
                sendMail(email, otp);
                resp.sendRedirect("verifyOtp.jsp");
            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("error", "Gửi email thất bại. Vui lòng thử lại.");
                req.getRequestDispatcher("forgotPassword.jsp").forward(req, resp);
            }
        }
    }

    private String generateOtp() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000)); // OTP gồm 4 chữ số
    }

    private void sendMail(String toEmail, String otp) throws Exception {
        // Cấu hình SMTP
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Tài khoản gửi email
        String fromEmail = "yuuwhisky@gmail.com";
        String password = "yivn cwzx jbkt ldqn";

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
        message.setSubject("Mã OTP đặt lại mật khẩu của bạn");
        message.setText(generateOtpEmailContent(otp));

        // Gửi email
        Transport.send(message);
    }

    private String generateOtpEmailContent(String otp) {
        StringBuilder sb = new StringBuilder();
        sb.append("Chào bạn,\n\n");
        sb.append("Chúng tôi đã nhận được yêu cầu đặt lại mật khẩu cho tài khoản của bạn.\n");
        sb.append("Mã OTP của bạn là: ").append(otp).append("\n");
        sb.append("Mã này có hiệu lực trong vòng 5 phút.\n");
        sb.append("\nNếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này.\n");
        sb.append("Trân trọng,\n");
        sb.append("Đội ngũ hỗ trợ.");
        return sb.toString();
    }

}
