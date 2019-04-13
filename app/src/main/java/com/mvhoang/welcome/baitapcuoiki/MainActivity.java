package com.mvhoang.welcome.baitapcuoiki;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView tvusername, tvemail;
    TextView tv1 , tv2;
    private SQLiteDatabase db;

    ArrayList<String> mangTieude, Noidung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa();
        initdata();
        loaddata();
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        View v = navigationView.getHeaderView(0);
//
//        tv1 =(TextView) findViewById(R.id);
        mangTieude.add("thu thoi");
        Noidung.add("Khong");



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Bạn đã chạm vào tôi !", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(getApplicationContext(),XoayManHinhActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this, "Trá hình thôi !", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_congviec) {
            Toast.makeText(this, "Bạn chọn công việc !", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),MainCongViecActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Toast.makeText(this, "Bạn chọn tablayout !", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),ViewPagerTabLayoutActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_slideshow) {
            Toast.makeText(this, "Bạn muốn chơi game !", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),HomeMiniGameActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            String sql = "DELETE FROM user ";
            db.execSQL(sql);
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void loaddata(){
        String sql = "SELECT * FROM user";
        Cursor cursor = db.rawQuery(sql,null);
        String email1 = "", name1 = "", pass1 = "";
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            int id = cursor.getInt(0);
            name1 = cursor.getString(1);
            email1 = cursor.getString(2);
            pass1 = cursor.getString(3);
            cursor.moveToNext();
        }

        if (name1 == ""){
            Intent intent = getIntent();
            String name = intent.getStringExtra("us");
            String pass = intent.getStringExtra("pa");
            String email = intent.getStringExtra("em");

            if (name == null){
                Intent intent1 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent1);
            }else{
                insert(name,pass,email);
            }
        }
        tvusername.setText(name1);
        tvemail.setText(email1);


        Toast.makeText(this, ""+name1, Toast.LENGTH_SHORT).show();

    }
    private void insert(String name, String pass, String email){
        String sql = "INSERT INTO user(username , email, password)VALUES('"+ name +"','"+ email +"','"+ pass +"')";
        db.execSQL(sql);
    }
    private void initdata(){
        db = openOrCreateDatabase("quanly.db",MODE_PRIVATE,null);
        String sql = "CREATE TABLE IF NOT EXISTS user(id integer primary key autoincrement, username text, email text, password text)";
        db.execSQL(sql);
    }


    private void anhxa(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        View v = navigationView.getHeaderView(0);
        tvusername = (TextView) v.findViewById(R.id.tvusername);
        tvemail = (TextView) v.findViewById(R.id.tvemail);
    }

}
