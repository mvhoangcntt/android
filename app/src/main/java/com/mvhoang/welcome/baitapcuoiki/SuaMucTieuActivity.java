package com.mvhoang.welcome.baitapcuoiki;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.mvhoang.welcome.baitapcuoiki.Model.ModelCongViec;
import com.mvhoang.welcome.baitapcuoiki.Model.Model_MucTieu;

import java.util.HashMap;
import java.util.Map;

public class SuaMucTieuActivity extends AppCompatActivity {
    String url = "http://192.168.56.1/musicandroid/cuoiki/muctieu/update.php";
    EditText edtNoidung;
    Button btnuodatecv, btncancv;
    int id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_muc_tieu);
        anhxa();

        Intent intent = getIntent();
        Model_MucTieu model_mucTieu = (Model_MucTieu) intent.getSerializableExtra("datamt");
        edtNoidung.setText(model_mucTieu.getNoiDung());
        id = model_mucTieu.getId();
        btncancv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnuodatecv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noidung = edtNoidung.getText().toString().trim();
                if (noidung.isEmpty()){
                    Toast.makeText(SuaMucTieuActivity.this, "Không được để trống !", Toast.LENGTH_SHORT).show();
                }else{
                    capnhat(url);
                }
            }
        });

    }
    private void capnhat(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toast.makeText(getApplicationContext(), "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainMucTieuActivity.class));
                        }else{
                            Toast.makeText(getApplicationContext(), "Lỗi cập nhật !", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {// có thể lỗi server ...
                        Toast.makeText(getApplicationContext(), "Lỗi vui lòng thử lại !", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",String.valueOf(id));
                params.put("noidung",edtNoidung.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void anhxa(){
        edtNoidung = findViewById(R.id.edtNoiDung);
        btnuodatecv = findViewById(R.id.addupdatecv);
        btncancv = findViewById(R.id.cancelcv);
    }
}
