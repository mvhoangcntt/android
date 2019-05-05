package com.mvhoang.welcome.baitapcuoiki.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mvhoang.welcome.baitapcuoiki.MainCongViecActivity;
import com.mvhoang.welcome.baitapcuoiki.MainMucTieuActivity;
import com.mvhoang.welcome.baitapcuoiki.Model.ModelCongViec;
import com.mvhoang.welcome.baitapcuoiki.Model.Model_MucTieu;
import com.mvhoang.welcome.baitapcuoiki.R;
import com.mvhoang.welcome.baitapcuoiki.SuaCongViecActivity;
import com.mvhoang.welcome.baitapcuoiki.SuaMucTieuActivity;
import com.mvhoang.welcome.baitapcuoiki.ViewPagerTabLayoutActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Adapter_MucTieu extends RecyclerView.Adapter<Adapter_MucTieu.viewHolder> {

    String urldelete = "http://192.168.56.1/musicandroid/cuoiki/muctieu/delete.php";
    Context context;
    int layout;
    ArrayList<Model_MucTieu> dataMucTieu;

    public Adapter_MucTieu(Context context, int layout, ArrayList<Model_MucTieu> dataMucTieu) {
        this.context = context;
        this.layout = layout;
        this.dataMucTieu = dataMucTieu;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = (LayoutInflater) LayoutInflater.from(viewGroup.getContext());
        View itemview = layoutInflater.inflate(R.layout.item_muctieu, viewGroup, false);
        return new viewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, final int i) {
        final Model_MucTieu model_mucTieu = dataMucTieu.get(i);
        viewHolder.NoiDung.setText(dataMucTieu.get(i).getNoiDung());

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popup = new PopupMenu(context,v);
                popup.getMenuInflater().inflate(R.menu.sua_xoa_congviec, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId(); // id menu

                        final int idcv = dataMucTieu.get(i).getId();
                        if (id == R.id.xoacv){
                            Toast.makeText(context, "Bạn chọn xóa !", Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder dialogxoa = new AlertDialog.Builder(context);
                            dialogxoa.setMessage("Bạn có muốn xóa sinh viên "+ dataMucTieu.get(i).getNoiDung() +" không ?");
                            dialogxoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DeleteCV(idcv);
                                    Toast.makeText(context, "Bạn đã chọn xoa", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(context, MainMucTieuActivity.class);
                                    context.startActivity(intent);
                                }
                            });
                            dialogxoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            dialogxoa.show();
                        }
                        if (id == R.id.suacv){
                            Toast.makeText(context, "Bạn chọn sua !", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, SuaMucTieuActivity.class);
                            intent.putExtra("datamt", model_mucTieu);
                            context.startActivity(intent);
                        }
                        return true;
                    }
                });
                popup.show();
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataMucTieu.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder{
        TextView NoiDung;
        ImageView icon;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            NoiDung   = (TextView) itemView.findViewById(R.id.tvNoiDung);
            icon    = (ImageView) itemView.findViewById(R.id.icon);
        }
    }

    private void DeleteCV(final int idcv){

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urldelete,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){// success trong php
                            Toast.makeText(context, "Xóa thành công ! ", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(context, "Lỗi xóa !", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Có lỗi sảy ra !", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",String.valueOf(idcv));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
