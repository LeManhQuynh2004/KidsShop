package fpoly.group6_pro1122.kidsshop.Model;

import java.io.Serializable;

public class Voucher implements Serializable {
//    String CreateTableVoucher = "CREATE TABLE IF NOT EXISTS Voucher(" +
//            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//            "code TEXT NOT NULL," +
//            "discount_amount FLOAT NOT NULL," +
//            "start_date TEXT NOT NULL," +
//            "expiration_date TEXT NOT NULL," +
//            "user_id INTEGER," +
//            "order_id INTEGER," +
//            "FOREIGN KEY(order_id) REFERENCES Orders(id)," +
//            "FOREIGN KEY(user_id) REFERENCES User(id))";
//        sqLiteDatabase.execSQL(CreateTableVoucher);
    private int id;
    private String code;
    private int discount_amount;
    private String start_date;
    private String expiration_date;
    private int user_id;

    public Voucher() {
    }

    public Voucher(int id, String code, int discount_amount, String start_date, String expiration_date, int user_id) {
        this.id = id;
        this.code = code;
        this.discount_amount = discount_amount;
        this.start_date = start_date;
        this.expiration_date = expiration_date;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getDiscount_amount() {
        return discount_amount;
    }

    public void setDiscount_amount(int discount_amount) {
        this.discount_amount = discount_amount;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
