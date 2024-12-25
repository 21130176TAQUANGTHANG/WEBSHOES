package jar;

import java.security.*;
import java.util.Base64;

public class SignatureUtils {

    // Phương thức để tạo chữ ký
    public static String generateSignature(String data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(data.getBytes("UTF-8"));
        byte[] digitalSignature = signature.sign();
        return Base64.getEncoder().encodeToString(digitalSignature);
    }

    // Phương thức để xác minh chữ ký
    public static boolean verifySignature(String data, String signature, PublicKey publicKey) throws Exception {
        Signature sig = Signature.getInstance("SHA256withRSA");
        sig.initVerify(publicKey);
        sig.update(data.getBytes("UTF-8"));
        byte[] decodedSignature = Base64.getDecoder().decode(signature);
        return sig.verify(decodedSignature);
    }

    // Hàm tạo dữ liệu từ thông tin đơn hàng (có thể chỉnh sửa theo yêu cầu)
    public static String createOrderData(String orderId, String userId, double totalPrice) {
        return orderId + "|" + userId + "|" + totalPrice;
    }
}
