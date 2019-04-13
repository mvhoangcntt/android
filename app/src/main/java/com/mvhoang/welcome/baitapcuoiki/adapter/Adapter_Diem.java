package com.mvhoang.welcome.baitapcuoiki.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mvhoang.welcome.baitapcuoiki.Model.Model_Diemso;
import com.mvhoang.welcome.baitapcuoiki.R;

import java.util.ArrayList;

public class Adapter_Diem extends RecyclerView.Adapter<Adapter_Diem.viewHolder> {
    Context context;
    ArrayList<Model_Diemso> datas;
    public Adapter_Diem(Context context, ArrayList<Model_Diemso> datas){
        this.context = context;
        this.datas = datas;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) LayoutInflater.from(context);
        View itemview = layoutInflater.inflate(R.layout.layout_diem,parent,false);
        viewHolder holder = new viewHolder(itemview);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.diem.setText(datas.get(position).getDiem());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
    public class viewHolder extends RecyclerView.ViewHolder {
        TextView diem;

        public viewHolder(final View itemView) {
            super(itemView);
            diem = itemView.findViewById(R.id.diem);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent view = new Intent(context,Main3Activity.class);
//                    view.putExtra("thu",thu.getText().toString());
//                    view.putExtra("noidung",noidung.getText().toString());
//                    view.putExtra("noidung",gioitinh.getText().toString());
//                    context.startActivity(view);
//                }
//            });

        }
    }
}
