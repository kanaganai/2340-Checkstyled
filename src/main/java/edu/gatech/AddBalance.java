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
 * An activity that allows user to add balance.
 * @author Seok Jung
 *
 */
public class AddBalance extends Activity {
    /**
     * a field where the name goes in.
     */
    private EditText rAmnNameField;
    /**
     * Amount of the balance deposited.
     */
    private String amount;
    /**
     * The source.
     */
    private String source;
    /**
     * The date of the transaction.
     */
    private String date;
    /**
     * the transaction ammount.
     */
    protected double transAmnt;
    /**
     * balance adding.
     */
    private BalanceAdd balAdd;
    /**
     * Transaction Id of 1.
     */
    private static final int TRANSBUT_ID = 1;
    /**
     * the month.
     */
    int month;
    /**
     * the year.
     */
    int year;
    /**
     * the day.
     */
    int day;
	/**
	 * Sets the year, month and day.
	 */
    public AddBalance() {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_balance2);
        DBHandler database = new DBHandler(getApplicationContext());
        TextView accountView = (TextView) findViewById(R.id.accountView2);
        rAmnNameField = (EditText) findViewById(R.id.balanceBox);
        accountView.setText("Account: " + database.selectedAccount);
        Button cancelBut = (Button) findViewById(R.id.cancelBut2);
        final EditText sourceField = (EditText) findViewById(R.id.EditText01);
        sourceField.setOnClickListener(new View.OnClickListener() {
			
            @Override
            public void onClick(View v) {
                sourceField.setText("");
				
            }
        });
        cancelBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("clicks", "Clicked return button");
                Intent i = new Intent(AddBalance.this, AccountsPageActivity.class);
                startActivity(i);
            }
        });
        Button addBut = (Button) findViewById(R.id.addBut);
        amount = rAmnNameField.getText().toString();
        addBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("clicks", "Clicked add button");
                source = ((EditText) findViewById(R.id.EditText01)).getText().toString();
                attemptAdd(amount);
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
     * Allows user to set date of transaction.
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
        getMenuInflater().inflate(R.menu.add_balance, menu);
        return true;
    }
    /**
     * the date picker is created.
     * @param id finds the item of the id.
     * @return returns the datepicker.
     */
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this,
                   mDateSetListener,
                    year, month, day);
    }
    /**
     * tries to add.
     * @param amount the amount to add.
     */
    public void attemptAdd(String amount) {
        DBHandler database = new DBHandler(getApplicationContext());
        double accountAmount;
        rAmnNameField.setError(null);
        boolean cancel = false;
        View focusView = null;
        accountAmount = database.getBalance(database.selectedAccount);
        Log.d("amount: ", Double.toString(database.getBalance(database.selectedAccount)));
        if (TextUtils.isEmpty(amount)) {
            rAmnNameField.setError(getString(R.string.error_field_required));
            focusView = rAmnNameField;
            cancel = true;
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
            transAmnt = Double.parseDouble(rAmnNameField.getText().toString());
            balAdd = new BalanceAdd();
            balAdd.execute();
        }
    }
    /**
     * Tries to actually add the balance to the database.
     * @author Seok Jung
     *
     */
    private class BalanceAdd extends AsyncTask<Void, Void, Boolean> {
        /**
         * the database holding account information.
         */
        private DBHandler database = new DBHandler(getApplicationContext());
        /**
         * Consistently active in the background.
         * @param args Inactive.
         * @return returns true when it is active.
         */
        protected Boolean doInBackground (Void... args) {
            Log.d("Depositing", "Here");
            database.depositBalance(transAmnt, database.selectedAccount, source, date);
            return true;
        }
		
        @Override
        protected void onPostExecute(final Boolean success) {
            balAdd = null;
		
            if (success) {
                Intent i = new Intent(
                        AddBalance.this,
						TransactionActivity.class);
                Toast.makeText(getApplicationContext(), "Deposited $" + transAmnt, Toast.LENGTH_LONG).show();
                startActivity(i);
            } else {
                rAmnNameField.setError("Failed to deposit");
                rAmnNameField.requestFocus();
            }
        }
		
        @Override
        protected void onCancelled() {
            balAdd = null;
        }
    }
	

}
