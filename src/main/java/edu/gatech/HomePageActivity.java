package main.java.edu.gatech;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * the homepage activity.
 * @author Seok Jung
 *
 */
public class HomePageActivity extends Activity {
    /**
     * when this activity is called.
     * @param savedInstanceState calls back the state in which it was previously in.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        
        Button btn = (Button) findViewById(R.id.button_return);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("clicks", "clicked return button");
                Intent i = new Intent(
						HomePageActivity.this,
						MainMenu.class);
                startActivity(i);
            }
        });
    }
}
