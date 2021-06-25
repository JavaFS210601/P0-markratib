package com.revature.models;

import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.core.util.CloseShieldOutputStream;

import com.revature.daos.UserDao;

public class Authentication 
{
	public boolean Begin()
	{
		boolean quit = false;
		boolean stayIn = true;
		String greeting = "Please enter your username: ";
		Scanner in = new Scanner(System.in);
		UserDao ud = new UserDao();
		List<User> userList = ud.getUsers();
		
		while(stayIn)
		{
			String username;
			System.out.print(greeting);
			
			username = in.nextLine();//get the users username

			if(authenticate(username))//check if the username is in the DB
			{
				System.out.println("Username Authenticated.");
			}
			else
			{
				System.out.println("Username not found, would you like to add it (y/n)?");
				char response = in.nextLine().charAt(0);
				
				if(response == 'y')
				{
					ud.addUser(username);
				}
				else//Print a message...
				{
					System.out.println("Going back to the top...");
				}
			}
		}
		return quit;
	}
	
	public boolean authenticate(String username)
	{
		boolean present = false;
		UserDao ud = new UserDao();
		List<User> userList = ud.getUsers();
		System.out.println("Authenticating...");
		for(User u: userList)//loop through the list of users, comparing username to each
		{
			if(u.getUser_name().compareTo(username) == 0)
			{
				present = true;
				break;
			}
		}
		
		return present;
	}
}
