package main.java.edu.gatech;

import java.util.Calendar;
import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

public class generateReport extends Activity {
	
	
	Button btnSelectBeginDate;
	Button btnSelectEndDate;
	static final int DATE1_DIALOG_ID = 0; 
	static final int DATE2_DIALOG_ID = 1;
	public int yearSelected, monthSelected, daySelected; 
    public int year,month,day; 
    public int year2, month2, day2;
    private int mYear, mMonth, mDay; 

    
    public generateReport()
    {
         
    			final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                
    }
    

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_generate_report);
		btnSelectBeginDate=(Button)findViewById(R.id.buttonSelectBeginDate); 
		btnSelectEndDate= (Button)findViewById(R.id.buttonSelectEndDate);
		Button btn = (Button) findViewById(R.id.button_return);
		btnSelectBeginDate.setOnClickListener(new View.OnClickListener() {
             
             @SuppressWarnings("deprecation")
			public void onClick(View v) {
                 // Show the DatePickerDialog
                  showDialog(DATE1_DIALOG_ID);
             }
         });
	    
		btnSelectEndDate.setOnClickListener(new View.OnClickListener() {
            
            @SuppressWarnings("deprecation")
			public void onClick(View v) {
                // Show the TimePickerDialog
                 showDialog(DATE2_DIALOG_ID);
            }
        });
		
        btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i("clicks", "clicked return button");
				Intent i = new Intent(
						generateReport.this,
						MainMenu.class);
				startActivity(i);
			}
		});
	}
	
	 private DatePickerDialog.OnDateSetListener mDateSetListener =
             new DatePickerDialog.OnDateSetListener() {
         // the callback received when the user "sets" the Date in the DatePickerDialog
                 public void onDateSet(DatePicker view, int yearSelected,
                                       int monthOfYear, int dayOfMonth) {
                    year = yearSelected;
                    month = monthOfYear;
                    day = dayOfMonth;
                    // Set the Selected Date in Select date Button
                    btnSelectBeginDate.setText("Date selected : "+day+"-"+month+"-"+year);
                 }
             };

             // Register  TimePickerDialog listener                 
             private DatePickerDialog.OnDateSetListener mDate2SetListener =
                 new DatePickerDialog.OnDateSetListener() {
             // the callback received when the user "sets" the TimePickerDialog in the dialog
                     public void onDateSet(DatePicker view, int yearSelected,
                             int monthOfYear, int dayOfMonth) {
                         year2 = yearSelected; 
                         month2 = monthOfYear;
                         day2 = dayOfMonth;
                         // Set the Selected Date in Select date Button
                         btnSelectEndDate.setText("End Date selected :"+day2+"-"+month2+"-"+year2);
                       }
                 };

             // Method automatically gets Called when you call showDialog()  method
             @Override
             protected Dialog onCreateDialog(int id) {
                     switch (id) {
                     case DATE1_DIALOG_ID:
              // create a new DatePickerDialog with values you want to show 
                         return new DatePickerDialog(this,
                                     mDateSetListener,
                                     mYear, mMonth, mDay);
             // create a new DatePickerDialog with values you want to show 
                     case DATE2_DIALOG_ID:
                    	 return new DatePickerDialog(this,
                                 mDate2SetListener,
                                 mYear, mMonth, mDay);
                    
                     }
                     return null;
                 }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.generate_report, menu);
		return true;
	}
	
	 
	
	
}
