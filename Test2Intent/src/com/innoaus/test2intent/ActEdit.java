package com.innoaus.test2intent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ActEdit extends Activity {
	 EditText mEdit;

     public void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.act);

     mEdit = (EditText)findViewById(R.id.stredit);
     // 키 값 TextIn으로 데이터를 받는다

     Intent intent = getIntent();
     String text = intent.getStringExtra("TextIn");

     if (text != null) {
        mEdit.setText(text);
     }
  }

  public void mOnClick_2(View v) {
     // 키 값 Textout로 데이터를 보낸다.
     Intent intent = new Intent();
     intent.putExtra("TextOut", mEdit.getText().toString());
     setResult(RESULT_OK, intent);
     finish();  
  }

  public void mOnClick_3(View v) {
     setResult(RESULT_CANCELED);
     finish();

	}
}
