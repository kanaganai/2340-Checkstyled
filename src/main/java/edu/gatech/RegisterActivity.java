package main.java.edu.gatech;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Registers the user.
 * @author Ruobin Ling
 *
 */
public class RegisterActivity extends Activity {
    /**
     * the string of user email.
     */
    private String rEmail;
    /**
     * string of user password.
     */
    private String rPassword;
    /**
     * string of user username.
     */
    private String rUsername;
    
    /**
     * UI reference.
     */
    private EditText rEmailField;
    /**
     * UI reference.
     */
    private EditText rPasswordField;
    /**
     * UI reference.
     */
    private EditText rUsernameField;
	/**
	 * the register task.
	 */
    private RegisterTask regTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
		
        rEmailField = (EditText) findViewById(R.id.field_email);
        rEmailField.setText(rEmail);
		
        rUsernameField = (EditText) findViewById(R.id.field_username);
        rUsernameField.setText(rUsername);
		
        rPasswordField = (EditText) findViewById(R.id.field_password);
        rPasswordField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                    KeyEvent event) {
                if (actionId == R.id.button_return || actionId == EditorInfo.IME_NULL) {
                    attemptReg();
                    return true;
                }
                return false;
            }
			
        });
		
        Button btn = (Button) findViewById(R.id.button_return);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptReg();
            }
        });
		
        Button btn2 = (Button) findViewById(R.id.button_cancel);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("clicks", "clicked cancel button");
                Intent i = new Intent(
						RegisterActivity.this,
						LoginActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.register, menu);
        return true;
    }
	/**
	 * tries to register the account.
	 */
    public void attemptReg() {
        rEmailField.setError(null);
        rPasswordField.setError(null);
        rUsernameField.setError(null);
		
        rEmail = rEmailField.getText().toString();
        rPassword = rPasswordField.getText().toString();
        rUsername = rUsernameField.getText().toString();
		
        boolean cancel = false;
        View focusView = null;
		
        if (TextUtils.isEmpty(rPassword)) {
            rPasswordField.setError(getString(R.string.error_field_required));
            focusView = rPasswordField;
            cancel = true;
        }
        else if (rPassword.length() < 4) {
            rPasswordField.setError(getString(R.string.error_invalid_password));
            focusView = rPasswordField;
            cancel = true;
        }
		
        if (TextUtils.isEmpty(rEmail)) {
            rEmailField.setError(getString(R.string.error_field_required));
            focusView = rEmailField;
            cancel = true;
        } else if (!rEmail.contains("@")) {
            rEmailField.setError(getString(R.string.error_invalid_email));
            focusView = rEmailField;
            cancel = true;
        }
		
        if (TextUtils.isEmpty(rUsername)) {
            rUsernameField.setError(getString(R.string.error_field_required));
            focusView = rUsernameField;
            cancel = true;
        }
		
        if (cancel) {
            focusView.requestFocus();
        }
        else if (!cancel) {
            regTask = new RegisterTask();
            regTask.execute((Void) null);
        }
    }

	/**
	 * An asynchronous task for registering a new user.
	 */
    public class RegisterTask extends AsyncTask<Void, Void, Boolean> {
        /**
         * the databsae of the account.
         */
        private DBHandler database = new DBHandler(RegisterActivity.this);
		
        @Override
        protected Boolean doInBackground(Void... params) {
            database.createUser(rEmail, rPassword);
            return true;
        }
		
        @Override
        protected void onPostExecute(final Boolean success) {
            regTask = null;

            if (success) {
                Intent i = new Intent(
                        RegisterActivity.this,
                        LoginActivity.class);
	        startActivity(i);
            } else {
                rPasswordField
						.setError("Failed to register new user.");
                rPasswordField.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            regTask = null;
        }
    }
}
