package fpoly.group6_pro1122.kidsshop.Model;

public class Payment {
    private int id;
    private String type;
    private int user_id;

    public Payment() {
    }

    public Payment(int id, String type, int user_id) {
        this.id = id;
        this.type = type;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
