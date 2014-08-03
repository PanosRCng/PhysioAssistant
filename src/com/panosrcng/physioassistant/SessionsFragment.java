package com.panosrcng.physioassistant;

import java.util.List;

import com.google.gson.Gson;
import com.panosrcng.physioassistant.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SessionsFragment extends ListFragment
{	
	Patient patient;
	
    /*
     * Create a new instance of SessionsFragment
     */
    public static SessionsFragment newInstance()
    {
    	SessionsFragment f = new SessionsFragment();

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
        
        View view = inflater.inflate(R.layout.fragment_sessions_layout, container, false);
        
        Bundle bundle = getArguments();
        
        if( bundle  != null && bundle.containsKey("patient") )
        {    
        	patient = new Gson().fromJson(bundle.getString("patient"), Patient.class);
        }
        
        TextView textView = (TextView) view.findViewById(R.id.sessionsViewText);
        textView.setText(patient.getLastname() + "  " + patient.getFirstname());
        
        ListView listView = (ListView) view.findViewById(android.R.id.list);
      
        DatabaseDAO databaseDAO = new DatabaseDAO(view.getContext());
        databaseDAO.open();

        List<Session> sessionsList = databaseDAO.getAllsessions(patient);
        
        databaseDAO.close();
        
        listView.setAdapter (new SessionsAdapter (view.getContext(), R.layout.sessions_list_item, 
                R.id.nameView, sessionsList));

        return view;
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {	
    	Session session = (Session) l.getItemAtPosition(position);
    	
        showSession( session );
    }
    
    
    private void showSession(Session session)
    {
    	SessionFragment sessionFragment = SessionFragment.newInstance();
    	
   	 	Bundle bundle = new Bundle();
   	 	bundle.putString("session", new Gson().toJson(session));
   	 	bundle.putString("patient", new Gson().toJson(patient));
   	 	sessionFragment.setArguments(bundle); 
    	
    	FragmentTransaction ft = getFragmentManager().beginTransaction();
    	
    	ft.remove( getFragmentManager().findFragmentByTag("SessionsFragment") );
    	
    	ft.replace(R.id.content, sessionFragment, "SessionFragment");
    	ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    	ft.addToBackStack(null);
        ft.commit();
    }
    
    
    // customAdapter

    private class SessionsAdapter extends ArrayAdapter<Session>
    {
    	private Context mContext;
    	private int mLayoutId;

    
    	//constructor

    	public SessionsAdapter(Context context, int layoutId, int textViewResourceId, List<Session> items) 
    	{
    		super(context, textViewResourceId, items);
    		mContext = context;
    		mLayoutId = layoutId;
    	}


    	// returns a view that displays an item in the array.

    	public View getView (int position, View convertView, ViewGroup parent) 
    	{
    		View v = convertView;
	
    		if (v == null)
    		{
    			LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    			v = vi.inflate (mLayoutId, null);
    		}

    		View itemView = v;

    		TextView descriptionView = (TextView) itemView.findViewById(R.id.descriptionView);
    		TextView treatmentView = (TextView) itemView.findViewById (R.id.dateView);
    		ImageView imageView = (ImageView) itemView.findViewById(R.id.sessionStatus);
    		
    		Session session = getItem (position);
    		
    		descriptionView.setText(session.getDescription());
    		treatmentView.setText(session.getDateStr());
    		
    		return itemView;
    	}

    }
     
}