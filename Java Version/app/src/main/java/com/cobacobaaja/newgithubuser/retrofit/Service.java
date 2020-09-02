package com.cobacobaaja.newgithubuser.retrofit;

import com.cobacobaaja.newgithubuser.model.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface Service {
    @GET("users")
    Call<List<User>> user();
    @GET("users/{username}/followers")
    Call<ArrayList<User>> userFollower(@Header("Authorization") String authorization,
                                   @Path("username") String username);
    @GET("users/{username}/following")
    Call<ArrayList<User>> userFollowing(@Header("Authorization") String authorization,
                                       @Path("username") String username);
}
