package com.panosrcng.physioassistant;

import com.google.gson.Gson;
import com.panosrcng.physioassistant.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;


public class PatientFragment extends Fragment
{	
	Patient patient;
	
    /**
     * Create a new instance of PatientFragment
     */
    public static PatientFragment newInstance()
    {
    	PatientFragment f = new PatientFragment();

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
        
        View view = inflater.inflate(R.layout.fragment_patient_layout, container, false);
        
        patient = new Patient();
        Bundle bundle = getArguments();
        if( bundle  != null && bundle.containsKey("patient") )
        {    
        	patient = new Gson().fromJson(bundle.getString("patient"), Patient.class);
        }
        
        TextView titleTextView = (TextView) view.findViewById(R.id.patientTitleTextView);
        titleTextView.setText("Patient Profile");
        
        TextView lastnameLabelTextView = (TextView) view.findViewById(R.id.patientLastnameLabelTextView);
        lastnameLabelTextView.setText("Lastname:");
        TextView lastnameTextView = (TextView) view.findViewById(R.id.patientLastnameTextView);
        lastnameTextView.setText(patient.getLastname());
        
        TextView firstnameLabelTextView = (TextView) view.findViewById(R.id.patientFirstnameLabelTextView);
        firstnameLabelTextView.setText("Firstname:");
        TextView firstnameTextView = (TextView) view.findViewById(R.id.patientFirstnameTextView);
        firstnameTextView.setText(patient.getFirstname());
        
        TextView phoneLabelTextView = (TextView) view.findViewById(R.id.patientPhoneLabelTextView);
        phoneLabelTextView.setText("Phone:");
        TextView phoneTextView = (TextView) view.findViewById(R.id.patientPhoneTextView);
        phoneTextView.setText(patient.getPhone());
        
        TextView addressLabelTextView = (TextView) view.findViewById(R.id.patientAddressLabelTextView);
        addressLabelTextView.setText("Address:");
        TextView addressTextView = (TextView) view.findViewById(R.id.patientAddressTextView);
        addressTextView.setText(patient.getAddress());
        
        TextView notesLabelTextView = (TextView) view.findViewById(R.id.patientNotesLabelTextView);
        notesLabelTextView.setText("Notes:");
        TextView notesTextView = (TextView) view.findViewById(R.id.patientNotesTextView);
        notesTextView.setText(patient.getNotes());
        
        
        ImageView deleteButton = (ImageView) view.findViewById(R.id.deleteButton);
        
        deleteButton.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{	
		        DatabaseDAO databaseDAO = new DatabaseDAO(v.getContext());
		        databaseDAO.open();
				
				databaseDAO.deletePatient(patient);
				
				databaseDAO.close();
				
				showPatientsFragment();		
			}
		} );
        
        ImageView editButton = (ImageView) view.findViewById(R.id.editButton);
        
        editButton.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{	
				showPatientEditFragment();		
			}
		} );
        
        ImageView menuButton = (ImageView) view.findViewById(R.id.personMenuButton);
        
        menuButton.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{	
				showPatientMenuFragment();	
			}
		} );
        
        menuButton.setOnTouchListener( new OnTouchListener(){
            
        	@Override
            public boolean onTouch(View v, MotionEvent me)
        	{
                switch (me.getAction())
                {
                	case MotionEvent.ACTION_DOWN: 
                	{
                		((ImageView) v).setImageResource(R.drawable.person_menu_pressed);
                		break;
                	}
                	case MotionEvent.ACTION_UP:
                	{
                		((ImageView) v).setImageResource(R.drawable.person_menu);
                		break;
                	}
                }
                
                return false;
            }	
        });
          
        editButton.setOnTouchListener( new OnTouchListener(){
            
        	@Override
            public boolean onTouch(View v, MotionEvent me)
        	{
                switch (me.getAction())
                {
                	case MotionEvent.ACTION_DOWN: 
                	{
                		((ImageView) v).setImageResource(R.drawable.person_edit_pressed);
                		break;
                	}
                	case MotionEvent.ACTION_UP:
                	{
                		((ImageView) v).setImageResource(R.drawable.person_edit);
                		break;
                	}
                }
                
                return false;
            }	
        });
        
        deleteButton.setOnTouchListener( new OnTouchListener(){
            
        	@Override
            public boolean onTouch(View v, MotionEvent me)
        	{
                switch (me.getAction())
                {
                	case MotionEvent.ACTION_DOWN: 
                	{
                		((ImageView) v).setImageResource(R.drawable.person_delete_pressed);
                		break;
                	}
                	case MotionEvent.ACTION_UP:
                	{
                		((ImageView) v).setImageResource(R.drawable.person_delete);
                		break;
                	}
                }
                
                return false;
            }	
        });
 
        return view;
    }
    
    private void showPatientMenuFragment()
    {	
    	// Make new PatientMenuFragment
    	PatientMenuFragment patientMenuFragment = PatientMenuFragment.newInstance();
		
   	 	Bundle bundle = new Bundle();
   	 	bundle.putString("patient", new Gson().toJson(patient));
   	 	patientMenuFragment.setArguments(bundle); 
    	
    	// Execute a transaction, replacing any existing fragment with this inside the frame.
    	FragmentTransaction ft = getFragmentManager().beginTransaction();
    	
		ft.remove( getFragmentManager().findFragmentByTag("PatientFragment") );
    	
    	ft.replace(R.id.content, patientMenuFragment, "PatientMenuFragment");
    	ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    	ft.addToBackStack(null);
    	ft.commit();
    }    
    
    private void showPatientsFragment()
    {	
		// clear the back stack
		getFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);

    	// Make new PatientsFragment
    	PatientsFragment patientsFragment = PatientsFragment.newInstance();
		
    	// Execute a transaction, replacing any existing fragment with this inside the frame.
    	FragmentTransaction ft = getFragmentManager().beginTransaction();
		
    	// ????? ///
		PatientsFragment patients1Fragment = (PatientsFragment) getFragmentManager().findFragmentByTag("PatientsFragment");
		if( patients1Fragment != null )
		{
			ft.remove( patients1Fragment );
		}
    	
		ft.remove( getFragmentManager().findFragmentByTag("PatientFragment") );
    	
    	ft.replace(R.id.content, patientsFragment, "PatientsFragment");
    	ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    	ft.commit();
    }
    
    
    private void showPatientEditFragment()
    {
		// clear the back stack
		getFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
    	
		// Make new fragment to edit this patient
		PatientEditFragment patientEditFragment = PatientEditFragment.newInstance();
		
   	 	Bundle bundle = new Bundle();
   	 	bundle.putString("patient", new Gson().toJson(patient));
   	 	patientEditFragment.setArguments(bundle); 
		
        // Execute a transaction, replacing any existing fragment with this inside the frame.
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		
		PatientsFragment patientsFragment = (PatientsFragment) getFragmentManager().findFragmentByTag("PatientsFragment");
		if( patientsFragment != null )
		{
			ft.remove( patientsFragment );
		}
			
		ft.replace(R.id.content, patientEditFragment, "PatientEditFragment");
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();
    }
    
}
