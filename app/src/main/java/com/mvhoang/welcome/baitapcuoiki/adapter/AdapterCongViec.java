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

import com.mvhoang.welcome.baitapcuoiki.MainCongViecActivity;
import com.mvhoang.welcome.baitapcuoiki.Model.ModelCongViec;
import com.mvhoang.welcome.baitapcuoiki.R;
import com.mvhoang.welcome.baitapcuoiki.SuaCongViecActivity;

import java.util.ArrayList;

public class AdapterCongViec extends RecyclerView.Adapter<AdapterCongViec.viewHolder> {
    MainCongViecActivity context;
    int layout;
    ArrayList<ModelCongViec> dataCongViec;

    public  AdapterCongViec(ArrayList<ModelCongViec> dataCongViec, int layout,MainCongViecActivity context){
        this.context = context;
        this.layout = layout;
        this.dataCongViec = dataCongViec;
    }

    @NonNull
    @Override
    public AdapterCongViec.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = (LayoutInflater) LayoutInflater.from(viewGroup.getContext());
        View itemview = layoutInflater.inflate(R.layout.item_congviec, viewGroup, false);
        return new viewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCongViec.viewHolder viewHolder, final int i) {
        final ModelCongViec modelCongViec = dataCongViec.get(i);
        viewHolder.name.setText(dataCongViec.get(i).getName());
        viewHolder.date.setText(dataCongViec.get(i).getDate());
        viewHolder.status.setText(dataCongViec.get(i).getStatus());

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                PopupMenu popup = new PopupMenu(context,view);
                popup.getMenuInflater().inflate(R.menu.sua_xoa_congviec, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId(); // id menu

                        final int idcv = dataCongViec.get(i).getSTT();
                        if (id == R.id.xoacv){
                            Toast.makeText(context, "Bạn chọn xóa !", Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder dialogxoa = new AlertDialog.Builder(context);
                            dialogxoa.setMessage("Bạn có muốn xóa sinh viên "+ dataCongViec.get(i).getName() +" không ?");
                            dialogxoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    context.DeleteCV(idcv);
                                    Toast.makeText(context, "Bạn đã chọn xoa", Toast.LENGTH_SHORT).show();
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
                            Intent intent = new Intent(context, SuaCongViecActivity.class);
                            intent.putExtra("datacv",modelCongViec);
                            context.startActivity(intent);
                        }
                        return true;
                    }
                });
                popup.show();
                return false;
            }
        });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Nhấn giữ đển chọn sửa xóa !", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataCongViec.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder{
        TextView name, status,date;
        ImageView stt;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name   = (TextView) itemView.findViewById(R.id.tvname);
            stt    = (ImageView) itemView.findViewById(R.id.tvstt);
            status = (TextView) itemView.findViewById(R.id.tvstatus);
            date   = (TextView) itemView.findViewById(R.id.tvdate);
        }
    }
}
