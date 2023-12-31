package fpoly.group6_pro1122.kidsshop.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

import fpoly.group6_pro1122.kidsshop.Database.Db_Helper;
import fpoly.group6_pro1122.kidsshop.Model.Product;

public class ProductDao {
    Db_Helper dbHelper;
    private final static String TABLE_NAME = "Product";
    private final static String COLUMN_ID = "id";
    private final static String COLUMN_NAME = "product_name";
    private final static String COLUMN_PRICE = "product_price";
    private final static String COLUMN_CATEGORY_ID = "category_id";
    private final static String COLUMN_TAG_ID = "tag_id";
    private final static String COLUMN_QUANTITY = "quantity";
    private final static String COLUMN_DESCRIBE = "description";
    private final static String COLUMN_IMAGE = "image";

    public ProductDao(Context context) {
        dbHelper = new Db_Helper(context);
    }

    public boolean insertData(Product product) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CATEGORY_ID, product.getCategory_id());
        contentValues.put(COLUMN_TAG_ID, product.getTag_id());
        contentValues.put(COLUMN_NAME, product.getProduct_name());
        contentValues.put(COLUMN_PRICE, product.getProduct_price());
        contentValues.put(COLUMN_QUANTITY, product.getQuantity());
        contentValues.put(COLUMN_DESCRIBE, product.getDescribe());
        contentValues.put(COLUMN_IMAGE,product.getImage());
        long check = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        product.setProduct_id((int) check);
        return check != -1;
    }
    public boolean deleteData(Product product) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(product.getProduct_id())};
        long check = sqLiteDatabase.delete(TABLE_NAME, COLUMN_ID + "=?", dk);
        return check != -1;
    }

    public boolean updateData(Product product) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String dk[] = {String.valueOf(product.getProduct_id())};
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CATEGORY_ID, product.getCategory_id());
        contentValues.put(COLUMN_TAG_ID, product.getTag_id());
        contentValues.put(COLUMN_NAME, product.getProduct_name());
        contentValues.put(COLUMN_PRICE, product.getProduct_price());
        contentValues.put(COLUMN_QUANTITY, product.getQuantity());
        contentValues.put(COLUMN_DESCRIBE, product.getDescribe());
        contentValues.put(COLUMN_IMAGE,product.getImage());
        long check = sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_ID + "=?", dk);
        return check != -1;
    }

    public ArrayList<Product> getAll(String sql, String... selectionArgs) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ArrayList<Product> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                int price = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRICE)));
                int quantity = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY)));
                String describe = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIBE));
                int category_id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY_ID)));
                String image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
                int tag_id = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TAG_ID)));
// public Product(int product_id, String product_name, int product_price, int quantity, String image, String describe, int category_id) {
                list.add(new Product(id,name,price,quantity,image,describe,category_id,tag_id));
            }
        }
        return list;
    }

    public ArrayList<Product> SelectAll() {
        String query = "SELECT * FROM " + TABLE_NAME;
        return getAll(query);
    }
    public ArrayList<Product> SelectAllNew(int number) {
        String dk [] = {String.valueOf(number)};
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE tag_id = ?";
        return getAll(query,dk);
    }

    public Product SelectID(String id) {
        String query = "SELECT * FROM Product WHERE id = ?";
        ArrayList<Product> list = getAll(query, id);
        return list.get(0);
    }
    public ArrayList<Product> findID(String id){
        String sql = "SELECT * FROM Product WHERE category_id = ?";
        ArrayList<Product> list = getAll(sql,id);
        return list;
    }
}
