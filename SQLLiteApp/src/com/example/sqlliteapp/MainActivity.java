package com.example.sqlliteapp;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import au.com.bytecode.opencsv.CSVWriter;

public class MainActivity extends Activity {
   public final static String EXTRA_MESSAGE = "com.example.sqlliteapp.MESSAGE";

   private ListView obj;	
   DBHelper mydb;
   
   private TextView no_records_text;
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.page_main);

      mydb = new DBHelper(this);
      ArrayList array_list = mydb.getAllCotacts();
      no_records_text	=	(TextView)findViewById(R.id.no_records_text);
      obj = (ListView)findViewById(R.id.linear_list);
      if(array_list.size()>0) {
    	  no_records_text.setVisibility(View.INVISIBLE);
	      ArrayAdapter arrayAdapter =      
	      new ArrayAdapter(this,android.R.layout.simple_list_item_1, array_list);
	
	      //adding it to the list view.
	      
	      obj.setAdapter(arrayAdapter);

      }
      else {
    	 
    	  no_records_text.setGravity(Gravity.CENTER);
    	  no_records_text.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);   
    	  no_records_text.setVisibility(View.VISIBLE);
      }
      
     obj.setOnItemClickListener(new OnItemClickListener(){

     @Override
     public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
     long arg3) {
         // TODO Auto-generated method stub
         int id_To_Search = arg2 + 1;
         Bundle dataBundle = new Bundle();
         dataBundle.putInt("id", id_To_Search);
         Intent intent = new Intent(getApplicationContext(),AddNewRecords.class);
         intent.putExtras(dataBundle);
         startActivity(intent);
     }
     });
   }
   
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate(R.menu.main, menu);
      return true;
      }
   @Override 
   public boolean onOptionsItemSelected(MenuItem item) 
   { 
      super.onOptionsItemSelected(item); 
      switch(item.getItemId()) 
      { 
         case R.id.add_new: 
            Bundle dataBundle = new Bundle();
            dataBundle.putInt("id", 0);
            Intent intent = new Intent(getApplicationContext(),AddNewRecords.class);
            intent.putExtras(dataBundle);
            startActivity(intent);
            return true;
         case R.id.excel_dump: 
        	 AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
       		 
    	        // Setting Dialog Title
    	        alertDialog.setTitle("Excel Dump Confirmation");
    	 
    	        // Setting Dialog Message
    	        alertDialog.setMessage("Are you sure you want export this?");
    	 
    	        // Setting Icon to Dialog
    	        alertDialog.setIcon(R.drawable.excel);
    	 
    	        // Setting Positive "Yes" Button
    	        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
    	            public void onClick(DialogInterface dialog,int which) {
    	            new ExportDatabaseCSVTask().execute();
    	            // Write your code here to invoke YES event
    	            //Toast.makeText(getApplicationContext(), "You clicked on YES", Toast.LENGTH_SHORT).show();
    	            }
    	        });
    	 
    	        // Setting Negative "NO" Button
    	        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
    	            public void onClick(DialogInterface dialog, int which) {
    	            // Write your code here to invoke NO event
    	            Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
    	            dialog.cancel();
    	            }
    	        });
    	 
    	        // Showing Alert Message
    	        alertDialog.show();    				
     				return true; 
         default: 
            return super.onOptionsItemSelected(item); 

       } 

   } 
   public boolean onKeyDown(int keycode, KeyEvent event) {
      if (keycode == KeyEvent.KEYCODE_BACK) {
         moveTaskToBack(true);
      }
      return super.onKeyDown(keycode, event);
   }
   
/*   public void export_dump_data(){
	  
	//   File dbFile=getDatabasePath("UserInfo.db");
      
       
       AlertDialog.Builder builder = new AlertDialog.Builder(this);
       builder.setMessage(R.string.excel_dump)
      .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
             Toast.makeText(getApplicationContext(), "Exported Successfully", Toast.LENGTH_SHORT).show();             
        
             DBHelper dbhelper = new DBHelper(getApplicationContext());
             File exportDir = new File(Environment.getExternalStorageDirectory(), "/vairamuthu/");        
            if (!exportDir.exists()) 
            {
                exportDir.mkdirs();
            }

       File file = new File(exportDir, "csvname.csv");
       Log.d("Storage Path = ",file.getAbsolutePath());
       try 
       {
           file.createNewFile();   
           CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
           SQLiteDatabase db = dbhelper.getReadableDatabase();
           Cursor curCSV = db.rawQuery("SELECT * FROM Users",null);
           csvWrite.writeNext(curCSV.getColumnNames());
           while(curCSV.moveToNext())
           {
              //Which column you want to exprort
               String arrStr[] ={curCSV.getString(0),curCSV.getString(1), curCSV.getString(2)};
               csvWrite.writeNext(arrStr);
           }
           csvWrite.close();
           curCSV.close();
       }
       catch(Exception sqlEx)
       {
           Log.e("MainActivity", sqlEx.getMessage(), sqlEx);
       }
       
          }
      })
     .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
         public void onClick(DialogInterface dialog, int id) {
            // User cancelled the dialog
         }
      });
      
   }*/
   class ExportDatabaseCSVTask extends AsyncTask<String, Void, Boolean>

   {

   private final ProgressDialog dialog = new ProgressDialog(MainActivity.this);

    @Override

   protected void onPreExecute()

   {

       this.dialog.setMessage("Exporting database...");

       this.dialog.show();

   }



   protected Boolean doInBackground(final String... args)

   {


       File dbFile=getDatabasePath("Userinfo.db");
       //AABDatabaseManager dbhelper = new AABDatabaseManager(getApplicationContext());
       DBHelper dbhelper = new DBHelper(getApplicationContext());
       System.out.println(dbFile);  // displays the data base path in your logcat 


       File exportDir = new File(Environment.getExternalStorageDirectory(), "");        

       if (!exportDir.exists()) 

       {
           exportDir.mkdirs();
       }


       File file = new File(exportDir, "excerDB.csv");


       try

       {

           if (file.createNewFile()){
               System.out.println("File is created!");
               System.out.println("myfile.csv "+file.getAbsolutePath());
             }else{
               System.out.println("File already exists.");
             }

           CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
           SQLiteDatabase db = dbhelper.getWritableDatabase();

           Cursor curCSV=db.rawQuery("select * from Users",null);

           csvWrite.writeNext(curCSV.getColumnNames());

           while(curCSV.moveToNext())

           {

               String arrStr[] ={curCSV.getString(0),curCSV.getString(1),curCSV.getString(2)};

            /*curCSV.getString(3),curCSV.getString(4)};*/

               csvWrite.writeNext(arrStr);


           }

           csvWrite.close();
           curCSV.close();
           /*String data="";
           data=readSavedData();
           data= data.replace(",", ";");
           writeData(data);*/

           return true;

       }

       catch(SQLException sqlEx)

       {

           Log.e("MainActivity", sqlEx.getMessage(), sqlEx);

           return false;

       }

       catch (IOException e)

       {

           Log.e("MainActivity", e.getMessage(), e);

           return false;

       }

   }

   protected void onPostExecute(final Boolean success)

   {

       if (this.dialog.isShowing())

       {

           this.dialog.dismiss();

       }

       if (success)

       {

           Toast.makeText(MainActivity.this, "Export succeed", Toast.LENGTH_SHORT).show();

       }

       else

       {

           Toast.makeText(MainActivity.this, "Export failed", Toast.LENGTH_SHORT).show();

       }
   }}
}
