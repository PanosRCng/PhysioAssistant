package com.panosrcng.physioassistant;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.panosrcng.physioassistant.R;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;


public class SessionFragment extends Fragment
{	
	private Session session;
	private Patient patient;
	private View view;
	private String photoFilename;
	private DatabaseDAO databaseDAO;
	private Utils utils;
	
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
        
        view = inflater.inflate(R.layout.fragment_session_layout, container, false);
        
        Bundle bundle = getArguments();
        
        if( bundle  != null && bundle.containsKey("session") )
        {    
        	session = new Gson().fromJson(bundle.getString("session"), Session.class);
        }
        
        if( bundle  != null && bundle.containsKey("patient") )
        {    
        	patient = new Gson().fromJson(bundle.getString("patient"), Patient.class);
        }
        
        databaseDAO = new DatabaseDAO(view.getContext());
        databaseDAO.open();
        
        utils = new Utils();
        
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

        TextView photoNotesLabelTextView = (TextView) view.findViewById(R.id.sessionPhotoNotesLabelTextView);
        photoNotesLabelTextView.setText("Photo notes:");

        // populate photo notes
        populatePhotoNotes();
        
        ImageView photoButton = (ImageView) view.findViewById(R.id.photoButton);
        
        photoButton.setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v)
			{	
				takePicture();		
			}
		} );
        
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
				databaseDAO.deleteSession(session);
				
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
                
        
        photoButton.setOnTouchListener( new OnTouchListener(){
            
        	@Override
            public boolean onTouch(View v, MotionEvent me)
        	{
                switch (me.getAction())
                {
                	case MotionEvent.ACTION_DOWN: 
                	{
                		((ImageView) v).setImageResource(R.drawable.photo_button_pressed);
                		break;
                	}
                	case MotionEvent.ACTION_UP:
                	{
                		((ImageView) v).setImageResource(R.drawable.photo_button);
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
    
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        databaseDAO.open();
        
        if (requestCode == 0 && resultCode == -1)
        {            
			databaseDAO.createPhoto(photoFilename, patient.getId(), session.getId());

			
			
			populatePhotoNotes();
        }
    }
    
    
    private void takePicture()
    {
    	Utils utils = new Utils();
        
    	photoFilename = Long.toString( System.currentTimeMillis() ) + ".jpg";
    	
        String file = utils.getPhotosDirectoryPath() + "/" + photoFilename;
        File newfile = new File(file);
        
        try
        {
            newfile.createNewFile();
        }
        catch(IOException e)
        {
        	//
        }       

        Uri outputFileUri = Uri.fromFile(newfile);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        startActivityForResult(cameraIntent, 0);
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
    
    /*
     * populate photo notes
     */
    public void populatePhotoNotes()
    {
    	final List<Photo> photos = databaseDAO.getAllPhotos(session);
        
        LinearLayout photosView = (LinearLayout) view.findViewById(R.id.photosSessionView);
        
        photosView.removeAllViews();
        
        if( photos.size() > 0 )
        {
        	for(int i=0; i<photos.size(); i++)
        	{
        		Photo myPhoto = photos.get(i);
        	
        		ImageView myPhotoView = new ImageView(getActivity());
        	
        		myPhotoView.setLayoutParams(new LayoutParams( LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        		myPhotoView.setPadding(0, 0, 10, 0);
        		myPhotoView.setClickable(true);
  
        		Bitmap btm = BitmapFactory.decodeFile( utils.getPhotosDirectoryPath() + "/" + myPhoto.getFilename() );
        	    Bitmap resizedbitmap = Bitmap.createScaledBitmap(btm, 70, 70, true);
        		myPhotoView.setImageBitmap( resizedbitmap );
        		myPhotoView.setId(i);
        		
        		myPhotoView.setOnClickListener( new View.OnClickListener() {
    			
        			@Override
        			public void onClick(View v)
        			{	
        				Photo selectedPhoto = photos.get(v.getId());
        				
        				Intent intent = new Intent();
        				intent.setAction(android.content.Intent.ACTION_VIEW);
        				intent.setDataAndType(Uri.fromFile(new File(utils.getPhotosDirectoryPath() + "/" + selectedPhoto.getFilename())), "image/jpg");
        				startActivity(intent);
        			}
        		} );
        		
        		photosView.addView(myPhotoView);
        	}
        }
        else
        {
        	// (!) pending delete
        	TextView noPhotosText = new TextView(getActivity());
    		noPhotosText.setLayoutParams(new LayoutParams( LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        	noPhotosText.setPadding(0, 10, 0, 10);
        	noPhotosText.setText(" ");
        	
        	photosView.addView(noPhotosText);
        }
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