package com.panosrcng.physioassistant;

import com.panosrcng.physioassistant.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class AboutFragment extends Fragment
{
    /**
     * Create a new instance of DetailsFragment, initialized to
     * show the text at 'index'.
     */
    public static AboutFragment newInstance()
    {
    	AboutFragment f = new AboutFragment();

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
        
        View view = inflater.inflate(R.layout.fragment_about_layout, container, false);
        
        TextView textView = (TextView) view.findViewById(R.id.aboutViewText);
        textView.setText("About");

        return view;
    }
}