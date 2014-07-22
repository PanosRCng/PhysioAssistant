package com.panosrcng.physioassistant;

import java.util.Arrays;
import java.util.List;

import com.panosrcng.physioassistant.R;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * This is a top levefragment, showing a list of items that the
 * user can pick.  Upon picking an item, it takes care of displaying the
 * data to the user as appropriate, based on the current screen size
 * and orientation.
 */

public class TitlesFragment extends ListFragment
{
    boolean mDualPane;
    int mCurCheckPosition = 0;


@Override public void onActivityCreated(Bundle savedInstanceState)
{
        super.onActivityCreated(savedInstanceState);

        // Populate list with our static array of titles.
        // Use a custom adapter so we can have something more than the just the text view filled in.
        setListAdapter (new CustomAdapter (getActivity (), R.layout.complex_list_item, 
                                           R.id.text1, Arrays.asList (Settings.TITLES)));

        // Check to see if we have a frame in which to embed the details
        // fragment directly in the containing UI.
        View detailsFrame = getActivity().findViewById(R.id.content);
        mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        if (savedInstanceState != null) {
            // Restore last state for checked position.
            mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
        }

        if (mDualPane)
        {
            // In dual-pane mode, the list view highlights the selected item.
            getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            // Make sure our UI is in the correct state.
            showDetails(mCurCheckPosition);
        }
    }

/**
 */

@Override public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", mCurCheckPosition);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        showDetails(position);
    }

    /**
     * Helper function to show the details of a selected item, either by
     * displaying a fragment in-place in the current UI, or starting a
     * whole new activity in which it is displayed.
     */
    void showDetails(int index)
    {	
    	mCurCheckPosition = index;
    	
		// clear the back stack
		getFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
    	
    	if (mDualPane)
    	{
    		// We can display everything in-place with fragments, so update
    		// the list to highlight the selected item and show the data.
    		getListView().setItemChecked(index, true);
    		
    		switch(index)
    		{
				case 0:
					showPatientsFragment();
					break;
					
				case 1:
					showPatientEditFragment();
					break;
					
				case 2:			
					//
					break;
					
				case 4:			
					showAboutFragment();
		            break;
		            
				default:			
					//
					break;
    		}

    	}
    	else
    	{
    		// else launch a new activity to display the content fragment
    		Intent intent = new Intent();
    		intent.setClass(getActivity(), ContentActivity.class);
    		intent.putExtra("index", index);
    		startActivity(intent);
    	}

    }

    private void showPatientsFragment()
    {
		// Make new fragment to show this selection.
		PatientsFragment patientsFragment = PatientsFragment.newInstance();
		
        // Execute a transaction, replacing any existing fragment with this inside the frame.
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.content, patientsFragment, "PatientsFragment");
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();
    }
    
    private void showPatientEditFragment()
    {
		// Make new fragment to show this selection.
		PatientEditFragment patientEditFragment = PatientEditFragment.newInstance();
		
        // Execute a transaction, replacing any existing fragment with this inside the frame.
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.content, patientEditFragment, "PatientEditFragment");
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();
    }
    
    private void showGridFragment()
    {
		// Make new fragment to show this selection.
		GridFragment gridFragment = GridFragment.newInstance();
		
        // Execute a transaction, replacing any existing fragment with this inside the frame.
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.content, gridFragment, "GridFragment");
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();
    }
    
    
    private void showAboutFragment()
    {
        // Make new fragment to show this selection.
        AboutFragment aboutFragment = AboutFragment.newInstance();

        // Execute a transaction, replacing any existing fragment with this inside the frame.
        FragmentTransaction ft2 = getFragmentManager().beginTransaction();
        ft2.replace(R.id.content, aboutFragment, "AboutFragment");
        ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft2.commit();
    }
    

    // customAdapter

    private class CustomAdapter extends ArrayAdapter<String>
    {
    	private Context mContext;
    	private int mLayoutId;

    
    	//constructor

    	public CustomAdapter(Context context, int layoutId, int textViewResourceId, List<String> items) 
    	{
    		super(context, textViewResourceId, items);
    		mContext = context;
    		mLayoutId = layoutId;
    	}


    	// returns a view that displays an item in the array.

    	public View getView (int position, View convertView, ViewGroup parent) 
    	{
    		View v = convertView;
	
    		if (v == null)
    		{
    			LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    			v = vi.inflate (mLayoutId, null);
    		}

    		View itemView = v;

    		ImageView image = (ImageView) itemView.findViewById(R.id.imageView1);
    		image.setImageResource(Settings.mThumbIds[position]);
    		
    		if (!mDualPane)
    		{
    			String text1 = getItem (position);
    			TextView tv1 = (TextView) itemView.findViewById (R.id.text1);
    			if (tv1 != null) tv1.setText (text1);
    		}
    			
    		return itemView;
    	}

    }

}
