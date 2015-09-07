package com.innoaus.testpreference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {
	
	boolean cp_info;
	
	final static int set_num = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//final SharedPreferences setRefer = PreferenceManager.getDefaultSharedPreferences(this);
		//cp_info = setRefer.getBoolean("chkPswd", false);
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
    			Intent SettingsActivity = new Intent(this, SettingsActivity.class);
    			startActivity(SettingsActivity);
    			
    	}
    	return super.onOptionsItemSelected(item);
    }

}
