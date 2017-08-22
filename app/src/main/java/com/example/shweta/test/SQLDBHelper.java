package com.example.shweta.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.security.Key;
import java.util.jar.Attributes;

/**
 * Created by Shweta on 7/4/2017.
 */
public class SQLDBHelper extends SQLiteOpenHelper {
    public static final String DataBaseName="User_db";
    public static final String TableName="User";
    public static final String SR_NO="SN";
    public static final String FirstName = "First_Name";
    public static final String LastName = "Last_Name";
    public static final String Email="Email";
    public static final String Password="Password";
    public static final String DATEOFBIRTH="dob";
    Context mContext;
    public SQLDBHelper(Context context) {
        super(context,DataBaseName,null,1);
        this.mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+TableName+" ( "+SR_NO+ " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +FirstName+ " VARCHAR , "+LastName+ " VARCHAR, "+Email+ " VARCHAR, "+Password+ " VARCHAR, " + DATEOFBIRTH + " VARCHAR ) ");}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS"+TableName+"");
    }
    public boolean insertUserDetails(String firstname,String lastname,String email,String password,String DOB){
        SQLiteDatabase db =this.getWritableDatabase();
        if(this.checkUserExist(email)){
            return false;
        } else{

            ContentValues contentValues = new ContentValues();
            contentValues.put(FirstName, firstname);
            contentValues.put(Email, email);
            contentValues.put(LastName,lastname);
            contentValues.put(Password, password);
contentValues.put(DATEOFBIRTH,DOB);

            db.insert(TableName, null, contentValues);
            db.close();
            return true;
        }

        }
    
    public boolean checkUserExist(String email){
        SQLiteDatabase database=this.getReadableDatabase();
        String query = "SELECT "+Email+" from "+TableName+" WHERE "+Email+" = '"+email+"' ";
        Cursor c= database.rawQuery(query,null);
        if(c.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }
    public Cursor authenticate(String email,String password){
        SQLiteDatabase db=this.getReadableDatabase();
        String query = "SELECT * from "+TableName+" WHERE "+Email+" = '"+email+"' AND "+Password+" = '"+password+"'";
        Cursor cursor = db.rawQuery(query,null);
        return  cursor;
    }

/*public boolean updatedetails(String updatefirst,String updatelast,String updatedate,String updatePass){
    SQLiteDatabase udb =this.getWritableDatabase();
    ContentValues contentValues=new ContentValues();
    contentValues.put(FirstName,updatefirst);
    contentValues.put(LastName,updatelast);
    contentValues.put(DATEOFBIRTH,updatedate);
    contentValues.put(Password,updatePass);
    int i= udb.update(DataBaseName,contentValues,FirstName+"="+updatefirst,null);
    return i>0;
}*/
    public Cursor update(String update_firstname,String update_lastname,String update_password, String email){
        SQLiteDatabase db=this.getWritableDatabase();
        String query="UPDATE  "+TableName+" SET "+FirstName+" = '"+update_firstname+"' , "+LastName+" = '"+update_lastname+"' , "+Password+" = '"+update_password+"' where "+Email+" = '"+ email +"' ";
        Cursor i = db.rawQuery(query,null);
        return i;

    }
    public void update_byEMAIL(String email, String fname, String lname){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FirstName, fname);
        values.put(LastName, lname);
        db.update(TableName, values, Email+"="+email, null);
    }

}
