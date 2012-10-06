package com.bemyapp.nfc.admin;


import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class LockActivity extends Activity {
    
	
	String data;
	
	public final static String LOCKER_ID = "room42"; 
	private ImageView mImageView;
	private Button mButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lock);
        
        this.mImageView = (ImageView) this.findViewById(R.id.img_lock);
        
        this.mButton = (Button) this.findViewById(R.id.button_create);
        this.mButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				Intent intent = new Intent(LockActivity.this, NFKeyWriteActivity.class);
				LockActivity.this.startActivity(intent);
				//Intent intent = new Intent(LockActivity.this, CreateActivity.class);
				//LockActivity.this.startActivity(intent);
			}
		});
    }
    
	@Override
	protected void onResume() {
		super.onResume();

		data = resolveIntent(getIntent());
		
		if (data != null) {
			Access access = new Access(data);
			if (access.isValid()) {
				// OPEN DOOR
				Log.e("TAG", "OK");
			} else {
				// STAY CLOSED, ILLEGAL ACCESS
				Log.e("TAG", "ILLEGAL");
			}
		} else {
			// No data... Stay locked
			Log.e("TAG", "NOK");
		}
	}

	class Access {
		String lockerId;
		int startTimestamp;
		int endTimestamp;
		String extra;
		
		public Access(String data) {
			String splited[] = data.split(",");
			lockerId = splited[0];
			startTimestamp = Integer.parseInt(splited[1]);
			endTimestamp = Integer.parseInt(splited[2]);
			extra = splited[3];
		}

		public boolean isValid() {
			return lockerId.contentEquals(LOCKER_ID);
		}
	}
	
	
	
	private String resolveIntent(Intent intent) {
		// Parse the intent
		String action = intent.getAction(); 
		Log.e("Action", action);
		if (action.contains("android.intent.action.MAIN"))
			return null;
		if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)) {
			Parcelable[] rawMsgs = intent
			.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
			NdefMessage[] msgs;
			if (rawMsgs != null) {
				msgs = new NdefMessage[rawMsgs.length];
				for (int i = 0; i < rawMsgs.length; i++) {
					// tableau de message contenant les x rawmessage
					msgs[i] = (NdefMessage) rawMsgs[i];
				}
				String data = new String(msgs[0].getRecords()[0].getPayload());
				data = data.substring(data.indexOf("en") + "en".length());
				Log.e("TAG", "TAG="+data);
				return data;
			} else {
				// Unknown tag type
				return null;
			}
		} else {
			Log.e("tag", "Unknown intent " + intent);
			return null;
		}
	}
    
    
}