package fpoly.group6_pro1122.kidsshop.Model;

public class CartItem {
    private int id;
    private int product_id;
    private int user_id;
    private int quantity;

    public CartItem() {
    }

    public CartItem(int id, int product_id,int user_id, int quantity) {
        this.id = id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
