package fpoly.group6_pro1122.kidsshop.Dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Database.Db_Helper;
import fpoly.group6_pro1122.kidsshop.Model.User;

public class UserDao {
    Db_Helper dbHelper;
    private static final String TABLE = "User";
    private static final String COLUM_EMAIL = "email";
    private static final String COLUM_PASS = "password";
    private static final String COLUM_IMGAE = "image";
    private static final String COLUM_FULLNAME = "fullName";
    private static final String COLUM_ROLE = "role";
    private static final String COLUM_PHONE = "phoneNumber";

    public ArrayList<User> getAll(String sql, String... selectionArgs){
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        ArrayList<User> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql,selectionArgs);
        if (cursor.getCount() >0){
            while (cursor.moveToNext()){
                list.add(new User(cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getInt(5)));

            }
        }
        return list;
    }

    public boolean inserDatabase(User user){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUM_EMAIL, user.getEmail());
        values.put(COLUM_PASS, user.getPassword());
        values.put(COLUM_FULLNAME, user.getFullname());
        values.put(COLUM_IMGAE, user.getImage());
        values.put(COLUM_PHONE, user.getPhone());
        values.put(COLUM_ROLE, user.getRole());
        long check = sqLiteDatabase.insert(TABLE , null,values);
        return check !=-1;
    }

    public boolean deleteDatabase(User user){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String dk[] ={user.getEmail()};
        long check = database.delete(TABLE , COLUM_EMAIL + "=?" , dk);
        return check != -1;
    }

}
