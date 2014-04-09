package main.java.edu.gatech;

import java.util.Calendar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Withdraws balance.
 * @author Seok Jung
 *
 */
public class SubtractBalance extends Activity {
    /**
     * UI reference.
     */
    private EditText rAmnNameField;
    /**
     * string holding amount balance.
     */
    private String amount;
    /**
     * the destination of the transaction.
     */
    private String destination;
    /**
     * the date of transaction.
     */
    private String date;
    /**
     * the transaction ammount.
     */
    protected double transAmnt;
    /**
     * the balance subtract class.
     */
    private BalanceSubtract balSub;
    /**
     * the day of the transaction.
     */
    int day;
    /**
     * the month of the transaction.
     */
    int month;
    /**
     * the year of transaction.
     */
    int year;
    /**
     * the transaction id of 1.
     */
    private static final int TRANSBUT_ID = 1;
	
    /**
     * the constructor.
     */
    public SubtractBalance() {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtract_balance2);
        DBHandler database = new DBHandler(getApplicationContext());
        rAmnNameField = (EditText) findViewById(R.id.balanceBox);
        TextView accountView = (TextView) findViewById(R.id.accountView3);
        accountView.setText("Account: " + database.selectedAccount);
        final EditText sourceField = (EditText) findViewById(R.id.EditText01);
        sourceField.setOnClickListener(new View.OnClickListener() {
			
            @Override
            public void onClick(View v) {
                sourceField.setText("");
				
            }
        });
        Button cancelBut = (Button) findViewById(R.id.cancelBut3);
        cancelBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("clicks", "Clicked return button");
                Intent i = new Intent(SubtractBalance.this, AccountsPageActivity.class);
                startActivity(i);
            }
        });
        Button subBut = (Button) findViewById(R.id.subBut);
        subBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("clicks", "Clicked subtract button");
                destination = ((EditText) findViewById(R.id.EditText01)).getText().toString();
                attemptSub();
            }
        });
        Button transBut = (Button) findViewById(R.id.transBut);
        transBut.setOnClickListener(new View.OnClickListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onClick(View v) {
                Log.i("clicks", "Clicked trans button");
                showDialog(TRANSBUT_ID);
            }
        });
    }
    /**
     * the date picker of the transaction.
     */
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
        // the callback received when the user "sets" the TimePickerDialog in the dialog
                public void onDateSet(DatePicker view, int yearSelected,
                        int monthOfYear, int dayOfMonth) {
                    year = yearSelected; 
                    month = monthOfYear;
                    day = dayOfMonth;
                    // Set the Selected Date in Select date Button
                    Button transBut = (Button) findViewById(R.id.transBut);
                    transBut.setText("Date selected :" + day + "-" + month + "-" + year);
                    date = Utils.getDate(month, year, day);
                }
            };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.subtract_balance, menu);
        return true;
    }
    /**
     * when the date picker is created.
     * @param id the id of the datepicker.
     * @return the datepicker.
     */
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this,
                mDateSetListener,
                year, month, day);
    }
    /**
     * attemps to withdraw.
     */
    public void attemptSub() {
        DBHandler database = new DBHandler(getApplicationContext());
        double accountAmount;
        rAmnNameField.setError(null);
        amount = rAmnNameField.getText().toString();
        boolean cancel = false;
        View focusView = null;
        accountAmount = database.getBalance(database.selectedAccount);
        Log.d("amount: ", Double.toString(database.getBalance(database.selectedAccount)));
        if (TextUtils.isEmpty(amount)) {
            rAmnNameField.setError(getString(R.string.error_field_required));
            focusView = rAmnNameField;
            cancel = true;
        }
        if (!TextUtils.isEmpty(amount)) {
            transAmnt = Double.parseDouble(rAmnNameField.getText().toString());
            if (accountAmount < transAmnt) {
                rAmnNameField.setError("Cannot withdraw more than the balance.");
                focusView = rAmnNameField;
                cancel = true;
            }
        }
        for (int i = 0; i < amount.length(); i++) {
            if (amount.substring(i, i + 1).equals(".")) {
                if (amount.substring(i + 1).length() > 2) {
                    rAmnNameField.setError("Error: Not a valid Balance");
                    focusView = rAmnNameField;
                    cancel = true;
                }
            }
        }
        if (cancel) {
            focusView.requestFocus();
        }
        else {
            balSub = new BalanceSubtract();
            balSub.execute();
        }
    }
    /**
     * withdraws balance.
     * @author Seok Jung
     *
     */
    private class BalanceSubtract extends AsyncTask<Void, Void, Boolean> {
        /**
         * the database of the account.
         */
        private DBHandler database = new DBHandler(getApplicationContext());
        /**
         * actively performs in the background.
         * @param args inactive.
         * @return if it is active.
         */
        protected Boolean doInBackground (Void... args) {
            Log.d("Withdrawing", "here");
            database.withdrawBalance(transAmnt, database.selectedAccount, destination, date);
            return true;
        }
		
        @Override
        protected void onPostExecute(final Boolean success) {
            balSub = null;
			
            if (success) {
                Intent i = new Intent(
						SubtractBalance.this,
						TransactionActivity.class);
                Toast.makeText(getApplicationContext(), "Withdrew $" + transAmnt, Toast.LENGTH_LONG).show();
                startActivity(i);
            } else {
                rAmnNameField.setError("Failed to withdraw");
                rAmnNameField.requestFocus();
            }
        }
		
        @Override
        protected void onCancelled() {
            balSub = null;
        }
    }
}
