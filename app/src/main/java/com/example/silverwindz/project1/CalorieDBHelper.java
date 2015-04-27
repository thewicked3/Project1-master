package com.example.silverwindz.project1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CalorieDBHelper extends SQLiteOpenHelper {

    private static final String name = "caloriess.sqlite3";
    private static final int version = 2;


    public CalorieDBHelper(Context ctx) {
        super(ctx, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE caloriess (" +
                "_id integer primary key autoincrement," +
                "gender text not null," +             // course code
                "height double default 0," +           // credit
                "weight double default 0," +            // letter grade e.g. A, B+
                "age int default 0," +
                "bmr double default 0);";
        db.execSQL(sql);

        String sql2 = "CREATE TABLE calories2 (" +
                     "_id integer primary key autoincrement," +
                    "exercise text not null," +
                    "caloburn double default 0);";
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS caloriess;";
        db.execSQL(sql);
        this.onCreate(db);
    }
}
