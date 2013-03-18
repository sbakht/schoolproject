package com.example.gifting;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Scan extends Activity implements OnClickListener{
	
	Button b1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scan);
		b1=(Button)findViewById(R.id.bScanCode);
		b1.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bScanCode:
			IntentIntegrator integrator = new IntentIntegrator(this);
			integrator.initiateScan();
			break;
		}
	}

	@Override
	protected void onPause() {
	    super.onPause();
	   
	}

	@Override
	protected void onResume() {
	    super.onResume();
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		  IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		  if (scanResult != null) {
		    // handle scan result
		  }
		  // else continue with any other code you need in the method
		 // ...
		}
}
