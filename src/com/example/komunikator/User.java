package com.example.komunikator;

import java.util.NoSuchElementException;

public class User {
	
	private static String username;
	private static String password;
	
	
	public User(String username, String password){
		User.username=username;
		User.password=password;
	}
	
	public static String getUsername() throws NoSuchElementException{
		if(username == null)
			throw new NoSuchElementException("User has not been initialized!");
		return username;
	}
	
	public static String getPassword(){
		if(password == null)
			throw new NoSuchElementException("User has not been initialized!");
		return password;
	}
	
	private String createBlowfishHash(String Password, String salt){
		return BCrypt.hashpw(Password, salt);		
	}
}
