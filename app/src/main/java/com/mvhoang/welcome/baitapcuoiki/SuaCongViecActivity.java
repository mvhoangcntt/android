package com.mvhoang.welcome.baitapcuoiki;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SuaCongViecActivity extends AppCompatActivity {
    String url = "https://hai80184.000webhostapp.com/cuoiki/congviec/updateonline.php";
//    String url = "http://192.168.56.1/musicandroid/cuoiki/congviec/update.php";
    EditText edtname, edtstatust, edtdate;
    Button btnuodatecv, btncancv;
    int id = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_cong_viec);
        anhxa();
        edtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();
            }
        });
        Intent intent = getIntent();
        ModelCongViec modelCongViec = (ModelCongViec) intent.getSerializableExtra("datacv");
        edtname.setText(modelCongViec.getName());
        edtstatust.setText(modelCongViec.getStatus());
        edtdate.setText(modelCongViec.getDate()+"");
        id = modelCongViec.getSTT();
        btncancv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnuodatecv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten = edtname.getText().toString().trim();
                String stat = edtstatust.getText().toString().trim();
                String da = edtdate.getText().toString().trim();
                if (ten.isEmpty() || stat.isEmpty() || da.isEmpty()){
                    Toast.makeText(SuaCongViecActivity.this, "Không được để trống !", Toast.LENGTH_SHORT).show();
                }else{
                    capnhat(url);
                }
            }
        });

    }
    private void ChonNgay(){
        final Calendar calendar = Calendar.getInstance();
        int Ngay = calendar.get(Calendar.DATE);
        int Thang = calendar.get(Calendar.MONTH);
        int Nam = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy");
                edtdate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        },Nam,Thang,Ngay);
        datePickerDialog.show();
    }
    private void capnhat(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toast.makeText(getApplicationContext(), "Cập nhật thành công !", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainCongViecActivity.class));
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
                params.put("name",edtname.getText().toString().trim());
                params.put("status",edtstatust.getText().toString().trim());
                params.put("date",edtdate.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void anhxa(){
        edtname = findViewById(R.id.tencvv);
        edtstatust = findViewById(R.id.statuscv);
        edtdate = findViewById(R.id.datecv);
        btnuodatecv = findViewById(R.id.addupdatecv);
        btncancv = findViewById(R.id.cancelcv);
    }
}
