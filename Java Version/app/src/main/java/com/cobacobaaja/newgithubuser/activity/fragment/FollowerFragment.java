package com.cobacobaaja.newgithubuser.activity.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cobacobaaja.newgithubuser.R;
import com.cobacobaaja.newgithubuser.activity.DetailActivity;
import com.cobacobaaja.newgithubuser.adapter.UserFollowerAdapter;
import com.cobacobaaja.newgithubuser.model.User;
import com.cobacobaaja.newgithubuser.viewmodel.FollowerViewModel;

import java.util.ArrayList;
import java.util.Objects;

import static com.cobacobaaja.newgithubuser.activity.DetailActivity.EXTRA_USER;

/**
 * made by yudhan 30/08/2003
 */
public class FollowerFragment extends Fragment {
    private RecyclerView recyclerView;
    private FollowerViewModel followerViewModel;
    private UserFollowerAdapter userAdapter;

    ArrayList<User>mListFav = new ArrayList<>();
    private SearchView search;
    User user;
    String login;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_follower, container, false);
        recyclerView = v.findViewById(R.id.rv_follower);
        search = v.findViewById(R.id.search_follower);
        //init view model
        userAdapter = new UserFollowerAdapter(getContext(),mListFav);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(userAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        followerViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(FollowerViewModel.class);
        setdetail();
        search.setQueryHint(getResources().getString(R.string.search_user_follower));
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getContext(), query, Toast.LENGTH_SHORT).show();
                followerViewModel.setUsersView(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        followerViewModel.setUsersView(DetailActivity.follower);
        followerViewModel.getUsers().observe(getViewLifecycleOwner(), new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> userss) {
                if (userss != null) {
                    userAdapter.setTasks(userss);
                } else {
                    Toast.makeText(getContext(), "No Result", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return v;
    }
    private void setdetail(){
        user = new User();
        user = Objects.requireNonNull(getActivity()).getIntent().getParcelableExtra(EXTRA_USER);
        login = user.getLogin();
    }

}