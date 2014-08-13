package com.panosrcng.physioassistant;

public class Photo
{
	private long id;
	private String filename;
	private long patient_id;
	private long session_id;
	private String notes;
	
	public void setId(long id)
	{
		this.id = id;
	}
	
	public void setFilename(String filename)
	{
		this.filename = filename;
	}
	
	public void setPatientId(long patient_id)
	{
		this.patient_id = patient_id;
	}
	
	public void setSessionId(long session_id)
	{
		this.session_id = session_id;
	}
	
	public void setNotes(String notes)
	{
		this.notes = notes;
	}
	
	public long getId()
	{
		return this.id;
	}
	
	public String getFilename()
	{
		return this.filename;
	}
		
	public long getPatientId()
	{
		return this.patient_id;
	}
	
	public long getSessionId()
	{
		return this.session_id;
	}
	
	public String getNotes()
	{
		return this.notes;
	}
		
}
