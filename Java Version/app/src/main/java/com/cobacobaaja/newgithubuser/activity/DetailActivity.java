package com.cobacobaaja.newgithubuser.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.cobacobaaja.newgithubuser.R;
import com.cobacobaaja.newgithubuser.database.DatabaseHelper;
import com.cobacobaaja.newgithubuser.database.UserHelper;
import com.cobacobaaja.newgithubuser.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;


public class DetailActivity extends AppCompatActivity {
    private User user;
    TextView textView;
    ImageView imageView;
    FloatingActionButton fabAdd;
    public static String login,type,follower;
    Boolean action = true;
    Boolean insert = true;
    Boolean delete = true;
    UserHelper userHelper;

    public static final String EXTRA_USER = "extra_user";
    public static final String EXTRA_POSITION = "extra_position";


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
        fabAdd = findViewById(R.id.floatingadd);
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
        userHelper = UserHelper.getInstance(DetailActivity.this);
        

        setdetail();
        type();
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFavorite();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void setFavorite(){
        DatabaseHelper myDB = new DatabaseHelper(DetailActivity.this);
        if (insert && action && type.equals("USER")){
            user.setLogin(user.getLogin());
            user.setAvatar_url(user.getAvatar_url());
            long result = myDB.addUser(user);
            if (result > 0 ){
                Toast.makeText(DetailActivity.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                fabAdd.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_favorite_white_24dp));
            }else {
                Toast.makeText(DetailActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        } else if (delete && !action && type.equals("USER")){
            DatabaseHelper db = new DatabaseHelper(DetailActivity.this);
            db.deleteUser(user.getLogin());
            fabAdd.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_favorite_border_white_24dp));
            Intent intent = new Intent(DetailActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
    private void type() {
        if (type.equals("USER") && userHelper.readRowData(login))
        {
            action = false;
            delete = true;
            fabAdd.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white_24dp));
        }
        else if (type.equals("USER") && !userHelper.readRowData(login))
        {
            action = true;
            insert = true;
            fabAdd.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white_24dp));
        }
    }
    private void setdetail(){
        user = new User();
        user = getIntent().getParcelableExtra(EXTRA_USER);
        type = user.getType();
        login = user.getLogin();
    }

}
