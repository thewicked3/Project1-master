package com.example.silverwindz.project1;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class exercise_list extends ActionBarActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    CalorieDBHelper helper;
    SimpleCursorAdapter adapter;
    SimpleCursorAdapter adapter1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);

        helper = new CalorieDBHelper(this);

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT _id,(gender || '/' || 'Height: ' || height || '/' || 'Weight: ' || weight) AS gg, ('Age: ' || age || '/' || 'BMR: ' || bmr) AS cbmr FROM caloriess " +
                        "ORDER BY _id ASC;",null);

        adapter = new SimpleCursorAdapter(this,
                R.layout.mylistitem,
                cursor,
                new String[] {"gg", "cbmr"},
                new int[] {R.id.text1, R.id.text2},0);

        ListView lv = (ListView)findViewById(R.id.listView2);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);


        SQLiteDatabase db2 = helper.getReadableDatabase();
        Cursor cursor2 = db2.rawQuery("SELECT _id, exercise, ('Calorie-Burn: ' || ROUND(caloburn,2)) AS cal FROM calories2 " +
                "ORDER BY _id ASC;",null);

        adapter1 = new SimpleCursorAdapter(this,
                R.layout.mylistitem,
                cursor2,
                new String[] {"exercise", "cal"},
                new int[] {R.id.text1, R.id.text2},0);

        ListView lv2 = (ListView)findViewById(R.id.listView);
        lv2.setAdapter(adapter1);
        lv2.setOnItemClickListener(this);
        lv2.setOnItemLongClickListener(this);

    }

    public void buttonBackClicked(View v) {
        int id = v.getId();
        Intent i;

        switch(id) {
            case R.id.toMain:
                i = new Intent(this, MainActivity.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exercise_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view,
                           int position, long id) {
       Log.d("caloriess", id + " is clicked");

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view,
                                int position, long id) {
        SQLiteDatabase db = helper.getWritableDatabase();

        int n = db.delete("caloriess",
                "_id = ?",
                new String[]{Long.toString(id)});

        if (n == 1) {
            db.delete("calories2",
                    "_id = ?",
                    new String[]{Long.toString(id)});
            Toast t = Toast.makeText(this,
                    "Successfully deleted the selected item.",
                    Toast.LENGTH_SHORT);
            t.show();
            Cursor cursor2 = db.rawQuery("SELECT _id, exercise, ('Calorie-Burn: ' || ROUND(caloburn,2)) AS cal FROM calories2 " +
                    "ORDER BY _id ASC;",null);

            // retrieve a new collection of records
            Cursor cursor = db.rawQuery("SELECT _id,(gender || '/' || 'Height: ' || height || '/' || 'Weight: ' || weight) AS gg, ('Age: ' || age || '/' || 'BMR: ' || bmr) AS cbmr FROM caloriess " +
                    "ORDER BY _id ASC;",null);

            // update the adapter
            adapter1.changeCursor(cursor2);
            adapter.changeCursor(cursor);
        }

        int n2 = db.delete("calories2",
                "_id = ?",
                new String[]{Long.toString(id)});
        if (n2 == 1) {
            db.delete("caloriess",
                    "_id = ?",
                    new String[]{Long.toString(id)});
            Toast t = Toast.makeText(this,
                    "Successfully deleted the selected item.",
                    Toast.LENGTH_SHORT);
            t.show();
            Cursor cursor2 = db.rawQuery("SELECT _id, exercise, ('Calorie-Burn: ' || ROUND(caloburn,2)) AS cal FROM calories2 " +
                    "ORDER BY _id ASC;",null);

            Cursor cursor = db.rawQuery("SELECT _id,(gender || '/' || 'Height: ' || height || '/' || 'Weight: ' || weight) AS gg, ('Age: ' || age || '/' || 'BMR: ' || bmr) AS cbmr FROM caloriess " +
                    "ORDER BY _id ASC;",null);


            adapter1.changeCursor(cursor2);
            adapter.changeCursor(cursor);

        }
        db.close();
        return true;
    }
}
