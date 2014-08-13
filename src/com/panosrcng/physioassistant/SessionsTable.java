package com.panosrcng.physioassistant;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


/*
 * Sessions Database Table 
 */
public class SessionsTable
{
	/*
	 *  Sessions table structure
	 */
	public static final String TABLE_SESSIONS = "sessions";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_PATIENT_ID = "patient_id";
	public static final String COLUMN_DATE = "date";
	public static final String COLUMN_DESCRIPTION = "description";
	public static final String COLUMN_TREATMENT = "treatment";
	public static final String COLUMN_NOTES = "notes";

	public static final String[] allColumns = { COLUMN_ID, COLUMN_PATIENT_ID, COLUMN_DATE, COLUMN_DESCRIPTION, 
												COLUMN_TREATMENT, COLUMN_NOTES };
	
	/*
	 *  Database creation SQL statement
	 */
	private static final String CREATE_SESSIONS_TABLE = "create table " 
			+ TABLE_SESSIONS
			+ "(" 
			+ COLUMN_ID + " integer primary key autoincrement, " 
			+ COLUMN_PATIENT_ID + " integer not null,"
			+ COLUMN_DATE + " integer not null, "
			+ COLUMN_DESCRIPTION + " text not null, " 
			+ COLUMN_TREATMENT + " text not null,"
			+ COLUMN_NOTES + " text not null"
			+ ");";

	public static void onCreate(SQLiteDatabase database)
	{
		database.execSQL(CREATE_SESSIONS_TABLE);
	}

	/*
	 *  Database upgrade SQL statement
	 */
	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
	{
		Log.w(PatientsTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
	    
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_SESSIONS);
		onCreate(database);
	}

} 