package fpoly.group6_pro1122.kidsshop.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Db_helper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "kidsShop.db";
    public static final int DATABASE_VERSION = 1;

    public Db_helper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CreateTableCategory =
                "CREATE TABLE Category(" +
                        "category_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT NOT NULL," +
                        "describe TEXT NOT NULL," +
                        "image TEXT NOT NULL)";
        sqLiteDatabase.execSQL(CreateTableCategory);
        String CreateTableProduct =
                "CREATE TABLE Product(" +
                        "product_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "category_id INTEGER NOT NULL," +
                        "product_name TEXT NOT NULL," +
                        "product_price INTEGER NOT NULL," +
                        "quantity INTEGER NOT NULL," +
                        "describe TEXT NOT NULL," +
                        "image TEXT NOT NULL," +
                        "FOREIGN KEY(category_id) REFERENCES Category(category_id))";
        sqLiteDatabase.execSQL(CreateTableProduct);
        String CreateTableUser =
                "CREATE TABLE User(" +
                        "email TEXT PRIMARY KEY," +
                        "password TEXT NOT NULL," +
                        "fullName TEXT," +
                        "image TEXT," +
                        "phoneNumber TEXT," +
                        "role INTEGER NOT NULL)";
        sqLiteDatabase.execSQL(CreateTableUser);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
