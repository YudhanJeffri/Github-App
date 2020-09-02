package com.cobacobaaja.consumerapp.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.cobacobaaja.consumerapp.R;
import com.cobacobaaja.consumerapp.model.User;
import com.google.android.material.tabs.TabLayout;


public class DetailActivity extends AppCompatActivity {
    private User user;
    TextView textView;
    ImageView imageView;
    public static String login,type,follower;

    public static final String EXTRA_USER = "extra_user";
    public static final String EXTRA_POSITION = "extra_position";


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        getSupportActionBar().setElevation(0);
        user = getIntent().getParcelableExtra(EXTRA_USER);
        textView = (TextView) findViewById(R.id.name_user);
        imageView = (ImageView) findViewById(R.id.image_user);
        final User user = getIntent().getParcelableExtra(EXTRA_USER);
        type = "USER";
        follower = user.getLogin();
        user.setType(type);
        textView.setText(user.getLogin());
        Glide.with(getApplicationContext())
                .load(user.getAvatar_url())
                .into(imageView);
        setdetail();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(intent);
    }
    private void setdetail(){
        user = new User();
        user = getIntent().getParcelableExtra(EXTRA_USER);
        type = user.getType();
        login = user.getLogin();
    }

}
