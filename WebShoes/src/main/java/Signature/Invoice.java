package Signature;

public class Invoice {
    private String userId;
    private int orderId;
    private String hashFromContent;
    private String signature;

    // Getters và Setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getHashFromContent() {
        return hashFromContent;
    }

    public void setHashFromContent(String hashFromContent) {
        this.hashFromContent = hashFromContent;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}

