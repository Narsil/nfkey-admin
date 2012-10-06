package com.bemyapp.nfc.admin.models;

public class Param {
	
	public String label;
	public String value;
	public int radioValuesResId;
	public IParamEditDialog editDialog;
	
	/**
	 * @param parcel
	 */
	public Param(String label, String value, IParamEditDialog editDialog) {
		
		this.label = label;
		this.value = value;
		this.editDialog = editDialog;
	}
	
	public Param(String label, String value, IParamEditDialog editDialog, int radioValuesResId) {
		this(label, value, editDialog);
		
		this.radioValuesResId = radioValuesResId;
	}
	
	public void showDialog() {
		this.editDialog.show(this);
	}
	
	// ===========================================================
    // Inner classes
    // ===========================================================
	
	public static interface OnParamEditedListener {
		
		public void onEdit(Param param);
	}
	
	public static interface IParamEditDialog {
		
		public void show(Param param);
	}
}
