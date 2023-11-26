package fpoly.group6_pro1122.kidsshop.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Database.Db_Helper;
import fpoly.group6_pro1122.kidsshop.Model.CartItem;
import fpoly.group6_pro1122.kidsshop.Model.Order;
import fpoly.group6_pro1122.kidsshop.Model.OrderItem;

public class OrderDao {
    Db_Helper dbHelper;
    public OrderDao(Context context) {
        dbHelper = new Db_Helper(context);
    }

    //     //    String CreateTableOrders = "CREATE TABLE IF NOT EXISTS Orders(" +
////            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
////            "date TEXT NOT NULL," +
////            "total_price INTEGER NOT NULL," +
////            "user_id INTEGER NOT NULL," +
////            "payment_id INTEGER NOT NULL," +
////            "shipment_id INTEGER NOT NULL," +
////            "FOREIGN KEY(user_id) REFERENCES User(id)," +
////            "FOREIGN KEY(payment_id) REFERENCES Payment(id)," +
////            "FOREIGN KEY(shipment_id) REFERENCES Shipment(id))";
////        sqLiteDatabase.execSQL(CreateTableOrders);
    public boolean insertData(Order order) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", order.getDate());
        contentValues.put("time", order.getTime());
        contentValues.put("total_price", order.getTotal_price());
        contentValues.put("user_id", order.getUser_id());
        contentValues.put("status", order.getStatus());
        contentValues.put("shipment_id", order.getShipment_id());
        contentValues.put("payment_id", order.getPayment_id());
        long check = sqLiteDatabase.insert("Orders", null, contentValues);
        order.setId((int) check);
        return check != -1;
    }
    public boolean deleteData(Order order) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk [] = {String.valueOf(order.getId())};
        long check = sqLiteDatabase.delete("Orders","id=?",dk);
        return check != -1;
    }
    public boolean updateData(Order order) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk [] = {String.valueOf(order.getId())};
        ContentValues contentValues = new ContentValues();
        contentValues.put("date", order.getDate());
        contentValues.put("time", order.getTime());
        contentValues.put("total_price", order.getTotal_price());
        contentValues.put("user_id", order.getUser_id());
        contentValues.put("status", order.getStatus());
        contentValues.put("shipment_id", order.getShipment_id());
        contentValues.put("payment_id", order.getPayment_id());
        long check = sqLiteDatabase.update("Orders",contentValues,"id=?",dk);
        return check != -1;
    }
    private ArrayList<Order> getAll(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<Order> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("id")));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                int total_price = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("total_price")));
                int user_id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("user_id")));
                int shipment_id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("shipment_id")));
                int payment_id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("payment_id")));
                int status = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("status")));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
//                 public Order(int id, String date, int total_price, int status, int user_id, int payment_id, int shipment_id) {
                list.add(new Order(id, date, total_price, status, user_id, payment_id, shipment_id,time));
            }
        }
        return list;
    }
    public ArrayList<Order> SelectAll() {
        String query = "SELECT * FROM Orders";
        return getAll(query);
    }

    public Order SelectID(String id) {
        String dk[] = {String.valueOf(id)};
        String query = "SELECT * FROM Orders WHERE id = ?";
        ArrayList<Order> list = getAll(query, dk);
        return list.get(0);
    }
}
