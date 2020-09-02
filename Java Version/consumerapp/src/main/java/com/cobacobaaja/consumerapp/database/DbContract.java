package com.cobacobaaja.consumerapp.database;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DbContract {
    public static final String AUTHORITY = "com.cobacobaaja.newgithubuser";
    public static String TABLE_NAME = "user";
    // untuk membuat URI content://com.cobacobaaja.consumerapp/user
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();
    public static final class UserColumns implements BaseColumns {
        public static final String LOGIN = "login";
        public static final String AVATAR_URL = "avatar_url";
    }
    public static int getUserFavoriteInt(Cursor cursor,String coloumnUser){
        return cursor.getInt(cursor.getColumnIndex(coloumnUser));
    }
    public static String getUserFavoriteString(Cursor cursor, String coloumnUser){
        return cursor.getString(cursor.getColumnIndex(coloumnUser));
    }
}
