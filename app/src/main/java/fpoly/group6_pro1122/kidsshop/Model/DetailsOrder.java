package fpoly.group6_pro1122.kidsshop.Model;

import java.io.Serializable;

public class DetailsOrder implements Serializable {
    //                list.add(new OrderItem(productName, productPrice, image, orderItemPrice, date, status, quantity,
//                        paymentId, shipmentId, userId, productId, orderId, totalPrice));
    private int id;
    private String product_name;
    private int product_price;
    private String image;
    private int orderItemPrice;
    private String date;
    private int status;
    private int quantity;
    private int paymentID;
    private int shipmentID;
    private int userID;
    private int product_id;
    private int order_id;
    private int total_price;
    private String time;

    public DetailsOrder() {
    }

    public DetailsOrder(int id,String product_name, int product_price, String image, int orderItemPrice, String date, int status, int quantity, int paymentID, int shipmentID, int userID, int product_id, int order_id, int total_price,String time) {
        this.id = id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.image = image;
        this.orderItemPrice = orderItemPrice;
        this.date = date;
        this.status = status;
        this.quantity = quantity;
        this.paymentID = paymentID;
        this.shipmentID = shipmentID;
        this.userID = userID;
        this.product_id = product_id;
        this.order_id = order_id;
        this.total_price = total_price;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getProduct_price() {
        return product_price;
    }

    public void setProduct_price(int product_price) {
        this.product_price = product_price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getOrderItemPrice() {
        return orderItemPrice;
    }

    public void setOrderItemPrice(int orderItemPrice) {
        this.orderItemPrice = orderItemPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }

    public int getShipmentID() {
        return shipmentID;
    }

    public void setShipmentID(int shipmentID) {
        this.shipmentID = shipmentID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
