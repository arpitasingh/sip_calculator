package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

   public static final String DATABASE_NAME = "MyGoalsDB.db";
   private static final int DATABASE_VERSION = 1;
   public static final String GOALS_TABLE_NAME = "goals";
   public static final String GOALS_COLUMN_ID = "id";
   public static final String GOALS_COLUMN_MONTHLY = "monthly";
   public static final String GOALS_COLUMN_NAME = "name";
   public static final String GOALS_COLUMN_QUARTERLY = "quarterly";
   public static final String GOALS_COLUMN_ANNUALLY = "annually";
   public static final String GOALS_COLUMN_OPTION_VAL = "option";
   public static final String GOALS_COLUMN_FILE_PATH = "filePath";
   public static final String GOALS_COLUMN_GOAL_IMAGE_PATH = "imagePath";
   private HashMap hp;

   public DBHelper(Context context) {
      super(context, DATABASE_NAME , null, DATABASE_VERSION);
   }

   @Override
   public void onCreate(SQLiteDatabase db) {
      // TODO Auto-generated method stub
      /*db.execSQL(
         "create table goals " +
         "(id integer primary key, name text,monthlyVal text,quarterlyVal text, street text,place text)"
      );*/
      String CREATE_CONTACTS_TABLE = "CREATE TABLE " + GOALS_TABLE_NAME + "("
              + GOALS_COLUMN_ID + " INTEGER PRIMARY KEY," + GOALS_COLUMN_NAME + " TEXT,"
              + GOALS_COLUMN_MONTHLY + " TEXT," + GOALS_COLUMN_QUARTERLY + " TEXT,"
              + GOALS_COLUMN_ANNUALLY + " TEXT," + GOALS_COLUMN_OPTION_VAL + " TEXT,"
              + GOALS_COLUMN_FILE_PATH + " TEXT" + ")";
      db.execSQL(CREATE_CONTACTS_TABLE);
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      // TODO Auto-generated method stub
      db.execSQL("DROP TABLE IF EXISTS goals");
      onCreate(db);
   }

   public boolean insertContact (String monthly, String quarterly, String annually, String option, String name, String filePath) {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put("monthly", monthly);
      contentValues.put("quarterly", quarterly);
      contentValues.put("annually", annually);
      contentValues.put("option", option);
      contentValues.put("name", name);
      contentValues.put("filePath", filePath);
      db.insert("goals", null, contentValues);
      return true;
   }
   
   public Cursor getData(int id) {
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor res =  db.rawQuery( "select * from goals where id="+id+"", null );
      return res;
   }
   
   public int numberOfRows(){
      SQLiteDatabase db = this.getReadableDatabase();
      int numRows = (int) DatabaseUtils.queryNumEntries(db, GOALS_TABLE_NAME);
      return numRows;
   }
   
   public boolean updateContact (Integer id, String monthly, String quarterly, String annually, String option, String name, String filePath) {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put("monthly", monthly);
      contentValues.put("quarterly", quarterly);
      contentValues.put("annually", annually);
      contentValues.put("option", option);
      contentValues.put("name", name);
      contentValues.put("filePath", filePath);
      db.update("goals", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
      return true;
   }

   public Integer deleteContact (Integer id) {
      SQLiteDatabase db = this.getWritableDatabase();
      return db.delete("goals",
      "id = ? ", 
      new String[] { Integer.toString(id) });
   }

   public List<FinanceGoal> getAllGoals() {
      List<FinanceGoal> goalList = new ArrayList<FinanceGoal>();
      // Select All Query
      String selectQuery = "SELECT  * FROM " + GOALS_TABLE_NAME;

      SQLiteDatabase db = this.getWritableDatabase();
      Cursor cursor = db.rawQuery(selectQuery, null);

      // looping through all rows and adding to list
      if (cursor.moveToFirst()) {
         do {
            FinanceGoal contact = new FinanceGoal();
            contact.setId(Integer.parseInt(cursor.getString(0)));
            contact.setName(cursor.getString(1));
            contact.setMonthlyVal(cursor.getString(2));
            contact.setQuarterlyVal(cursor.getString(3));
            contact.setAnnuallyVal(cursor.getString(4));
            contact.setOption(cursor.getString(5));
            contact.setFilePath(cursor.getString(6));
            // Adding contact to list
            goalList.add(contact);
         } while (cursor.moveToNext());
      }

      // return contact list
      return goalList;
   }
}