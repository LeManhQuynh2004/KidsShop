package fpoly.group6_pro1122.kidsshop.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Database.Db_Helper;
import fpoly.group6_pro1122.kidsshop.Model.CartItem;
import fpoly.group6_pro1122.kidsshop.Model.DetailsOrder;
import fpoly.group6_pro1122.kidsshop.Model.Order;
import fpoly.group6_pro1122.kidsshop.Model.OrderItem;
import fpoly.group6_pro1122.kidsshop.Model.Payment;

public class OrderItemDao {
    Db_Helper dbHelper;

    public OrderItemDao(Context context) {
        dbHelper = new Db_Helper(context);
    }
//      private int id;
//    private int quantity;
//    private int price;
//    private int product_id;
//    private int order_id;
    public boolean insertData(OrderItem orderItem){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("quantity",orderItem.getQuantity());
        contentValues.put("price",orderItem.getPrice());
        contentValues.put("product_id",orderItem.getProduct_id());
        contentValues.put("order_id",orderItem.getOrder_id());
        long check = sqLiteDatabase.insert("OrderItem",null,contentValues);
        orderItem.setOrder_id((int) check);
        return check != -1;
    }
    public boolean deleteData(OrderItem orderItem){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk [] = {String.valueOf(orderItem.getOrder_id())};
        long check = sqLiteDatabase.delete("OrderItem","id=?",dk);
        return check != -1;
    }
    public boolean updateData(OrderItem orderItem){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk [] = {String.valueOf(orderItem.getOrder_id())};
        ContentValues contentValues = new ContentValues();
        contentValues.put("quantity",orderItem.getQuantity());
        contentValues.put("price",orderItem.getPrice());
        contentValues.put("product_id",orderItem.getProduct_id());
        contentValues.put("order_id",orderItem.getOrder_id());
        long check = sqLiteDatabase.update("OrderItem",contentValues,"id=?",dk);
        return check != -1;
    }
    private ArrayList<OrderItem> getAll(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<OrderItem> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("id")));
                int product_id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("product_id")));
                int order_id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("order_id")));
                int quantity = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("quantity")));
                int price = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("price")));
                list.add(new OrderItem(id,quantity,price,product_id,order_id));
            }
        }
        return list;
    }
    public ArrayList<DetailsOrder> SelectAllJoin() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<DetailsOrder> list = new ArrayList<>();
        String query = "SELECT " +
                "Product.product_name, Product.product_price, Product.image, " +
                "OrderItem.price AS order_item_price, Orders.date, Orders.status, OrderItem.quantity AS soLuong, " +
                "Orders.payment_id, Orders.shipment_id, Orders.user_id, OrderItem.product_id, " +
                "OrderItem.order_id, Orders.total_price ,OrderItem.id AS orderItem_id,Orders.time " +
                "FROM OrderItem " +
                "INNER JOIN Orders ON OrderItem.order_id = Orders.id " +
                "INNER JOIN Product ON OrderItem.product_id = Product.id";

        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("orderItem_id"));
                String productName = cursor.getString(cursor.getColumnIndexOrThrow("product_name"));
                int productPrice = cursor.getInt(cursor.getColumnIndexOrThrow("product_price"));
                String image = cursor.getString(cursor.getColumnIndexOrThrow("image"));
                int orderItemPrice = cursor.getInt(cursor.getColumnIndexOrThrow("order_item_price"));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                int status = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("status")));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("soLuong"));
                int paymentId = cursor.getInt(cursor.getColumnIndexOrThrow("payment_id"));
                int shipmentId = cursor.getInt(cursor.getColumnIndexOrThrow("shipment_id"));
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
                int productId = cursor.getInt(cursor.getColumnIndexOrThrow("product_id"));
                int orderId = cursor.getInt(cursor.getColumnIndexOrThrow("order_id"));
                int totalPrice = cursor.getInt(cursor.getColumnIndexOrThrow("total_price"));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
                list.add(new DetailsOrder(id,productName, productPrice, image, orderItemPrice, date, status, quantity,
                        paymentId, shipmentId, userId, productId, orderId, totalPrice,time));
            }
        }
        cursor.close();
        return list;
    }


    public ArrayList<OrderItem> SelectAll() {
        String query = "SELECT * FROM OrderItem";
        return getAll(query);
    }

    public ArrayList<OrderItem> SelectArrayID(String id) {
        String dk[] = {String.valueOf(id)};
        String query = "SELECT * FROM OrderItem WHERE id = ?";
        ArrayList<OrderItem> list = getAll(query, dk);
        return list;
    }
    public OrderItem SelectOrder(String id) {
        String dk[] = {String.valueOf(id)};
        String query = "SELECT * FROM OrderItem WHERE order_id = ?";
        ArrayList<OrderItem> list = getAll(query, dk);

        if (list != null && !list.isEmpty()) {
            return list.get(0);
        } else {
            Log.e("TAG", "SelectOrder: Null or Empty");
            return null;
        }
    }

}
