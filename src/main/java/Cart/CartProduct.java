package Cart;

import Product.Product;

public class CartProduct {
    private Product product;
    private int quantity;
    private String size;

    public CartProduct() {
    }

    public CartProduct(Product product, int quantity, String size) {
        this.product = product;
        this.quantity = quantity;
        this.size = size;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public boolean incri(int proid, int quantity){
        this.quantity+=quantity;
        if(this.product.getProductQuantity() < this.quantity){
            this.quantity -=quantity;
            return false;
        }
        return true;
    }
    public boolean decri(int proid, int quantity){
        this.quantity-=quantity;
        if(this.product.getProductQuantity() <=0){
            this.quantity +=quantity;
            return false;
        }
        return true;
    }
    public int getSubtotal(){
        return product.getProductPrice() * quantity;
    }
}
