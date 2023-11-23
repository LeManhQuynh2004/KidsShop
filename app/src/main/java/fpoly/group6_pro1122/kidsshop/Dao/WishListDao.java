package fpoly.group6_pro1122.kidsshop.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Database.Db_Helper;
import fpoly.group6_pro1122.kidsshop.Model.CartItem;
import fpoly.group6_pro1122.kidsshop.Model.Product;
import fpoly.group6_pro1122.kidsshop.Model.WishList;

public class WishListDao{
    Db_Helper dbHelper;

    public WishListDao(Context context) {
        dbHelper = new Db_Helper(context);
    }
    public boolean insertData(WishList wishList) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("product_id", wishList.getProduct_id());
        values.put("user_id",wishList.getUser_id());
        values.put("quantity",wishList.getQuantity());
        values.put("status",wishList.getStatus());
        long check = sqLiteDatabase.insert("WishList", null, values);
        wishList.setId((int) check);
        return check != -1;
    }
    public boolean deleteData(WishList wishList) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk [] = {String.valueOf(wishList.getId())};
        long check = sqLiteDatabase.delete("WishList","id=?",dk);
        return check != -1;
    }
    public boolean updateData(WishList wishList) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk [] = {String.valueOf(wishList.getId())};
        ContentValues values = new ContentValues();
        values.put("product_id", wishList.getProduct_id());
        values.put("user_id",wishList.getUser_id());
        values.put("quantity",wishList.getQuantity());
        values.put("status",wishList.getStatus());
        long check = sqLiteDatabase.update("WishList",values,"id=?",dk);
        return check != -1;
    }
    private ArrayList<WishList> getAll(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<WishList> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("id")));
                int user_id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("user_id")));
                int product_id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("product_id")));
                int quantity = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("quantity")));
                int status = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("status")));
                list.add(new WishList(id,quantity,user_id,product_id,status));
            }
        }
        return list;
    }
    public WishList getWishListByProductId(int productId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        WishList wishList = null;

        String query = "SELECT * FROM WishList WHERE product_id = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(productId)});

        if (cursor != null && cursor.moveToFirst()) {
            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("id")));
            int user_id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("user_id")));
            int product_id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("product_id")));
            int quantity = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("quantity")));
            int status = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("status")));
            wishList = new WishList(id,quantity,user_id,product_id,status);
        }
        db.close();
        return wishList;
    }
    public ArrayList<WishList> SelectAll(){
        String query = "SELECT * FROM WishList";
        return getAll(query);
    }
    public WishList SelectID(String id){
        String dk[] = {String.valueOf(id)};
        String query = "SELECT * FROM WishList WHERE id = ?";
        ArrayList<WishList> list = getAll(query, id);
        return list.get(0);
    }
}