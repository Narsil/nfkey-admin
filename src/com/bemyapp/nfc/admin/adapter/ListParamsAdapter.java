/**
 * 
 */
package com.bemyapp.nfc.admin.adapter;

import com.bemyapp.nfc.admin.R;
import com.bemyapp.nfc.admin.R.id;
import com.bemyapp.nfc.admin.models.Param;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author M-F.P
 * 
 */
public class ListParamsAdapter extends ArrayAdapter<Param> {
	
	private int mLineLayout;
	private LayoutInflater mInflater;
	
	public ListParamsAdapter(Context pContext, int pLineLayout) {
        super(pContext, pLineLayout);
        
        this.mLineLayout = pLineLayout;
        this.mInflater = (LayoutInflater) pContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

	static class ViewHolder {
		
        TextView label;
        TextView value;
    }
	
	@Override
    public View getView(int pPosition, View pView, ViewGroup pParent) {

        ViewHolder holder;
        if (pView == null) {
            
        	pView = this.mInflater.inflate(this.mLineLayout, null);
            
            holder = new ViewHolder();
            holder.label = (TextView) pView.findViewById(R.id.labelTv);
            holder.value = (TextView) pView.findViewById(R.id.valueTv);
            
            pView.setTag(holder);
            
        } else {
            holder = (ViewHolder) pView.getTag();
        }
        
        Param param = getItem(pPosition);
        if (param != null) {
    		
        	holder.label.setText(param.label);
        	holder.value.setText(param.value);
        }
        
        return pView;
    }
}
