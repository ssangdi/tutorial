package com.innoaus.testpreference;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;

public class SplashActivity extends Activity {
	Handler myHandler;
	boolean cp_info;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		myHandler = new Handler();
		
		final SharedPreferences setRefer = PreferenceManager.getDefaultSharedPreferences(this);
		cp_info = setRefer.getBoolean("chkPswd", false);
		Log.d("splash_activity", "check "+cp_info);
		Runnable r = new Runnable(){
			
			public void run()
			{
				Log.d("splash_activity", "@@ check "+cp_info);
				if(cp_info)
					startActivity(new Intent(SplashActivity.this, PswdActivity.class));
				else
					startActivity(new Intent(SplashActivity.this, MainActivity.class));
			}
		};
		myHandler.postDelayed(r, 1000);
	}    
}
