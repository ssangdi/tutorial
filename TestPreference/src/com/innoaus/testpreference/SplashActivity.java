package com.innoaus.testpreference;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		Handler handler = new Handler(){
			public void handleMessage(Message msg){
				finish();
			}
		};
		
		handler.sendEmptyMessageDelayed(0, 3000);
	}
}
