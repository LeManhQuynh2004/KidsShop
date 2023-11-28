package fpoly.group6_pro1122.kidsshop.Model;

public class Top {
    private String product_name;
    private int quantity;
    private int product_id;
    private int id;
    private int order_id;
    private int price;

    public Top() {
    }

    public Top(String product_name, int quantity, int product_id, int id, int order_id,int price) {
        this.product_name = product_name;
        this.quantity = quantity;
        this.product_id = product_id;
        this.id = id;
        this.order_id = order_id;
        this.price = price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
