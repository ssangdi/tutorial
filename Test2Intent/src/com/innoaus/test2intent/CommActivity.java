package com.innoaus.test2intent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CommActivity extends Activity {
	TextView mText;
    final static int ACT_EDIT = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mText = (TextView)findViewById(R.id.text);
	}
    public void mOnClick_1(View v) {
        // 키 값 TextIn으로 ActEdit.class로 데이터를 보낸다.

        Intent intent = new Intent(this, ActEdit.class);    
        intent.putExtra("TextIn", mText.getText().toString());
        startActivityForResult(intent, ACT_EDIT);
     }
     
     protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        case ACT_EDIT:
        if (resultCode == RESULT_OK) {

            // 키 값 textOut로 데이터를 받는다
            mText.setText(data.getStringExtra("TextOut"));
        }
        break;
      }
    }

}
