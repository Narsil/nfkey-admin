package com.bemyapp.nfc.admin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WaitActivity extends Activity {
    
	private Button mCancelButton;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_wait);
        
        this.mCancelButton = (Button) this.findViewById(R.id.button_cancel);
        this.mCancelButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				WaitActivity.this.finish();
			}
		});
    }
    
    
}