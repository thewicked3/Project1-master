package com.example.silverwindz.project1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class BMRcal extends ActionBarActivity {
    CalorieDBHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmr_cal);

        helper = new CalorieDBHelper(this);
    }
    public void buttonlinkClicked(View v) {
        int id = v.getId();
        Intent i;

        switch (id) {
            case R.id.gotocal:
                i = new Intent(this, Exercal.class);
                startActivityForResult(i,99);

                break;
        }
    }

    public void buttonBMRClicked (View v)
    {
        EditText height = (EditText)findViewById(R.id.height);
        String h = height.getText().toString();
        Double height1 = Double.parseDouble(h);

        EditText weight = (EditText)findViewById(R.id.weight);
        String w = weight.getText().toString();
        Double weight2 = Double.parseDouble(w);

        EditText age = (EditText)findViewById(R.id.age);
        String a = age.getText().toString();
        Integer age3 = Integer.parseInt(a);

        double bmrout = 0;


        RadioGroup gender = (RadioGroup)findViewById(R.id.gender);
        RadioButton gr = (RadioButton) findViewById(gender.getCheckedRadioButtonId());
        int selgend = gender.getCheckedRadioButtonId();

        if (selgend == R.id.male) { //
            bmrout = ((13.75*weight2)+(5*height1)-(6.76*age3)+66);
            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues r = new ContentValues();

            r.put("height", height1);
            r.put("weight", weight2);
            r.put("age", age3);
            r.put("gender","Male");
            r.put("bmr", bmrout);

            long new_id = db.insert("caloriess", null, r);

            Log.d("caloriess", "onActivityResult");
        }

        else //
        {
            bmrout = ((9.56*weight2)+(1.85*height1)-(4.68*age3)+655);
            SQLiteDatabase db = helper.getWritableDatabase();
            ContentValues r = new ContentValues();

            r.put("height", height1);
            r.put("weight", weight2);
            r.put("age", age3);
            r.put("gender","Female");
            r.put("bmr", bmrout);


            long new_id = db.insert("caloriess", null, r);

            Log.d("caloriess", "onActivityResult");
        }

        TextView tv = (TextView)findViewById(R.id.bmrout);
        tv.setText(String.format("%.2f",bmrout));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bmr_cal, menu);
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
