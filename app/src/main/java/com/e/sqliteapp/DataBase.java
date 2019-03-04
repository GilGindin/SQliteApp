package com.e.sqliteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBase extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "ProductsDatabaseDB";
    public static final String TABLE_NAME = "products";
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_PRICE = "price";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        try {
            String CREATE_TABLE_PRODUCTS = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT NOT NULL ," + KEY_PRICE + "  REAL DEFAULT 0" + ")";
            db.execSQL(CREATE_TABLE_PRODUCTS);
        } catch (Exception er) {
            Log.e("ERROR: ", String.valueOf(er));
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addProduct(Product product) {

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, product.getName());
        values.put(KEY_PRICE, product.getPrice());

        SQLiteDatabase db = this.getWritableDatabase();
        int id = (int) db.insert(TABLE_NAME, null, values);
        product.setId(id);
        close();
    }

    Product getProduct(int _id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = (Cursor) db.query(TABLE_NAME, new String[]{KEY_ID, KEY_NAME, KEY_PRICE}, KEY_ID + "=?",
                new String[]{String.valueOf(_id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        Product product = new Product(Integer.parseInt(String.valueOf(cursor.getInt(0))), cursor.getString(1), cursor.getDouble(2));
        return product;
    }

    public Cursor getProductsCursor() {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(selectQuery, null);
    }

    public String getProductName(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT " + KEY_NAME + " FROM " + TABLE_NAME + " WHERE _id= " + id;
        Cursor cursor = (Cursor) db.rawQuery(select, null);
        int nameIndex = cursor.getColumnIndex(KEY_NAME);

        cursor.moveToNext();
        String name = cursor.getString(nameIndex);

        cursor.close();
        close();
        return name;
    }

    public Product getOneProduct(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String select = "SELECT * FROM " + TABLE_NAME + " WHERE _id= " + id;
        Cursor cursor = (Cursor) db.rawQuery(select, null);
        int nameIndex = cursor.getColumnIndex(KEY_NAME);
        int priceIndex = cursor.getColumnIndex(KEY_PRICE);

        cursor.moveToNext();
        String name = cursor.getString(nameIndex);
        double price = cursor.getDouble(priceIndex);

        Product product = new Product(id, name, price);
        cursor.close();
        close();
        return product;
    }

    public int updateProduct(Product product) {

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, product.getName());
        values.put(KEY_PRICE, product.getPrice());

        SQLiteDatabase db = this.getWritableDatabase();
        return db.update(TABLE_NAME, values, KEY_ID + "=?", new String[]{String.valueOf(product.getId())});
    }

    public int deleteProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        return (int) db.delete(TABLE_NAME, KEY_ID + "=?", new String[]{String.valueOf(product.getId())});
    }

    public void deleteProduct(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + "=?", new String[]{String.valueOf(id)});
        close();
    }

}
