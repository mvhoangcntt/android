package com.mvhoang.welcome.baitapcuoiki.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mvhoang.welcome.baitapcuoiki.Fragment_Layout_Pager;
import com.mvhoang.welcome.baitapcuoiki.R;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public String tabTitle[] = new String[] {"Xuân","Hạ","Thu","Đông"};
    public static final int so = 4;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return Fragment_Layout_Pager.newInstance(R.drawable.android_hinhnen7);
        }else if (position == 1){
            return Fragment_Layout_Pager.newInstance(R.drawable.android_hinhnen2);
        }else if (position == 2){
            return Fragment_Layout_Pager.newInstance(R.drawable.android_hinhnen1);
        }if (position == 3){
            return Fragment_Layout_Pager.newInstance(R.drawable.android_hinhnen6);
        }
        return null;
    }

    @Override
    public int getCount() {
        return so;
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}
