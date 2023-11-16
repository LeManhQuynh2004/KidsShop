package fpoly.group6_pro1122.kidsshop.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Database.Db_helper;
import fpoly.group6_pro1122.kidsshop.Model.Category;

public class CategoryDao {
    Db_helper dbHelper;
    Context context;

    public CategoryDao(Db_helper dbHelper, Context context) {
        this.dbHelper = dbHelper;
        this.context = context;
    }
    public ArrayList<Category> getAll(){
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        ArrayList<Category> list = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = database.rawQuery("SELECT * FROM Category",null);
           if (cursor.getCount()>0 && cursor != null){
               cursor.moveToFirst();
               while (!cursor.isAfterLast()){
                   list.add(new Category(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3) ));
                   cursor.moveToNext();
               }
           }else {
               Toast.makeText(context, "Danh sách trống", Toast.LENGTH_SHORT).show();
           }
        }finally {
            if (cursor != null){
                cursor.close();
            }
            database.close();
        }
        return list;
    }
    public boolean deleteTL(int id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long kq = database.delete("Category","category_id = ?",new String[]{String.valueOf(id)});
        return kq != -1;
    }
    public boolean insertTL(Category category){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",category.getName());
        values.put("describe",category.getDescribe());
        values.put("name",category.getName());
        values.put("image",category.getImage());
        long kq = database.insert("Category",null,values);
        category.setCategory_id((int) kq);
        return kq != -1;
    }
}
