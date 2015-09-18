package com.dropbox.android.sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.DropboxFileInfo;
import com.dropbox.client2.DropboxAPI.DropboxInputStream;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.exception.DropboxException;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;


public class ReadActivity extends AsyncTask<Void, Void, ArrayList<Entry>> {

    //private final ProgressDialog mDialog;
    private DropboxAPI<?> mApi;
    private String mPath;

    
    private Handler handler;
    
    private final static String Text_FILE_NAME = "test.txt";
    
	public ReadActivity(DropboxAPI<?> dropbox, String path, Handler handler) {
		this.mApi = dropbox;
		this.mPath = path;
		this.handler = handler;
	}
	@Override
	protected ArrayList<Entry> doInBackground(Void... params) {
		ArrayList<String> files = new ArrayList<String>();
		ArrayList<Entry> array = new ArrayList<Entry>();

		try {
			Entry directory = mApi.metadata(mPath, 1000, null, true, null);
			for (Entry entry : directory.contents) {
				files.add(entry.fileName());
				array.add(entry);
			}
		} catch (DropboxException e) {
			e.printStackTrace();
		}
		
		//return files;
		return array;
	}

	@Override
	protected void onPostExecute(ArrayList<Entry> result) {
		Message msgObj = handler.obtainMessage();
		Bundle b = new Bundle();
		
		for (Entry entry : result)
		{
			FileOutputStream outputStream = null;
			 try {
			     File file = new File("/sdcard/Download/" + entry.fileName());
			     outputStream = new FileOutputStream(file);
			     mApi.getFile(entry.path, entry.rev, outputStream, null);
			 } catch (Exception e) {
			     System.out.println("Something went wrong: " + e);
			 } finally {
			     if (outputStream != null) {
			         try {
			             outputStream.close();
			         } catch (IOException e) {}
			     }
			 }
			 
			 try {
			//This line of code reads the content of the local file that we downloaded
			 InputStream instream = new FileInputStream("/sdcard/Download/" + entry.fileName());
			 InputStreamReader inputreader = new InputStreamReader(instream);
			 BufferedReader buffreader = new BufferedReader(inputreader);
				Log.d("", "@@" + entry.fileName() + "/" + buffreader.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		ArrayList<String> files = new ArrayList<String>();
		for (Entry entry : result) {
			files.add(entry.fileName());
		}
		b.putStringArrayList("data", files);
		msgObj.setData(b);
		handler.sendMessage(msgObj);

	}
}
