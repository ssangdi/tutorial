package com.dropbox.api.samples;

import java.util.Map;

import com.dropbox.api.samples.chooser_start.R;
import com.dropbox.chooser.android.DbxChooser;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session.AccessType;
import com.dropbox.client2.session.TokenPair;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

    static final String APP_KEY = "wuss2d0wxpfhhar" /* This is for you to fill in! */;
    private static final String APP_SECRET = "c9lqggas43ok4in";
    static final int DBX_CHOOSER_REQUEST = 0;  // You can change this if needed
    
    private final static String DROPBOX_NAME = "dropbox_prefs";
    private static final String ACCESS_KEY = "ACCESS_KEY";
    private static final String ACCESS_SECRET = "ACCESS_SECRET";

    private static final boolean USE_OAUTH1 = false;
    
    private final String TXT_DIR = "/DropboxSample/";
    private Button mChooserButton;
    private DbxChooser mChooser;
    
    private boolean isLoggedIn;
	private Button logIn;
	private Button uploadFile;
    
    DropboxAPI<AndroidAuthSession> dropbox;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
                              
        mChooser = new DbxChooser(APP_KEY);
        
        logIn = (Button) findViewById(R.id.dropbox_login);
		logIn.setOnClickListener(this);
		uploadFile = (Button) findViewById(R.id.upload_file);
		uploadFile.setOnClickListener(this);
		
		loggedIn(false);
		AndroidAuthSession session;
		AppKeyPair pair = new AppKeyPair(ACCESS_KEY, ACCESS_SECRET);
		
		SharedPreferences prefs = getSharedPreferences(DROPBOX_NAME, 0);
		String key = prefs.getString(ACCESS_KEY, null);
		String secret = prefs.getString(ACCESS_SECRET, null);

		if (key != null && secret != null) {
			AccessTokenPair token = new AccessTokenPair(key, secret);
			session = new AndroidAuthSession(pair, AccessType.APP_FOLDER, token);
		} else {
			session = new AndroidAuthSession(pair, AccessType.APP_FOLDER);
		}
		dropbox = new DropboxAPI<AndroidAuthSession>(session);
        
   /*     mWrite = (Button)findViewById(R.id.upload_file);
        mWrite.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {           	
            	WriteActivity write = new WriteActivity(MainActivity.this, dropbox, TXT_DIR);
            	write.execute();
            }
        });*/
        
        mChooserButton = (Button) findViewById(R.id.chooser_button);
        mChooserButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DbxChooser.ResultType resultType;
                switch (((RadioGroup) findViewById(R.id.link_type)).getCheckedRadioButtonId()) {
                    case R.id.link_type_direct: resultType = DbxChooser.ResultType.DIRECT_LINK; break;
                    case R.id.link_type_content: resultType = DbxChooser.ResultType.FILE_CONTENT; break;
                    case R.id.link_type_preview: resultType = DbxChooser.ResultType.PREVIEW_LINK; break;
                    default: throw new RuntimeException("unexpected link type!!");
                }
                
                
                mChooser.forResultType(resultType)
                        .launch(MainActivity.this, DBX_CHOOSER_REQUEST);
            }
        });
    }
    @Override
	protected void onResume() {
		super.onResume();

		AndroidAuthSession session = dropbox.getSession();
		if (session.authenticationSuccessful()) {
			try {
				session.finishAuthentication();
				TokenPair tokens = session.getAccessTokenPair();
				SharedPreferences prefs = getSharedPreferences(DROPBOX_NAME, 0);
				Editor editor = prefs.edit();
				editor.putString(ACCESS_KEY, tokens.key);
				editor.putString(ACCESS_SECRET, tokens.secret);
				editor.commit();
				loggedIn(true);
			} catch (IllegalStateException e) {
				Toast.makeText(this, "Error during Dropbox authentication",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	public void loggedIn(boolean isLogged) {
		isLoggedIn = isLogged;
		uploadFile.setEnabled(isLogged);
		logIn.setText(isLogged ? "Log out" : "Log in");
	}
	@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DBX_CHOOSER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                DbxChooser.Result result = new DbxChooser.Result(data);
                Log.d("main", "Link to selected file: " + result.getLink());
                
                showLink(R.id.uri, result.getLink());
                ((TextView) findViewById(R.id.filename)).setText(result.getName().toString(), TextView.BufferType.NORMAL);
                ((TextView) findViewById(R.id.size)).setText(String.valueOf(result.getSize()), TextView.BufferType.NORMAL);
                showLink(R.id.icon, result.getIcon());

                Map<String, Uri> thumbs = result.getThumbnails();
                showLink(R.id.thumb64, thumbs.get("64x64"));
                showLink(R.id.thumb200, thumbs.get("200x200"));
                showLink(R.id.thumb640, thumbs.get("640x480"));
                //((TextView) findViewById(R.id.textcontents)).setText(result.getText().toString(), TextView.BufferType.NORMAL);
            } else {
                // Failed or was cancelled by the user.
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void showLink(int id, Uri uri) {
        TextView v = (TextView) findViewById(id);
        if (uri == null) {
            v.setText("", TextView.BufferType.NORMAL);
            return;
        }
        v.setText(uri.toString(), TextView.BufferType.NORMAL);
        v.setMovementMethod(LinkMovementMethod.getInstance());
    }
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.dropbox_login:
			if (isLoggedIn) {
				dropbox.getSession().unlink();
				loggedIn(false);
			} 
			else 
			{
				//dropbox.getSession().startAuthentication(MainActivity.this);
				dropbox.getSession().startOAuth2Authentication(MainActivity.this);
			}

			break;	
		case R.id.upload_file:
			WriteActivity upload = new WriteActivity(this, dropbox,
					TXT_DIR);
			upload.execute();
			break;
		
		}

	}
}