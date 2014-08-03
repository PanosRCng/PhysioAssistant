package com.panosrcng.physioassistant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper
{
	private static final String DATABASE_NAME = "physioassistant.db";
	private static final int DATABASE_VERSION = 6;

	public DatabaseHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/*
	 *  Method is called during creation of the database
	 */
	@Override
	public void onCreate(SQLiteDatabase database)
	{
		PatientsTable.onCreate(database);
		SessionsTable.onCreate(database);
	}

	/*
	* Method is called during an upgrade of the database,
	*/
	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
	{
		PatientsTable.onUpgrade(database, oldVersion, newVersion);
		SessionsTable.onUpgrade(database, oldVersion, newVersion);
	}
	
}
	 
