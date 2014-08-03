package com.panosrcng.physioassistant;

import com.panosrcng.physioassistant.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter
{
    private Context mContext;
	private int mLayoutId;
	private Integer[] patientMenuButtons;
	private String[] patientMenuTitles;
	
    public GridAdapter(Context c) {
        mContext = c;
    }
    
	//constructor

	public GridAdapter(Context context, int layoutId, Integer[] patientMenuButtons, String[] patientMenuTitles) 
	{
		mContext = context;
		mLayoutId = layoutId;
		this.patientMenuButtons = patientMenuButtons;
		this.patientMenuTitles = patientMenuTitles;
	}

    @Override
    public int getCount() {
        return patientMenuButtons.length;
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {    
		View v = convertView;
		
		if (v == null)
		{
			LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate (mLayoutId, null);
		}

		View itemView = v;

		ImageView image = (ImageView) itemView.findViewById(R.id.gridItemImageView);	
	    image.setImageResource(patientMenuButtons[position]);

		TextView tv1 = (TextView) itemView.findViewById (R.id.gridItemTextView);
		tv1.setText( patientMenuTitles[position] );
			
		return itemView;
    }

}
