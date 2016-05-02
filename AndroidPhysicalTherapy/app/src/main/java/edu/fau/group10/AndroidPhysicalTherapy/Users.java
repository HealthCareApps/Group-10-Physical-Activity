package edu.fau.group10.AndroidPhysicalTherapy;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by myers on 4/27/2016.
 */
public class Users {
    private int _id;
    private String _username;
    private String _password;
    private String _vista;

    public Users() {
    }

    public Users(String username, String password, String vista) {
        this._username = username;
        this._password = password;
        this._vista = vista;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public String get_vista() {
        return _vista;
    }

    public void set_vista(String _vista) {
        this._vista = _vista;
    }

    public static class MyDBHandler extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "users.db";
        public static final String TABLE_USERS = "users";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_USERNAME = "username";
        public static final String COLUMN_VISTA = "vista";
        public static final String COLUMN_PASSWORD = "password";

        public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_USERS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_VISTA + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT " +
                    ");";
            db.execSQL(query);
        }

        //needed only if table is upgraded (new columns need to be added, removed, etc)
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            onCreate(db);
        }

        //Add new rows to the database
        public void addUser(Users user) {
            // ContentValues values = new ContentValues();
            //values.put(COLUMN_USERNAME, user.get_username());
            //values.put(COLUMN_PASSWORD, user.get_password());
            //values.put(COLUMN_VISTA, user.get_vista());

            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("INSERT INTO USERS (username, password, vista) VALUES ('" + user.get_username() + "','" + user.get_password() + "','" + user.get_vista() + "');");
            db.close();
        }

        //delete users from the database
        public void deleteUser(String username) {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("DELETE FROM " + TABLE_USERS + " WHERE" + COLUMN_USERNAME + "=\"" + username + "\";");

        }

        public String getSingleEntry(String userName)
        {
            SQLiteDatabase db = getWritableDatabase();


            Cursor cursor = db.query("USERS", null, " username=?", new String[]{userName}, null, null, null);
            if(cursor.getCount()<1) // UserName Not Exist
            {
                cursor.close();
                return "Username does not exist - Try again";
            }
            cursor.moveToFirst();
            String password = cursor.getString(3);

            //cursor.getString(cursor.getColumnIndex("password"));
            cursor.close();
            return password;
        }

        public Boolean checkforUser(String username)
        {
            SQLiteDatabase db = getWritableDatabase();
            Cursor cursor = db.query("USERS", null, " username=?", new String[]{username}, null, null, null);
            if(cursor.getCount()<1) // UserName Not Exist
            {
                return true;
            }
            else return false;
        }



        //Print database as string for testing
        public String databaseToString() {
            String dbString = "";
            SQLiteDatabase db = getWritableDatabase();
            String query = "SELECT * FROM " + TABLE_USERS + " WHERE 1";

            //cursor points to the location in results(i.e. row 1)
            Cursor c = db.rawQuery(query, null);
            c.moveToFirst();
            String user = c.getString(2);


            while (!c.isAfterLast()) {
                if (c.getString(c.getColumnIndex("username")) != null) {
                    dbString += c.getString(c.getColumnIndex("username"));
                    dbString += "\n";
                }
            }
            db.close();
            //return dbString;
            return user;
        }

    }
}
