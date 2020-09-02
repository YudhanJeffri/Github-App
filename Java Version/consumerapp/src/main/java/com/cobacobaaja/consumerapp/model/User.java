package com.cobacobaaja.consumerapp.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.cobacobaaja.consumerapp.database.DbContract;

import static com.cobacobaaja.consumerapp.database.DbContract.getUserFavoriteString;
import static com.cobacobaaja.consumerapp.database.DbContract.getUserFavoriteInt;

public class User implements Parcelable {
    public int user_id;
    public String login;
    public String avatar_url;
    public String type;

    public User(String login, String avatar_url,String type){
        this.login = login;
        this.avatar_url = avatar_url;
        this.type = type;
    }

    public User() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User(int user_id, String login, String avatar_url, String type) {
        this.user_id = user_id;
        this.login = login;
        this.avatar_url = avatar_url;
        this.type = type;

    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
    protected User(Parcel in) {
        user_id = in.readInt();
        login = in.readString();
        avatar_url = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(user_id);
        dest.writeString(login);
        dest.writeString(avatar_url);
        dest.writeString(type);
    }
    public User(Cursor cursor) {
        this.user_id = getUserFavoriteInt(cursor, DbContract.UserColumns._ID);
        this.login = getUserFavoriteString(cursor, DbContract.UserColumns.LOGIN);
        this.avatar_url = getUserFavoriteString(cursor, DbContract.UserColumns.AVATAR_URL);

    }
}
