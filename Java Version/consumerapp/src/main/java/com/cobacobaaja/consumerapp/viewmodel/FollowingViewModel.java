package com.cobacobaaja.consumerapp.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cobacobaaja.consumerapp.BuildConfig;
import com.cobacobaaja.consumerapp.model.User;
import com.cobacobaaja.consumerapp.retrofit.Client;
import com.cobacobaaja.consumerapp.retrofit.Service;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowingViewModel extends ViewModel {
    private MutableLiveData<ArrayList<User>> listUsers = new MutableLiveData<>();

    public void setUsersView(String username) {
        try {
            Service service = Client.getRetrofitInstance().create(Service.class);
            String api = BuildConfig.TOKEN;
            Call<ArrayList<User>> eventCall = service.userFollowing(api,username);
            eventCall.enqueue(new Callback<ArrayList<User>>() {
                private Response<ArrayList<User>> response;
                @Override
                public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                    this.response = response;
                    listUsers.setValue(response.body());
                }

                @Override
                public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                    Log.e("failure", t.toString());

                }
            });
        } catch (Exception e) {
            Log.d("token e", String.valueOf(e));
        }
    }
    public LiveData<ArrayList<User>> getUsers() {
        if (listUsers == null) {
            listUsers = new MutableLiveData<>();
        }
        return listUsers;
    }
}