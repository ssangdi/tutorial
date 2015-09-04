package com.innoaus.testlistview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	String[] monthsArray = {"1월", "2월", "3월", "4월", "5월", "6월", "7월",
            "8월", "9월", "10월", "11월", "12월"};
    private ListView monthsListView;
    private ArrayAdapter arrayAdapter;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		startActivity(new Intent(this, SplashActivity.class));
		
		monthsListView = (ListView)findViewById(R.id.months_list);
        // 어댑터 설정
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,monthsArray);
        // setAdapter 를 이용해서 monthsListView 에 어댑터 설정
        monthsListView.setAdapter(arrayAdapter);
	}
	 public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.menu_main, menu);
	        return true;
	    }
	 


}
