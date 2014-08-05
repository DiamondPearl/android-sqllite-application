package com.example.sqlliteapp;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class DBHelper extends SQLiteOpenHelper {

   public static final String DATABASE_NAME = "UserInfo.db";
   public static final String TABLE_NAME = "Users";
   public static final String COLUMN_ID = "id";
   public static final String COLUMN_NAME = "name";
   public static final String COLUMN_EMAIL = "email";
   public static final String COLUMN_ADDRESS = "address";
   public static final String COLUMN_CITY = "city";
   public static final String COLUMN_PINCODE = "pincode";
   public static final String COLUMN_PHONE = "phone";

   public DBHelper(Context context)
   {
      super(context, DATABASE_NAME , null, 1);
   }

   @Override
   public void onCreate(SQLiteDatabase db) {
      // TODO Auto-generated method stub
      db.execSQL(
      "create table "+ TABLE_NAME +
      "("+ COLUMN_ID +" integer primary key, "
      		+ 	COLUMN_NAME		+	" text,"
      		+	COLUMN_EMAIL	+	" text, "
      		+	COLUMN_ADDRESS	+	" text,"
      		+	COLUMN_CITY		+	" text,"
      		+	COLUMN_PINCODE	+	" text,"
      		+	COLUMN_PHONE	+	" text)"
      );
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      // TODO Auto-generated method stub
      db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
      onCreate(db);
   }

   public boolean insertContact  (String name, String email, String address, String city, String pincode,  String phone)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();

      contentValues.put(COLUMN_NAME, name);
      contentValues.put(COLUMN_EMAIL, email);
      contentValues.put(COLUMN_ADDRESS, address);	
      contentValues.put(COLUMN_CITY, city);
      contentValues.put(COLUMN_PINCODE, pincode);
      contentValues.put(COLUMN_PHONE, phone);

      db.insert(TABLE_NAME, null, contentValues);
      return true;
   }
   public Cursor getData(int id){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor res =  db.rawQuery( "select * from "+TABLE_NAME+" where "+COLUMN_ID+"="+id+"", null );
      return res;
   }
   public int numberOfRows(){
      SQLiteDatabase db = this.getReadableDatabase();
      int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
      return numRows;
   }
   public boolean updateContact (Integer id, String name, String email, String address,String city, String pincode, String phone)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      
      contentValues.put(COLUMN_NAME, name);
      contentValues.put(COLUMN_EMAIL, email);
      contentValues.put(COLUMN_ADDRESS, address);	
      contentValues.put(COLUMN_CITY, city);
      contentValues.put(COLUMN_PINCODE, pincode);
      contentValues.put(COLUMN_PHONE, phone);
      db.update(TABLE_NAME, contentValues, ""+COLUMN_ID+" = ? ", new String[] { Integer.toString(id) } );
      return true;
   }

   public Integer deleteContact (Integer id)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      return db.delete(TABLE_NAME, 
      ""+COLUMN_ID+" = ? ", 
      new String[] { Integer.toString(id) });
   }
   public ArrayList getAllCotacts()
   {
      ArrayList array_list = new ArrayList();
      //hp = new HashMap();
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor res =  db.rawQuery( "select * from "+TABLE_NAME+"", null );
      res.moveToFirst();
      while(res.isAfterLast() == false){
      array_list.add(res.getString(res.getColumnIndex(COLUMN_NAME)));
      res.moveToNext();
      }
   return array_list;
   }
}
