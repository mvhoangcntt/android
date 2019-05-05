package com.mvhoang.welcome.baitapcuoiki;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mvhoang.welcome.baitapcuoiki.adapter.ViewPagerAdapter;
import com.mvhoang.welcome.baitapcuoiki.fragmentTabLayout.FragmentA;
import com.mvhoang.welcome.baitapcuoiki.fragmentTabLayout.FragmentB;

public class ViewPagerTabLayoutActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout_view_pager);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        // add fragment
        adapter.AddFragment(new FragmentA(),"Kiểu 1");
        adapter.AddFragment(new FragmentB(),"Kiểu 2");



        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        // nêu không muốn chữ mà là icon thì
//        tabLayout.getTabAt(0).setIcon(R.drawable.ic_menu_camera);
//        tabLayout.getTabAt(2).setIcon(R.drawable.ic_menu_gallery);

        // remove actionbar shadow the action bar

        ActionBar actionBar = getSupportActionBar();
        actionBar.setElevation(0);
    }

    // tạo options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back_thoigianbieu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    // goi den menu item add_congviec
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menuback){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
