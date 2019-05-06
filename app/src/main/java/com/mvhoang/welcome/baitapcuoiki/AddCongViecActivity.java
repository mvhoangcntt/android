package com.mvhoang.welcome.baitapcuoiki;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddCongViecActivity extends AppCompatActivity {
    EditText edtname, edtstatust, edtdate;
    Button btnadd, btncan;
    String urladdcv = "https://hai80184.000webhostapp.com/cuoiki/congviec/insertonline.php";
//    String urladdcv = "http://192.168.56.1/musicandroid/cuoiki/congviec/insert.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cong_viec);
        anhxa();
        edtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay();
            }
        });
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten = edtname.getText().toString();
                String tust = edtstatust.getText().toString();
                String da = edtdate.getText().toString();
                if (ten.isEmpty() || tust.isEmpty() || da.isEmpty()){
                    Toast.makeText(AddCongViecActivity.this, "Không được để trống !", Toast.LENGTH_SHORT).show();
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
    private void them(String urladdcv){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urladdcv,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toast.makeText(getApplicationContext(), "Thêm mới thành công !", Toast.LENGTH_SHORT).show();
                            edtname.setText("");
                            edtdate.setText("");
                            edtstatust.setText("");
                            Intent intent = new Intent(getApplicationContext(),MainCongViecActivity.class);
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
                params.put("name",edtname.getText().toString().trim());
                params.put("date",edtdate.getText().toString().trim());
                params.put("status",edtstatust.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void anhxa(){
        edtname = findViewById(R.id.tencv);
        edtdate = findViewById(R.id.date);
        edtstatust = findViewById(R.id.status);
        btnadd = findViewById(R.id.addcv);
        btncan = findViewById(R.id.cancel);

    }
}
