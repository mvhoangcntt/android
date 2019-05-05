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
import com.mvhoang.welcome.baitapcuoiki.fragmentTabLayout.FragmentA;

import java.util.HashMap;
import java.util.Map;

public class AddThoiGianBieuActivity extends AppCompatActivity {
    EditText edtname, edtstatust, edtdate;
    Button btnadd, btncan;
    //    String urladdcv = "https://hai80184.000webhostapp.com/cuoiki/congviec/insertonline.php";
    String urladdcv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_thoi_gian_bieu);
        Intent intent = getIntent();
        String kieu = intent.getStringExtra("kieu");
        Toast.makeText(this, ""+kieu, Toast.LENGTH_SHORT).show();
        if (kieu.equals("1")){
            urladdcv = "http://192.168.56.1/musicandroid/cuoiki/thoigianbieu/insert.php";
        }else{
            urladdcv = "http://192.168.56.1/musicandroid/cuoiki/thoigianbieu2/insert.php";
        }
        anhxa();
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tust = edtstatust.getText().toString();
                String da = edtdate.getText().toString();
                if (tust.isEmpty() || da.isEmpty()){
                    Toast.makeText(AddThoiGianBieuActivity.this, "Không được để trống !", Toast.LENGTH_SHORT).show();
                }else{
                    them(urladdcv);
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urladdcv,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toast.makeText(getApplicationContext(), "Thêm mới thành công !", Toast.LENGTH_SHORT).show();
                            edtdate.setText("");
                            edtstatust.setText("");
                            Intent intent = new Intent(getApplicationContext(), ViewPagerTabLayoutActivity.class);
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
                params.put("date",edtdate.getText().toString().trim());
                params.put("status",edtstatust.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void anhxa(){
        edtdate = findViewById(R.id.date);
        edtstatust = findViewById(R.id.status);
        btnadd = findViewById(R.id.addcv);
        btncan = findViewById(R.id.cancel);

    }
}
