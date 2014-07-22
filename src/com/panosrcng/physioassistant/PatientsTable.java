package com.panosrcng.physioassistant;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


/*
 * Patients Database Table 
 */
public class PatientsTable
{
	/*
	 *  Patients table structure
	 */
	public static final String TABLE_PATIENTS = "patients";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_FIRSTNAME = "firstname";
	public static final String COLUMN_LASTNAME = "lastname";
	public static final String COLUMN_PHONE = "phone";
	public static final String COLUMN_ADDRESS = "address";
	public static final String COLUMN_NOTES = "notes";

	public static final String[] allColumns = { COLUMN_ID, COLUMN_FIRSTNAME, COLUMN_LASTNAME,
									COLUMN_PHONE, COLUMN_ADDRESS, COLUMN_NOTES };
	
	/*
	 *  Database creation SQL statement
	 */
	private static final String CREATE_PATIENTS_TABLE = "create table " 
			+ TABLE_PATIENTS
			+ "(" 
			+ COLUMN_ID + " integer primary key autoincrement, " 
			+ COLUMN_FIRSTNAME + " text not null, " 
			+ COLUMN_LASTNAME + " text not null,"
			+ COLUMN_PHONE + " text not null,"
			+ COLUMN_ADDRESS + " text not null,"
			+ COLUMN_NOTES + " text not null"
			+ ");";

	public static void onCreate(SQLiteDatabase database)
	{
		database.execSQL(CREATE_PATIENTS_TABLE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
	{
		Log.w(PatientsTable.class.getName(), "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
	    
		database.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENTS);
		onCreate(database);
	}

} 