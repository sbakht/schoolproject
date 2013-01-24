package com.example.gifting;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;
import android.widget.Toast;

public class AndroidUploadActivity extends Activity {
	/** Called when the activity is first created. */
	TextView textView1;

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_androidupload);

		textView1 = (TextView) findViewById(R.id.textView1);
		/*
		try {
			upload();
		} catch (Exception e) {
			Toast toast = Toast.makeText(this, e.toString(), Toast.LENGTH_LONG);
			toast.show();
			e.printStackTrace();
		}
		*/
		try {
			new MyAsyncTask().execute();
		} catch (Exception e) {
			Toast toast = Toast.makeText(this, e.toString(), Toast.LENGTH_LONG);
			toast.show();
			e.printStackTrace();
		}
	}
	
	private class MyAsyncTask extends AsyncTask<Void, Void, Void>
    {

        ProgressDialog mProgressDialog;
        protected void onPostExecute(Void result) {
            mProgressDialog.dismiss();
        }

        protected void onPreExecute() {
            mProgressDialog = ProgressDialog.show(AndroidUploadActivity.this, "Loading...", "Data is Loading...");
        }

        protected Void doInBackground(Void... params) {
    		try {
    			upload();
    		} catch (Exception e) {
    			//Toast toast = Toast.makeText(this, e.toString(), Toast.LENGTH_LONG);
    			//toast.show();
    			e.printStackTrace();
    		}
            return null;
        }
    }


	public void upload() throws Exception {
        HttpURLConnection connection = null;
        DataOutputStream outputStream = null;

        String pathToOurFile = Environment.getExternalStorageDirectory()
				.getPath() + "/Redex/derp.mp4";
        String urlServer = "http://pushbackstudios.com/directory/uploader.php";
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;

            FileInputStream fileInputStream = new FileInputStream(new File(pathToOurFile));
            URL url = new URL(urlServer);

            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);

            outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            outputStream
                    .writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\""
                            + pathToOurFile + "\"" + lineEnd);
            outputStream.writeBytes(lineEnd);

            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                outputStream.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens
                    + lineEnd);

            String serverResponseMessage = connection.getResponseMessage();

            textView1.setText(serverResponseMessage);

            fileInputStream.close();
            outputStream.flush();
            outputStream.close();
    }
}