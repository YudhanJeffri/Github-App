package com.cobacobaaja.consumerapp.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cobacobaaja.consumerapp.R;
import com.cobacobaaja.consumerapp.adapter.UserFavoriteAdapter;
import com.cobacobaaja.consumerapp.model.User;

import static com.cobacobaaja.consumerapp.database.DbContract.CONTENT_URI;

public class FavoriteActivity extends AppCompatActivity{
    private UserFavoriteAdapter adapter;
    private RecyclerView rv;
    Cursor mListFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        rv = findViewById(R.id.rv_favorite);
        String type = "USER";
        User user = new User();
        user.setType(type);
        adapter = new UserFavoriteAdapter(this);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);
        new threadFav().execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(FavoriteActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private class threadFav extends AsyncTask<Void,Void, Cursor> {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Cursor doInBackground(Void... voids) {
            return FavoriteActivity.this.getContentResolver().query(CONTENT_URI,null,null,null);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            mListFav = cursor;
            adapter.setUserCursor(mListFav);
            adapter.notifyDataSetChanged();
        }
    }
}