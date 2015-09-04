package com.innoaus.testintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		final Button requestInfo = (Button) findViewById(R.id.callActivity);
		
		requestInfo.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v){
				Intent intent = new Intent(MainActivity.this, NewActivity.class);
				startActivityForResult(intent, 1);
			}
		});
		
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TextView tv_name = (TextView) findViewById(R.id.data_name);
        TextView tv_number = (TextView) findViewById(R.id.data_number);
        if (resultCode == RESULT_OK) // 액티비티가 정상적으로 종료되었을 경우
        {
            if (requestCode == 1) // CreateActivity에서 호출한 경우에만 처리
            {
                tv_name.setText(data.getStringExtra("data_name"));
                tv_number.setText(data.getStringExtra("data_number"));
            }
        }
    }
	
}
