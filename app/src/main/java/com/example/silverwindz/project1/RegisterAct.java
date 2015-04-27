package com.example.silverwindz.project1;
//http://www.codeproject.com/Articles/803688/Lets-create-the-Screen-Android-UI-Layout-and-Contr >>> login system
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class RegisterAct extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    }

    public void buttonlinkClicked(View v) {
        EditText username = (EditText) findViewById(R.id.username);
        String user = username.getText().toString().trim();

        EditText password = (EditText) findViewById(R.id.pass);
        String pass = password.getText().toString().trim();

        EditText Email = (EditText) findViewById(R.id.email);
        String email = Email.getText().toString().trim();



        EditText H = (EditText) findViewById(R.id.height);
        String height = H.getText().toString().trim();
        Double height2 = Double.parseDouble(height);

        EditText W = (EditText) findViewById(R.id.weight);
        String weight = W.getText().toString().trim();
        Double weight2 = Double.parseDouble(weight);

        EditText Age = (EditText) findViewById(R.id.age);
        String age = Age.getText().toString().trim();
        Double age2 = Double.parseDouble(age);

        double bmrout = 0;
        RadioGroup gender = (RadioGroup) findViewById(R.id.ger);
        RadioButton gr = (RadioButton) findViewById(gender.getCheckedRadioButtonId());
       int selgend = gender.getCheckedRadioButtonId();
        if (selgend == R.id.male) { //
            bmrout = ((13.75 * weight2) + (5 * height2) - (6.76 * age2) + 66);
            String bmr = String.valueOf(bmrout);
            PostMessageTask p = new PostMessageTask();
            p.execute(user, pass, email, "male", height, weight, age, bmr);
        }
        else{
            bmrout = ((9.56 * weight2) + (1.85 * height2) - (4.68 * age2) + 655);
            String bmr = String.valueOf(bmrout);
            PostMessageTask p = new PostMessageTask();
            p.execute(user, pass, email, "female", height, weight, age, bmr);
        }

            int id = v.getId();
            Intent i;

            switch (id) {
                case R.id.regist:

                    i = new Intent(this, LoginAct.class);
                    startActivityForResult(i, 99);

                    break;
            }
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
         getMenuInflater().inflate(R.menu.menu_register, menu);
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

    class PostMessageTask extends AsyncTask<String, Void, Boolean> {
        String line;
        StringBuilder buffer = new StringBuilder();
        String errMsg = "";

        @Override
        protected Boolean doInBackground(String... params) {
            Log.d("DEBUG", "Start Task");
            String username = params[0];
            String pass= params[1];
            String email = params[2];
            String gender= params[3];
            String height = params[4];
            String weight= params[5];
            String age = params[6];
            String bmr= params[7];
            String resp;
            HttpClient h = new DefaultHttpClient();
            HttpPost p = new HttpPost("http://ict.siit.tu.ac.th/~u5522790500/post.php");

            List<NameValuePair> values = new ArrayList<NameValuePair>();
            values.add(new BasicNameValuePair("username", username));
            values.add(new BasicNameValuePair("pass", pass));
            values.add(new BasicNameValuePair("email", email));
            values.add(new BasicNameValuePair("gender", gender));
            values.add(new BasicNameValuePair("height", height));
            values.add(new BasicNameValuePair("weight", weight));
            values.add(new BasicNameValuePair("age", age));
            values.add(new BasicNameValuePair("bmr", bmr));
            try {
                p.setEntity(new UrlEncodedFormEntity(values));
                HttpResponse response = h.execute(p);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()));
                Log.d("DEBUG", "HERE");
                while ((line = reader.readLine()) != null) {
                    Log.d("DEBUGL", line);
                    buffer.append(line);
                }

                //Log.e("LoadMessageTask", buffer.toString());
                //Parsing JSON and displaying messages

                //To append a new message:
                //Map<String, String> item = new HashMap<String, String>();
                //item.put("user", u);
                //item.put("message", m);
                //data.add(0, item);
                Log.d("DEBUG", buffer.toString());
                JSONObject json = new JSONObject(buffer.toString());

                boolean respo = json.getBoolean("response");
                errMsg = json.getString("errmsg");
                if(respo) {
                    return true;
                }


            }catch(JSONException e){
               Log.e("LoadMessageTask", "Invalid JSON");
            }catch(UnsupportedEncodingException e){
                Log.e("Error", "Invalid encoding");
            }catch(ClientProtocolException e){
                Log.e("Error", "Error in posting a message");
            }catch(IOException e){
                Log.e("Error", "I/O Exception");
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast t = Toast.makeText(RegisterAct.this.getApplicationContext(),
                        "Successfully register",
                        Toast.LENGTH_SHORT);
                t.show();
            }
            else {
                Toast t = Toast.makeText(RegisterAct.this.getApplicationContext(),
                        "Unable to register: " + errMsg,
                        Toast.LENGTH_SHORT);
                t.show();
            }
        }



    }
}
