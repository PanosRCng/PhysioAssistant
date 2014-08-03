package com.panosrcng.physioassistant;

import com.google.gson.Gson;
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
 * This is a fragment that displays the patient's menu 
 */

public class PatientMenuFragment extends Fragment
{    
	private Patient patient;
	
    /**
     * Create a new instance of PatientMenuFragment, initialized to
     * show the text at 'index'.
     */
    public static PatientMenuFragment newInstance()
    {
        PatientMenuFragment f = new PatientMenuFragment();

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
        
        Bundle bundle = getArguments();
        if( bundle  != null && bundle.containsKey("patient") )
        {    
        	patient = new Gson().fromJson(bundle.getString("patient"), Patient.class);
        }
        
        TextView textView = (TextView) view.findViewById(R.id.gridViewText);
        
        textView.setText(patient.getLastname() + " " + patient.getFirstname());
        
        GridView gridView = (GridView) view.findViewById(R.id.gridViewButtons);
        gridView.setAdapter(new GridAdapter(view.getContext(), R.layout.grid_item, Settings.patientMenuButtons, Settings.patientMenuTitles));
        
        gridView.setOnItemClickListener(new OnItemClickListener()
        {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				
				switch(position)
				{
					case 0:
						
						showSessionEditFragment();
						break;
						
					case 1:
						
						//
						break;
						
					case 2:
						
						showSessionsFragment();
						break;
						
					default:
						
						//
						break;
				}
			}
        	
        });

        return view;
    }
    
    private void showSessionEditFragment()
    {
    	SessionEditFragment sessionEditFragment = SessionEditFragment.newInstance();
    	
   	 	Bundle bundle = new Bundle();
   	 	bundle.putString("patient", new Gson().toJson(patient));  	 	
   	 	sessionEditFragment.setArguments(bundle); 
    	
    	FragmentTransaction ft = getFragmentManager().beginTransaction();
    	
    	ft.remove( getFragmentManager().findFragmentByTag("PatientMenuFragment") );
    	
    	ft.replace(R.id.content, sessionEditFragment, "SessionEditFragment");
    	ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    	ft.addToBackStack(null);
        ft.commit();
    }
    
    private void showSessionsFragment()
    {
    	SessionsFragment sessionsFragment = SessionsFragment.newInstance();
    	
   	 	Bundle bundle = new Bundle();
   	 	bundle.putString("patient", new Gson().toJson(patient));  	 	
   	 	sessionsFragment.setArguments(bundle); 
    	
    	FragmentTransaction ft = getFragmentManager().beginTransaction();
    	
    	ft.remove( getFragmentManager().findFragmentByTag("PatientMenuFragment") );
    	
    	ft.replace(R.id.content, sessionsFragment, "SessionsFragment");
    	ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    	ft.addToBackStack(null);
        ft.commit();
    }

}
