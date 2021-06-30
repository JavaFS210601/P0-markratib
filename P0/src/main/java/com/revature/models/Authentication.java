package com.revature.models;

import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.util.CloseShieldOutputStream;

import com.revature.daos.UserDao;

public class Authentication 
{
	private static final Logger log = LogManager.getLogger(Authentication.class);
	
	public boolean authenticate(String username, String userpass)
	{
		boolean authenticated = false;//this is returned, and tells whether the user is in the database
								//initialized to false, and if the user is found, set to true
		UserDao ud = new UserDao();
		List<User> userList = ud.getUsers();//get a list with the users
		System.out.println("Authenticating...");
		for(User u: userList)//loop through the list of users, comparing username to each
		{
			if(u.getUser_name().compareTo(username) == 0)//If the user is found...
			{
				if(u.getUser_pw().compareTo(userpass) == 0)
				{
					log.info("User " + username + " authenticated");
					authenticated = true;//set present to true
					break;//break our of the loop, as the user was already found
				}
				else//pw is wrong
				{
					log.warn("Unsuccessfull login attempt with username: " + username);
					System.out.println("incorrect password");
				}
			}
			
		}
		
		return authenticated;//true if the user was found, false if not found
	}
	
	//This was gonig to be the menu, but i decided to change my mind
//	public boolean Begin()
//	{
//		boolean quit = false;
//		boolean stayIn = true;
//		String greeting = "Please enter your username: ";
//		Scanner in = new Scanner(System.in);
//		UserDao ud = new UserDao();
//		List<User> userList = ud.getUsers();
//		
//		while(stayIn)
//		{
//			String username;
//			System.out.print(greeting);
//			
//			username = in.nextLine();//get the users username
//
//			if(authenticate(username))//check if the username is in the DB
//			{
//				System.out.println("Username Authenticated.");
//			}
//			else
//			{
//				System.out.println("Username not found, would you like to add it (y/n)?");
//				char response = in.nextLine().charAt(0);
//				
//				if(response == 'y')
//				{
//					ud.addUser(username);
//				}
//				else//Print a message...
//				{
//					System.out.println("Going back to the top...");
//				}
//			}
//		}
//		return quit;
//	}
	
}
