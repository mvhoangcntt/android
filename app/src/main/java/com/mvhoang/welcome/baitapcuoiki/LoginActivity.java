package com.mvhoang.welcome.baitapcuoiki;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    EditText username, password;
    Button btnlogin , btncreate;
    String urluser = "https://hai80184.000webhostapp.com/cuoiki/user/listonlineuser.php";
//    String urluser = "http://192.168.56.1/musicandroid/cuoiki/user/listuser.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        anhxa();
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String user = username.getText().toString();
                final String pass = password.getText().toString();
                if (user.isEmpty() || pass.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Không được để trống !", Toast.LENGTH_SHORT).show();
                }else{
                    GetUser(urluser);
                }
            }
        });
        btncreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateAccountActivity.class);
                startActivity(intent);
            }
        });
    }
    public void anhxa(){
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        btnlogin = (Button)findViewById(R.id.btnlogin);
        btncreate = (Button) findViewById(R.id.btncreate);
    }
    private void GetUser(String urluser){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, urluser, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        final String user = username.getText().toString();
                        final String pass = password.getText().toString();
                        String emai = "";
                        int kiemtra = 0;
                        for (int i = 0; i< response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                String xuser = object.getString("username");
                                String ypass = object.getString("password");

                                if (user.equals(xuser) && pass.equals(ypass)){
                                    emai = object.getString("email");
                                    kiemtra = 1;
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        if (kiemtra == 0){
                            Toast.makeText(getApplicationContext(), "Đăng nhập thất bại !", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(), "Đăng nhập thành công !", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("us",user);
                            intent.putExtra("pa",pass);
                            intent.putExtra("em",emai);
                            startActivity(intent);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volly Error", error.toString());
                        Toast.makeText(getApplicationContext(), "Bị lỗi hệ thống !", Toast.LENGTH_SHORT).show();

                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
}
