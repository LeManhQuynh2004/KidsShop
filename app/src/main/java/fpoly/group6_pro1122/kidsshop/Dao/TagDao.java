package fpoly.group6_pro1122.kidsshop.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Database.Db_Helper;
import fpoly.group6_pro1122.kidsshop.Model.Tag;

public class TagDao {
    Db_Helper dbHelper;

    public TagDao(Context context) {
        dbHelper = new Db_Helper(context);
    }
    private ArrayList<Tag> getAll(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<Tag> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("id")));
                String tag_name = cursor.getString(cursor.getColumnIndexOrThrow("tag_name"));
                list.add(new Tag(id,tag_name));
            }
        }
        return list;
    }
    public ArrayList<Tag> SelectAll(){
        String query = "SELECT * FROM Tag";
        return getAll(query);
    }
    public Tag SelectID(String id){
        String dk[] = {String.valueOf(id)};
        String query = "SELECT * FROM Tag WHERE id = ?";
        ArrayList<Tag> list = getAll(query,dk);
        return list.get(0);
    }
}
