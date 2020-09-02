package com.cobacobaaja.newgithubuser.database;

import android.net.Uri;
import android.provider.BaseColumns;

public class DbContract {
    public static final String AUTHORITY = "com.cobacobaaja.newgithubuser";
    public static String TABLE_NAME = "user";
    // untuk membuat URI content://com.cobacobaaja.newgithubuser/user
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();
    public static final class UserColumns implements BaseColumns {
        public static final String LOGIN = "login";
        public static final String AVATAR_URL = "avatar_url";
    }
}
