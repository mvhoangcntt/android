package com.mvhoang.welcome.baitapcuoiki;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MenuHome extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_home, container, false);

        view.findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager manager = getFragmentManager();
                MenuHome simpleFragment = (MenuHome) manager.findFragmentById(R.id.mylayout);

                if (simpleFragment != null){
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.remove(simpleFragment);
                    transaction.commit();
                }

                //FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                PhepToan fragment = new PhepToan();
                transaction.add(R.id.mylayout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}
