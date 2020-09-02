package com.cobacobaaja.consumerapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cobacobaaja.consumerapp.R;
import com.cobacobaaja.consumerapp.adapter.UserAdapter;
import com.cobacobaaja.consumerapp.model.User;
import com.cobacobaaja.consumerapp.retrofit.Client;
import com.cobacobaaja.consumerapp.retrofit.Service;
import com.cobacobaaja.consumerapp.viewmodel.SearchViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cobacobaaja.consumerapp.activity.DetailActivity.EXTRA_USER;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private UserAdapter adapter;
    private RecyclerView recyclerView;
    private SearchViewModel searchViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Service service = Client.getRetrofitInstance().create(Service.class);
        Call<List<User>> call = service.user();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateDataList(List<User> list) {
        recyclerView = findViewById(R.id.rv_user);
        adapter = new UserAdapter(this, list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        searchViewModel = new ViewModelProvider(this,new ViewModelProvider.NewInstanceFactory()).get(SearchViewModel.class);
        SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
        searchView.setQueryHint(getResources().getString(R.string.app_name));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                User user = new User();
                user.setLogin(query);
                user.setType("USER");
                searchViewModel.setUsers(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchViewModel.getUsers().observe(this, new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> userss) {
                if (userss != null) {
                    adapter.setTasks(userss);
                } else {
                    Toast.makeText(MainActivity.this, "No Result", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.favorite_action) {
            startActivity(new Intent(this, FavoriteActivity.class));
        } else if (item.getItemId() == R.id.settings){
            startActivity(new Intent(this,SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        String type = "USER";
        User user = new User();
        user.setType(type);
        Intent detail = new Intent(getApplicationContext(), DetailActivity.class);
        detail.putExtra(EXTRA_USER,user);
        startActivity(detail);
    }
}