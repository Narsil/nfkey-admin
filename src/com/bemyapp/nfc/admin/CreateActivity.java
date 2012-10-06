package com.bemyapp.nfc.admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.bemyapp.nfc.admin.adapter.ListParamsAdapter;
import com.bemyapp.nfc.admin.dialogs.ParamDateEditDialog;
import com.bemyapp.nfc.admin.dialogs.ParamEditTextDialog;
import com.bemyapp.nfc.admin.dialogs.ParamRadioEditDialog;
import com.bemyapp.nfc.admin.models.Param;
import com.bemyapp.nfc.admin.models.Param.OnParamEditedListener;

public class CreateActivity extends Activity implements OnParamEditedListener  {
    
	private ListView mListView;
	private ListParamsAdapter mAdapter;
	private Button mSaveButton;
	
	private Toast mUpdateInfo;
	private ParamEditTextDialog mParamEditTextDialog;
	private ParamDateEditDialog mParamDateEditDialog;
	private ParamRadioEditDialog mParamRadioEditDialog;
	
	private int mLastUpdatedItemPosition = -1;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_create);
        
        this.mParamEditTextDialog = new ParamEditTextDialog(this, this);
		this.mParamDateEditDialog = new ParamDateEditDialog(this, this);
		this.mParamRadioEditDialog = new ParamRadioEditDialog(this, this);
		
		this.mUpdateInfo = Toast.makeText(this, "", Toast.LENGTH_SHORT);
		
		LinearLayout footer = (LinearLayout) this.getLayoutInflater().inflate(R.layout.footer_list_params, null);
        this.mSaveButton = (Button) footer.findViewById(R.id.button_save);
        this.mSaveButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				
				Intent intent = new Intent(CreateActivity.this, WaitActivity.class);
				CreateActivity.this.startActivity(intent);
			}
		});
        
        this.mAdapter = new ListParamsAdapter(this, R.layout.item_list_profile);
        this.mAdapter.add(new Param("Chambre", "24", this.mParamEditTextDialog));
        this.mAdapter.add(new Param("Type de réservation", "Standard", this.mParamRadioEditDialog, R.array.reservations));
        this.mAdapter.add(new Param("Date de début", "2012-10-01", this.mParamDateEditDialog));
        this.mAdapter.add(new Param("Date de fin", "2012-10-02", this.mParamDateEditDialog));
        
        LinearLayout header = (LinearLayout) this.getLayoutInflater().inflate(R.layout.header_list_params, null);
        
        this.mListView = (ListView) this.findViewById(R.id.list_params);
        this.mListView.addHeaderView(header, null, false);
        this.mListView.addFooterView(footer, null, false);
        this.mListView.setAdapter(this.mAdapter);
        this.mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				CreateActivity.this.mLastUpdatedItemPosition = position;
				Object item = CreateActivity.this.mAdapter.getItem(position - 1);
				if (item instanceof Param) {
					
					Param param = (Param) item;
					param.showDialog();
				}
			}
		});
    }

	/* (non-Javadoc)
	 * @see com.bemyapp.nfc.admin.Param.OnParamEditedListener#onEdit(com.bemyapp.nfc.admin.Param)
	 */
	public void onEdit(Param param) {
		
		if (this.mLastUpdatedItemPosition > 0) {
			
			Object item = this.mAdapter.getItem(this.mLastUpdatedItemPosition - 1);
			if (item instanceof Param) {
				
				Param newParamData = (Param) item;
				this.mAdapter.notifyDataSetChanged();
				this.mUpdateInfo.setText(newParamData.label+" updated");
				this.mUpdateInfo.show();
			}
		}
	}
}