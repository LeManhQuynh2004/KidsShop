package fpoly.group6_pro1122.kidsshop.Model;

public class OrderItem {
//    id INTEGER PRIMARY KEY AUTOINCREMENT," +
//            "quantity INTEGER NOT NULL," +
//            "price INTEGER NOT NULL," +
//            "product_id INTEGER NOT NULL," +
//            "order_id INTEGER NOT NULL," +
//            "FOREIGN KEY(product_id) REFERENCES Product(id)," +
//            "FOREIGN KEY(order_id) REFERENCES Orders(id))";
    private int id;
    private int quantity;
    private int price;
    private int product_id;
    private int order_id;

    public OrderItem() {
    }

    public OrderItem(int id, int quantity, int price, int product_id, int order_id) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.product_id = product_id;
        this.order_id = order_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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
}
