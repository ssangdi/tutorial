package com.innoaus.testpreference;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;


public class SettingsActivity extends PreferenceActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getFragmentManager()
		.beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
	}
	
	public static class MyPreferenceFragment extends PreferenceFragment{
		@Override
		public void onCreate(final Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.pref_settings);
		}
	}
}
