package com.panosrcng.physioassistant;

import java.io.File;

import android.os.Environment;

public class Utils
{
	public Utils()
	{
		//
	}
	
	/*
	 *  setup the program directory tree
	 */
	public void createDirectories()
	{
		createDir( getProgramDirectoryPath() );
		
		createDir( getPhotosDirectoryPath() );
		
		createDir( getBackupsDirectoryPath() );
	}
	
	/*
	 *  deletes the program directory tree
	 */
	public void deleteDirectories()
	{
		deleteDir( getProgramDirectoryPath() );
	}
	
	/*
	 *  creates a directory
	 */
	public void createDir(String path)
	{
    	File dir = new File(path); 
    	
    	dir.mkdirs();
	}
	
	/*
	 *  deletes a directory and its contents
	 */
	public void deleteDir(String path)
	{
		File dir = new File(path);
		
		if(dir.isDirectory())
		{
			for( File child : dir.listFiles() )
			{
				deleteDir( child.getPath() );
			}
		}
		
	    dir.delete();
	}
	
	/*
	 * deletes a single file
	 */
	public void deleteFile(String path)
	{
		File dir = new File(path);
		
		dir.delete();
	}
	
    public String getProgramDirectoryPath()
    { 
    	return Environment.getExternalStorageDirectory().getPath() + "/" + Settings.PROGRAM_DIRECTORY;
    }
    
    public String getPhotosDirectoryPath()
    {
    	return getProgramDirectoryPath() + "/" + Settings.PHOTOS_DIRECTORY;
    }
    
    public String getBackupsDirectoryPath()
    {
    	return getProgramDirectoryPath() + "/" + Settings.BACKUPS_DIRECTORY;
    }
}
