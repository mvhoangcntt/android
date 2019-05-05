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
import android.widget.LinearLayout;
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
import java.util.Random;

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
        addhome();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Snackbar.make(view, "Bạn đã chạm vào tôi !", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//                Intent intent = new Intent(getApplicationContext(),XoayManHinhActivity.class);
//                startActivity(intent);
//            }
//        });

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
        } else if (id == R.id.nav_time) {
            Toast.makeText(this, "Bạn chọn thời gian biểu !", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),ViewPagerTabLayoutActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_muctieu) {
            Intent intent = new Intent(getApplicationContext(),MainMucTieuActivity.class);
            startActivity(intent);
        }else if (id == R.id.nav_slideshow) {
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

    private void addhome(){
        mangTieude = new ArrayList<String>();
        Noidung = new ArrayList<String>();
        mangTieude.add("Theo bạn, thời gian là gì?");
        Noidung.add("Với tôi, thời gian là thứ tài sản thượng đế ban tặng muôn loài đồng đều nhất, có giới hạn, không thay đổi được và không mua, bán được. Vì những đặc điểm này, thực sự chúng ta không bao giờ quản lý được thời gian, điều duy nhất chúng ta có thể làm được là sắp xếp và sử dụng thời gian như thế nào cho hiệu quả nhất. Người sử dụng thời gian hiệu quả, là người “giàu có” thời gian hơn.");

        mangTieude.add("Lên kế hoạch trước các việc cần làm");
        Noidung.add("Phương pháp sử dụng danh sách lên kế hoạch cần làm để viết ra mọi thứ là một cách rất tuyệt để kiểm soát các dự án và công việc của bạn, khiến bản thân bạn sắp xếp có tổ chức hơn.\n" +
                "Sắp xếp thứ tự ưu tiên cho danh sách việc cần làm của bạn, sẽ giúp bạn tập trung và dành nhiều thời gian vào những việc thật sự quan trọng. Đánh giá công việc một cách logic, có trình tự, sử dụng hệ thống thứ tự ABCD được diễn giải trong các ví dụ khoa học về quản lí thời gian.");

        mangTieude.add("Nhận ra thói quen xấu");
        Noidung.add("Bạn nên lập ra danh sách các thói quen xấu tiêu tốn thời gian của bạn, phá hủy các mục tiêu và hạn chế thành công của bạn, sau khi lập ra danh sách thì bạn hãy loại bỏ từng thói quen một ra khỏi cuộc sống của bạn một cách logic. Bạn hãy nhớ rằng cách tốt nhất để loại bỏ một thói quen xấu là thay thế bằng những thói quen tốt.");

        mangTieude.add("Hãy học cách nói “không “");
        Noidung.add("Do hứa hẹn quá nhiều, nhiều người bị quá tải vì có quá nhiều việc, họ nói “có” trong khi đáng lẽ ra họ phải nói “không”. Vì vậy, bạn hãy học cách nói “không” với những yêu cầu có mức ưu tiên thấp, và bạn sẽ có nhiều thời gian để làm những việc quan trọng hơn.");

        mangTieude.add("Không ngừng nâng cao bản thân");
        Noidung.add("Bạn nên dành nhiều thời gian trong kế hoạch làm việc để học những điều mới và phát triển khả năng tiềm ẩn của bạn. Ví dụ bạn có thể đăng kí một lớp học ngắn hạn, đọc một quyển sách hay hoặc là tham gia một chương trình đào tạo,…Không ngừng nâng cao kiến thức của bạn sẽ tăng khả năng tìm được thành công trong sự nghiệp và là một hướng đi đáng tin cậy để trở thành người độc lập về tài chính.");

        mangTieude.add("Tạo cho mình tính kỉ luật và thói quen");
        Noidung.add("Thay vì thường xuyên kéo lê cả đống việc, cần xác định hạn chót để hoàn thành công việc. Một mẹo nhỏ là nếu cần hoàn tất một công việc vào 5 giờ chiều thì hãy bắt mình kết thúc vào lúc 12 giờ trưa. Nên giải quyết các việc khó trước, Chúng ta nên tập trung cho mình thói quen làm việc chăm chỉ và có kế hoạch trước đó chính là dấu hiệu của một người thành đạt.");

        mangTieude.add("Suy nghĩ trước khi hành động");
        Noidung.add("Bao nhiêu lần bạn nói “có” với một số việc, và sau đó bạn lại cảm thấy hối tiếc? Vì vậy trước khi hứa hẹn làm một công việc mới, bạn hãy ngừng lại để suy nghĩ một chút trước khi đưa ra câu trả lời của mình. Điều này sẽ tránh cho bạn khỏi bị làm quá nhiều việc.");

        mangTieude.add("Đừng làm việc của người khác");
        Noidung.add("Bạn có thói quen làm việc hộ người khác bởi vì bạn mang tâm lý của “người hùng” hay không? Nếu bạn làm như thế, bạn sẽ tốn rất nhiều thời gian của bạn. Thay vào đó, bạn hãy tập trung vào những dự án và mục tiêu của chính bản thân bạn, học cách giao việc một cách hiệu quả, và dạy người khác làm thế nào để họ làm công việc của họ.");

        mangTieude.add("Ghi nhật ký về mục tiêu của bạn");
        Noidung.add("Thời gian làm việc nhằm mục đích lập và đánh giá các mục tiêu của bạn. Ban đầu, bạn hãy ghi ra mục tiêu của bạn, sau đó bạn viết quá trình thực hiện của bạn đối với từng mục tiêu. Nhìn lại nhật ký mục tiêu hàng tuần và đảm bảo bạn vẫn đang đi đúng hướng.");

        mangTieude.add("Đừng là người cầu toàn");
        Noidung.add("Một vài công việc yêu cầu bạn phải cố gắng hết sức. Ví dụ, khi gửi một email ngắn tới đồng nghiệp, bạn đừng nên dành thời gian nhiều hơn một vài phút để làm điều đó. Bạn hãy học cách phân biệt giữa những công việc cần phải hoàn thành một cách xuất sắc với những công việc chỉ cần làm được mà thôi.");

        mangTieude.add("Nhận biết những công việc “lấp chỗ trống”");
        Noidung.add("Khi bạn có danh sách những việc cần làm với rất nhiều việc quan trọng, bạn hãy thận trọng để không bị rối trí bởi những công việc “lấp chỗ trống”. Những công việc đã được sắp xếp trong sổ hoặc trong giấy tờ của bạn có thể đợi tới khi bạn giải quyết những công việc có thứ tự ưu tiên cao nhất.");

        int min = -1;
        int max = 10;
        Random random = new Random();
        int vitri = random.nextInt(max-min+1)+min;
        tv1.setText(mangTieude.get(vitri));
        tv2.setText(Noidung.get(vitri));
    }


    private void anhxa(){
        NavigationView navigationView = findViewById(R.id.nav_view);
        View v = navigationView.getHeaderView(0);
        tvusername = (TextView) v.findViewById(R.id.tvusername);
        tvemail = (TextView) v.findViewById(R.id.tvemail);
        LinearLayout linearLayout = findViewById(R.id.abcaaa);
        View view = linearLayout.getRootView();
        tv1 = view.findViewById(R.id.tv1);
        tv2 = view.findViewById(R.id.tv2);
    }

}
