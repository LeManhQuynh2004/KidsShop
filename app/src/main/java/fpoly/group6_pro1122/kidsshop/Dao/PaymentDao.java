package fpoly.group6_pro1122.kidsshop.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Database.Db_Helper;
import fpoly.group6_pro1122.kidsshop.Model.Payment;
import fpoly.group6_pro1122.kidsshop.Model.Tag;

public class PaymentDao {
    Db_Helper dbHelper;

    public PaymentDao(Context context) {
        dbHelper = new Db_Helper(context);
    }

    public boolean insertData(Payment payment) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", payment.getType());
        contentValues.put("user_id", payment.getUser_id());
        long check = sqLiteDatabase.insert("Payment", null, contentValues);
        payment.setId((int) check);
        return check != -1;
    }

    public boolean deleteData(Payment payment) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(payment.getId())};
        long check = sqLiteDatabase.delete("Payment", "id=?", dk);
        return check != -1;
    }

    public boolean updateData(Payment payment) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(payment.getId())};
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", payment.getType());
        contentValues.put("user_id", payment.getUser_id());
        long check = sqLiteDatabase.update("Payment", contentValues, "id=?", dk);
        return check != -1;
    }

    private ArrayList<Payment> getAll(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<Payment> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("id")));
                int user_id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("user_id")));
                String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
                list.add(new Payment(id, type, user_id));
            }
        }
        return list;
    }

    public ArrayList<Payment> SelectAll() {
        String query = "SELECT * FROM Payment";
        return getAll(query);
    }

    public Payment SelectID(String id) {
        String dk[] = {String.valueOf(id)};
        String query = "SELECT * FROM Payment WHERE id = ?";
        ArrayList<Payment> list = getAll(query, dk);
        if(list != null){
            return list.get(0);
        }else{
            return null;
        }
    }
}
