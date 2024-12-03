package Signature;

public class ClassSignature {
    private int id;
    private String userId;
    private String publicKey;
    private String privateKey;
    private String signature;

    public ClassSignature(int id, String userId, String publicKey, String privateKey, String signature) {
        this.id = id;
        this.userId = userId;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.signature = signature;
    }

    public ClassSignature(String userId, String publicKey, String privateKey, String signature) {
        this.userId = userId;
        this.publicKey = publicKey;
        this.privateKey = privateKey;
        this.signature = signature;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
