package com.panosrcng.physioassistant;

import com.panosrcng.physioassistant.R;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
/**
 * This activity displays the details using a DetailsFragment. This activity is started
 * by a TitlesFragment when a title in the list is selected.
 * The activity is used only if a DetailsFragment is not on the screen.
 */

public class ContentActivity extends FragmentActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.only_content_layout);
        
        if( getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE )
        {
            // If the screen is now in landscape mode, we can show the
            // dialog in-line with the list so we don't need this activity.
            finish();
            return;
        }
        
        if (savedInstanceState == null)
        {	
        	int index = getIntent().getExtras().getInt("index");
        	
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
    }
    
    private void showPatientsFragment()
    {
        PatientsFragment patientsFragment = (PatientsFragment) getSupportFragmentManager().findFragmentById(R.id.content);
        patientsFragment = PatientsFragment.newInstance();
    	FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    	ft.replace(android.R.id.content, patientsFragment, "PatientsFragment");
    	ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
    
    private void showPatientEditFragment()
    {
        PatientEditFragment patientEditFragment = (PatientEditFragment) getSupportFragmentManager().findFragmentById(R.id.content);
        patientEditFragment = PatientEditFragment.newInstance();
    	FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    	ft.replace(android.R.id.content, patientEditFragment, "PatientEditFragment");
    	ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
    
    private void showGridFragment()
    {
        GridFragment gridFragment = (GridFragment) getSupportFragmentManager().findFragmentById(R.id.content);
        gridFragment = GridFragment.newInstance();
    	FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    	ft.replace(android.R.id.content, gridFragment, "GridFragment");
    	ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
    
    
    private void showAboutFragment()
    {
        AboutFragment aboutFragment = (AboutFragment) getSupportFragmentManager().findFragmentById(R.id.content);
        aboutFragment = AboutFragment.newInstance();
    	FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    	ft.replace(android.R.id.content, aboutFragment, "AboutFragment");
    	ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }
    
    
}



