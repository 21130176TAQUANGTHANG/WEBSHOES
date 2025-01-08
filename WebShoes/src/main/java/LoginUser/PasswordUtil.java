package LoginUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtil {

    // Băm mật khẩu với SHA-256
    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        // Tạo đối tượng MessageDigest cho SHA-256
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        // Băm mật khẩu và chuyển đổi thành chuỗi hex
        byte[] hash = digest.digest(password.getBytes());

        // Chuyển đổi mảng byte thành chuỗi hex
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }

        return hexString.toString();  // Trả về mật khẩu băm dưới dạng chuỗi hex
    }

    // Kiểm tra mật khẩu với hash đã lưu trong database
    public static boolean checkPassword(String plainPassword, String storedPassword) throws NoSuchAlgorithmException {
        // Băm mật khẩu người dùng nhập vào
        String hashedPassword = hashPassword(plainPassword);

        // Kiểm tra xem mật khẩu băm có khớp với mật khẩu lưu trong database không
        return hashedPassword.equals(storedPassword);
    }
}


