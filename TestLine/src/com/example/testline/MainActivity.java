package com.example.testline;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

	public static final String FONT_MENLO = "fonts/Menlo.ttf";
	private RelativeLayout layoutCode1;
	private RelativeLayout layoutCode2;
	private LinearLayout layoutCode3;
	private LinearLayout layoutCode4;
	private View view1;
	private View view2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		
		view1 = (View) findViewById(R.id.view1);
		view2 = (View) findViewById(R.id.view2);
		
		layoutCode1 = (RelativeLayout) findViewById(R.id.layout_code1);
		layoutCode2 = (RelativeLayout) findViewById(R.id.layout_code2);
		
		layoutCode3 = (LinearLayout) findViewById(R.id.layout_code3);
		layoutCode4 = (LinearLayout) findViewById(R.id.layout_code4);
		
		getCodeLayout("QDPR1", layoutCode1, view1, layoutCode3);
		getCodeLayout("aaeaQ", layoutCode2, view2, layoutCode4);
	}
	
	private void getCodeLayout(String pin, RelativeLayout layout, View view, LinearLayout linearlayout)
	{
		DisplayMetrics metrics = getResources().getDisplayMetrics();
        int px = (int) ( 279 * (metrics.densityDpi / 160.0f));

		Log.d("getCodeLayout",  "@@ " + px);

		//int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 297, getResources().getDisplayMetrics());
		linearlayout.removeAllViews();
		layout.removeAllViews();
				
		float ySize;
		float fontSize = 0;
		if (pin.length() >= 6)
		{
			fontSize = 63.0f;
		}
		else
		{
			fontSize = 80.0f;
		}
		
		View LayoutView = new View(this);
		LayoutView.setBackgroundColor(getResources().getColor(R.color.white));
		
		
		Typeface face = Typeface.createFromAsset(getAssets(), FONT_MENLO);
		for (int i = 0; i < pin.length(); i++)
		{
			char ch = pin.charAt(i);
			TextView tv = new TextView(this);
			
			tv.setTextColor(getResources().getColor(R.color.white));
			tv.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			tv.setTextSize(fontSize);
			// String hex = String.format("%04x", (int) ch);
			if (ch <= 57 && ch >= 48)
			{
				tv.setTypeface(face);
				tv.setText("" + ch);
				tv.setTextColor(getResources().getColor(R.color.code_number));
			}
			else if ((ch >= 65 && ch <= 90) || (ch >= 97 && ch <= 122))
			{
				tv.setTypeface(face);
				tv.setText("" + ch);
			}
			else
			{
				tv.setTypeface(face);
				tv.setText("" + ch);
				tv.setTextColor(getResources().getColor(R.color.code_special));
			}
			linearlayout.addView(tv);		
			
		}
		
		TextView temp = new TextView(this);
		temp.setTextSize(fontSize);
		temp.setTypeface(face);
		temp.setText("A");
	
		Log.d("MainActivity", "@@ temp width:" + temp.getWidth());
		
		ySize = temp.getLineHeight()/2.5f;
		LayoutView.setLayoutParams(new LayoutParams(px, 4));
		LayoutView.setY(ySize);
		 
		layout.addView(linearlayout);
		layout.addView(LayoutView);
	}

}
