package com.mvhoang.welcome.baitapcuoiki;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity {
    String urladd = "https://hai80184.000webhostapp.com/cuoiki/user/insertonline.php";
//    String urladd = "http://192.168.56.1/musicandroid/cuoiki/user/insert.php";
    EditText name, email, pass;
    Button btncreatec, btncancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        anhxa();
        btncreatec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten = name.getText().toString();
                String mail = email.getText().toString();
                String pw = pass.getText().toString();
                if (ten.isEmpty() || mail.isEmpty() || pw.isEmpty()){
                    Toast.makeText(CreateAccountActivity.this, "Không được để trống ! ", Toast.LENGTH_SHORT).show();
                }else{
                    them(urladd);
                }
            }
        });
        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void anhxa(){
        name = findViewById(R.id.username);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.password);
        btncreatec = findViewById(R.id.btncreatec);
        btncancel = findViewById(R.id.btncancel);
    }

    private void them(String urladd){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urladd,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toast.makeText(getApplicationContext(), "Đăng ký thành công !", Toast.LENGTH_SHORT).show();
                            name.setText("");
                            email.setText("");
                            pass.setText("");
                            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(), "Loi save !", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Say ra loi !", Toast.LENGTH_SHORT).show();
                        Log.d("AAA","Lỗi\n"+ error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username",name.getText().toString().trim());
                params.put("email",email.getText().toString().trim());
                params.put("password",pass.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
