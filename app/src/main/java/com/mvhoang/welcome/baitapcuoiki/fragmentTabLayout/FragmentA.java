package com.mvhoang.welcome.baitapcuoiki.fragmentTabLayout;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mvhoang.welcome.baitapcuoiki.AddCongViecActivity;
import com.mvhoang.welcome.baitapcuoiki.AddThoiGianBieuActivity;
import com.mvhoang.welcome.baitapcuoiki.MainActivity;
import com.mvhoang.welcome.baitapcuoiki.MainCongViecActivity;
import com.mvhoang.welcome.baitapcuoiki.Model.ModelCongViec;
import com.mvhoang.welcome.baitapcuoiki.Model.Model_Fragment_Pager;
import com.mvhoang.welcome.baitapcuoiki.R;
import com.mvhoang.welcome.baitapcuoiki.ViewPagerTabLayoutActivity;
import com.mvhoang.welcome.baitapcuoiki.XoayManHinhActivity;
import com.mvhoang.welcome.baitapcuoiki.adapter.AdapterCongViec;
import com.mvhoang.welcome.baitapcuoiki.adapter.AdapterFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentA extends Fragment {
    View v;
    RecyclerView recyclerView;
    AdapterFragment adapterFragment;
    List<Model_Fragment_Pager> listCV;
    String urltg = "http://192.168.56.1/musicandroid/cuoiki/thoigianbieu/listtg.php";

    public FragmentA(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragmenta,container,false);

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Bạn đã chạm vào tôi !", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(getContext(), AddThoiGianBieuActivity.class);
                intent.putExtra("kieu","1");
                startActivity(intent);
            }
        });
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerviewA);
//        recyclerView.setHasFixedSize(true);
        adapterFragment = new AdapterFragment(getContext(),R.layout.item_fragment_pager,listCV);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.setAdapter(adapterFragment);


        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listCV = new ArrayList<>();
//        listCV.add(new Model_Fragment_Pager(R.drawable.ic_menu_manage,"dfsd","32"));
//        listCV.add(new Model_Fragment_Pager(R.drawable.ic_menu_send,"asger","dfsdsd"));
//        listCV.add(new Model_Fragment_Pager(R.drawable.ic_menu_share,"asasd","dfsda"));
        GetData(urltg);
    }

    private void GetData(String urltg){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, urltg, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listCV.clear();
                        for (int i = 0; i< response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                listCV.add(new Model_Fragment_Pager(
                                        object.getInt("id"),
                                        object.getString("Status"),
                                        object.getString("Date")
                                ));
                            }catch (JSONException e){
                                e.printStackTrace();
                            }
                        }
                        adapterFragment.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Loi", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }




}
