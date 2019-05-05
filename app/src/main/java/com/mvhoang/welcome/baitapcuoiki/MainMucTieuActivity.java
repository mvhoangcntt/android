package com.mvhoang.welcome.baitapcuoiki;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mvhoang.welcome.baitapcuoiki.Model.ModelCongViec;
import com.mvhoang.welcome.baitapcuoiki.Model.Model_MucTieu;
import com.mvhoang.welcome.baitapcuoiki.adapter.AdapterCongViec;
import com.mvhoang.welcome.baitapcuoiki.adapter.Adapter_MucTieu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainMucTieuActivity extends AppCompatActivity {
    String url = "http://192.168.56.1/musicandroid/cuoiki/muctieu/list.php";

    ArrayList<Model_MucTieu> dataMucTieu;
    Adapter_MucTieu adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_muc_tieu);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataMucTieu = new ArrayList<>();
        adapter = new Adapter_MucTieu(MainMucTieuActivity.this,R.layout.item_muctieu,dataMucTieu);
        recyclerView.setAdapter(adapter);
        GetData(url);
    }

    private void GetData(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        dataMucTieu.clear();
                        for (int i = 0; i< response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                dataMucTieu.add(new Model_MucTieu(
                                        object.getInt("id"),
                                        object.getString("noidung")
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
            Intent intent = new Intent(getApplicationContext(),AddMucTieuActivity.class);
            startActivity(intent);
        }
        if (item.getItemId() == R.id.menuback){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
