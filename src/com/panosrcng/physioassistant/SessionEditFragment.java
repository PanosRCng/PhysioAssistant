package com.panosrcng.physioassistant;

import com.google.gson.Gson;
import com.panosrcng.physioassistant.R;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class SessionEditFragment extends Fragment
{
	private DatabaseDAO databaseDAO;
	private Patient patient;
	private Session session;
	
    /**
     * Create a new instance of SessionEditFragment
 	*/
    public static SessionEditFragment newInstance()
    {
    	SessionEditFragment f = new SessionEditFragment();

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
        
        View view = inflater.inflate(R.layout.fragment_session_edit_layout, container, false);

        Bundle bundle = getArguments();
        
        if( bundle  != null && bundle.containsKey("patient") )
        {    
        	patient = new Gson().fromJson(bundle.getString("patient"), Patient.class);
        }

        if( bundle  != null && bundle.containsKey("session") )
        {    
        	session = new Gson().fromJson(bundle.getString("session"), Session.class);
        }
        
        TextView titleTextView = (TextView) view.findViewById(R.id.sessionTitleTextView);
        
        databaseDAO = new DatabaseDAO(view.getContext());
        databaseDAO.open();
        
        
        TextView descriptionLabelTextView = (TextView) view.findViewById(R.id.sessionDescriptionLabelTextView);
        descriptionLabelTextView.setText("Description:");
        final EditText descriptionEditText = (EditText) view.findViewById(R.id.sessionDescriptionEditText);
        
        TextView treatmentLabelTextView = (TextView) view.findViewById(R.id.sessionTreatmentLabelTextView);
        treatmentLabelTextView.setText("Treatment:");
        final EditText treatmentEditText = (EditText) view.findViewById(R.id.sessionTreatmentEditText);

        TextView notesLabelTextView = (TextView) view.findViewById(R.id.sessionNotesLabelTextView);
        notesLabelTextView.setText("Notes:");
        final EditText notesEditText = (EditText) view.findViewById(R.id.sessionNotesEditText);
        
        ImageView saveButton = (ImageView) view.findViewById(R.id.saveButton);
        
        /*
         *  if session != null -> edit mode, else add mode
         */
        if(session != null)
        {
        	titleTextView.setText("Edit Session");
        	descriptionEditText.setText(session.getDescription());
        	treatmentEditText.setText(session.getTreatment());
        	notesEditText.setText(session.getNotes());
        }
        else
        {
        	titleTextView.setText("Add Session");
        }
        
        saveButton.setOnTouchListener( new OnTouchListener(){
            
        	@Override
            public boolean onTouch(View v, MotionEvent me)
        	{
                switch (me.getAction())
                {
                	case MotionEvent.ACTION_DOWN: 
                	{
                		((ImageView) v).setImageResource(R.drawable.save_button_pressed);
                		break;
                	}
                	case MotionEvent.ACTION_UP:
                	{
                		((ImageView) v).setImageResource(R.drawable.save_button);
                		break;
                	}
                }
                
                return false;
            }	
        });
      
        
        saveButton.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{	
				Long patient_id = patient.getId();
				String description = descriptionEditText.getText().toString();
				String treatment = treatmentEditText.getText().toString();
				String notes = notesEditText.getText().toString();
				
				if( checkFields(descriptionEditText, treatmentEditText) )
				{
					Session newSession = null;
					
					//
					 // edit session mode, or add session mode
					 //
					if(session != null)
					{	
						ContentValues values = new ContentValues();
						
						values.put(SessionsTable.COLUMN_PATIENT_ID, patient_id);
						values.put(SessionsTable.COLUMN_DATE, session.getDateTime());
						values.put(SessionsTable.COLUMN_DESCRIPTION, description);
						values.put(SessionsTable.COLUMN_TREATMENT, treatment);
						values.put(SessionsTable.COLUMN_NOTES, notes);
					
						newSession = databaseDAO.editSession(session, values);
					}
					else
					{
						 // create a new session

						newSession = databaseDAO.createSession(patient_id,
																System.currentTimeMillis(),
																description,
																treatment,
																notes
									);
					}
				
					if(newSession != null)
					{
						showSession(newSession);
					}	
				}
			}
		} );
        
        
        descriptionEditText.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
		    public void onFocusChange(View v, boolean hasFocus)
			{
				if (hasFocus)
		        {
					if( descriptionEditText.getText().toString().equals("(!) description can not be empty") )
					{
						descriptionEditText.setTextColor(getActivity().getResources().getColor(R.color.black));
						descriptionEditText.setText("");
					}
		        }
			}
        } );
        
        treatmentEditText.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
		    public void onFocusChange(View v, boolean hasFocus)
			{
				if (hasFocus)
		        {
					if( treatmentEditText.getText().toString().equals("(!) treatment can not be empty") )
					{
						treatmentEditText.setTextColor(getActivity().getResources().getColor(R.color.black));
						treatmentEditText.setText("");
					}
		        }
			}
        } );
        
        return view;
    }
    
    
    private void showSession(Session session)
    {
		// clear the back stack
		getFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
    	
    	SessionFragment sessionFragment = SessionFragment.newInstance();
    	
   	 	Bundle bundle = new Bundle();
   	 	bundle.putString("session", new Gson().toJson(session));
   	 	bundle.putString("patient", new Gson().toJson(patient));
   	 	sessionFragment.setArguments(bundle); 
   	 	
    	FragmentTransaction ft = getFragmentManager().beginTransaction();

    	// ????? ///
		PatientsFragment patients1Fragment = (PatientsFragment) getFragmentManager().findFragmentByTag("PatientsFragment");
		if( patients1Fragment != null )
		{
			ft.remove( patients1Fragment );
		}
    	
    	ft.remove( getFragmentManager().findFragmentByTag("SessionEditFragment") );
    	
    	ft.replace(R.id.content, sessionFragment, "SessionFragment");
    	ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
    
    
    private boolean checkFields(EditText descriptionEditText, EditText treatmentEditText)
    {	
    	boolean description = true;
    	boolean treatment = true;
    	
    	String descriptionText = descriptionEditText.getText().toString();
    	String treatmentText = treatmentEditText.getText().toString();
    	
    	if( (descriptionText.length() == 0) || (descriptionText.equals("(!) description can not be empty")) )
    	{
    		description = false;
    		descriptionEditText.setText("(!) description can not be empty");
			descriptionEditText.setTextColor(getActivity().getResources().getColor(R.color.red));
    	}
    	
    	if( (treatmentText.length() == 0) || (treatmentText.equals("(!) treatment can not be empty")) )
    	{
    		treatment = false;
    		treatmentEditText.setText("(!) treatment can not be empty");
			treatmentEditText.setTextColor(getActivity().getResources().getColor(R.color.red));
    	}
    	
    	if( description && treatment )
    	{
    		return true;
    	}
    	
    	return false;
    }
    
    
    @Override
    public void onResume()
    {
      databaseDAO.open();
      super.onResume();
    }

    @Override
	public void onPause()
    {
      databaseDAO.close();
      super.onPause();
    }
}