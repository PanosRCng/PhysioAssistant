package com.panosrcng.physioassistant;

import java.util.List;

import com.google.gson.Gson;
import com.panosrcng.physioassistant.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class PatientsFragment extends ListFragment
{	
    /*
     * Create a new instance of PatientsFragment
     */
    public static PatientsFragment newInstance()
    {
    	PatientsFragment f = new PatientsFragment();

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
        
        View view = inflater.inflate(R.layout.fragment_patients_layout, container, false);
        
        TextView textView = (TextView) view.findViewById(R.id.patientsViewText);
        textView.setText("PatientsTitle");
        
        ListView listView = (ListView) view.findViewById(android.R.id.list);
      
        DatabaseDAO databaseDAO = new DatabaseDAO(view.getContext());
        databaseDAO.open();

        List<Patient> patientsList = databaseDAO.getAllpatients();
        
        databaseDAO.close();
        
        listView.setAdapter (new PatientsAdapter (view.getContext(), R.layout.patients_list_item, 
                R.id.nameView, patientsList));

        return view;
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {	
    	Patient patient = (Patient) l.getItemAtPosition(position);
    	
        showPatient( patient );
    }
    
    
    private void showPatient(Patient patient)
    {
    	PatientFragment patientFragment = PatientFragment.newInstance();
    	
   	 	Bundle bundle = new Bundle();
   	 	bundle.putString("patient", new Gson().toJson(patient));
   	 	patientFragment.setArguments(bundle); 
    	
    	FragmentTransaction ft = getFragmentManager().beginTransaction();
    	
    	ft.remove( getFragmentManager().findFragmentByTag("PatientsFragment") );
    	
    	ft.replace(R.id.content, patientFragment, "PatientFragment");
    	ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    	ft.addToBackStack(null);
        ft.commit();
    }
    
    
    // customAdapter

    private class PatientsAdapter extends ArrayAdapter<Patient>
    {
    	private Context mContext;
    	private int mLayoutId;

    
    	//constructor

    	public PatientsAdapter(Context context, int layoutId, int textViewResourceId, List<Patient> items) 
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

    		TextView phoneView = (TextView) itemView.findViewById(R.id.phoneView);
    		TextView nameView = (TextView) itemView.findViewById (R.id.nameView);
    		
    		Patient patient = getItem (position);
    		
    		nameView.setText(patient.getFirstname() + "  " + patient.getLastname());
    		phoneView.setText(patient.getPhone());
    		
    		return itemView;
    	}

    }
     
}
