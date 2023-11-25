package fpoly.group6_pro1122.kidsshop.Model;

public class Order {
    //    String CreateTableOrders = "CREATE TABLE IF NOT EXISTS Orders(" +
//            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//            "date TEXT NOT NULL," +
//            "total_price INTEGER NOT NULL," +
//            "user_id INTEGER NOT NULL," +
//            "payment_id INTEGER NOT NULL," +
//            "shipment_id INTEGER NOT NULL," +
//            "FOREIGN KEY(user_id) REFERENCES User(id)," +
//            "FOREIGN KEY(payment_id) REFERENCES Payment(id)," +
//            "FOREIGN KEY(shipment_id) REFERENCES Shipment(id))";
//        sqLiteDatabase.execSQL(CreateTableOrders);
    private int id;
    private String date;
    private int total_price;
    private int status;
    private int user_id;
    private int payment_id;
    private int shipment_id;

    public Order() {

    }


    public Order(int id, String date, int total_price, int status, int user_id, int payment_id, int shipment_id) {
        this.id = id;
        this.date = date;
        this.total_price = total_price;
        this.status = status;
        this.user_id = user_id;
        this.payment_id = payment_id;
        this.shipment_id = shipment_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(int payment_id) {
        this.payment_id = payment_id;
    }

    public int getShipment_id() {
        return shipment_id;
    }

    public void setShipment_id(int shipment_id) {
        this.shipment_id = shipment_id;
    }
}
