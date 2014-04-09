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

/**
 * Generates Report.
 * @author Ivana Perez
 *
 */
public class GenerateReportActivity extends Activity {
	
	/**
	 * button to select date.
	 */
    Button btnSelectBeginDate;
    /**
     * button to select date.
     */
    Button btnSelectEndDate;
    /**
     * the dialog id of 0.
     */
    static final int DATE1_DIALOG_ID = 0;
    /**
     * the dialog id of 1.
     */
    static final int DATE2_DIALOG_ID = 1;
    /**
     * the year selected.
     */
    public int yearSelected;
    /**
     * the month selected.
     */
    public int monthSelected;
    /**
     * the day selected.
     */
    public int daySelected;
    /**
     * the second year selected.
     */
    public int year2;
    /**
     * the second month selected.
     */
    public int month2;
    /**
     * second day selected.
     */
    public int day2;
    /**
     * the start date.
     */
    public String startDate;
    /**
     * the end date.
     */
    public String endDate;
    /**
     * the year of the transaction.
     */
    private int mYear;
    /**
     * the month of the transaction.
     */
    private int mMonth;
    /**
     * the day of the transaction.
     */
    private int mDay; 

    /**
     * the constructor.
     */
    public GenerateReportActivity() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_report);
        btnSelectBeginDate = (Button) findViewById(R.id.buttonSelectBeginDate); 
        btnSelectEndDate = (Button) findViewById(R.id.buttonSelectEndDate);
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
						GenerateReportActivity.this,
						MainMenu.class);
                startActivity(i);
            }
        });
        
        Button gbtn = (Button) findViewById(R.id.button1);
        gbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	String report = generateReport(startDate, endDate);
            	Intent i = new Intent(
						GenerateReportActivity.this,
						ReportPageActivity.class);
            	i.putExtra("Report", report);
            	startActivity(i);
            }
        });
    }
	
    /**
     * Selects the date.
     */
    
    private DatePickerDialog.OnDateSetListener mDateSetListener =
             new DatePickerDialog.OnDateSetListener() {
         // the callback received when the user "sets" the Date in the DatePickerDialog
                public void onDateSet(DatePicker view, int yearSelected,
                                       int monthOfYear, int dayOfMonth) {
                    // Set the Selected Date in Select date Button
                    btnSelectBeginDate.setText("Date selected : " + dayOfMonth + "-" + monthOfYear + "-" + yearSelected);                   
                    startDate = Utils.getDate(monthOfYear, yearSelected, dayOfMonth);
                    Log.d("startdate", "Setting startdate = " + startDate);
                }
            };

             // Register  TimePickerDialog listener           
            /**
             * selects the second date.
             */
    private DatePickerDialog.OnDateSetListener mDate2SetListener =
                 new DatePickerDialog.OnDateSetListener() {
             // the callback received when the user "sets" the TimePickerDialog in the dialog
                public void onDateSet(DatePicker view, int yearSelected,
                             int monthOfYear, int dayOfMonth) {
                         // Set the Selected Date in Select date Button
                    btnSelectEndDate.setText("End Date selected :" + dayOfMonth + "-" + monthOfYear + "-" + yearSelected);
                    endDate = Utils.getDate(monthOfYear, yearSelected, dayOfMonth);
                    Log.d("enddate", "Setting enddate = " + endDate);
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
	
    /**
     * generates the report.
     * @param startDate of the report.
     * @param endDate of the report.
     * @return report the generated report.
     */
    private String generateReport(String startDate, String endDate) {
        DBHandler database = new DBHandler(getApplicationContext());
        String report = database.generateSpendingCategoriesReport(startDate, endDate);
        return report;
    }
	
	
}
