package fpoly.group6_pro1122.kidsshop.Model;

public class Evaluation {
    //      "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//              "comment TEXT NOT NULL," +
//              "date TEXT NOT NULL," +
//              "product_id INTEGER NOT NULL," +
//              "user_id INTEGER NOT NULL," +
//              "start INTEGER NOT NULL," +
    private int id;
    private String comment;
    private String date;
    private String time;
    private int product_id;
    private int user_id;
    private int start;

    public Evaluation() {
    }

    public Evaluation(int id, String comment,String date,String time,int product_id, int user_id, int start) {
        this.id = id;
        this.comment = comment;
        this.date = date;
        this.time = time;
        this.product_id = product_id;
        this.user_id = user_id;
        this.start = start;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
