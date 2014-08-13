package com.panosrcng.physioassistant;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


/*
 * Photos Database Table 
 */
public class PhotosTable
{
	/*
	 *  Photos table structure
	 */
	public static final String TABLE_PHOTOS = "photos";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_FILENAME = "filename"; 
	public static final String COLUMN_PATIENT_ID = "patient_id";
	public static final String COLUMN_SESSION_ID = "session_id";

	public static final String[] allColumns = { COLUMN_ID, COLUMN_FILENAME, COLUMN_PATIENT_ID, COLUMN_SESSION_ID };
	
	/*
	 *  Database creation SQL statement
	 */
	private static final String CREATE_PHOTOS_TABLE = "create table " 
			+ TABLE_PHOTOS
			+ "(" 
			+ COLUMN_ID + " integer primary key autoincrement, "
			+ COLUMN_FILENAME + " text not null,"
			+ COLUMN_PATIENT_ID + " integer not null,"
			+ COLUMN_SESSION_ID + " integer not null"
			+ ");";

	public static void onCreate(SQLiteDatabase database)
	{
		database.execSQL(CREATE_PHOTOS_TABLE);
	}

	/*
	 *  Database upgrade SQL statement
	 */
	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
	{
		Log.w(PatientsTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
	    
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_PHOTOS);
		onCreate(database);
	}

} 