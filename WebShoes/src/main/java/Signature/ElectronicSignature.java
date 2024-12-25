package Signature;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
public class ElectronicSignature {
    KeyPair keyPair;
    Signature signature;
    SecureRandom secureRandom;
    PublicKey publicKey;
    PrivateKey privateKey;

    public ElectronicSignature() {
    }

    public ElectronicSignature(String alg, String algRandom, String prov) throws NoSuchAlgorithmException, NoSuchProviderException {
        // Sử dụng BouncyCastle cho RSA
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
        secureRandom = SecureRandom.getInstance(algRandom, prov);
        generator.initialize(2048, secureRandom); // RSA key size is typically 2048 or 4096 bits
        keyPair = generator.generateKeyPair();
        // Sử dụng SHA256 với RSA cho thuật toán chữ ký
        signature = Signature.getInstance("SHA256withRSA", "BC"); // Use SHA256withRSA for better security
    }

    public boolean genKey() {
        if (keyPair == null) return false;
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
        return true;
    }

    public void loadPublic(String base64Key) throws GeneralSecurityException {
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
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
        Security.addProvider(new BouncyCastleProvider());

        ElectronicSignature electronicSignature = new ElectronicSignature("RSA", "SHA1PRNG", "SUN");

        electronicSignature.genKey();

        // Hiển thị khóa công khai và khóa bí mật
        System.out.println("Public Key: " + electronicSignature.getPublicKeyAsString());
        System.out.println("Private Key: " + electronicSignature.getPrivateKeyAsString());

        // Tạo và kiểm tra chữ ký
        String data = electronicSignature.sign("XIN CHAO");
        System.out.println("Signature: " + data);
        System.out.println("Verification: " + (electronicSignature.verify("XIN CHAO", data) ? "Hợp lệ" : "Không hợp lệ"));
    }

}
