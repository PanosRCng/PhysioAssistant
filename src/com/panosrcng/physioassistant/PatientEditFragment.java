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


public class PatientEditFragment extends Fragment
{
	private DatabaseDAO databaseDAO;
	private Patient patient;
	
    /**
     * Create a new instance of PatientEditFragment
 	*/
    public static PatientEditFragment newInstance()
    {
    	PatientEditFragment f = new PatientEditFragment();

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
        
        View view = inflater.inflate(R.layout.fragment_patient_edit_layout, container, false);

        Bundle bundle = getArguments();
        if( bundle  != null && bundle.containsKey("patient") )
        {    
        	patient = new Gson().fromJson(bundle.getString("patient"), Patient.class);
        }
        
        
        TextView titleTextView = (TextView) view.findViewById(R.id.patientTitleTextView);
        
        
        databaseDAO = new DatabaseDAO(view.getContext());
        databaseDAO.open();
        
        
        TextView firstnameLabelTextView = (TextView) view.findViewById(R.id.patientFirstnameLabelTextView);
        firstnameLabelTextView.setText("Firstname:");
        final EditText firstnameEditText = (EditText) view.findViewById(R.id.patientFirstnameEditText);
        
        TextView lastnameLabelTextView = (TextView) view.findViewById(R.id.patientLastnameLabelTextView);
        lastnameLabelTextView.setText("Lastname:");
        final EditText lastnameEditText = (EditText) view.findViewById(R.id.patientLastnameEditText);
        
        TextView phoneLabelTextView = (TextView) view.findViewById(R.id.patientPhoneLabelTextView);
        phoneLabelTextView.setText("Phone:");
        final EditText phoneEditText = (EditText) view.findViewById(R.id.patientPhoneEditText);

        TextView addressLabelTextView = (TextView) view.findViewById(R.id.patientAddressLabelTextView);
        addressLabelTextView.setText("Address:");
        final EditText addressEditText = (EditText) view.findViewById(R.id.patientAddressEditText);
        
        TextView notesLabelTextView = (TextView) view.findViewById(R.id.patientNotesLabelTextView);
        notesLabelTextView.setText("Notes:");
        final EditText notesEditText = (EditText) view.findViewById(R.id.patientNotesEditText);
        
        ImageView saveButton = (ImageView) view.findViewById(R.id.saveButton);
        
        /*
         *  if patient != null -> edit mode, else add mode
         */
        if(patient != null)
        {
        	titleTextView.setText("Edit Patient");
        	firstnameEditText.setText(patient.getFirstname());
        	lastnameEditText.setText(patient.getLastname());
        	phoneEditText.setText(patient.getPhone());
        	addressEditText.setText(patient.getAddress());
        	notesEditText.setText(patient.getNotes());
        }
        else
        {
        	titleTextView.setText("Add Patient");
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
				String firstname = firstnameEditText.getText().toString();
				String lastname = lastnameEditText.getText().toString();
				String phone = phoneEditText.getText().toString();
				String address = addressEditText.getText().toString();
				String notes = notesEditText.getText().toString();
				
				if( checkFields(firstnameEditText, lastnameEditText) )
				{
					Patient newPatient = null;
					
					//
					 // edit patient mode, or add patient mode
					 //
					if(patient != null)
					{	
						//
						 // check first if there is not a patient with this firstname and lastname 
						 // or is the patient we want to edit
						 //
						Long checkId = databaseDAO.checkIfExists(firstname, lastname);
						
						if( (checkId == -1) || (checkId == patient.getId()) )
						{
							ContentValues values = new ContentValues();
						
							values.put(PatientsTable.COLUMN_FIRSTNAME, firstname);
							values.put(PatientsTable.COLUMN_LASTNAME, lastname);
							values.put(PatientsTable.COLUMN_PHONE, phone);
							values.put(PatientsTable.COLUMN_ADDRESS, address);
							values.put(PatientsTable.COLUMN_NOTES, notes);
					
							newPatient = databaseDAO.editPatient(patient, values);
						}
						else
						{
							Toast.makeText(v.getContext(), "this patient already exists", Toast.LENGTH_LONG).show();
						}
					}
					else
					{
						//
						 // check first if there is not a patient with this firstname and lastname
						 //
						if( (databaseDAO.checkIfExists(firstname, lastname)) == -1  )
						{
							newPatient = databaseDAO.createPatient(firstname,
									lastname,
									phone,
									address,
									notes
									);
						}
						else
						{
							Toast.makeText(v.getContext(), "this patient already exists", Toast.LENGTH_LONG).show();
						}
					}
				
					if(newPatient != null)
					{
						showPatient(newPatient);
					}	
				}
			}
		} );
        
        
        firstnameEditText.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
		    public void onFocusChange(View v, boolean hasFocus)
			{
				if (hasFocus)
		        {
					if( firstnameEditText.getText().toString().equals("(!) firstname can not be empty") )
					{
						firstnameEditText.setTextColor(getActivity().getResources().getColor(R.color.black));
						firstnameEditText.setText("");
					}
		        }
			}
        } );
        
        lastnameEditText.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
		    public void onFocusChange(View v, boolean hasFocus)
			{
				if (hasFocus)
		        {
					if( lastnameEditText.getText().toString().equals("(!) lastname can not be empty") )
					{
						lastnameEditText.setTextColor(getActivity().getResources().getColor(R.color.black));
						lastnameEditText.setText("");
					}
		        }
			}
        } );
        
        return view;
    }
    
    
    private void showPatient(Patient patient)
    {
		// clear the back stack
		getFragmentManager().popBackStack(null, getFragmentManager().POP_BACK_STACK_INCLUSIVE);
    	
    	PatientFragment patientFragment = PatientFragment.newInstance();
    	
   	 	Bundle bundle = new Bundle();
   	 	bundle.putString("patient", new Gson().toJson(patient));
   	 	patientFragment.setArguments(bundle); 
    	
    	FragmentTransaction ft = getFragmentManager().beginTransaction();

    	ft.remove( getFragmentManager().findFragmentByTag("PatientEditFragment") );
    	
    	ft.replace(R.id.content, patientFragment, "PatientFragment");
    	ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
    
    private boolean checkFields(EditText firstnameEditText, EditText lastnameEditText)
    {	
    	boolean firstname = true;
    	boolean lastname = true;
    	
    	String firstnameText = firstnameEditText.getText().toString();
    	String lastnameText = lastnameEditText.getText().toString();
    	
    	if( (firstnameText.length() == 0) || (firstnameText.equals("(!) firstname can not be empty")) )
    	{
    		firstname = false;
    		firstnameEditText.setText("(!) firstname can not be empty");
			firstnameEditText.setTextColor(getActivity().getResources().getColor(R.color.red));
    	}
    	
    	if( (lastnameText.length() == 0) || (lastnameText.equals("(!) lastname can not be empty")) )
    	{
    		lastname = false;
    		lastnameEditText.setText("(!) lastname can not be empty");
			lastnameEditText.setTextColor(getActivity().getResources().getColor(R.color.red));
    	}
    	
    	if( firstname && lastname )
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