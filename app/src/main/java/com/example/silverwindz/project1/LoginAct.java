package com.example.silverwindz.project1;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;




public class LoginAct extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void buttonClicked(View v) {
        EditText etUser = (EditText)findViewById(R.id.etUser);
        EditText etPass = (EditText)findViewById(R.id.etPass);
        String user = etUser.getText().toString().trim();
        String pass = etPass.getText().toString().trim();
        int id = v.getId();
        Intent i;

        switch(id) {
            case R.id.sign_in:

                if (user.length() == 0) {
                    Toast t = Toast.makeText(this.getApplicationContext(),
                            "Please input the user name",
                            Toast.LENGTH_SHORT);
                    t.show();
                }
                else if (pass.length() == 0) {
                    Toast tt = Toast.makeText(this.getApplicationContext(),
                            "Please input the password",
                            Toast.LENGTH_SHORT);
                    tt.show();
                }
                else {
                    if(user.) {
                        i = new Intent(this, MainActivity.class);
                        i.putExtra("user", user);
                        startActivity(i);
                    }else{
                        Toast t = Toast.makeText(this.getApplicationContext(),
                                "Username/Password is incorrect.",
                                Toast.LENGTH_SHORT);
                        t.show();
                    }
                }
                break;

            case R.id.register:
                i = new Intent(this, RegisterAct.class);
                startActivity(i);
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
