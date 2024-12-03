package Signature;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
public class ChuKyDientu {
    KeyPair keyPair;
    Signature signature;
    SecureRandom secureRandom;
    PublicKey publicKey;
    PrivateKey privateKey;

    public ChuKyDientu() {
    }

    public ChuKyDientu(String alg, String algRandom, String prov) throws NoSuchAlgorithmException, NoSuchProviderException {
        // Thêm BouncyCastle làm provider
        Security.addProvider(new BouncyCastleProvider());

        KeyPairGenerator generator = KeyPairGenerator.getInstance(alg, "BC"); // Sử dụng BouncyCastle (BC) provider
        secureRandom = SecureRandom.getInstance(algRandom, "SUN");  // Dùng SUN provider cho SecureRandom (SHA1PRNG)
        generator.initialize(2048, secureRandom);  // Kích thước khóa RSA: 2048 bits (thường dùng với RSA)
        keyPair = generator.generateKeyPair();
        signature = Signature.getInstance("SHA256withRSA", "BC");  // RSA với SHA-256 để tạo chữ ký
    }

    public boolean genKey() {
        if (keyPair == null) return false;
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
        return true;
    }

    public void loadPublic(String base64Key) throws GeneralSecurityException {
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC"); // Sử dụng BouncyCastle
        publicKey = keyFactory.generatePublic(new X509EncodedKeySpec(keyBytes));
    }

    public String sign(String mes) throws InvalidKeyException, SignatureException {
        byte[] data = mes.getBytes();
        signature.initSign(privateKey);
        signature.update(data);
        byte[] sign = signature.sign();
        return Base64.getEncoder().encodeToString(sign);
    }

    public String signFile(String src) throws InvalidKeyException, IOException, SignatureException {
        byte[] data = src.getBytes();
        signature.initSign(privateKey);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
        byte[] buff = new byte[1024];
        int read;
        while ((read = bis.read(buff)) != -1) {
            signature.update(buff, 0, read);
        }
        bis.close();
        byte[] sign = signature.sign();
        return Base64.getEncoder().encodeToString(sign);
    }

    public boolean verify(String mes, String sign) throws InvalidKeyException, SignatureException {
        signature.initVerify(publicKey);
        byte[] data = mes.getBytes();
        byte[] signValue = Base64.getDecoder().decode(sign);
        signature.update(data);
        return signature.verify(signValue);
    }

    public boolean verifyFile(String src, String sign) throws SignatureException, IOException, InvalidKeyException {
        signature.initVerify(publicKey);
        byte[] signValue = Base64.getDecoder().decode(sign);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
        byte[] buff = new byte[1024];
        int read;
        while ((read = bis.read(buff)) != -1) {
            signature.update(buff, 0, read);
        }
        bis.close();
        return signature.verify(signValue);
    }

    // Phương thức để lấy chuỗi Base64 của khóa công khai
    public String getPublicKeyAsString() {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    // Phương thức để lấy chuỗi Base64 của khóa bí mật
    public String getPrivateKeyAsString() {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    public static void main(String[] args) throws InvalidKeyException, SignatureException, NoSuchAlgorithmException, NoSuchProviderException {
        // Thêm BouncyCastle làm provider
        Security.addProvider(new BouncyCastleProvider());

        ChuKyDientu chuKyDientu = new ChuKyDientu("RSA", "SHA1PRNG", "BC"); // Sử dụng BouncyCastle làm provider cho RSA

        chuKyDientu.genKey();

        // Hiển thị khóa công khai và khóa bí mật
        System.out.println("Public Key: " + chuKyDientu.getPublicKeyAsString());
        System.out.println("Private Key: " + chuKyDientu.getPrivateKeyAsString());

        // Tạo và kiểm tra chữ ký
        String data = chuKyDientu.sign("XIN CHAOa");
        System.out.println("Signature: " + data);
        System.out.println("Verification: " + (chuKyDientu.verify("XIN CHAOa", data) ? "Hợp lệ" : "Không hợp lệ"));
    }
}
