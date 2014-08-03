package com.panosrcng.physioassistant;

import java.util.Date;

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


public class SessionFragment extends Fragment
{	
	Session session;
	Patient patient;
	
    /**
     * Create a new instance of SessionFragment
     */
    public static SessionFragment newInstance()
    {
    	SessionFragment f = new SessionFragment();

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
        
        View view = inflater.inflate(R.layout.fragment_session_layout, container, false);
        
        Bundle bundle = getArguments();
        
        if( bundle  != null && bundle.containsKey("session") )
        {    
        	session = new Gson().fromJson(bundle.getString("session"), Session.class);
        }
        
        if( bundle  != null && bundle.containsKey("patient") )
        {    
        	patient = new Gson().fromJson(bundle.getString("patient"), Patient.class);
        }
        
        TextView titleTextView = (TextView) view.findViewById(R.id.sessionTitleTextView);
        titleTextView.setText(patient.getLastname() + " " + patient.getFirstname());

        TextView sessionDateTextView = (TextView) view.findViewById(R.id.sessionDateTextView);
        sessionDateTextView.setText("date: " + session.getDateStr() );
        
        TextView descriptionLabelTextView = (TextView) view.findViewById(R.id.sessionDescriptionLabelTextView);
        descriptionLabelTextView.setText("Description:");
        TextView descriptionTextView = (TextView) view.findViewById(R.id.sessionDescriptionTextView);
        descriptionTextView.setText(session.getDescription());
        
        TextView treatmentLabelTextView = (TextView) view.findViewById(R.id.sessionTreatmentLabelTextView);
        treatmentLabelTextView.setText("Treatment:");
        TextView treatmentTextView = (TextView) view.findViewById(R.id.sessionTreatmentTextView);
        treatmentTextView.setText(session.getTreatment());

        TextView notesLabelTextView = (TextView) view.findViewById(R.id.sessionNotesLabelTextView);
        notesLabelTextView.setText("Notes:");
        TextView notesTextView = (TextView) view.findViewById(R.id.sessionNotesTextView);
        notesTextView.setText(session.getNotes());

        ImageView profileButton = (ImageView) view.findViewById(R.id.personProfileButton);
        
        profileButton.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{	
				showPatient();		
			}
		} );
        
        ImageView deleteButton = (ImageView) view.findViewById(R.id.sessionDeleteButton);
        
        deleteButton.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{	
		        DatabaseDAO databaseDAO = new DatabaseDAO(v.getContext());
		        databaseDAO.open();
				
				databaseDAO.deleteSession(session);
				
				databaseDAO.close();
				
				showSessionsFragment();		
			}
		} );
        
        ImageView editButton = (ImageView) view.findViewById(R.id.sessionEditButton);
        
        editButton.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{	
				showSessionEditFragment();		
			}
		} );
                
        editButton.setOnTouchListener( new OnTouchListener(){
            
        	@Override
            public boolean onTouch(View v, MotionEvent me)
        	{
                switch (me.getAction())
                {
                	case MotionEvent.ACTION_DOWN: 
                	{
                		((ImageView) v).setImageResource(R.drawable.session_edit_pressed);
                		break;
                	}
                	case MotionEvent.ACTION_UP:
                	{
                		((ImageView) v).setImageResource(R.drawable.session_edit);
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
                		((ImageView) v).setImageResource(R.drawable.session_delete_pressed);
                		break;
                	}
                	case MotionEvent.ACTION_UP:
                	{
                		((ImageView) v).setImageResource(R.drawable.session_delete);
                		break;
                	}
                }
                
                return false;
            }	
        });

        return view;
    }
        
    private void showSessionsFragment()
    {	
    	SessionsFragment sessionsFragment = SessionsFragment.newInstance();
    	
   	 	Bundle bundle = new Bundle();
   	 	bundle.putString("patient", new Gson().toJson(patient));  	 	
   	 	sessionsFragment.setArguments(bundle); 
    	
    	FragmentTransaction ft = getFragmentManager().beginTransaction();
		    	
		ft.remove( getFragmentManager().findFragmentByTag("SessionFragment") );
    	
		ft.replace(R.id.content, sessionsFragment, "SessionsFragment");
    	ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    	ft.addToBackStack(null);
    	ft.commit();
    }
    
    
    private void showSessionEditFragment()
    {
		// clear the back stack
		getFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
    	
		// Make new fragment to edit this session
    	SessionEditFragment sessionEditFragment = SessionEditFragment.newInstance();
		
   	 	Bundle bundle = new Bundle();
   	 	bundle.putString("session", new Gson().toJson(session));
   	 	bundle.putString("patient", new Gson().toJson(patient));
   	 	sessionEditFragment.setArguments(bundle); 
		
        // Execute a transaction, replacing any existing fragment with this inside the frame.
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		
    	// ????? ///
		PatientsFragment patients1Fragment = (PatientsFragment) getFragmentManager().findFragmentByTag("PatientsFragment");
		if( patients1Fragment != null )
		{
			ft.remove( patients1Fragment );
		}
		
    	ft.remove( getFragmentManager().findFragmentByTag("SessionFragment") );
			    	
    	ft.replace(R.id.content, sessionEditFragment, "SessionEditFragment");
    	ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
    
    private void showPatient()
    {
    	PatientFragment patientFragment = PatientFragment.newInstance();
    	
   	 	Bundle bundle = new Bundle();
   	 	bundle.putString("patient", new Gson().toJson(patient));
   	 	patientFragment.setArguments(bundle); 
    	
    	FragmentTransaction ft = getFragmentManager().beginTransaction();
    	
    	ft.remove( getFragmentManager().findFragmentByTag("SessionFragment") );
    	
    	ft.replace(R.id.content, patientFragment, "PatientFragment");
    	ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    	ft.addToBackStack(null);
        ft.commit();
    }
    
}