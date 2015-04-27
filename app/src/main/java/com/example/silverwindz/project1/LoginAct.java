package com.example.silverwindz.project1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


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
                    if(user==GET['username']) {
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
    class LoadMessageTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            BufferedReader reader;
            StringBuilder buffer = new StringBuilder();
            String line;

            try {
                Log.e("LoadMessageTask", "" );
                URL u = new URL("http://ict.siit.tu.ac.th/~u5522790500/fetch.php");
                HttpURLConnection h = (HttpURLConnection)u.openConnection();
                h.setRequestMethod("GET");
                h.setDoInput(true);
                h.connect();

                int response = h.getResponseCode();
                if (response == 200) {
                    reader = new BufferedReader(new InputStreamReader(h.getInputStream()));
                    while((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }

                    Log.e("LoadMessageTask", buffer.toString());



                    JSONObject json = new JSONObject(buffer.toString());
                    JSONArray Msg = json.getJSONArray("msg");
                    int MsgLength;
                    int i ;
                    JSONObject onemsg;
                    MsgLength = Msg.length();
                    for(i=0 ; i<MsgLength ; i++)
                    {
                        onemsg = Msg.getJSONObject(i);
                        Map<String, String> item = new HashMap<String, String>();
                        item.put("username", onemsg.getString("username"));
                        item.put("pass", onemsg.getString("pass"));
                    }
                    //timestamp = json.getInt("timestamp");
                    return true;
                }
            } catch (MalformedURLException e) {
                Log.e("LoadMessageTask", "Invalid URL");
            } catch (IOException e) {
                Log.e("LoadMessageTask", "I/O Exception");
            } catch (JSONException e) {
                Log.e("LoadMessageTask", "Invalid JSON");
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                /*adapter.notifyDataSetChanged();
                lastUpdate = System.currentTimeMillis();
                Toast t = Toast.makeText(MessageActivity.this.getApplicationContext(),
                        "Updated the timeline",
                        Toast.LENGTH_SHORT);
                t.show();*/
            }
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
