package fpoly.group6_pro1122.kidsshop.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Database.Db_Helper;
import fpoly.group6_pro1122.kidsshop.Model.CartItem;
import fpoly.group6_pro1122.kidsshop.Model.Evaluation;
import fpoly.group6_pro1122.kidsshop.Model.Order;

public class EvaluationDao {
    Db_Helper dbHelper;

    public EvaluationDao(Context context) {
        dbHelper = new Db_Helper(context);
    }

    //       "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                "comment TEXT NOT NULL," +
//                "date TEXT NOT NULL," +
//                "product_id INTEGER NOT NULL," +
//                "user_id INTEGER NOT NULL," +
//                "start INTEGER NOT NULL," +
    public boolean insertData(Evaluation evaluation) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("product_id", evaluation.getProduct_id());
        values.put("user_id", evaluation.getUser_id());
        values.put("comment", evaluation.getComment());
        values.put("date", evaluation.getDate());
        values.put("time", evaluation.getTime());
        values.put("start", evaluation.getStart());
        long check = sqLiteDatabase.insert("Evaluation", null, values);
        evaluation.setId((int) check);
        return check != -1;
    }

    public boolean deleteData(Evaluation evaluation) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(evaluation.getId())};
        long check = sqLiteDatabase.delete("Evaluation", "id=?", dk);
        return check != -1;
    }

    public boolean updateData(Evaluation evaluation) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(evaluation.getId())};
        ContentValues values = new ContentValues();
        values.put("product_id", evaluation.getProduct_id());
        values.put("user_id", evaluation.getUser_id());
        values.put("comment", evaluation.getComment());
        values.put("date", evaluation.getDate());
        values.put("time", evaluation.getTime());
        values.put("start", evaluation.getStart());
        long check = sqLiteDatabase.update("Evaluation", values, "id=?", dk);
        return check != -1;
    }

    private ArrayList<Evaluation> getAll(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<Evaluation> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("id")));
                int user_id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("user_id")));
                int product_id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("product_id")));
                int start = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("start")));
                String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
                String time = cursor.getString(cursor.getColumnIndexOrThrow("time"));
                String comment = cursor.getString(cursor.getColumnIndexOrThrow("comment"));
//                public Evaluation(int id, String comment,String date,String time,int product_id, int user_id, int start) {
                list.add(new Evaluation(id, comment, date, time, product_id, user_id, start));
            }
        }
        return list;
    }

    public ArrayList<Evaluation> SelectAll() {
        String query = "SELECT * FROM Evaluation";
        return getAll(query);
    }
    public ArrayList<Evaluation> SelectProduct(int product_id) {
        String dk[] = {String.valueOf(product_id)};
        String query = "SELECT * FROM Evaluation WHERE product_id = ?";
        return getAll(query, dk);
    }


    public Evaluation SelectID(String id) {
        String dk[] = {String.valueOf(id)};
        String query = "SELECT * FROM Evaluation WHERE id = ?";
        ArrayList<Evaluation> list = getAll(query, dk);
        return list.get(0);
    }
}
