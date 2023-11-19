package fpoly.group6_pro1122.kidsshop.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Db_Helper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "kidsShop.db";
    public static final int DATABASE_VERSION = 1;

    public Db_Helper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CreateTableCategory =
                "CREATE TABLE IF NOT EXISTS Category(" +
                        "category_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT NOT NULL," +
                        "description TEXT NOT NULL," +
                        "image TEXT NOT NULL)";
        sqLiteDatabase.execSQL(CreateTableCategory);

        String CreateTableProduct =
                "CREATE TABLE IF NOT EXISTS Product(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "category_id INTEGER NOT NULL," +
                        "product_name TEXT NOT NULL," +
                        "product_price INTEGER NOT NULL," +
                        "quantity INTEGER NOT NULL," +
                        "description TEXT NOT NULL," +
                        "image TEXT NOT NULL," +
                        "FOREIGN KEY(category_id) REFERENCES Category(category_id))";
        sqLiteDatabase.execSQL(CreateTableProduct);

        String CreateTableUser =
                "CREATE TABLE IF NOT EXISTS User(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "email TEXT NOT NULL," +
                        "password TEXT NOT NULL," +
                        "fullName TEXT," +
                        "image TEXT," +
                        "phoneNumber TEXT," +
                        "address TEXT," +
                        "role INTEGER NOT NULL)";
        sqLiteDatabase.execSQL(CreateTableUser);

        String insertDefaultAdmin = "INSERT OR IGNORE INTO User (id, email, password, role) VALUES (1, 'quynhlm.dev@gmail.com', '123', 0)";
        sqLiteDatabase.execSQL(insertDefaultAdmin);

        String insertDefaultCustomer = "INSERT OR IGNORE INTO User (id, email, password, role) VALUES (2, 'chinhtd.dev@gmail.com', '123', 1)";
        sqLiteDatabase.execSQL(insertDefaultCustomer);

        String CreateTableCartItem = "CREATE TABLE IF NOT EXISTS CartItem(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "product_id INTEGER NOT NULL," +
                "user_id INTEGER NOT NULL," +
                "quantity INTEGER NOT NULL," +
                "FOREIGN KEY(product_id) REFERENCES Product(id)," +
                "FOREIGN KEY(user_id) REFERENCES User(id))";
        sqLiteDatabase.execSQL(CreateTableCartItem);

        String CreateTableEvaluation = "CREATE TABLE IF NOT EXISTS Evaluation(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "comment TEXT NOT NULL," +
                "date TEXT NOT NULL," +
                "product_id INTEGER NOT NULL," +
                "user_id INTEGER NOT NULL," +
                "FOREIGN KEY(product_id) REFERENCES Product(id)," +
                "FOREIGN KEY(user_id) REFERENCES User(id))";
        sqLiteDatabase.execSQL(CreateTableEvaluation);

        String CreateTableShipment = "CREATE TABLE IF NOT EXISTS Shipment(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "date TEXT NOT NULL," +
                "address TEXT NOT NULL," +
                "status INTEGER NOT NULL," +
                "user_id INTEGER NOT NULL," +
                "FOREIGN KEY(user_id) REFERENCES User(id))";
        sqLiteDatabase.execSQL(CreateTableShipment);

        String CreateTablePayment = "CREATE TABLE IF NOT EXISTS Payment(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "date TEXT NOT NULL," +
                "type INTEGER NOT NULL," +
                "quantity INTEGER NOT NULL," +
                "user_id INTEGER NOT NULL," +
                "FOREIGN KEY(user_id) REFERENCES User(id))";
        sqLiteDatabase.execSQL(CreateTablePayment);

        String CreateTableOrders = "CREATE TABLE IF NOT EXISTS Orders(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "date TEXT NOT NULL," +
                "total_price INTEGER NOT NULL," +
                "user_id INTEGER NOT NULL," +
                "payment_id INTEGER NOT NULL," +
                "shipment_id INTEGER NOT NULL," +
                "FOREIGN KEY(user_id) REFERENCES User(id)," +
                "FOREIGN KEY(payment_id) REFERENCES Payment(id)," +
                "FOREIGN KEY(shipment_id) REFERENCES Shipment(id))";
        sqLiteDatabase.execSQL(CreateTableOrders);

        String CreateTableOrderItem = "CREATE TABLE IF NOT EXISTS OrderItem(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "quantity INTEGER NOT NULL," +
                "price INTEGER NOT NULL," +
                "product_id INTEGER NOT NULL," +
                "order_id INTEGER NOT NULL," +
                "FOREIGN KEY(product_id) REFERENCES Product(id)," +
                "FOREIGN KEY(order_id) REFERENCES Orders(id))";
        sqLiteDatabase.execSQL(CreateTableOrderItem);

        String CreateTableWishList = "CREATE TABLE IF NOT EXISTS Wishlist(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER NOT NULL," +
                "product_id INTEGER NOT NULL," +
                "FOREIGN KEY(product_id) REFERENCES Product(id)," +
                "FOREIGN KEY(user_id) REFERENCES User(id))";
        sqLiteDatabase.execSQL(CreateTableWishList);

        String CreateTableVoucher = "CREATE TABLE IF NOT EXISTS Voucher(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "code TEXT NOT NULL," +
                "discount_amount FLOAT NOT NULL," +
                "start_date TEXT NOT NULL," +
                "expiration_date TEXT NOT NULL," +
                "user_id INTEGER," +
                "order_id INTEGER," +
                "FOREIGN KEY(order_id) REFERENCES Orders(id),"+
                "FOREIGN KEY(user_id) REFERENCES User(id))";

        sqLiteDatabase.execSQL(CreateTableVoucher);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion < newVersion) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Category");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Product");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS User");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS CartItem");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Evaluation");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Shipment");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Payment");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Orders");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS OrderItem");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Wishlist");
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Voucher");
            onCreate(sqLiteDatabase);
        }
    }
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
}
