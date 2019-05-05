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

public class AddMucTieuActivity extends AppCompatActivity {
    EditText edtNoidung;
    Button btnadd, btncan;
    String urladd = "http://192.168.56.1/musicandroid/cuoiki/muctieu/insert.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_muc_tieu);
        anhxa();
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nd = edtNoidung.getText().toString();
                if (nd.isEmpty()){
                    Toast.makeText(AddMucTieuActivity.this, "Không được để trống !", Toast.LENGTH_SHORT).show();
                }else{
                    them(urladd);
                }
            }
        });
        btncan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void them(String urladdcv){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urladd,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toast.makeText(getApplicationContext(), "Thêm mới thành công !", Toast.LENGTH_SHORT).show();
                            edtNoidung.setText("");
                            Intent intent = new Intent(getApplicationContext(),MainMucTieuActivity.class);
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
                params.put("noidung",edtNoidung.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void anhxa(){
        edtNoidung = findViewById(R.id.edtnoidung);
        btnadd = findViewById(R.id.addcv);
        btncan = findViewById(R.id.cancel);

    }
}
