package com.safetymate.safetymate;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity {

    DBConnection db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*db = new DBConnection(this);
        db.addThreshold(78);
        final Button button = (Button) findViewById(R.id.button_notify);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


            }
        });*/
        SafetyMate.setContext(this);
    }


    public void testSqliteDB(View view)
    {
       db.getThreshold();
    }

}
