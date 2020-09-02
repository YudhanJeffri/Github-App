package com.cobacobaaja.newgithubuser.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cobacobaaja.newgithubuser.R;
import com.cobacobaaja.newgithubuser.adapter.UserAdapter;
import com.cobacobaaja.newgithubuser.database.UserHelper;
import com.cobacobaaja.newgithubuser.model.User;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity{
    private UserAdapter adapter;
    private RecyclerView rv;

    ArrayList<User>mListFav = new ArrayList<>();
    UserHelper userHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        rv = findViewById(R.id.rv_favorite);
        String type = "USER";
        userHelper = UserHelper.getInstance(FavoriteActivity.this);
        userHelper.open();
        storeDataInArrays();
        adapter = new UserAdapter(this);
        rv.setAdapter(adapter);
        rv.setHasFixedSize(true);
        User user = new User();
        user.setType(type);
        rv.setLayoutManager(new LinearLayoutManager(FavoriteActivity.this));
    }
    public void storeDataInArrays(){
        UserAdapter adapter = new UserAdapter(this);
        mListFav = userHelper.getAllFavorites();
        adapter.setTasks(mListFav);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(FavoriteActivity.this, MainActivity.class);
        startActivity(intent);
    }
}