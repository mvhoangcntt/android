package com.mvhoang.welcome.baitapcuoiki;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ThongBao extends Fragment {
    Button btnreplay , btnexit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thong_bao, container, false);
        btnreplay = (Button) view.findViewById(R.id.replayy);
        btnexit = (Button) view.findViewById(R.id.exit);

        btnexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        btnreplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FragmentManager manager = getFragmentManager();
                ThongBao simpleFragment = (ThongBao) manager.findFragmentById(R.id.mylayout);

                if (simpleFragment != null){
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.remove(simpleFragment);
                    transaction.commit();
                }


                FragmentTransaction transaction = manager.beginTransaction();
                PhepToan fragment = new PhepToan();
                transaction.add(R.id.mylayout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();

                //Toast.makeText(getActivity(), "Thong bao", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
