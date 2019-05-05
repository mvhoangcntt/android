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
import com.mvhoang.welcome.baitapcuoiki.Model.Model_Fragment_Pager;
import com.mvhoang.welcome.baitapcuoiki.R;
import com.mvhoang.welcome.baitapcuoiki.SuaThoiGianBieuActivity;
import com.mvhoang.welcome.baitapcuoiki.ViewPagerTabLayoutActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterFragment2 extends RecyclerView.Adapter<AdapterFragment.MyViewHolder> {
    String urldelete = "http://192.168.56.1/musicandroid/cuoiki/thoigianbieu2/delete.php";
    Context mContext;
    int layout;
    List<Model_Fragment_Pager> mData;

    public AdapterFragment2(Context mContext, int layout, List<Model_Fragment_Pager> mData) {
        this.mContext = mContext;
        this.layout = layout;
        this.mData = mData;
    }

    @NonNull
    @Override
    public AdapterFragment.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_pager,viewGroup,false);
        AdapterFragment.MyViewHolder viewHolder = new AdapterFragment.MyViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterFragment.MyViewHolder myViewHolder, final int i) {
        final Model_Fragment_Pager model_fragment_pager = mData.get(i);
        myViewHolder.statusfr.setText(mData.get(i).getStatus());
        myViewHolder.datefr.setText(mData.get(i).getDate());
//        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, ""+mData.get(i).getStatus(), Toast.LENGTH_SHORT).show();
//            }
//        });
        myViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                PopupMenu popup = new PopupMenu(mContext,view);
                popup.getMenuInflater().inflate(R.menu.sua_xoa_congviec, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId(); // id menu

                        final int idcv = mData.get(i).getId();
                        if (id == R.id.xoacv){
                            Toast.makeText(mContext, "Bạn chọn xóa !", Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder dialogxoa = new AlertDialog.Builder(mContext);
                            dialogxoa.setMessage("Bạn có muốn xóa "+ mData.get(i).getStatus() +" không ?");
                            dialogxoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DeleteCV(idcv);
                                    Toast.makeText(mContext, "Bạn đã chọn xoa", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(mContext, ViewPagerTabLayoutActivity.class);
                                    mContext.startActivity(intent);
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
                            Toast.makeText(mContext, "Bạn chọn sua !", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(mContext, SuaThoiGianBieuActivity.class);
                            intent.putExtra("kieu","2");
                            intent.putExtra("dulieu",model_fragment_pager);
                            mContext.startActivity(intent);
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
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView statusfr,datefr;
        ImageView sttfr;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            sttfr    = (ImageView) itemView.findViewById(R.id.icon);
            statusfr = (TextView) itemView.findViewById(R.id.noidung);
            datefr   = (TextView) itemView.findViewById(R.id.tvdatefr);
        }
    }

    private void DeleteCV(final int idcv){

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urldelete,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){// success trong php
                            Toast.makeText(mContext, "Xóa thành công ! ", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(mContext, "Lỗi xóa !", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mContext, "Có lỗi sảy ra !", Toast.LENGTH_SHORT).show();
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
