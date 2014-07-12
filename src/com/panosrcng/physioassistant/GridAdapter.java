package com.panosrcng.physioassistant;

import com.panosrcng.physioassistant.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GridAdapter extends BaseAdapter {

    private Context mContext;
	private int mLayoutId;

    public GridAdapter(Context c) {
        mContext = c;
    }
    
	//constructor

	public GridAdapter(Context context, int layoutId) 
	{
		mContext = context;
		mLayoutId = layoutId;
	}

    @Override
    public int getCount() {
        return mThumbIds.length;
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
	    image.setImageResource(mThumbIds[position]);

		TextView tv1 = (TextView) itemView.findViewById (R.id.gridItemTextView);
		tv1.setText ("title");
			
		return itemView;
    }

    static Integer[] mThumbIds = { R.drawable.button1, R.drawable.button2,
            R.drawable.button3, R.drawable.button4, R.drawable.button5, R.drawable.button6,
    };
}
