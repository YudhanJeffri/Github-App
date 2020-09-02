package com.cobacobaaja.newgithubuser.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cobacobaaja.newgithubuser.activity.MainActivity;
import com.cobacobaaja.newgithubuser.model.User;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchViewModel extends ViewModel {
    MainActivity mainActivity;
    private MutableLiveData<ArrayList<User>> listUsers = new MutableLiveData<>();

    public void setUsers(final String cities) {
        final ArrayList<User> listItems = new ArrayList<>();
        String url = "https://api.github.com/search/users?q="+cities;
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Authorization","token 9494232321425c54e8e3c3b3e5b737d4ac454de1");
        client.addHeader("User-Agent","request");
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    //parsing json
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray items = responseObject.getJSONArray("items");
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject users = items.getJSONObject(i);
                        User user = new User();
                        user.setLogin(users.getString("login"));
                        user.setAvatar_url(users.getString("avatar_url"));
                        listItems.add(user);
                    }
                    listUsers.postValue(listItems);
                } catch (Exception e) {
                    Log.d("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }
    public LiveData<ArrayList<User>> getUsers() {
        return listUsers;
    }
}
