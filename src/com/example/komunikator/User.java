package com.example.komunikator;

import java.util.NoSuchElementException;

public class User {
	
	private static String username;
	private static String passwordHash;
	
	
	public User(String username, String password){
		User.username=username;
		User.passwordHash=this.createBlowfishHash(password, username);
	}
	
	public static String getUsername() throws NoSuchElementException{
		if(username == null)
			throw new NoSuchElementException("User has not been initialized!");
		return username;
	}
	
	public static String getPasswordHash() throws NoSuchElementException{
		if(passwordHash == null)
			throw new NoSuchElementException("User has not been initialized!");
		return passwordHash;
	}
	
	private String createBlowfishHash(String Password, String salt){
		return BCrypt.hashpw(Password, salt);		
	}
}
