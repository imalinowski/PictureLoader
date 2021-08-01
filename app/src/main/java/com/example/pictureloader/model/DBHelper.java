package com.example.pictureloader.model;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "IMAGE_CACHE";
    private static final String KEY_ID = "id";
    private static final String KEY_URL = "url";
    private static final String KEY_AGE = "age";
    private static final String KEY_PATH = "path";
    private final Context context;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEMS_TABLE = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s INTEGER, %s PATH)",
                DB_NAME, KEY_ID, KEY_URL, KEY_AGE, KEY_PATH);
        Log.d("RASPBERRY", CREATE_ITEMS_TABLE);
        db.execSQL(CREATE_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion == 1) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
            onCreate(db);
        }
    }

    public Bitmap request(String url) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_NAME,
                new String[]{KEY_ID, KEY_URL, KEY_AGE, KEY_PATH},
                KEY_URL + "= ?",
                new String[]{url},
                null, null, null);
        if (cursor == null || !cursor.moveToFirst()) {
            return null;
        }
        String path = cursor.getString(3);
        update(url, cursor.getInt(2) + 1);
        Log.i("RASPBERRY", "DB loaded > " + cursor.getInt(2) + " " + path);
        cursor.close();
        db.close();
        return BitmapFactory.decodeFile(path);
    }

    private int update(String url, int age) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_AGE, Math.max(20,age));

        return db.update(DB_NAME, values, KEY_URL + " = ?", new String[]{url});
    }

    public void createItem(String url, Bitmap bitmap) {
        String path;
        try {
            path = saveToInternalStorage(url, bitmap);
        } catch (IOException e) {
            Log.e("RASPBERRY", "save to storage> " + e.getMessage());
            return;
        }
        Log.i("RASPBERRY", "new item in " + path);
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_URL, url);
        values.put(KEY_AGE, 3);
        values.put(KEY_PATH, path);

        long row = db.insertOrThrow(DB_NAME, null, values);
        db.close();

        if (row == -1)
            Log.e("RASPBERRY", "DB add " + url);
        else
            Log.i("RASPBERRY", "DB add " + url);
    }

    private String saveToInternalStorage(String name, Bitmap bitmapImage) throws NullPointerException, IOException {
        ContextWrapper cw = new ContextWrapper(context.getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File file = new File(directory, name.replaceAll("/", "") + ".png");

        try (FileOutputStream fos = new FileOutputStream(file)) {
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        }
        return file.getPath();
    }

    public void clear() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(DB_NAME, new String[]{KEY_URL, KEY_AGE},
                null, null, null, null, null);
        if (cursor == null || !cursor.moveToFirst()) {
            return;
        }
        while (cursor.moveToNext())
            update(cursor.getString(0), cursor.getInt(1) - 1);
        cursor.close();
        Log.i("RASPBERRY","cache > " +
                db.delete(DB_NAME, KEY_AGE + " = ?", new String[]{"0"})
                        + " rows > deleted as unused <");
        db.close();
    }
}