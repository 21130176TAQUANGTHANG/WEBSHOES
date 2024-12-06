package Signature;

public class KeyUser {
    private int id;
    private String userId;
    private String publicKey;

    public KeyUser() {
    }

    public KeyUser(String userId, String publicKey) {
        this.userId = userId;
        this.publicKey = publicKey;
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

}
