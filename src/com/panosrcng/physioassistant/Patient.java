package com.panosrcng.physioassistant;

public class Patient
{
	private long id;
	private String firstname;
	private String lastname;
	private String phone;
	private String address;
	private String notes;

	public void setId(long id)
	{
		this.id = id;
	}

	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}
	
	public void setLastname(String lastname)
	{
		this.lastname = lastname;
	}
	
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	
	public void setAddress(String address)
	{
		this.address = address;
	}
	
	public void setNotes(String notes)
	{
		this.notes = notes;
	}
	
	public Long getId()
	{
		return this.id;
	}
	
	public String getFirstname()
	{
		return this.firstname;
	}
	
	public String getLastname()
	{
		return this.lastname;
	}
	
	public String getPhone()
	{
		return this.phone;
	}
	
	public String getAddress()
	{
		return this.address;
	}
	
	public String getNotes()
	{
		return this.notes;
	}
	
}
