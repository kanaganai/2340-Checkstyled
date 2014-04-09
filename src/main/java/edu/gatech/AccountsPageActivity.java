package main.java.edu.gatech;

import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
/**
 * Activity of accounts page, showing the Account, balance and the ability to deposit or withdraw balance.
 * @author Seok Jung
 *
 */
public class AccountsPageActivity extends Activity {
    /**
     * shows account task.
     */
    private ShowAccountsTask accShowTask;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts_homepage);
        Button btn1 = (Button) findViewById(R.id.returnButton);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("clicks", "Clicked return button");
                Intent i = new Intent(AccountsPageActivity.this, MainMenu.class);
                startActivity(i);
            }
        });
        Button viewBut = (Button) findViewById(R.id.view);
        viewBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("clicks", "Clicked return button");
                Intent i = new Intent(AccountsPageActivity.this, TransactionActivity.class);
                startActivity(i);
            }
        });
        accShowTask = new ShowAccountsTask();
        accShowTask.execute((Void) null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }
	/**
	 * Creates a Spinner of accounts.
	 * @author Seok Jung
	 *
	 */
    private class ShowAccountsTask extends AsyncTask<Void, Void, Boolean> implements OnItemSelectedListener {
        /**
         * Database holding account informations.
         */
        private DBHandler database = new DBHandler(AccountsPageActivity.this);
        /**
         * Spinner that holds all accounts.
         */
        private Spinner accSpin = (Spinner) findViewById(R.id.accSpin);

        @Override
        protected Boolean doInBackground(Void... arg0) {
            Log.d("Set", "item listener");
            accSpin.setOnItemSelectedListener(this);
            Log.d("load", "Spinner data");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadSpinnerData(accSpin);
                }
            });
            return true;
        }
		
        @Override
        protected void onPostExecute(final Boolean success) {
            accShowTask = null;
        }
		
        @Override
        protected void onCancelled() {
            accShowTask = null;
        }
        /**
         * loads the spinner data (the accoutns).
         * @param accSpin the Spinner with accounts.
         */
        private void loadSpinnerData(Spinner accSpin) {
            Log.d("Getting Accounts", "get account names");
            List<String> accounts = database.getAccountNames();
            Log.d("Creating Spinner", "");
            ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, accounts);
            Log.d("Set", "Drop Down");
            spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Log.d("Set", "adapter");
            accSpin.setAdapter(spinAdapter);
        }
        /**
         * A method when the spinner is clicked is called.
         * @param parent the parent class of this
         * @param view the view
         * @param position defines position
         * @param id finds the id
         */
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            String account = parent.getItemAtPosition(position).toString();
            database.selectedAccount = account;
            Toast.makeText(parent.getContext(), "Selected: " + account, Toast.LENGTH_LONG).show();
			
        }
        /**
         * When nothing has been clicked, the method will automatically call the first option.
         * @param arg0 the first argument of the spinner.
         */
        public void onNothingSelected(AdapterView<?> arg0) {
			//TODO Auto-generated method stub
        }
    }
}
