package com.datj.mobile.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CartDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "CartDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CART = "cart";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PRODUCT_ID = "product_id";
    private static final String COLUMN_QUANTITY = "quantity";

    public CartDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_PRODUCT_ID + " INTEGER,"
                + COLUMN_QUANTITY + " INTEGER" + ")";
        db.execSQL(CREATE_CART_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(db);
    }

    // Thêm sản phẩm vào giỏ hàng
    public void addToCart(int productId, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_ID, productId);
        values.put(COLUMN_QUANTITY, quantity);
        db.insert(TABLE_CART, null, values);
        db.close();
    }

    // Lấy tổng số lượng mặt hàng trong giỏ hàng
    public int getCartCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_QUANTITY + ") as total FROM " + TABLE_CART, null);
        int count = 0;
        if (cursor.moveToFirst()) {
            count = cursor.getInt(cursor.getColumnIndexOrThrow("total"));
        }
        cursor.close();
        db.close();
        return count;
    }

    // Lấy danh sách sản phẩm trong giỏ hàng để đồng bộ
    public Cursor getCartItems() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_CART, null);
    }

    // Xóa giỏ hàng sau khi đặt hàng thành công
    public void clearCart() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CART);
        db.close();
    }
}