package com.innoaus.testsubject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class SubActivity extends Activity {
	
	TextView textView, textView2, txt1, txt2;
	Handler myHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub);
		
		textView = (TextView) findViewById(R.id.txt_time);
		textView.setText(getMyCurrentTime());
		textView2 = (TextView) findViewById(R.id.txt3);
		textView2.setText(getRandomNumber());
		
		myHandler = new Handler();
		
		txt1 = (TextView) findViewById(R.id.txt1);
		txt2 = (TextView) findViewById(R.id.txt2);
		
		Intent intent = getIntent();
	    String title = intent.getStringExtra("Text1");
	    String data = intent.getStringExtra("Text2");
	    int interval = intent.getIntExtra("num", 1);
	    
	    txt1.setText(title);
	    txt2.setText(data);
	    
	    NumberThread numThread = new NumberThread(interval);
		numThread.start();
		
		TimeThread timerThread = new TimeThread();
		timerThread.start();
		
	}
	
	//현재 시간 받아오기
	public String getMyCurrentTime(){
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("mm"+":"+"ss");
		String time = sdf.format(calendar.getTime());
		return time;
	}
	
	// 랜덤값 받아오기
	public String getRandomNumber(){
		int offset = 0;
		Random rand = new Random();
		int nResult = rand.nextInt(50)+offset;
		return String.valueOf(nResult);
	}
	


	class NumberThread extends Thread
	{
		private int interval;

		public NumberThread(int interval) 
		{
			this.interval = interval;
		}
		
		public void run(){
			super.run();
			while(true){
				try{
					sleep(interval*1000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				myHandler.post(new Runnable(){
					public void run(){
						textView2.setText(getRandomNumber());
					}
				});
			}
		}
	}
	class NumberThread2 extends Thread{
		public void run(){
			super.run();
			while(true){
				try{
					sleep(4000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				myHandler.post(new Runnable(){
					public void run(){
						textView2.setText(getRandomNumber());
					}
				});
			}
		}
	}
	class NumberThread3 extends Thread{
		public void run(){
			super.run();
			while(true){
				try{
					sleep(1000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				myHandler.post(new Runnable(){
					public void run(){
						textView2.setText(getRandomNumber());
					}
				});
			}
		}
	}
	class TimeThread extends Thread{
		public void run(){
			super.run();
			while(true){
				try{
					sleep(1000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
				myHandler.post(new Runnable(){
					public void run(){
						textView.setText(getMyCurrentTime());
						
					}
				});
			}
		}
	}

}
