package fpoly.group6_pro1122.kidsshop.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.Array;
import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Database.Db_Helper;
import fpoly.group6_pro1122.kidsshop.Model.CartItem;
import fpoly.group6_pro1122.kidsshop.Model.Category;
import fpoly.group6_pro1122.kidsshop.Model.Product;

public class CartItemDao {
    Db_Helper dbHelper;
    public static final String TAG = "CartItemDao";
    public CartItemDao(Context context) {
        dbHelper = new Db_Helper(context);
    }
    public boolean insertData(CartItem cartItem) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("product_id", cartItem.getProduct_id());
        values.put("user_id",cartItem.getUser_id());
        values.put("quantity",cartItem.getQuantity());
        values.put("total_price",cartItem.getTotal_price());
        values.put("status",cartItem.getStatus());
        long check = sqLiteDatabase.insert("CartItem", null, values);
        cartItem.setId((int) check);
        return check != -1;
    }
    public boolean deleteData(CartItem cartItem) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk [] = {String.valueOf(cartItem.getId())};
        long check = sqLiteDatabase.delete("CartItem","id = ?",dk);
        return check != -1;
    }
    public boolean updateData(CartItem cartItem) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk [] = {String.valueOf(cartItem.getId())};
        ContentValues values = new ContentValues();
        values.put("user_id",cartItem.getUser_id());
        values.put("product_id", cartItem.getProduct_id());
        values.put("quantity",cartItem.getQuantity());
        values.put("total_price",cartItem.getTotal_price());
        values.put("status",cartItem.getStatus());
        long check = sqLiteDatabase.update("CartItem",values,"id = ?",dk);
        return check != -1;
    }
    private ArrayList<CartItem> getAll(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<CartItem> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("id")));
                int user_id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("user_id")));
                int product_id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("product_id")));
                int quantity = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("quantity")));
                int price = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("total_price")));
                int status = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("status")));
                Log.e(TAG, "Status: "+status);
                list.add(new CartItem(id,product_id,user_id,quantity,price,status));
            }
        }
        return list;
    }
    public CartItem getCartItemByProductId(int userID,int productId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        CartItem cartItem = null;

        String query = "SELECT * FROM CartItem WHERE user_id = ? AND product_id = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userID),String.valueOf(productId)});

        if (cursor != null && cursor.moveToFirst()) {
            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("id")));
            int user_id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("user_id")));
            int product_id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("product_id")));
            int quantity = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("quantity")));
            int price = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("total_price")));
            int status = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("status")));
            //int id, int product_id,int user_id, int quantity,int total_price,int status
            cartItem = new CartItem(id,product_id,user_id,quantity,price,status);
        }
        db.close();
        return cartItem;
    }
    public CartItem getCartItemByUserId(int id_user) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        CartItem cartItem = null;

        String query = "SELECT * FROM CartItem WHERE user_id = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id_user)});

        if (cursor != null && cursor.moveToFirst()) {
            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("id")));
            int user_id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("user_id")));
            int product_id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("product_id")));
            int quantity = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("quantity")));
            int price = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("total_price")));
            int status = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("status")));
            //int id, int product_id,int user_id, int quantity,int total_price,int status
            cartItem = new CartItem(id,product_id,user_id,quantity,price,status);
        }
        db.close();
        return cartItem;
    }
    public ArrayList<CartItem> SelectAll(){
        String query = "SELECT * FROM CartItem";
        return getAll(query);
    }
    public ArrayList<CartItem> SelectUser(String id){
        String query = "SELECT * FROM CartItem WHERE user_id = ?";
        return getAll(query,id);
    }
    public CartItem SelectID(String id){
        String dk[] = {String.valueOf(id)};
        String query = "SELECT * FROM CartItem WHERE id = ?";
        ArrayList<CartItem> list = getAll(query,dk);
        return list.get(0);
    }
}
