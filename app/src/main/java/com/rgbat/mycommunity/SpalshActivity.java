package com.rgbat.mycommunity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class SpalshActivity extends AppCompatActivity {

    private TextView appName;
    private ImageView imageView;
    private Button btnClick;
    public static List<String> catList =new ArrayList<>();
//    public static List<CategoryModel> catList = new ArrayList<>();
//    public static int selected_cat_index = 0;
//    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_spalsh);
        appName = findViewById(R.id.appName);
        imageView = findViewById(R.id.imageView);
       // btnClick = findViewById(R.id.click);
        //firestore = FirebaseFirestore.getInstance();
        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setElevation(60);
     //   bottomNavigationView.setSelectedItemId(R.id.product);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.find_job:

                        Intent apartment = new Intent(getBaseContext(),MainActivity.class);
                        startActivity(apartment);

                        break;
                    case R.id.product:

                       Intent apartment1 = new Intent(getBaseContext(),Productsctivity.class);
                        startActivity(apartment1);
                        break;
                    case R.id.find_apartment:

                        Intent apartment3 = new Intent(getBaseContext(),Apartemnts2Activity.class);
                        startActivity(apartment3);
                        break;

                    case R.id.share_job:

                        Intent apartment4 = new Intent(getBaseContext(),postJobsActivity.class);
                        startActivity(apartment4);
                        break;
                }
                return false;
            }
        });




        /////  font to textView
        // Typeface typeface = ResourcesCompat.getFont(this,R.we need the font file in resource)
        // appName.setTypeface(typeface);
        ((AnimationDrawable)imageView.getBackground()).start();

//        btnClick.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Handler handler = new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        Intent intent = new Intent(getBaseContext(),MainActivity.class);
//                        startActivity(intent);
//
//                    }
//                },1000);
//
//            }
//        });
        ///// animation
//        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.my_anim);
//        appName.setAnimation(anim);

//        new Thread() {
//            public void run() {
////DATA FROM SERVER
        //loadData();
//                try {
//                    sleep(4000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//
//                }
//                Intent intent = new Intent(getBaseContext(),MainActivity.class);
//                startActivity(intent);
//
//            }
//
//
//        }.start();
    }
}

