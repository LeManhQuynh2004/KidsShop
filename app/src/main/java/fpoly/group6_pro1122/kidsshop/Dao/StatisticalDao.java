package fpoly.group6_pro1122.kidsshop.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Database.Db_Helper;
import fpoly.group6_pro1122.kidsshop.Model.DetailsOrder;
import fpoly.group6_pro1122.kidsshop.Model.Top;

public class StatisticalDao {
    Db_Helper dbHelper;

    public StatisticalDao(Context context) {
        dbHelper = new Db_Helper(context);
    }

    public ArrayList<Top> ThongKeBanChay() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<Top> list = new ArrayList<>();
        String query = "SELECT " +
                "Product.product_name,OrderItem.order_id,OrderItem.id AS orderItem_id,OrderItem.product_id," +
                "COUNT(OrderItem.product_id) AS quantity_order ,Sum(OrderItem.price) as tongTien  " +
                "FROM OrderItem " +
                "INNER JOIN Orders ON OrderItem.order_id = Orders.id " +
                "INNER JOIN Product ON OrderItem.product_id = Product.id " +
                "WHERE Orders.status = 2 " +
                "GROUP BY OrderItem.product_id " +
                "ORDER BY quantity_order DESC " +
                "LIMIT 10";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("orderItem_id"));
                String productName = cursor.getString(cursor.getColumnIndexOrThrow("product_name"));
                int Order_id = cursor.getInt(cursor.getColumnIndexOrThrow("order_id"));
                int productId = cursor.getInt(cursor.getColumnIndexOrThrow("product_id"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity_order"));
                int price = cursor.getInt(cursor.getColumnIndexOrThrow("tongTien"));
                list.add(new Top(productName, quantity, productId, id, Order_id,price));
            }
        }
        cursor.close();
        return list;
    }

    public int DoanhThu(String startDate, String endDate) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String query = "SELECT SUM(OrderItem.price) as doanhThu, " +
                "Product.product_name, OrderItem.order_id, OrderItem.id AS orderItem_id, OrderItem.product_id, " +
                "COUNT(OrderItem.product_id) AS quantity_order " +
                "FROM OrderItem " +
                "INNER JOIN Orders ON OrderItem.order_id = Orders.id " +
                "INNER JOIN Product ON OrderItem.product_id = Product.id " +
                "WHERE Orders.date BETWEEN ? AND ? AND Orders.status = 2 " +
                "GROUP BY OrderItem.product_id";
        String dk[] = {startDate, endDate};
        int doanhThu = 0;
        Cursor cursor = sqLiteDatabase.rawQuery(query, dk);
        while (cursor.moveToNext()) {
            try {
                doanhThu += cursor.getInt(cursor.getColumnIndexOrThrow("doanhThu"));
            } catch (Exception e) {
                doanhThu = 0;
            }
        }
        cursor.close();
        return doanhThu;
    }

    public ArrayList<Top> ThongKeBanChay2(String startDate, String endDate) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<Top> list = new ArrayList<>();
        String query = "SELECT " +
                "Product.product_name, OrderItem.order_id, OrderItem.id AS orderItem_id, OrderItem.product_id, " +
                "COUNT(OrderItem.product_id) AS quantity_order,Sum(OrderItem.price) as tongTien " +
                "FROM OrderItem " +
                "INNER JOIN Orders ON OrderItem.order_id = Orders.id " +
                "INNER JOIN Product ON OrderItem.product_id = Product.id " +
                "WHERE Orders.date BETWEEN ? AND ? AND Orders.status = 2 " +
                "GROUP BY OrderItem.product_id " +
                "ORDER BY quantity_order DESC " +
                "LIMIT 10";
        String dk[] = {startDate, endDate};
        Cursor cursor = sqLiteDatabase.rawQuery(query, dk);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("orderItem_id"));
                String productName = cursor.getString(cursor.getColumnIndexOrThrow("product_name"));
                int Order_id = cursor.getInt(cursor.getColumnIndexOrThrow("order_id"));
                int productId = cursor.getInt(cursor.getColumnIndexOrThrow("product_id"));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow("quantity_order"));
                int price = cursor.getInt(cursor.getColumnIndexOrThrow("tongTien"));
                list.add(new Top(productName, quantity, productId, id, Order_id,price));
            }
        }
        cursor.close();
        return list;
    }
}
