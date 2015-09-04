package com.innoaus.testlistview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	String[] monthsArray = {"1��", "2��", "3��", "4��", "5��", "6��", "7��",
            "8��", "9��", "10��", "11��", "12��"};
    private ListView monthsListView;
    private ArrayAdapter arrayAdapter;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		startActivity(new Intent(this, SplashActivity.class));
		
		monthsListView = (ListView)findViewById(R.id.months_list);
        // ����� ����
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,monthsArray);
        // setAdapter �� �̿��ؼ� monthsListView �� ����� ����
        monthsListView.setAdapter(arrayAdapter);
	}
	 public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.menu_main, menu);
	        return true;
	    }
	 


}
