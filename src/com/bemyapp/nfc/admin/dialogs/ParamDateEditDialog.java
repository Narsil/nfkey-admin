package com.bemyapp.nfc.admin.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;

import com.bemyapp.nfc.admin.R;
import com.bemyapp.nfc.admin.models.Param;
import com.bemyapp.nfc.admin.models.Param.IParamEditDialog;
import com.bemyapp.nfc.admin.models.Param.OnParamEditedListener;
import com.bemyapp.nfc.admin.utils.StringUtils;

/**
 * @author M-F.P
 * 
 */
public class ParamDateEditDialog extends AlertDialog implements IParamEditDialog/*, OnCheckedChangeListener*/ {

	private DatePicker mDatePicker;
	private CheckBox mNotSpecifiedCheckBox;
	private Param mParamItem;
	private OnParamEditedListener mOnParamItemEditedListener;

	/**
	 * @param context
	 */
	public ParamDateEditDialog(Context context, OnParamEditedListener onProfileItemEditedListener) {
		super(context);
		
		LayoutInflater inflater = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
		View customView = inflater.inflate(R.layout.dialog_edit_profile_date_item, null, false);
		this.mDatePicker = (DatePicker) customView.findViewById(R.id.datePicker);
		/*this.mNotSpecifiedCheckBox = (CheckBox) customView.findViewById(R.id.notSpecifiedCheckBox);
		this.mNotSpecifiedCheckBox.setOnCheckedChangeListener(this);
		*/
		this.setView(customView);
		this.setButton(BUTTON_POSITIVE, "Save", this.mSaveOnClickListener);
		this.setButton(BUTTON_NEGATIVE, "Cancel", this.mCancelOnClickListener);
		this.mOnParamItemEditedListener = onProfileItemEditedListener;
	}

	@Override
	public void show(Param param) {
		
		this.mDatePicker.setEnabled(true);
		this.setTitle("Entrez votre "+param.label.toLowerCase());
		/*this.mNotSpecifiedCheckBox.setVisibility((item.optional) ? View.VISIBLE : View.GONE);
		this.mNotSpecifiedCheckBox.setChecked(false);
		*/
		String[] date = null;
		if (!StringUtils.isEmpty(param.value)) {
			date = param.value.split("-");
		}
		
		if (date != null && date.length == 3) {
			
			int year = Integer.parseInt(date[0]);
			int monthOfYear = Integer.parseInt(date[1]) - 1;
			int dayOfMonth = Integer.parseInt(date[2]);
			
			year = (year >= 1900) ? year : 1900;
			monthOfYear = (monthOfYear >= 0) ? monthOfYear : 0;
			dayOfMonth = (dayOfMonth > 0) ? dayOfMonth : 1;
			this.mDatePicker.init(year, monthOfYear, dayOfMonth, null);
		} else {
			this.mDatePicker.init(1985, 7, 15, null);
		}
		
		this.mParamItem = param;
		this.show();
	}
	/*
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		this.mDatePicker.setEnabled(!isChecked);
	}
	*/
	// ===========================================================
	// Listeners
	// ===========================================================
	
	DialogInterface.OnClickListener mSaveOnClickListener = new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			
			ParamDateEditDialog profileDialog = (ParamDateEditDialog) dialog;
			/*if (ParamDateEditDialog.this.mNotSpecifiedCheckBox.isChecked()) {
				profileDialog.mParamItem.value = "";
				profileDialog.mParamItem.displayedValue = ProfileActivity.LABEL_NOT_SPECIFIED;
			} else {*/
				DatePicker datePicker = profileDialog.mDatePicker;
				
				String month = StringUtils.toStringFormat(datePicker.getMonth(), "00");
				String day = StringUtils.toStringFormat(datePicker.getDayOfMonth(), "00");
				
				profileDialog.mParamItem.value = datePicker.getYear()+"-"+month+"-"+day;
				//profileDialog.mParamItem.displayedValue = datePicker.getYear()+"-"+month+"-"+day;
			//}
			
			profileDialog.mOnParamItemEditedListener.onEdit(profileDialog.mParamItem);
		}
	};
	
	DialogInterface.OnClickListener mCancelOnClickListener = new DialogInterface.OnClickListener() {
		
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.cancel();
		}
	};
}
