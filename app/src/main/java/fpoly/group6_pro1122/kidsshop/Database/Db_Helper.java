package fpoly.group6_pro1122.kidsshop.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Db_Helper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "kidsShop.1db";
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
        String insertDefaultCategory = "INSERT OR IGNORE INTO Category(" +
                "category_id,name,description,image) " +
                "VALUES (1,'category 1','ngon bo re','https://www.google.com/url?sa=i&url=https%3A%2F%2Fbansidotreem.com%2Fbo-18-mon-quan-ao-so-sinh-phong-cach-cho-tre-0-3-thang-mua-he-cho-tre-so-sinh-226.html&psig=AOvVaw2zth2mbRG8P7J8wWHsQBaa&ust=1700646894678000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCJCdgMTp1IIDFQAAAAAdAAAAABAD')";
        sqLiteDatabase.execSQL(insertDefaultCategory);

        String CreateTableProduct =
                "CREATE TABLE IF NOT EXISTS Product(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "product_name TEXT NOT NULL," +
                        "product_price INTEGER NOT NULL," +
                        "quantity INTEGER NOT NULL," +
                        "description TEXT NOT NULL," +
                        "image TEXT NOT NULL," +
                        "category_id INTEGER NOT NULL," +
                        "tag_id INTEGER," +
                        "FOREIGN KEY(category_id) REFERENCES Category(category_id)," +
                        "FOREIGN KEY(tag_id) REFERENCES Tag(id))";
        sqLiteDatabase.execSQL(CreateTableProduct);
        String insertDefaultProduct = "INSERT OR IGNORE INTO Product(" +
                "id,product_name,product_price,quantity,description,image,category_id,tag_id) " +
                "VALUES (1,'product 1',2000,2000,'ngon bo re','https://www.google.com/url?sa=i&url=https%3A%2F%2Fbansidotreem.com%2Fbo-18-mon-quan-ao-so-sinh-phong-cach-cho-tre-0-3-thang-mua-he-cho-tre-so-sinh-226.html&psig=AOvVaw2zth2mbRG8P7J8wWHsQBaa&ust=1700646894678000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCJCdgMTp1IIDFQAAAAAdAAAAABAD'," +
                "1,1)";
        String insertDefaultProduct2 = "INSERT OR IGNORE INTO Product(" +
                "id,product_name,product_price,quantity,description,image,category_id,tag_id) " +
                "VALUES (2,'product 2',3000,2000,'ngon bo re','https://www.google.com/url?sa=i&url=https%3A%2F%2Fbansidotreem.com%2Fbo-18-mon-quan-ao-so-sinh-phong-cach-cho-tre-0-3-thang-mua-he-cho-tre-so-sinh-226.html&psig=AOvVaw2zth2mbRG8P7J8wWHsQBaa&ust=1700646894678000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCJCdgMTp1IIDFQAAAAAdAAAAABAD'," +
                "1,2)";
        String insertDefaultProduct3 = "INSERT OR IGNORE INTO Product(" +
                "id,product_name,product_price,quantity,description,image,category_id,tag_id) " +
                "VALUES (3,'product 3',4000,2000,'ngon bo re','https://www.google.com/url?sa=i&url=https%3A%2F%2Fbansidotreem.com%2Fbo-18-mon-quan-ao-so-sinh-phong-cach-cho-tre-0-3-thang-mua-he-cho-tre-so-sinh-226.html&psig=AOvVaw2zth2mbRG8P7J8wWHsQBaa&ust=1700646894678000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCJCdgMTp1IIDFQAAAAAdAAAAABAD'," +
                "1,2)";
        String insertDefaultProduct4 = "INSERT OR IGNORE INTO Product(" +
                "id,product_name,product_price,quantity,description,image,category_id,tag_id) " +
                "VALUES (4,'product 4',5000,2000,'ngon bo re','https://www.google.com/url?sa=i&url=https%3A%2F%2Fbansidotreem.com%2Fbo-18-mon-quan-ao-so-sinh-phong-cach-cho-tre-0-3-thang-mua-he-cho-tre-so-sinh-226.html&psig=AOvVaw2zth2mbRG8P7J8wWHsQBaa&ust=1700646894678000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCJCdgMTp1IIDFQAAAAAdAAAAABAD'," +
                "1,2)";
        String insertDefaultProduct5 = "INSERT OR IGNORE INTO Product(" +
                "id,product_name,product_price,quantity,description,image,category_id,tag_id) " +
                "VALUES (5,'product 5',6000,2000,'ngon bo re','https://www.google.com/url?sa=i&url=https%3A%2F%2Fbansidotreem.com%2Fbo-18-mon-quan-ao-so-sinh-phong-cach-cho-tre-0-3-thang-mua-he-cho-tre-so-sinh-226.html&psig=AOvVaw2zth2mbRG8P7J8wWHsQBaa&ust=1700646894678000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCJCdgMTp1IIDFQAAAAAdAAAAABAD'," +
                "1,1)";
        sqLiteDatabase.execSQL(insertDefaultProduct);
        sqLiteDatabase.execSQL(insertDefaultProduct2);
        sqLiteDatabase.execSQL(insertDefaultProduct3);
        sqLiteDatabase.execSQL(insertDefaultProduct4);
        sqLiteDatabase.execSQL(insertDefaultProduct5);
        String CreateTableTag =
                "CREATE TABLE IF NOT EXISTS Tag(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "tag_name TEXT NOT NULL)";
        sqLiteDatabase.execSQL(CreateTableTag);

        String insertDefaultTag = "INSERT OR IGNORE INTO Tag (id,tag_name) VALUES (2,'Ưu đãi khủng')";
        String insertDefaultTag2 = "INSERT OR IGNORE INTO Tag (id,tag_name) VALUES (1,'Sản phẩm mới')";
        String insertDefaultTag3 = "INSERT OR IGNORE INTO Tag (id,tag_name) VALUES (3,'Đại giảm giá')";
        String insertDefaultTag4 = "INSERT OR IGNORE INTO Tag (id,tag_name) VALUES (4,'Sản phẩm tốt')";
        sqLiteDatabase.execSQL(insertDefaultTag);
        sqLiteDatabase.execSQL(insertDefaultTag2);
        sqLiteDatabase.execSQL(insertDefaultTag3);
        sqLiteDatabase.execSQL(insertDefaultTag4);

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
                "total_price INTEGER NOT NULL," +
                "status INTEGER NOT NULL," +
                "FOREIGN KEY(product_id) REFERENCES Product(id)," +
                "FOREIGN KEY(user_id) REFERENCES User(id))";
        sqLiteDatabase.execSQL(CreateTableCartItem);

        String CreateTableEvaluation = "CREATE TABLE IF NOT EXISTS Evaluation(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "comment TEXT NOT NULL," +
                "date TEXT NOT NULL," +
                "time TEXT NOT NULL," +
                "product_id INTEGER NOT NULL," +
                "user_id INTEGER NOT NULL," +
                "start INTEGER NOT NULL," +
                "FOREIGN KEY(product_id) REFERENCES Product(id)," +
                "FOREIGN KEY(user_id) REFERENCES User(id))";
        sqLiteDatabase.execSQL(CreateTableEvaluation);

        String CreateTableShipment = "CREATE TABLE IF NOT EXISTS Shipment(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "phone TEXT NOT NULL," +
                "date TEXT NOT NULL," +
                "city TEXT NOT NULL," +
                "district TEXT NOT NULL," +
                "address TEXT NOT NULL," +
                "address_type INTEGER NOT NULL," +
                "status INTEGER NOT NULL," +
                "user_id INTEGER NOT NULL," +
                "FOREIGN KEY(user_id) REFERENCES User(id))";
        sqLiteDatabase.execSQL(CreateTableShipment);

        String CreateTablePayment = "CREATE TABLE IF NOT EXISTS Payment(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "type INTEGER NOT NULL," +
                "user_id INTEGER," +
                "FOREIGN KEY(user_id) REFERENCES User(id))";
        sqLiteDatabase.execSQL(CreateTablePayment);
        String insertDefaultPayment = "INSERT OR IGNORE INTO Payment (id, type, user_id) VALUES (1, 'Thanh toán khi nhận hàng', 0)";
        sqLiteDatabase.execSQL(insertDefaultPayment);

        String insertDefaultPayment2 = "INSERT OR IGNORE INTO Payment (id, type, user_id) VALUES (2, 'Thanh toán thẻ tín dụng', 0)";
        sqLiteDatabase.execSQL(insertDefaultPayment2);

        String CreateTableOrders = "CREATE TABLE IF NOT EXISTS Orders(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "date TEXT NOT NULL," +
                "total_price INTEGER NOT NULL," +
                "status INTEGER NOT NULL," +
                "user_id INTEGER NOT NULL," +
                "payment_id INTEGER NOT NULL," +
                "shipment_id INTEGER NOT NULL," +
                "time TEXT NOT NULL," +
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
                "quantity INTEGER NOT NULL," +
                "user_id INTEGER NOT NULL," +
                "product_id INTEGER NOT NULL," +
                "status INTEGER NOT NULL," +
                "FOREIGN KEY(product_id) REFERENCES Product(id)," +
                "FOREIGN KEY(user_id) REFERENCES User(id))";
        sqLiteDatabase.execSQL(CreateTableWishList);

        String CreateTableVoucher = "CREATE TABLE IF NOT EXISTS Voucher(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "code TEXT NOT NULL," +
                "discount_amount INTEGER NOT NULL," +
                "start_date TEXT NOT NULL," +
                "expiration_date TEXT NOT NULL," +
                "user_id INTEGER," +
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
