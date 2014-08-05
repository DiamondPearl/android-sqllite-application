package com.example.sqlliteapp;
import com.mobsandgeeks.saripaar.Rule;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.Validator.ValidationListener;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Required;
import com.mobsandgeeks.saripaar.annotation.TextRule;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddNewRecords extends Activity implements ValidationListener{

   
   int from_Where_I_Am_Coming = 0;
   private DBHelper mydb ;
   
   @Required(order = 1)   
   @TextRule(order = 2, minLength = 6,  maxLength = 25,message = "Enter text range between 6 to 25 characters.")
   private TextView name;
   
   @Required(order = 3)   
   @TextRule(order = 4, minLength = 6,  maxLength = 100,message = "Enter text range between 6 to 25 characters.")
   @Email(order = 5, message = "Invalid Email Address.")
   private TextView email;
   
   @Required(order = 6)   
   @TextRule(order = 7, minLength = 6, maxLength = 25, message = "Enter text range between 6 to 25 characters.")
   private TextView address;
   
   @Required(order = 8)   
   @TextRule(order = 9, minLength = 6,  maxLength = 25,message = "Enter text range between 6 to 25 characters.")
   private TextView city;
   
   @Required(order = 10)   
   @TextRule(order = 11, minLength = 6,  maxLength = 8,message = "Enter text range between 6 to 8 characters.")
  // @NumberRule(order = 1, type=INTEGER, message = "Enter at least 6 characters.")
   private TextView pincode;
   
   @Required(order = 12)   
   @TextRule(order = 13, minLength = 6,  maxLength = 10,message = "Enter text range between 6 to 10 characters.")
  // @Number(order = 16, message = "Please enter numeric value.")
   private TextView phone;
   
   private Validator validator;
   
   private Button submit_btn;
   
   int id_To_Update = 0;


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      
      validator = new Validator(this);
      validator.setValidationListener(this);
      
      
      
      setContentView(R.layout.add_new);
      name = (TextView) findViewById(R.id.name);
      email = (TextView) findViewById(R.id.email);
      address = (TextView) findViewById(R.id.address);
      city = (TextView) findViewById(R.id.city);
      pincode = (TextView) findViewById(R.id.pincode);
      phone = (TextView) findViewById(R.id.phone);  
      
      submit_btn = (Button)findViewById(R.id.save);
      
      submit_btn.setOnClickListener(new View.OnClickListener() {
          public void onClick(View v) {
              // Perform action on click   
              validator.validate();
          }
      });
      

      mydb = new DBHelper(this);

      Bundle extras = getIntent().getExtras(); 
      if(extras !=null)
      {
         int Value = extras.getInt("id");
         if(Value>0){
            //means this is the view part not the add contact part.
            Cursor rs = mydb.getData(Value);
            id_To_Update = Value;
            rs.moveToFirst();
            String nam = rs.getString(rs.getColumnIndex(DBHelper.COLUMN_NAME));
            String emai = rs.getString(rs.getColumnIndex(DBHelper.COLUMN_EMAIL));
            String addres = rs.getString(rs.getColumnIndex(DBHelper.COLUMN_ADDRESS));
            String cit = rs.getString(rs.getColumnIndex(DBHelper.COLUMN_CITY));
            String pincod = rs.getString(rs.getColumnIndex(DBHelper.COLUMN_PINCODE));
            String phon = rs.getString(rs.getColumnIndex(DBHelper.COLUMN_PHONE));
            if (!rs.isClosed()) 
            {
               rs.close();
            }
            Button b = (Button)findViewById(R.id.save);
            b.setVisibility(View.INVISIBLE);

            name.setText((CharSequence)nam);
            name.setFocusable(false);
            name.setClickable(false);
            
            email.setText((CharSequence)emai);
            email.setFocusable(false);
            email.setClickable(false);
            
            address.setText((CharSequence)addres);
            address.setFocusable(false);
            address.setClickable(false);

            city.setText((CharSequence)cit);
            city.setFocusable(false); 
            city.setClickable(false);
            
            pincode.setText((CharSequence)pincod);
            pincode.setFocusable(false); 
            pincode.setClickable(false);
            
            phone.setText((CharSequence)phon);
            phone.setFocusable(false); 
            phone.setClickable(false);
           }
      }
   }
   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      // Inflate the menu; this adds items to the action bar if it is present.
      Bundle extras = getIntent().getExtras(); 
      if(extras !=null)
      {
         int Value = extras.getInt("id");
         if(Value>0){
            getMenuInflater().inflate(R.menu.users_lists_menu, menu);
         }
         else{
            //getMenuInflater().inflate(R.menu.main, menu);
         }
      }
      return true;
   }

   public boolean onOptionsItemSelected(MenuItem item) 
   { 
      super.onOptionsItemSelected(item); 
      switch(item.getItemId()) 
   { 
      case R.id.Edit_Contact: 
      Button b = (Button)findViewById(R.id.save);
      b.setText(R.string.update_record);
      b.setVisibility(View.VISIBLE);
      
      name.setEnabled(true);
      name.setFocusableInTouchMode(true);
      name.setClickable(true);
      
      email.setEnabled(true);
      email.setFocusableInTouchMode(true);
      email.setClickable(true);
      
      address.setEnabled(true);
      address.setFocusableInTouchMode(true);
      address.setClickable(true);

      city.setEnabled(true);
      city.setFocusableInTouchMode(true); 
      city.setClickable(true);
      
      pincode.setEnabled(true);
      pincode.setFocusableInTouchMode(true); 
      pincode.setClickable(true);
      
      phone.setEnabled(true);
      phone.setFocusableInTouchMode(true); 
      phone.setClickable(true);
      
      return true; 
      case R.id.Delete_Contact:

      AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setMessage(R.string.delete)
     .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
         public void onClick(DialogInterface dialog, int id) {
            mydb.deleteContact(id_To_Update);
            Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();  
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
         }
      })
     .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
         public void onClick(DialogInterface dialog, int id) {
            // User cancelled the dialog
         }
      });
      AlertDialog d = builder.create();
      d.setTitle("Are you sure");
      d.show();

      return true;
      default: 
      return super.onOptionsItemSelected(item); 

      } 
   } 

   public void run()
   {	
   
	   Bundle extras = getIntent().getExtras();
      if(extras !=null)
      {
    	  Log.d("AddNew", extras.toString());
    	  int Value = extras.getInt("id");
         if(Value>0){
            if(mydb.updateContact(id_To_Update,name.getText().toString(), email.getText().toString(), address.getText().toString(), city.getText().toString(), pincode.getText().toString(), phone.getText().toString())){
               Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(getApplicationContext(),MainActivity.class);
               startActivity(intent);
             }		
            else{
               Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();	
            }
		 }
         else{
            if(mydb.insertContact(name.getText().toString(), email.getText().toString(), address.getText().toString(), city.getText().toString(), pincode.getText().toString(), phone.getText().toString())){
               Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();	
            }		
            else{
               Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();	
            }
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            }
      }
   }
   
   @Override
   public void onValidationFailed(View failedView, Rule<?> failedRule) {
	   String message = failedRule.getFailureMessage();
	   if(failedView instanceof EditText){
	   failedView.requestFocus();
	   ((EditText) failedView).setError(message);
	   }else{
	   Toast.makeText(getApplicationContext(), message , Toast.LENGTH_SHORT).show();	
	   }
	   }
   
	@Override
	public void onValidationSucceeded() {
		// TODO Auto-generated method stub
		
		run();
	}
}