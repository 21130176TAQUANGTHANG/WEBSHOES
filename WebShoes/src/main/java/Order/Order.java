package Order;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderId;
    private String userId;
    private int totalPrice;
    private Timestamp orderDate;
    private String notes;
    private String name;
    private String address;
    private String phone;
    private String status;
    private String signature;
    private List<OrderItem> orderItems; // Danh sách sản phẩm trong hóa đơn



    public Order() {
    }

    // Constructor với đầy đủ tham số
    public Order(int orderId, String userId, int totalPrice, Timestamp orderDate, String notes, String name, String address, String phone , String status,String signature) {
        this.orderId = orderId;
        this.userId = userId;
        this.totalPrice = totalPrice;
        this.orderDate = orderDate;
        this.notes = notes;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.status = status;
        this.signature = signature;
        this.orderItems = new ArrayList<>(); // Khởi tạo danh sách orderItems

    }
    public Order(int orderId, Timestamp orderDate, String notes, int totalPrice, String name, String address, String phone, String status) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.notes = notes;
        this.totalPrice = totalPrice;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.status = status;
        this.orderItems = new ArrayList<>(); // Khởi tạo danh sách orderItems

    }

    // Getter và Setter cho các thuộc tính
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }


    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void addOrderItem(OrderItem item) {
        this.orderItems.add(item);
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId='" + userId + '\'' +
                ", totalPrice=" + totalPrice +
                ", orderDate=" + orderDate +
                ", notes='" + notes + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", status='" + status + '\'' +
                ", signature='" + signature + '\'' +
                ", orderItems=" + orderItems +
                '}';
    }
}
