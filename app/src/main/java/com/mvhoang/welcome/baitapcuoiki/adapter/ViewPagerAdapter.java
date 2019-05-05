package com.mvhoang.welcome.baitapcuoiki.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> isFrangment = new ArrayList<>();
    private final List<String> isTitles = new ArrayList<>();


    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return isFrangment.get(i);
    }

    @Override
    public int getCount() {
        return isFrangment.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return isTitles.get(position);
    }

    public void AddFragment (Fragment fragment, String title){
        isFrangment.add(fragment);
        isTitles.add(title);
    }
}
