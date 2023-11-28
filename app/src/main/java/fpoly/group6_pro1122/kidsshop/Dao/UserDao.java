package fpoly.group6_pro1122.kidsshop.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Database.Db_Helper;
import fpoly.group6_pro1122.kidsshop.Model.User;

public class UserDao {
    Db_Helper dbHelper;
    private static final String TABLE_NAME = "User";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_FULLNAME = "fullName";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_PHONE = "phoneNumber";
    private static final String COLUMN_ROLE = "role";
    private static final String COLUMN_IMAGE = "image";

    public UserDao(Context context) {
        dbHelper = new Db_Helper(context);
    }

    public boolean insertData(User user) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PASSWORD, user.getPassword());
        contentValues.put(COLUMN_FULLNAME, user.getFullname());
        contentValues.put(COLUMN_EMAIL, user.getEmail());
        contentValues.put(COLUMN_PHONE, user.getPhone());
        contentValues.put(COLUMN_IMAGE, user.getImage());
        contentValues.put(COLUMN_ADDRESS, user.getAddress());
        contentValues.put(COLUMN_ROLE, user.getRole());
        long check = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        user.setId((int) check);
        return check != -1;
    }

    public boolean deleteData(User user) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {user.getEmail()};
        long check = sqLiteDatabase.delete(TABLE_NAME, COLUMN_EMAIL + "=?", dk);
        return check != -1;
    }

    public boolean updateData(User user) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {user.getEmail()};
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, user.getId());
        contentValues.put(COLUMN_PASSWORD, user.getPassword());
        contentValues.put(COLUMN_FULLNAME, user.getFullname());
        contentValues.put(COLUMN_EMAIL, user.getEmail());
        contentValues.put(COLUMN_PHONE, user.getPhone());
        contentValues.put(COLUMN_IMAGE, user.getImage());
        contentValues.put(COLUMN_ADDRESS, user.getAddress());
        contentValues.put(COLUMN_ROLE, user.getRole());
        long check = sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_EMAIL + "=?", dk);
        return check != -1;
    }

    public ArrayList<User> getAll(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<User> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE));
                String password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
                String fullname = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FULLNAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL));
                String address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS));
                String image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
                int role = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROLE)));
                list.add(new User(id, password, fullname, email, image, phone, address, role));
            }
        }
        return list;
    }

    public ArrayList<User> SelectAll() {
        String query = "SELECT * FROM " + TABLE_NAME;
        return getAll(query);
    }

    public User SelectID(String id) {
        String query = "SELECT * FROM User WHERE email = ?";
        ArrayList<User> list = getAll(query, id);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }
    public User SelectIDTwo(String id) {
        String query = "SELECT * FROM User WHERE id = ?";
        ArrayList<User> list = getAll(query, String.valueOf(id));
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public boolean checkLogin(String email, String password, String role) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM User WHERE " + COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=? AND " + COLUMN_ROLE + " = ?";
        String[] selectionArgs = new String[]{email, password, role};
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        boolean result = cursor.getCount() > 0;
        return result;
    }

    public User checkUser(String emailUser) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String query = "SELECT * FROM User WHERE " + COLUMN_EMAIL + "=?";
        String dk[] = {emailUser};
        Cursor cursor = sqLiteDatabase.rawQuery(query, dk);
        if (cursor != null && cursor.getCount() == 1) {
            cursor.moveToFirst();
            int id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)));
            String phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
            String fullname = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FULLNAME));
            String email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL));
            String address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS));
            String image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
            int role = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ROLE)));
            User user = new User(id, password, fullname, email, image, phone, address, role);
            return user;
        } else {
            return null;
        }
    }

    public boolean isEmailExists(String email) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_EMAIL + "=?";
        String[] selectionArgs = new String[]{email};
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        boolean result = cursor.getCount() > 0;
        cursor.close();
        return result;
    }

    public boolean checkPassword(String pass) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM User WHERE password = ?";
        Cursor cursor = database.rawQuery(sql, new String[]{ pass});
        boolean kq = cursor.getCount() > 0;
        cursor.close();
        return kq;
    }
    public boolean updatePass(String mail,String pass){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD,pass);
        long kq = database.update(TABLE_NAME,values,"email = ?",new String[]{mail});
        return kq != -1;
    }
}
