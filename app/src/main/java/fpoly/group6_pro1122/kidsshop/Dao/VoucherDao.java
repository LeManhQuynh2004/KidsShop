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
import fpoly.group6_pro1122.kidsshop.Model.Voucher;

public class VoucherDao {
    Db_Helper dbHelper;


    public VoucherDao(Context context) {
        dbHelper = new Db_Helper(context);
    }

    //    public Voucher(int id, String code, float discount_amount, String start_date, String expiration_date, int user_id, int order_id) {
//    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//            "code TEXT NOT NULL," +
//            "discount_amount FLOAT NOT NULL," +
//            "start_date TEXT NOT NULL," +
//            "expiration_date TEXT NOT NULL," +
//            "user_id INTEGER," +
//            "order_id INTEGER," +
    public boolean insertData(Voucher voucher) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("code", voucher.getCode());
        contentValues.put("discount_amount", voucher.getDiscount_amount());
        contentValues.put("start_date", voucher.getStart_date());
        contentValues.put("expiration_date", voucher.getExpiration_date());
        contentValues.put("user_id", voucher.getUser_id());
        long check = sqLiteDatabase.insert("Voucher", null, contentValues);
        voucher.setId((int) check);
        return check != -1;
    }

    public boolean deleteData(Voucher voucher) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(voucher.getId())};
        long check = sqLiteDatabase.delete("Voucher", "id=?", dk);
        return check != -1;
    }

    public boolean updateData(Voucher voucher) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(voucher.getId())};
        ContentValues contentValues = new ContentValues();
        contentValues.put("code", voucher.getCode());
        contentValues.put("discount_amount", voucher.getDiscount_amount());
        contentValues.put("start_date", voucher.getStart_date());
        contentValues.put("expiration_date", voucher.getExpiration_date());
        contentValues.put("user_id", voucher.getUser_id());
        long check = sqLiteDatabase.update("Voucher", contentValues, "id=?", dk);
        return check != -1;
    }

    private ArrayList<Voucher> getAll(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<Voucher> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("id")));
                String code = cursor.getString(cursor.getColumnIndexOrThrow("code"));
                int user_id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("user_id")));
                String start_date = cursor.getString(cursor.getColumnIndexOrThrow("start_date"));
                String expiration_date = cursor.getString(cursor.getColumnIndexOrThrow("expiration_date"));
                int discount = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("discount_amount")));
                //    public Voucher(int id, String code, float discount_amount, String start_date, String expiration_date, int user_id, int order_id) {
                list.add(new Voucher(id, code, discount, start_date, expiration_date, user_id));
            }
        }
        return list;
    }

    public ArrayList<Voucher> SelectAll() {
        String query = "SELECT * FROM Voucher";
        return getAll(query);
    }
    public ArrayList<Voucher> SelectAllLIMIT() {
        String query = "SELECT * FROM Voucher LIMIT 3";
        return getAll(query);
    }
    public Voucher SelectID(String id) {
        String dk[] = {String.valueOf(id)};
        String query = "SELECT * FROM Voucher WHERE id = ?";
        ArrayList<Voucher> list = getAll(query, dk);
        return list.get(0);
    }
    public ArrayList<Voucher> SelectUser_ID(String id) {
        String dk[] = {String.valueOf(id)};
        String query = "SELECT * FROM Voucher WHERE user_id = ?";
        ArrayList<Voucher> list = getAll(query, dk);
        return list;
    }
}
