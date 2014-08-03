package com.panosrcng.physioassistant;

import com.panosrcng.physioassistant.R;

/**
 * static data
 */

public final class Settings
{
	
    public static final String[] TITLES = {
    	"patients",   
        "add patient",
        "search patient",       
        "settings",
        "about"
    };
    
    public static final Integer[] mThumbIds = {
    	R.drawable.list_person,
    	R.drawable.add_person,
        R.drawable.search_person,
        R.drawable.settings,
        R.drawable.about,
    };
    
    public static Integer[] patientMenuButtons = { 
    	R.drawable.add_session, 
		R.drawable.scedule_session,
		R.drawable.list_session
    };
    
    public static String[] patientMenuTitles = {
    	"add session",
    	"schedule session",
    	"session list"
    };

}
