package Dashboard;

public class ProductStats {
    private int productId;
    private int totalQuantity;

    public ProductStats(int productId, int totalQuantity) {
        this.productId = productId;
        this.totalQuantity = totalQuantity;
    }

    public int getProductId() {
        return productId;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    @Override
    public String toString() {
        return "ProductStats{" +
                "productId=" + productId +
                ", totalQuantity=" + totalQuantity +
                '}';
    }
}
