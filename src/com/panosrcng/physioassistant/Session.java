package com.panosrcng.physioassistant;

import java.text.DateFormat;
import java.util.Date;

public class Session
{
	private long id;
	private long patient_id;
	private long dateTime;
	private String description;
	private String treatment;
	private String notes;
	
	public void setId(long id)
	{
		this.id = id;
	}
	
	public void setPatientId(long patient_id)
	{
		this.patient_id = patient_id;
	}
	
	public void setDateTime(long dateTime)
	{
		this.dateTime = dateTime;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public void setTreatment(String treatment)
	{
		this.treatment = treatment;
	}
	
	public void setNotes(String notes)
	{
		this.notes = notes;
	}
	
	public long getId()
	{
		return this.id;
	}
	
	public long getPatientId()
	{
		return this.patient_id;
	}
	
	public long getDateTime()
	{
		return this.dateTime;
	}
	
	public String getDateStr()
	{
		return DateFormat.getDateInstance().format(new Date(dateTime)); 
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public String getTreatment()
	{
		return this.treatment;
	}
	
	public String getNotes()
	{
		return this.notes;
	}
}
