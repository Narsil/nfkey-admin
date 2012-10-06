package com.bemyapp.nfc.admin.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bemyapp.nfc.admin.R;
import com.bemyapp.nfc.admin.models.Param;
import com.bemyapp.nfc.admin.models.Param.IParamEditDialog;
import com.bemyapp.nfc.admin.models.Param.OnParamEditedListener;

/**
 * @author M-F.P
 * 
 */
public class ParamRadioEditDialog extends AlertDialog implements IParamEditDialog {
	
	private Param mParamItem;
	private RadioGroup mRadioGroup;
	private RadioButton mNotSpecifiedRadioButton;
	private OnParamEditedListener mOnParamEditedListener;
	
	/**
	 * @param context
	 */
	public ParamRadioEditDialog(Context context, OnParamEditedListener onParamEditedListener) {
		super(context);
		
		LayoutInflater inflater = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
		View customView = inflater.inflate(R.layout.dialog_edit_profile_radio_item, null, false);
		this.mRadioGroup = (RadioGroup) customView.findViewById(R.id.radioGroup);
		/*this.mNotSpecifiedRadioButton = new RadioButton(this.getContext());
		this.mNotSpecifiedRadioButton.setText(ProfileActivity.LABEL_NOT_SPECIFIED);
		*/
		this.setView(customView);
		this.setButton(BUTTON_POSITIVE, "Save", this.mSaveOnClickListener);
		this.setButton(BUTTON_NEGATIVE, "Cancel", this.mCancelOnClickListener);
		this.mOnParamEditedListener = onParamEditedListener;
	}
	
	@Override
	public void show(Param param) {
		
		this.setTitle("Choisissez un "+param.label.toLowerCase());
		
		this.mRadioGroup.removeAllViews();
		//this.mRadioGroup.addView(this.mNotSpecifiedRadioButton);
		//this.mNotSpecifiedRadioButton.setVisibility((param.optional) ? View.VISIBLE : View.GONE);
		
		String[] radioValues = this.getContext().getResources().getStringArray(param.radioValuesResId);
		for (int i = 0; i < radioValues.length; i++) {
			
			RadioButton radioButton = new RadioButton(this.getContext());
			radioButton.setText(radioValues[i]);
			radioButton.setTag((i < radioValues.length) ? radioValues[i] : radioValues[i]);
			/*if (param.value.equals(radioValues[i])) {
				radioButton.setChecked(true);
			}*/
			
			this.mRadioGroup.addView(radioButton);
		}
		
		this.mParamItem = param;
		this.show();
	}
	
	// ===========================================================
	// Listeners
	// ===========================================================
	
	OnClickListener mSaveOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			
			ParamRadioEditDialog profileDialog = (ParamRadioEditDialog) dialog;
			
			int selectedRadioId = profileDialog.mRadioGroup.getCheckedRadioButtonId();
			RadioButton radioButton = (RadioButton) profileDialog.mRadioGroup.findViewById(selectedRadioId);
			/*if (radioButton == profileDialog.mNotSpecifiedRadioButton) {
				profileDialog.mProfileItem.value = "";
				profileDialog.mProfileItem.displayedValue = ProfileActivity.LABEL_NOT_SPECIFIED;
			} else {*/
				//int radioIndex = profileDialog.mRadioGroup.indexOfChild(radioButton);
			if (profileDialog.mParamItem != null) {
				profileDialog.mParamItem.value = radioButton.getTag().toString();
			}
				//profileDialog.mProfileItem.displayedValue = radioButton.getText().toString();
			//}
			
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
