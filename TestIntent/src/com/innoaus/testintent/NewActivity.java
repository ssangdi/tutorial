package com.innoaus.testintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NewActivity extends Activity {
	 private Button finishActivityButton;
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.newlayout);
       
	        finishActivityButton = (Button)findViewById(R.id.finishActivity);
	        finishActivityButton.setOnClickListener(new OnClickListener(){
	 
	            @Override
	            public void onClick(View v) {
	                Intent intent = new Intent();
	                EditText input_name = (EditText)findViewById(R.id.input_name);
	                EditText input_number = (EditText)findViewById(R.id.input_number);
	                intent.putExtra("data_name", input_name.getText().toString());
	                intent.putExtra("data_number", input_number.getText().toString());
	                setResult(Activity.RESULT_OK, intent);
	                finish();
	            }
	        });
	    }

}
