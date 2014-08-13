package com.panosrcng.physioassistant;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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
	
	
	public Session createSession(long patient_id, long date, String description, String treatment, String notes)
	{		
		ContentValues values = new ContentValues();
		    
		values.put(SessionsTable.COLUMN_PATIENT_ID, patient_id);
		values.put(SessionsTable.COLUMN_DATE, date);
		values.put(SessionsTable.COLUMN_DESCRIPTION, description);
		values.put(SessionsTable.COLUMN_TREATMENT, treatment);
		values.put(SessionsTable.COLUMN_NOTES, notes);
		
		long insertId = database.insert(SessionsTable.TABLE_SESSIONS, null, values);
		
		/*
		 * if session created ok
		 */
		if( insertId != -1 )
		{
			Cursor cursor = database.query(SessionsTable.TABLE_SESSIONS,
					SessionsTable.allColumns , SessionsTable.COLUMN_ID + " = " + insertId, null,
					null, null, null);
		    		
			cursor.moveToFirst();
		  	
			Session newSession = cursorToSession(cursor);
		    
			cursor.close();
				
			return newSession;
		}
			
		return null;
	}
	
	
	public Session editSession(Session session, ContentValues values)
	{
		int rowAffected = database.update(SessionsTable.TABLE_SESSIONS, values, SessionsTable.COLUMN_ID + " = " + session.getId(), null);
		
		if( rowAffected == 1 )
		{
			Cursor cursor = database.query(SessionsTable.TABLE_SESSIONS,
					SessionsTable.allColumns , SessionsTable.COLUMN_ID + " = " + session.getId(), null,
					null, null, null);
		   
			cursor.moveToFirst();
			
			Session newSession = cursorToSession(cursor);
		    
			cursor.close();
			
			return newSession;
		}
		
		return null;	
	}
	
	
	public Photo createPhoto(String filename, long patient_id, long session_id)
	{		
		ContentValues values = new ContentValues();
		    
		values.put(PhotosTable.COLUMN_FILENAME, filename);
		values.put(PhotosTable.COLUMN_PATIENT_ID, patient_id);
		values.put(PhotosTable.COLUMN_SESSION_ID, session_id);
		
		long insertId = database.insert(PhotosTable.TABLE_PHOTOS, null, values);
		
		/*
		 * if photo entry created ok
		 */
		if( insertId != -1 )
		{
			Cursor cursor = database.query(PhotosTable.TABLE_PHOTOS,
					PhotosTable.allColumns , PhotosTable.COLUMN_ID + " = " + insertId, null,
					null, null, null);
		    		
			cursor.moveToFirst();
		  	
			Photo newPhoto = cursorToPhoto(cursor);
		    
			cursor.close();
				
			return newPhoto;
		}
			
		return null;
	}	
	
	public Long checkIfPatientExists(String firstname, String lastname)
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

	public Long checkIfSessionExists(String description, String treatment)
	{	
		Long id = (long) -1;
		
		Cursor cursor = database.query(SessionsTable.TABLE_SESSIONS,
				SessionsTable.allColumns , (SessionsTable.COLUMN_DESCRIPTION + " = '" + description + "'") +
				" and " + (SessionsTable.COLUMN_TREATMENT + " = '" + treatment + "'"),
				null, null, null, null);
		
		if( cursor.getCount() == 1 )
		{
			cursor.moveToFirst();
			
			Session session = cursorToSession(cursor);
		
			id = session.getId();
		}
		
		cursor.close();
		
		return id;
	}
	
	private Patient cursorToPatient(Cursor cursor)
	{
		Patient patient = new Patient();
		
		patient.setId(cursor.getLong(0));
		patient.setFirstname(cursor.getString(1));
		patient.setLastname(cursor.getString(2));
		patient.setPhone(cursor.getString(3));
		patient.setAddress(cursor.getString(4));
		patient.setNotes(cursor.getString(5));
		
		return patient;
	}

	
	private Session cursorToSession(Cursor cursor)
	{
		Session session = new Session();
		
		session.setId(cursor.getLong(0));
		session.setPatientId(cursor.getLong(1));
		session.setDateTime(cursor.getLong(2));
		session.setDescription(cursor.getString(3));
		session.setTreatment(cursor.getString(4));
		session.setNotes(cursor.getString(5));
		
		return session;
	}
	
	private Photo cursorToPhoto(Cursor cursor)
	{
		Photo photo = new Photo();
		
		photo.setId(cursor.getLong(0));
		photo.setFilename(cursor.getString(1));
		photo.setPatientId(cursor.getLong(2));
		photo.setSessionId(cursor.getLong(3));
		
		return photo;
	}
	
	public void deletePatient(Patient patient)
	{    
		long id = patient.getId();    
		
		// delete all sessions for this patient first
		database.delete(SessionsTable.TABLE_SESSIONS, SessionsTable.COLUMN_PATIENT_ID + " = " + id, null);
		
		// delete all photo notes (files and entries) for this patient first
		List<Photo> photos = getAllPhotos(patient);
		
		if(photos.size() > 0)
		{
			Utils utils = new Utils();
			
			for(int i=0; i<photos.size(); i++)
			{
				utils.deleteFile( utils.getPhotosDirectoryPath() + "/" + photos.get(i).getFilename() );
			}
			
			// delete all photo notes entries
			database.delete(PhotosTable.TABLE_PHOTOS, PhotosTable.COLUMN_PATIENT_ID + " = " + id, null);
		}
		
		// delete this patient
		database.delete(PatientsTable.TABLE_PATIENTS, PatientsTable.COLUMN_ID + " = " + id, null);
	}
	
	public void deleteSession(Session session)
	{    
		long id = session.getId();    
		
		// delete all photo notes (files and entries) for this session first
		List<Photo> photos = getAllPhotos(session);
		
		if(photos.size() > 0)
		{
			Utils utils = new Utils();
			
			for(int i=0; i<photos.size(); i++)
			{
				utils.deleteFile( utils.getPhotosDirectoryPath() + "/" + photos.get(i).getFilename() );
			}
			
			// delete all photo notes entries
			database.delete(PhotosTable.TABLE_PHOTOS, PhotosTable.COLUMN_SESSION_ID + " = " + id, null);
		}
		
		// delete session
		database.delete(SessionsTable.TABLE_SESSIONS, SessionsTable.COLUMN_ID + " = " + id, null);
	}
	
	public void deletePhoto(Photo photo)
	{    
		long id = photo.getId();    
		
		database.delete(PhotosTable.TABLE_PHOTOS, PhotosTable.COLUMN_ID + " = " + id, null);
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
	
	public List<Session> getAllsessions(Patient patient)
	{
		List<Session> sessions = new ArrayList<Session>();

		Cursor cursor = database.query(SessionsTable.TABLE_SESSIONS, 
										SessionsTable.allColumns, 
										SessionsTable.COLUMN_PATIENT_ID + " = " + patient.getId(), 
										null, null, null, SessionsTable.COLUMN_DATE + " DESC");
		    
		cursor.moveToFirst();
		 
		while (!cursor.isAfterLast())
		{
			Session session = cursorToSession(cursor);
		    
			sessions.add(session);
		    			
			cursor.moveToNext();
		}
		
		// close the cursor
		cursor.close();
		
		return sessions;
	}
	
	/*
	 *  get all photo entries for a patient 
	 */
	public List<Photo> getAllPhotos(Patient patient)
	{
		List<Photo> photos = new ArrayList<Photo>();

		Cursor cursor = database.query(PhotosTable.TABLE_PHOTOS, 
										PhotosTable.allColumns, 
										PhotosTable.COLUMN_PATIENT_ID + " = " + patient.getId(), 
										null, null, null, null);
		    
		cursor.moveToFirst();
		 
		while (!cursor.isAfterLast())
		{
			Photo photo = cursorToPhoto(cursor);
		    
			photos.add(photo);
		    			
			cursor.moveToNext();
		}
		
		// close the cursor
		cursor.close();
		
		return photos;
	}
	
	/*
	 * get all photo entries for a session
	 */
	public List<Photo> getAllPhotos(Session session)
	{
		List<Photo> photos = new ArrayList<Photo>();

		Cursor cursor = database.query(PhotosTable.TABLE_PHOTOS, 
										PhotosTable.allColumns, 
										PhotosTable.COLUMN_SESSION_ID + " = " + session.getId(), 
										null, null, null, null);
		    
		cursor.moveToFirst();
		 
		while (!cursor.isAfterLast())
		{
			Photo photo = cursorToPhoto(cursor);
		    
			photos.add(photo);
		    			
			cursor.moveToNext();
		}
		
		// close the cursor
		cursor.close();
		
		return photos;
	}
}
