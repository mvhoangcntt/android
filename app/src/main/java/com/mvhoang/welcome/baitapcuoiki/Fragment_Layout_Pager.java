package com.mvhoang.welcome.baitapcuoiki;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Fragment_Layout_Pager extends Fragment {
    public static Fragment_Layout_Pager newInstance(int content) {//(truyen tham so)

        Bundle args = new Bundle();
        args.putInt("content",content);
        Fragment_Layout_Pager fragment = new Fragment_Layout_Pager();
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_viewpager,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ImageView imgNoidung = view.findViewById(R.id.image);
        Bundle bd = getArguments();

        if (bd != null){
            Integer duLieu = bd.getInt("content");
            imgNoidung.setImageResource(duLieu);
        }
    }
}
