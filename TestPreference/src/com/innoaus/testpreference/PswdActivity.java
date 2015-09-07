package com.innoaus.testpreference;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;


public class PswdActivity extends Activity {
	final static int set_num = 0;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pswd);
		
		//startActivity(new Intent(this, SplashActivity.class));
	}
	public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
    	int id = item.getItemId();
    	switch (id){
    		case R.id.setting:
    			Intent intentSet = new Intent (this, MainActivity.class);
    			startActivity(intentSet);
    			
    	}
    	return super.onOptionsItemSelected(item);
    }
    
}
