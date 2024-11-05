package Cart;

import DBConnect.DBDAO;
import Product.Product;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    Map<Integer, CartProduct> data = new HashMap<>();

    // Phương thức thêm sản phẩm với số lượng mặc định là 1
    public boolean add(int proid) {
        return add(proid, 1);
    }

    // Phương thức thêm sản phẩm với số lượng và các thuộc tính size, color
    public boolean add(int proid, int quantity, String size) {
        if (data.containsKey(proid)) {
            return data.get(proid).incri(proid, quantity);
        }
        DBDAO dao = new DBDAO();
        Product product = dao.getProductById(proid);
        if (product == null) return false;

        data.put(proid, new CartProduct(product, quantity, size));
        return true;
    }

    // Phương thức thêm sản phẩm mà không yêu cầu size và color
    public boolean add(int proid, int quantity) {
        return add(proid, quantity, "");
    }

    // Cập nhật số lượng sản phẩm trong giỏ
    public boolean update(int proid, int quantity) {
        if (!data.containsKey(proid)) {
            return false;
        }
        CartProduct cartProduct = data.get(proid);
        if (quantity <= 0 || quantity > cartProduct.getProduct().getProductQuantity()) {
            return false;
        }
        cartProduct.setQuantity(quantity);
        return true;
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public void remove(int proid) {
        data.remove(proid);
    }

    // Lấy tổng số sản phẩm trong giỏ
    public int getTotal() {
        return data.size();
    }

    // Lấy danh sách sản phẩm trong giỏ hàng
    public Map<Integer, CartProduct> getData() {
        return data;
    }

    // Tính tổng giá trị của giỏ hàng
    public int getTotalPrice() {
        int total = 0;
        for (CartProduct cartProduct : data.values()) {
            total += cartProduct.getSubtotal();
        }
        return total;
    }

    // Lấy thông tin một sản phẩm cụ thể từ giỏ hàng
    public CartProduct getProduct(int proid) {
        return data.get(proid);
    }
}
