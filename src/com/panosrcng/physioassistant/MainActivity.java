package com.panosrcng.physioassistant;

import com.panosrcng.physioassistant.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * This is main activity for the app. Depending on the size of the screen and the
 * orientation of the device, it displays a titles panel and, if the screen
 * width allows, a details panel where text for the selected title is displayed.
 */

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.fragment_layout);
    }

}
