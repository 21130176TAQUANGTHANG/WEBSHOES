package Signature;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class PublicKeyVerifier {
    public static PublicKey getPublicKeyFromDatabase(String base64PublicKey) throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(base64PublicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(keySpec);
    }

    public static void main(String[] args) {
        try {
            // Public key lưu trong cơ sở dữ liệu
            String storedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlpiIJXZXPJeUYULVltgSyb1Sc3lEYr0Cu+1HKaLUpUOekEflPT6ZcpKyHpOoGXL4B6/DjDKnTbqOWJHYK/YFNiNJ6GIs1BzTpMNjaTroa7DbGpIaLJNDUoDH8aonsDZz9zeYeKnSx9K/4/wACn2LuuOqUBv6NpQXzYsY9VIra3nSXCem52n7Z3bLG0oeU45uZywPLfACK1v+T9flzOXefvlaXr8dfjtKbuVehRwmLSf+zhlwYQeYS2QGXzjNox3RmJuZTsDzr4" +
                    "NAb9SRdOXXUClkDSiUmLtT3S1amfxb8H3l5D/efEta0KMyRul5ga7+Lv1RKkpV+OWSXP371UDmaQIDAQAB"; // Rút gọn
            PublicKey publicKey = getPublicKeyFromDatabase(storedPublicKey);
            System.out.println("Public Key: " + publicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
