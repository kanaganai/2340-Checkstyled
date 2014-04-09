package main.java.edu.gatech;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Activity showing the report.
 * @author kanag_000
 *
 */
public class ReportPageActivity extends Activity {
	/**
	 * called when created.
	 * @param savedInstanceState the previous state it was in.
	 */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_page);
        TextView report = (TextView) findViewById(R.id.textView1);
        Bundle extras = getIntent().getExtras();		
        report.setText(extras.getString("Report"));
        Button done = (Button) findViewById(R.id.button1);
        done.setOnClickListener(new View.OnClickListener() {
			
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ReportPageActivity.this, GenerateReportActivity.class);
                startActivity(i);				
            }
        });
		
		
    }
}
