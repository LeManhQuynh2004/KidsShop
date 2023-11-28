package fpoly.group6_pro1122.kidsshop.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Database.Db_Helper;
import fpoly.group6_pro1122.kidsshop.Model.Product;
import fpoly.group6_pro1122.kidsshop.Model.Shipment;

public class ShipmentDao {
    Db_Helper dbHelper;

//      "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//              "date TEXT NOT NULL," +
//              "city TEXT NOT NULL," +
//              "district TEXT NOT NULL," +
//              "address TEXT NOT NULL," +
//              "address_type INTEGER NOT NULL," +
//              "status INTEGER NOT NULL," +
//              "user_id INTEGER NOT NULL," +

    private final static String TABLE_NAME = "Shipment";
    private final static String COLUMN_ID = "id";
    private final static String COLUMN_NAME = "name";
    private final static String COLUMN_PHONE = "phone";
    private final static String COLUMN_DATE = "date";
    private final static String COLUMN_CITY = "city";
    private final static String COLUMN_DISTRICT = "district";
    private final static String COLUMN_ADDRESS = "address";
    private final static String COLUMN_ADDRESS_TYPE = "address_type";
    private final static String COLUMN_STATUS = "status";
    private final static String COLUMN_USER_ID = "user_id";

    public ShipmentDao(Context context) {
        dbHelper = new Db_Helper(context);
    }

    public boolean insertData(Shipment shipment) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, shipment.getName());
        contentValues.put(COLUMN_PHONE, shipment.getPhone());
        contentValues.put(COLUMN_DATE, shipment.getDate());
        contentValues.put(COLUMN_CITY, shipment.getCity());
        contentValues.put(COLUMN_DISTRICT, shipment.getDistrict());
        contentValues.put(COLUMN_ADDRESS, shipment.getAddress());
        contentValues.put(COLUMN_ADDRESS_TYPE, shipment.getAddress_type());
        contentValues.put(COLUMN_STATUS, shipment.getStatus());
        contentValues.put(COLUMN_USER_ID, shipment.getUser_id());
        long check = sqLiteDatabase.insert(TABLE_NAME, "", contentValues);
        shipment.setId((int) check);
        return check != -1;
    }

    public boolean deleteData(Shipment shipment) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(shipment.getId())};
        long check = sqLiteDatabase.delete(TABLE_NAME, COLUMN_ID + "=?", dk);
        return check != -1;
    }

    public boolean updateData(Shipment shipment) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(shipment.getId())};
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, shipment.getName());
        contentValues.put(COLUMN_PHONE, shipment.getPhone());
        contentValues.put(COLUMN_DATE, shipment.getDate());
        contentValues.put(COLUMN_CITY, shipment.getCity());
        contentValues.put(COLUMN_DISTRICT, shipment.getDistrict());
        contentValues.put(COLUMN_ADDRESS, shipment.getAddress());
        contentValues.put(COLUMN_ADDRESS_TYPE, shipment.getAddress_type());
        contentValues.put(COLUMN_STATUS, shipment.getStatus());
        contentValues.put(COLUMN_USER_ID, shipment.getUser_id());
        long check = sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_ID + "=?", dk);
        return check != -1;
    }

    public ArrayList<Shipment> getAll(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<Shipment> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONE));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE));
                String city = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CITY));
                String district = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DISTRICT));
                String address = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS));
                int address_type = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ADDRESS_TYPE)));
                int status = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS)));
                int user_id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)));
//              public Shipment(int id, String date, String city, String district, String address, int status, int address_type, int user_id) {
                list.add(new Shipment(id,name,phone, date, city, district, address, status, address_type, user_id));
            }
        }
        return list;
    }

    public ArrayList<Shipment> SelectAll() {
        String query = "SELECT * FROM " + TABLE_NAME;
        return getAll(query);
    }
    public ArrayList<Shipment> Select_Shipment(int id) {
        String query = "SELECT * FROM Shipment WHERE id = ?";
        return getAll(query, String.valueOf(id));
    }
    public Shipment SelectID(String id) {
        String query = "SELECT * FROM Shipment WHERE id = ?";
        ArrayList<Shipment> list = getAll(query, id);
        if(!list.isEmpty()){
            return list.get(0);
        }else{
            return null;
        }
    }
}
