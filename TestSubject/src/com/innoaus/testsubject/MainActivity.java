package com.innoaus.testsubject;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	ArrayList<MyData> arData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		arData = new ArrayList<MyData>();
		MyData mydata;
		
		mydata = new MyData(R.drawable.ic_action_rain, "RainPass", "Practice", 7);
		arData.add(mydata);
		mydata = new MyData(R.drawable.ic_action_bus, "IRUKEY", "Identity Random Unique Key", 4);
		arData.add(mydata);
		mydata = new MyData(R.drawable.ic_action_anchor, "Innoaus", "SSangdi", 1);
		arData.add(mydata);
		
		MyAdapter adapter = new MyAdapter(this, R.layout.item, arData, this);
		
		ListView list;
		list = (ListView)findViewById(R.id.list);
		list.setAdapter(adapter);
	}
}

//adapter�� �� ������ ���� 
class MyData {
	int Icon;
	String Name;
	String Name2;
	int interval;
	
	MyData(int aIcon, String aName, String bName, int interval){
		Icon = aIcon;
		Name = aName;
		Name2 = bName;
		this.interval = interval;
	}
}

//data�� ����Ʈ�信 ���������� Ŭ����
class MyAdapter extends BaseAdapter implements OnClickListener{
	
	Context con;
	LayoutInflater inflater;
	ArrayList<MyData> mData;
	int layout;
	Activity activity;
	TextView txt, txt2;
	
	//������
	public MyAdapter(Context context, int alayout, ArrayList<MyData> data, Activity activity){
		con = context;
		mData = data;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = alayout;
		this.activity = activity;
	}
	
	public int getCount(){
		return mData.size();
	}
	
	public Object getItem(int position){
		return mData.get(position).Name;
	}
	
	public long getItemId(int position){
		return position;
	}
	
	// �ݹ��������� ��� �����ŭ ���� ȭ�� �����ǿ� �ѷ���
	public View getView(final int position, View convertView, ViewGroup parent){
		if(convertView == null){
			convertView = inflater.inflate(layout, parent, false);
		}
		ImageView img = (ImageView) convertView.findViewById(R.id.img);
		img.setImageResource(mData.get(position).Icon);
		
		txt = (TextView) convertView.findViewById(R.id.txt);
		txt.setText(mData.get(position).Name);
		
		txt2 = (TextView) convertView.findViewById(R.id.txt2);
		txt2.setText(mData.get(position).Name2);
		
		ImageButton imgbtn = (ImageButton) convertView.findViewById(R.id.btn_image);
		imgbtn.setOnClickListener(this);
		imgbtn.setTag(position);

		return convertView;
		
	}
	@Override
	// ���� ��ư �������� ������ �������� ��������Ʈ �ѱ�
	public void onClick(View v) {
		int position = (Integer) v.getTag();
		MyData data = mData.get(position);
		
		Intent intent = new Intent(activity, SubActivity.class);
		intent.putExtra("Text1", data.Name);
		intent.putExtra("Text2", data.Name2);
		intent.putExtra("num", data.interval);
		activity.startActivityForResult(intent, 1);
	}
}