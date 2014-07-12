package com.panosrcng.physioassistant;

import com.panosrcng.physioassistant.R;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

/**
 * This is a fragment that displays the details of a particular
 * item.
 */

public class GridFragment extends Fragment
{
    /**
     * Create a new instance of DetailsFragment, initialized to
     * show the text at 'index'.
     */
    public static GridFragment newInstance()
    {
        GridFragment f = new GridFragment();

        // Supply index input as an argument.
    //    Bundle args = new Bundle();
    //    args.putInt("index", index);
    //    f.setArguments(args);

        return f;
    }

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        if (container == null) {
            // We have different layouts, and in one of them this
            // fragment's containing frame doesn't exist.  The fragment
            // may still be created from its saved state, but there is
            // no reason to try to create its view hierarchy because it
            // won't be displayed.  Note this is not needed -- we could
            // just run the code below, where we would create and return
            // the view hierarchy; it would just never be used.
            return null;
        }      
        
        View view = inflater.inflate(R.layout.fragment_grid_layout, container, false);
        
        TextView textView = (TextView) view.findViewById(R.id.gridViewText);
        
        textView.setText("GridTitle");
        
        GridView gridView = (GridView) view.findViewById(R.id.gridViewButtons);
        gridView.setAdapter(new GridAdapter(view.getContext(), R.layout.grid_item));
        
        gridView.setOnItemClickListener(new OnItemClickListener()
        {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				
				switch(position)
				{
					case 0:
						
						showPatientsFragment();
						break;
						
					case 2:
						
						//
						break;
						
					case 5:
						
						//
						break;
						
					default:
						
						//
						break;
				}
			}
        	
        });

        return view;
    }
    
    private void showPatientsFragment()
    {
    	PatientsFragment patientsFragment = PatientsFragment.newInstance();
    	FragmentTransaction ft = getFragmentManager().beginTransaction();
    	
    	ft.remove( getFragmentManager().findFragmentByTag("GridFragment") );
    	
    	ft.replace(R.id.content, patientsFragment, "PatientsFragment");
    	ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    	ft.addToBackStack(null);
        ft.commit();
    }

}
