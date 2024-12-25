package AdminManage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.Base64;

public class HashFileExample {

    public static void main(String[] args) {
        // Đảm bảo bạn thay đổi đường dẫn file tới file mà bạn muốn kiểm tra
        String filePath = "C:\\Users\\thang\\Downloads\\bill.txt";

        try {
            // Đọc toàn bộ nội dung file vào một chuỗi
            String fileContent = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);

            // Tính toán hash từ nội dung file
            String hash = calculateHash(fileContent);

            // In kết quả hash ra màn hình
            System.out.println("Hash of file content: " + hash);
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

    // Hàm tính hash (SHA-256)
    private static String calculateHash(String data) {
        try {
            // Tạo đối tượng MessageDigest với thuật toán SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Băm dữ liệu và lấy kết quả dạng byte[]
            byte[] hashBytes = digest.digest(data.getBytes(StandardCharsets.UTF_8));

            // Chuyển đổi mảng byte thành chuỗi Base64 để dễ đọc
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error calculating hash", e);
        }
    }
}
