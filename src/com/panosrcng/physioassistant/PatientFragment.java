package com.panosrcng.physioassistant;

import com.panosrcng.physioassistant.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class PatientFragment extends Fragment
{
    /**
     * Create a new instance of DetailsFragment, initialized to
     * show the text at 'index'.
     */
    public static PatientFragment newInstance()
    {
    	PatientFragment f = new PatientFragment();

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
        
        View view = inflater.inflate(R.layout.fragment_patient_layout, container, false);

        TextView titleTextView = (TextView) view.findViewById(R.id.patientTitleTextView);
        titleTextView.setText("Patient Title");
        
        TextView lastnameLabelTextView = (TextView) view.findViewById(R.id.patientLastnameLabelTextView);
        lastnameLabelTextView.setText("Lastname:");
        TextView lastnameTextView = (TextView) view.findViewById(R.id.patientLastnameTextView);
        lastnameTextView.setText("Lastname 1");
        
        TextView firstnameLabelTextView = (TextView) view.findViewById(R.id.patientFirstnameLabelTextView);
        firstnameLabelTextView.setText("Firstname:");
        TextView firstnameTextView = (TextView) view.findViewById(R.id.patientFirstnameTextView);
        firstnameTextView.setText("Firstname 1");
        
        TextView phoneLabelTextView = (TextView) view.findViewById(R.id.patientPhoneLabelTextView);
        phoneLabelTextView.setText("Phone:");
        TextView phoneTextView = (TextView) view.findViewById(R.id.patientPhoneTextView);
        phoneTextView.setText("Phone 1");
        
        return view;
    }
}
