package fpoly.group6_pro1122.kidsshop.Model;

public class Cart {
    private int id;
    private int user_id;
    private int total_price;

    public Cart() {
    }

    public Cart(int id, int user_id, int total_price) {
        this.id = id;
        this.user_id = user_id;
        this.total_price = total_price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }
}
