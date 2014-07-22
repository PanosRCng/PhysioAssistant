package com.panosrcng.physioassistant;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class DatabaseDAO
{
	private SQLiteDatabase database;
	private DatabaseHelper dbHelper;

	
	public DatabaseDAO(Context context)
	{
		dbHelper = new DatabaseHelper(context);
	}

	
	public void open() throws SQLException
	{
		database = dbHelper.getWritableDatabase();
	}

	
	public void close()
	{
		dbHelper.close();
	}
	
	
	public Patient createPatient(String firstname, String lastname, String phone, String address, String notes)
	{		
		ContentValues values = new ContentValues();
		    
		values.put(PatientsTable.COLUMN_FIRSTNAME, firstname);
		values.put(PatientsTable.COLUMN_LASTNAME, lastname);
		values.put(PatientsTable.COLUMN_PHONE, phone);
		values.put(PatientsTable.COLUMN_ADDRESS, address);
		values.put(PatientsTable.COLUMN_NOTES, notes);
		
		long insertId = database.insert(PatientsTable.TABLE_PATIENTS, null, values);
		
		/*
		 * if patient created ok
		 */
		if( insertId != -1 )
		{
			Cursor cursor = database.query(PatientsTable.TABLE_PATIENTS,
					PatientsTable.allColumns , PatientsTable.COLUMN_ID + " = " + insertId, null,
					null, null, null);
		    		
			cursor.moveToFirst();
		  	
			Patient newPatient = cursorToPatient(cursor);
		    
			cursor.close();
				
			return newPatient;
		}
			
		return null;
	}
	
	
	public Patient editPatient(Patient patient, ContentValues values)
	{
		int rowAffected = database.update(PatientsTable.TABLE_PATIENTS, values, PatientsTable.COLUMN_ID + " = " + patient.getId(), null);
		
		if( rowAffected == 1 )
		{
			Cursor cursor = database.query(PatientsTable.TABLE_PATIENTS,
					PatientsTable.allColumns , PatientsTable.COLUMN_ID + " = " + patient.getId(), null,
					null, null, null);
		   
			cursor.moveToFirst();
			
			Patient newPatient = cursorToPatient(cursor);
		    
			cursor.close();
			
			return newPatient;
		}
		
		return null;	
	}
	
	
	public Long checkIfExists(String firstname, String lastname)
	{	
		Long id = (long) -1;
		
		Cursor cursor = database.query(PatientsTable.TABLE_PATIENTS,
				PatientsTable.allColumns , (PatientsTable.COLUMN_FIRSTNAME + " = '" + firstname + "'") +
				" and " + (PatientsTable.COLUMN_LASTNAME + " = '" + lastname + "'"),
				null, null, null, null);
		
		if( cursor.getCount() == 1 )
		{
			cursor.moveToFirst();
			
			Patient patient = cursorToPatient(cursor);
		
			id = patient.getId();
		}
		
		cursor.close();
		
		return id;
	}
	
	private Patient cursorToPatient(Cursor cursor)
	{
		Patient patient= new Patient();
		
		patient.setId(cursor.getLong(0));
		patient.setFirstname(cursor.getString(1));
		patient.setLastname(cursor.getString(2));
		patient.setPhone(cursor.getString(3));
		patient.setAddress(cursor.getString(4));
		patient.setNotes(cursor.getString(5));
		
		return patient;
	}
	
	
	public void deletePatient(Patient patient)
	{    
		long id = patient.getId();    
		
		database.delete(PatientsTable.TABLE_PATIENTS, PatientsTable.COLUMN_ID + " = " + id, null);
	}
	
	  
	public List<Patient> getAllpatients()
	{
		List<Patient> patients = new ArrayList<Patient>();

		Cursor cursor = database.query(PatientsTable.TABLE_PATIENTS, 
										PatientsTable.allColumns, null, null, null, null, null);
		    
		cursor.moveToFirst();
		 
		while (!cursor.isAfterLast())
		{
			Patient patient = cursorToPatient(cursor);
		    
			patients.add(patient);
		    			
			cursor.moveToNext();
		}
		
		// close the cursor
		cursor.close();
		
		return patients;
	}
	
}
