package com.mvhoang.welcome.baitapcuoiki;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mvhoang.welcome.baitapcuoiki.Model.ModelCongViec;
import com.mvhoang.welcome.baitapcuoiki.adapter.AdapterCongViec;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainCongViecActivity extends AppCompatActivity {

    String urlcv = "http://hai80184.000webhostapp.com/cuoiki/congviec/listonline.php";
    String urldelete = "http://hai80184.000webhostapp.com/cuoiki/congviec/deleteonline.php";
//    String urlcv = "http://192.168.56.1/musicandroid/cuoiki/congviec/listcv.php";
//    String urldelete = "http://192.168.56.1/musicandroid/cuoiki/congviec/delete.php";
    ArrayList<ModelCongViec> dataCongViec;
    AdapterCongViec adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cong_viec);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataCongViec = new ArrayList<>();
        adapter = new AdapterCongViec(dataCongViec,R.layout.item_congviec,MainCongViecActivity.this);
        recyclerView.setAdapter(adapter);
        GetData(urlcv);

    }

    private void GetData(String urlcv){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, urlcv, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        dataCongViec.clear();
                        for (int i = 0; i< response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                dataCongViec.add(new ModelCongViec(
                                        object.getInt("id"),
                                        object.getString("Name"),
                                        object.getString("Status"),
                                        object.getString("Date")
                                ));
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Loi", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }



    // tạo options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_congviec, menu);
        return super.onCreateOptionsMenu(menu);
    }
    // goi den menu item add_congviec
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuadd){
            Intent intent = new Intent(getApplicationContext(),AddCongViecActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.menuback){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void DeleteCV(final int idcv){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urldelete,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){// success trong php
                            Toast.makeText(getApplicationContext(), "Xóa thành công ! "+idcv , Toast.LENGTH_SHORT).show();
                            GetData(urlcv);
                        }else{
                            Toast.makeText(getApplicationContext(), "Lỗi xóa !", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Có lỗi sảy ra !", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("idcv",String.valueOf(idcv));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}
