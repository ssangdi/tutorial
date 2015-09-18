package com.dropbox.android.sample;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.UploadRequest;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.exception.DropboxFileSizeException;
import com.dropbox.client2.exception.DropboxIOException;
import com.dropbox.client2.exception.DropboxParseException;
import com.dropbox.client2.exception.DropboxPartialFileException;
import com.dropbox.client2.exception.DropboxServerException;
import com.dropbox.client2.exception.DropboxUnlinkedException;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class WriteActivity extends AsyncTask<Void, Void, Boolean> {
	private DropboxAPI<?> mApi;
    private String path;
    private File mFile;
    private long mFileLen;
   
    private UploadRequest mRequest;
    private Context context;
    private ProgressDialog mDialog;
    
    private String mErrorMsg;
    
    public WriteActivity(Context context ,DropboxAPI<?> api, String path
            ) {
        // We set the context this way so we don't accidentally leak activities

        this.context = context.getApplicationContext();
		this.mApi = api;
		this.path = path;
    }

	@Override
	protected Boolean doInBackground(Void... params) {
		final File tempDir = context.getCacheDir();
		File tempFile;
		FileWriter fr;
		try {
			tempFile = File.createTempFile("file", ".txt", tempDir);
			fr = new FileWriter(tempFile);
			fr.write("Sample text file created for demo purpose. You may use some other file format for your app ");
			fr.close();

			FileInputStream fileInputStream = new FileInputStream(tempFile);
			mApi.putFile(path + "textfile.txt", fileInputStream,
					tempFile.length(), null, null);
			tempFile.delete();

            if (mRequest != null) {
                mRequest.upload();
                return true;
            }

        } catch (DropboxUnlinkedException e) {
            // This session wasn't authenticated properly or user unlinked
            mErrorMsg = "This app wasn't authenticated properly.";
        } catch (DropboxFileSizeException e) {
            // File size too big to upload via the API
            mErrorMsg = "This file is too big to upload";
        } catch (DropboxPartialFileException e) {
            // We canceled the operation
            mErrorMsg = "Upload canceled";
        } catch (DropboxServerException e) {
            // Server-side exception.  These are examples of what could happen,
            // but we don't do anything special with them here.
            if (e.error == DropboxServerException._401_UNAUTHORIZED) {
                // Unauthorized, so we should unlink them.  You may want to
                // automatically log the user out in this case.
            } else if (e.error == DropboxServerException._403_FORBIDDEN) {
                // Not allowed to access this
            } else if (e.error == DropboxServerException._404_NOT_FOUND) {
                // path not found (or if it was the thumbnail, can't be
                // thumbnailed)
            } else if (e.error == DropboxServerException._507_INSUFFICIENT_STORAGE) {
                // user is over quota
            } else {
                // Something else
            }
            // This gets the Dropbox error, translated into the user's language
            mErrorMsg = e.body.userError;
            if (mErrorMsg == null) {
                mErrorMsg = e.body.error;
            }
        } catch (DropboxIOException e) {
            // Happens all the time, probably want to retry automatically.
            mErrorMsg = "Network error.  Try again.";
        } catch (DropboxParseException e) {
            // Probably due to Dropbox server restarting, should retry
            mErrorMsg = "Dropbox error.  Try again.";
        } catch (DropboxException e) {
            // Unknown error
            mErrorMsg = "Unknown error.  Try again.";
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return false;
    }
    @Override
    protected void onPostExecute(Boolean result) {
        
        if (result) {
            showToast("Text successfully uploaded");
        } else {
            showToast(mErrorMsg);
        }
    }

    private void showToast(String msg) {
        Toast error = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        error.show();
    }

	
}
