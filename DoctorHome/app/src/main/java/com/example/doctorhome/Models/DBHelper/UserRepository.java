package com.example.doctorhome.Models.DBHelper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.doctorhome.Models.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private final String TABLE_NAME = "USER";
    private DBHelper dbHelper;

    public UserRepository(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public List<User> getAllUser() {
        String statement = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(statement, null);

        List<User> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            list.add(new User(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    cursor.getString(7),
                    cursor.getString(8)));
        }

        cursor.close();
        return list;
    }

    public ArrayList<User> getUserByMajorID(int major_ID) {
        String statement = "SELECT * FROM " + TABLE_NAME + " WHERE major_id = '" + major_ID + "'";

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(statement, null);

        ArrayList<User> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            list.add(new User(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    cursor.getString(7),
                    cursor.getString(8)));
        }

        cursor.close();
        return list;
    }

    public ArrayList<User> getAlldoctor() {
        String statement = "SELECT * FROM " + TABLE_NAME + " WHERE major_id NOT IN (0,9999)";

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(statement, null);

        ArrayList<User> list = new ArrayList<>();

        while (cursor.moveToNext()) {
            list.add(new User(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getInt(6),
                    cursor.getString(7),
                    cursor.getString(8)));
        }

        cursor.close();
        return list;
    }

    public void addUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("fullname", user.getFullname());
        values.put("email", user.getEmail());
        values.put("phone", user.getPhone());
        values.put("major_id", user.getMajorId());
        values.put("description", user.getDescription());
        values.put("image", user.getImage());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    @SuppressLint("Range")
    public User getUserByUsername(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                "id",
                "username",
                "password",
                "fullname",
                "email",
                "phone",
                "major_id",
                "description",
                "image"
        };

        String selection = "username LIKE ?";
        String[] selectionArgs = { username };

        Cursor cursor = db.query(
                "USER",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        User user = null;

        if (cursor != null && cursor.moveToFirst()) {
            user = new User(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("username")),
                    cursor.getString(cursor.getColumnIndex("password")),
                    cursor.getString(cursor.getColumnIndex("fullname")),
                    cursor.getString(cursor.getColumnIndex("email")),
                    cursor.getString(cursor.getColumnIndex("phone")),
                    cursor.getInt(cursor.getColumnIndex("major_id")),
                    cursor.getString(cursor.getColumnIndex("description")),
                    cursor.getString(cursor.getColumnIndex("image"))
            );
        }

        // Close the cursor after use
        if (cursor != null) {
            cursor.close();
        }

        return user;
    }
    @SuppressLint("Range")
    public User getUserById(int usernamex) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String username = String.valueOf(usernamex);
        String[] projection = {
                "id",
                "username",
                "password",
                "fullname",
                "email",
                "phone",
                "major_id",
                "description",
                "image"
        };

        String selection = "id LIKE ?";
        String[] selectionArgs = { username };

        Cursor cursor = db.query(
                "USER",
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        User user = null;

        if (cursor != null && cursor.moveToFirst()) {
            user = new User(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("username")),
                    cursor.getString(cursor.getColumnIndex("password")),
                    cursor.getString(cursor.getColumnIndex("fullname")),
                    cursor.getString(cursor.getColumnIndex("email")),
                    cursor.getString(cursor.getColumnIndex("phone")),
                    cursor.getInt(cursor.getColumnIndex("major_id")),
                    cursor.getString(cursor.getColumnIndex("description")),
                    cursor.getString(cursor.getColumnIndex("image"))
            );
        }

        // Close the cursor after use
        if (cursor != null) {
            cursor.close();
        }

        return user;
    }

    public boolean updateUser(User updatedUser) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("username", updatedUser.getUsername());
        values.put("password", updatedUser.getPassword());
        values.put("fullname", updatedUser.getFullname());
        values.put("email", updatedUser.getEmail());
        values.put("phone", updatedUser.getPhone());
        values.put("major_id", updatedUser.getMajorId());
        values.put("description", updatedUser.getDescription());
        values.put("image", updatedUser.getImage());

        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(updatedUser.getId())};

        try {
            int count = db.update(TABLE_NAME, values, selection, selectionArgs);

            return count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            db.close();
        }
    }
    public void deleteUser(User deleteUser){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String selection = "id = ?";
        String[] selectionArgs = {String.valueOf(deleteUser.getId())};
        db.delete(TABLE_NAME,selection,selectionArgs);
        db.close();
    }
    @SuppressLint("Range")
    public int checkUserCredentials(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = {
                "id",
                "password",
                "major_id"
        };
        String selection = "username = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        boolean isValid = false;
        int type = -1;
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String storedPassword = cursor.getString(cursor.getColumnIndex("password"));
            isValid = password.equals(storedPassword);
            if (isValid) {
                type = cursor.getInt(cursor.getColumnIndex("major_id"));
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return type;
    }

}
