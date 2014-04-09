package main.java.edu.gatech;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.content.Intent;
import android.util.Log;
import android.view.*;
import android.widget.*;

public class MainMenu extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);
        Button btn1 = (Button) findViewById(R.id.accountBut);
        btn1.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		Log.i("clicks","Clicked register account");
                Intent i=new Intent(
                       MainMenu.this,
                       RegisterAccountActivity.class);
                startActivity(i);
        	}
        });
        
        Button btn2 = (Button) findViewById(R.id.accountsViewButton);
        btn2.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v){
        		Log.i("clicks","Clicked view accounts");
        		Intent i = new Intent(
        				MainMenu.this,
        				AccountsPageActivity.class);
        		startActivity(i);
        	}
        });
        
        Button btn3 = (Button) findViewById(R.id.generateReportButton);
        btn3.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v){
        		Log.i("clicks","Clicked generate report");
        		Intent i = new Intent(
        				MainMenu.this,
        				generateReport.class);
        		startActivity(i);
        	}
        });
        
        
        
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}
	
	

}
