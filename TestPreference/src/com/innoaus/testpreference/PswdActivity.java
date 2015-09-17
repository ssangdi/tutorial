package com.innoaus.testpreference;

import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.pass.Spass;
import com.samsung.android.sdk.pass.SpassFingerprint;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class PswdActivity extends Activity {
	final static int set_num = 0;
	
	private SpassFingerprint mSpassFingerprint;
	private Context mContext;
	boolean isFeatureEnabled;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pswd);
		
		mContext = this;
		Spass mSpass = new Spass();
		
		try {
			mSpass.initialize(mContext);
			isFeatureEnabled = mSpass.isFeatureEnabled(Spass.DEVICE_FINGERPRINT);

			if(isFeatureEnabled){
				mSpassFingerprint = new SpassFingerprint(mContext);
			} else {
				//log("Fingerprint Service is not supported in the device.");
			}
			
			SpassFingerprint.IdentifyListener listener = new SpassFingerprint.IdentifyListener() {
				@Override
				public void onStarted() {
					// TODO Auto-generated method stub	
				}
				@Override
				public void onReady() {
					// TODO Auto-generated method stub
				}
				@Override
				public void onFinished(int eventStatus) {
					// TODO Auto-generated method stub
					if(eventStatus == SpassFingerprint.STATUS_AUTHENTIFICATION_SUCCESS){
						Intent intentSet = new Intent (PswdActivity.this, MainActivity.class);
		    			startActivity(intentSet);
					}
					else{
						
					}
					
				}
			};

			mSpassFingerprint.startIdentifyWithDialog(PswdActivity.this, listener, false);
			
		} catch (SsdkUnsupportedException e) {
		 // Error handling
		} catch (UnsupportedOperationException e){
		 // Error handling
		}
	
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
