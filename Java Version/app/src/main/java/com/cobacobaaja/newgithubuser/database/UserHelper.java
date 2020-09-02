package com.cobacobaaja.newgithubuser.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cobacobaaja.newgithubuser.model.User;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.cobacobaaja.newgithubuser.database.DbContract.TABLE_NAME;
import static com.cobacobaaja.newgithubuser.database.DbContract.UserColumns.AVATAR_URL;
import static com.cobacobaaja.newgithubuser.database.DbContract.UserColumns.LOGIN;

public class UserHelper {
    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseHelper dataBaseHelper;
    private static UserHelper INSTANCE;
    private static SQLiteDatabase database;

    public UserHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static UserHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
        database = dataBaseHelper.getReadableDatabase();
    }

    public void close() {
        dataBaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public Cursor queryAll() {
        return database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC");
    }
    public Cursor queryById(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public int update(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int delete(String login){
        return database.delete(TABLE_NAME, LOGIN+ " = " + "'"+login+"'" , null);
    }
    public long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }
    public int deleteById(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
    public ArrayList<User> getAllFavorites(){
        ArrayList<User> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE,null,
                null,
                null,
                null,
                null,
                LOGIN+ " ASC",
                null);
        cursor.moveToFirst();
        User userResponse;
        if (cursor.getCount()>0){
            do{
                userResponse = new User();
                userResponse.setLogin(cursor.getString(cursor.getColumnIndexOrThrow(LOGIN)));
                userResponse.setAvatar_url(cursor.getString(cursor.getColumnIndexOrThrow(AVATAR_URL)));
                arrayList.add(userResponse);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        } cursor.close();
        return arrayList;
    }

    public Boolean readRowData(String login){
        open();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + LOGIN+ " " + " LIKE " +"'"+login+"'" ;
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount()>0){
            return true;
        } else if (cursor.getCount() == 0){
            return false;
        }
        return false;
    }


}
