package com.cobacobaaja.newgithubuser.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.cobacobaaja.newgithubuser.model.User;

import static com.cobacobaaja.newgithubuser.database.DbContract.TABLE_NAME;
import static com.cobacobaaja.newgithubuser.database.DbContract.UserColumns.AVATAR_URL;
import static com.cobacobaaja.newgithubuser.database.DbContract.UserColumns.LOGIN;

public class DatabaseHelper extends SQLiteOpenHelper {
    Context context;
    public static String DATABASE_NAME = "userapp";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_NOTE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DbContract.TABLE_NAME,
            DbContract.UserColumns._ID,
            LOGIN,
            DbContract.UserColumns.AVATAR_URL
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.TABLE_NAME);
        onCreate(db);
    }
    public long addUser(User user){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LOGIN, user.getLogin());
        cv.put(AVATAR_URL, user.getAvatar_url());
        return db.insert(TABLE_NAME,null, cv);
    }
    public void deleteUser(String login){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, LOGIN+ " = " + "'"+login+"'",null);
        if (result == -1){
            Toast.makeText(context, "Failed delete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show();
        }
    }

}
