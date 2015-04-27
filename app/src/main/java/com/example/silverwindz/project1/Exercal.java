package com.example.silverwindz.project1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class Exercal extends ActionBarActivity {
    CalorieDBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_cal);


        helper = new CalorieDBHelper(this);

        SQLiteDatabase dbr = helper.getReadableDatabase();
        Cursor cursor = dbr.rawQuery("SELECT _id,bmr FROM caloriess ORDER BY _id DESC;", null);
        cursor.moveToFirst();

        Double bmr = cursor.getDouble(1);

        TextView bmr1 = (TextView)findViewById(R.id.bmrin);
        bmr1.setText(String.format("%.2f",bmr));

    }

    /*protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 99) {
            if (resultCode == RESULT_OK) {

                SQLiteDatabase dbr = helper.getReadableDatabase();
                Cursor cursor = dbr.rawQuery("SELECT bmr FROM caloriess;", null);
                cursor.moveToFirst();

                Double bmr = cursor.getDouble(0);
                Log.d("caloriess", bmr.toString(bmr));
                TextView bmr1 = (TextView)findViewById(R.id.bmrin);
                bmr1.setText(String.format("%.2f",bmr));

               //long new_id = db.insert("caloriess", null, r);

            }

       Log.d("caloriess", "onActivityResult");
    }*/



    public void buttonCaloClicked (View v)
    {
        SQLiteDatabase dbr = helper.getReadableDatabase();
        Cursor cursor = dbr.rawQuery("SELECT _id,bmr FROM caloriess ORDER BY _id DESC;", null);
        cursor.moveToFirst();

        Double bmr = cursor.getDouble(1);

        double cal1 = 0;
        double burn = 0;

        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues r = new ContentValues();



        EditText dura = (EditText)findViewById(R.id.duration);
        String t = dura.getText().toString();
        Double time1 = Double.parseDouble(t);

        RadioGroup exerlist = (RadioGroup)findViewById(R.id.exerlists);
        RadioButton exl = (RadioButton) findViewById(exerlist.getCheckedRadioButtonId());
        int selex = exerlist.getCheckedRadioButtonId();



            if (selex == R.id.rel) { //
                cal1 = ((bmr / 24) * 1.54);
                burn = cal1 * (time1 / 60);
                r.put("exercise", "Relaxing");
                r.put("caloburn", burn);
                long new_id = db.insert("calories2", null, r);
            } else if (selex == R.id.foot)//
            {
                cal1 = ((bmr / 24) * 8.00);
                burn = cal1 * (time1 / 60);
                r.put("exercise", "Playing Football");
                r.put("caloburn", burn);
                long new_id = db.insert("calories2", null, r);
            } else if (selex == R.id.run)//
            {
                cal1 = ((bmr / 24) * 7.50);
                burn = cal1 * (time1 / 60);
                r.put("exercise", "Running");
                r.put("caloburn", burn);
                long new_id = db.insert("calories2", null, r);
            } else if (selex == R.id.walk)//
            {
                cal1 = ((bmr / 24) * 3.80);
                burn = cal1 * (time1 / 60);
                r.put("exercise", "Walking");
                r.put("caloburn", burn);
                long new_id = db.insert("calories2", null, r);
            } else if (selex == R.id.lift)//
            {
                cal1 = ((bmr / 24) * 3.00);
                burn = cal1 * (time1 / 60);
                r.put("exercise", "Weight lifting");
                r.put("caloburn", burn);
                long new_id = db.insert("calories2", null, r);
            } else if (selex == R.id.mrt)//
            {
                cal1 = ((bmr / 24) * 10.00);
                burn = cal1 * (time1 / 60);
                r.put("exercise", "Martial Arts");
                r.put("caloburn", burn);
                long new_id = db.insert("calories2", null, r);
            } else {
                cal1 = ((bmr / 24) * 2.50);
                burn = cal1 * (time1 / 60);
                r.put("exercise", "Education");
                r.put("caloburn", burn);
                long new_id = db.insert("calories2", null, r);
            }


        TextView calburn = (TextView)findViewById(R.id.calout);
        calburn.setText(String.format("%.2f",burn));



    }

    public void buttonToListClicked(View v) {
        int id = v.getId();
        Intent i;

        switch(id) {
            case R.id.atl:
                i = new Intent(this, exercise_list.class);
                startActivity(i);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_exercal, menu);
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
}
