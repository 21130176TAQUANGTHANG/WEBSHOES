package Product;

import java.text.NumberFormat;
import java.util.Locale;

public class Product {
    private int productId;
    private String productName;
    private String productImage;
    private int productPrice; // price
    private String productDescription; // description
    private int productQuantity; // quantity
    private String productSize; // size
    private String productColor; // color
    private String productLogo; // brand

    // Constructor for basic product
    public Product(int productId, String productName, String productImage, int productPrice, String productDescription, int productQuantity) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productQuantity = productQuantity;
    }

    // Constructor for complete product details
    public Product(int productId, String productName, String productImage, int productPrice, String productDescription, int productQuantity, String productSize, String productColor, String productLogo) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.productDescription = productDescription;
        this.productQuantity = productQuantity;
        this.productSize = productSize;
        this.productColor = productColor;
        this.productLogo = productLogo;
    }

    // Constructor for product with size
    public Product(int productId, String productName, String productImage, int productPrice, int productQuantity, String productSize) {
        this.productId = productId;
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productSize = productSize;
    }

    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(int productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public String getProductLogo() {
        return productLogo;
    }

    public void setProductLogo(String productLogo) {
        this.productLogo = productLogo;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productImage='" + productImage + '\'' +
                ", productPrice=" + productPrice +
                ", productDescription='" + productDescription + '\'' +
                ", productQuantity=" + productQuantity +
                ", productSize=" + productSize +
                ", productColor='" + productColor + '\'' +
                ", productLogo='" + productLogo + '\'' +
                '}';
    }
}
