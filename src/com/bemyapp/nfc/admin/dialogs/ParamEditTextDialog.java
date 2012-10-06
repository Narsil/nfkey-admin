package com.bemyapp.nfc.admin.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.bemyapp.nfc.admin.R;
import com.bemyapp.nfc.admin.models.Param;
import com.bemyapp.nfc.admin.models.Param.IParamEditDialog;
import com.bemyapp.nfc.admin.models.Param.OnParamEditedListener;

/**
 * @author M-F.P
 * 
 */
public class ParamEditTextDialog extends AlertDialog implements IParamEditDialog/*, OnCheckedChangeListener*/ {
	
	private EditText mEditTextInput;
	private CheckBox mNotSpecifiedCheckBox;
	private Param mParamItem;
	private OnParamEditedListener mOnParamEditedListener;

	/**
	 * @param context
	 */
	public ParamEditTextDialog(Context context, OnParamEditedListener onParamEditedListener) {
		super(context);
		
		LayoutInflater inflater = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
		View customView = inflater.inflate(R.layout.dialog_edit_profile_normal_item, null, false);
		this.mEditTextInput = (EditText) customView.findViewById(R.id.editTextInput);
		/*this.mNotSpecifiedCheckBox = (CheckBox) customView.findViewById(R.id.notSpecifiedCheckBox);
		this.mNotSpecifiedCheckBox.setOnCheckedChangeListener(this);*/
		
		this.setView(customView);
		this.setButton(BUTTON_POSITIVE, "Save", this.mSaveOnClickListener);
		this.setButton(BUTTON_NEGATIVE, "Cancel", this.mCancelOnClickListener);
		this.mOnParamEditedListener = onParamEditedListener;
	}
	
	@Override
	public void show(Param param) {
		
		this.mEditTextInput.setEnabled(true);
		
		this.setTitle("Entrez votre "+param.label.toLowerCase());
		this.mEditTextInput.setText("");
		this.mEditTextInput.setHint(param.value);
		//this.mNotSpecifiedCheckBox.setVisibility((param.optional) ? View.VISIBLE : View.GONE);
		
		this.mParamItem = param;
		this.show();
	}
	/*
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		this.mEditTextInput.setEnabled(!isChecked);
	}*/
	
	// ===========================================================
	// Listeners
	// ===========================================================
	
	OnClickListener mSaveOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			
			ParamEditTextDialog profileDialog = (ParamEditTextDialog) dialog;
			/*if (ParamEditTextDialog.this.mNotSpecifiedCheckBox.isChecked()) {
				profileDialog.mProfileItem.value = "";
				profileDialog.mProfileItem.displayedValue = ProfileActivity.LABEL_NOT_SPECIFIED;
			} else {*/
				profileDialog.mParamItem.value = profileDialog.mEditTextInput.getText().toString();
			/*	profileDialog.mProfileItem.displayedValue = profileDialog.mEditTextInput.getText().toString();
			}*/
			
			profileDialog.mOnParamEditedListener.onEdit(profileDialog.mParamItem);
		}
	};
	
	OnClickListener mCancelOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {			
			dialog.cancel();
		}
	};
}
