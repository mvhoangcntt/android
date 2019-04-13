package com.mvhoang.welcome.baitapcuoiki;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

public class PhepToan extends Fragment {
    int tongdung = 0;
    TextView demgio;
    TextView soa, sob, pheptinh, ketqua, caonhat;
    int kiemtra = -1, tinh = 0, kq = 0, click = 0;
    float kiemtra2 = -1, chia1 = 0;
    int tu = 0;
    int den = 100;
    int min = 0;
    int max = 10;
    int soa1 = 0, soa2 = 0;
    int dung = 1;
    int sai = 2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.phep_tinh ,container,false);

        soa = view.findViewById(R.id.soA);

        sob = view.findViewById(R.id.soB);
        pheptinh = view.findViewById(R.id.pheptinh);
        ketqua = view.findViewById(R.id.ketqua);

        // ----- chon so ngau nhien ------
        for (int i = 1; i<=2; i++){
            if (i == 1){
                Random random = new Random();
                soa1 = random.nextInt(max-min+1)+min;
                soa.setText(" "+soa1+" ");
            }
            if (i == 2){
                Random random = new Random();
                soa2 = random.nextInt(max-min+1)+min;
                sob.setText(" "+soa2+" ");
            }
        }
        // ------ dau phep toan -------
        String dau = "";
        if (soa1%2==0 && soa2%2!=0){
            dau = "+";
            pheptinh.setText(""+dau);
        }
        if (soa1%2==0 && soa2%2==0){
            dau = "-";
            pheptinh.setText(""+dau);
        }
        if (soa1%2!=0 && soa2%2==0){
            dau = "/";
            pheptinh.setText(""+dau);
        }
        if (soa1%2!=0 && soa2%2!=0){
            dau = "*";
            pheptinh.setText(""+dau);
        }
        //----- tinh toan --------
        if( dau =="+"){
            tinh = soa1 +soa2;
        }
        if(dau=="-"){
            tinh = soa1 - soa2;
        }
        if(dau=="*"){
            tinh = soa1 * soa2;
        }
        if(dau=="/"){
            chia1 = (float) soa1 / soa2;
        }
        //------- ket qua ------

        Random random = new Random();
        int dungsai = random.nextInt(sai-dung+1)+dung;
        if (dungsai == 1){
            if (chia1 != 0){ // không cố kết quả / 0 = 0
                kiemtra2 = chia1;
                ketqua.setText(chia1+"");
            }else{
                kiemtra = tinh;
                ketqua.setText(tinh+"");
            }
        }
        if (dungsai==2){
            Random random1 = new Random();
            kq = random1.nextInt(den-tu+1)+tu;
            ketqua.setText(kq+"");
        }
        view.findViewById(R.id.dung).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( tinh == kiemtra || chia1 == kiemtra2 ){
                    FragmentManager manager = getFragmentManager();
                    PhepToan ptoan = (PhepToan) manager.findFragmentById(R.id.mylayout);
                    if (ptoan != null){
                        FragmentTransaction transaction1 = manager.beginTransaction();
                        transaction1.remove(ptoan);
                        transaction1.commit();
                    }

                    FragmentTransaction transaction = manager.beginTransaction();
                    PhepToan fragment = new PhepToan();
                    transaction.add(R.id.mylayout, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    tongdung++;

                    //Toast.makeText(getActivity(), "tong = "+ tongdung, Toast.LENGTH_SHORT).show();
                }else{
                    click = 1;
                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    ThongBao fragment = new ThongBao();
                    transaction.add(R.id.mylayout, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                    PhepToan ptoan = (PhepToan) manager.findFragmentById(R.id.mylayout);

                    if (ptoan != null){
                        FragmentTransaction transaction1 = manager.beginTransaction();
                        transaction1.remove(ptoan);
                        transaction1.commit();
                    }
                }
            }
        });

        view.findViewById(R.id.sai).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( tinh == kiemtra || chia1 == kiemtra2 ){
                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    ThongBao fragment = new ThongBao();
                    transaction.add(R.id.mylayout, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();

                    PhepToan ptoan = (PhepToan) manager.findFragmentById(R.id.mylayout);

                    if (ptoan != null){
                        FragmentTransaction transaction1 = manager.beginTransaction();
                        transaction1.remove(ptoan);
                        transaction1.commit();
                    }

                }else{
                    FragmentManager manager = getFragmentManager();
                    PhepToan ptoan = (PhepToan) manager.findFragmentById(R.id.mylayout);
                    if (ptoan != null){
                        FragmentTransaction transaction1 = manager.beginTransaction();
                        transaction1.remove(ptoan);
                        transaction1.commit();
                    }

                    FragmentTransaction transaction = manager.beginTransaction();
                    PhepToan fragment = new PhepToan();
                    transaction.add(R.id.mylayout, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    tongdung++;

                    //Toast.makeText(getActivity(), "tong = "+ tongdung, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //30000 = 30s 60000 = 1p = 60s
//        demgio = view.findViewById(R.id.demgio);
//        CountDownTimer w = new CountDownTimer(6000,1000) {
//            @Override
//            public void onTick(long l) {
//                demgio.setText("" + l/1000);
//            }
//
//            @Override
//            public void onFinish() {
//                demgio.setText("0");
//                FragmentManager manager1 = getFragmentManager();
//                PhepToan simpleFragment1 = (PhepToan) manager1.findFragmentById(R.id.mylayout);
//
//                if (simpleFragment1 != null){
//                    FragmentTransaction transaction = manager1.beginTransaction();
//                    transaction.remove(simpleFragment1);
//                    transaction.commit();
//                }
//                FragmentManager manager = getFragmentManager();
//                FragmentTransaction transaction = manager.beginTransaction();
//                ThongBao fragment = new ThongBao();
//                transaction.add(R.id.mylayout, fragment);
//                transaction.addToBackStack(null);
//                transaction.commit();


//            }
//        }.start();

        return view;
    }
}
