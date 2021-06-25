package com.revature.models;

import java.util.Scanner;

/***************************************
Menu.java
Function Definitions (or Methods....):

***************************************/
public class Menu 
{
	/*This is will be the root for the rest of the program.
	 * All other menus will spawn from this one.
	 */
	public void mainMenu()
	{
		String menu = "Please enter your user name: ";
		boolean stayIn = true;
		int userChoice = 0;
		//loop to get user input
		do
		{
			//print the menu
			System.out.print(menu);
			//get user input
			userChoice = getNumInput(menu);
			//switch on the user input depending on the menu
			stayIn = menuSwitch(userChoice);
		}while(stayIn);

	}/**********************************END MAINMenu**********************************/
	//No argument getStr. This gets the next string without whitespace, then gets the rest of the input stream.
	public String getStrInput()
	{
		String buf;
		Scanner input = new Scanner(System.in);
		
		buf = input.next();//get the next string without newline
		input.nextLine();//get the next newline from the input stream
		
		return buf;//return user input
	}
	//not implemented
	public String getStrInput(String stuff)
	{
		String userString = "";
		
		return userString;
	}
	
	public int getNumInput(String theMenu)
	{
		Scanner input = new Scanner(System.in);
		boolean stayIn = true;
		int userChoice = 0;
		
		//parse user input
		while(stayIn)
		{
			//If the user enters an integer, get the input,
			//leave the loop, and get the next line
			if(input.hasNextInt())
			{
				userChoice = input.nextInt();
				stayIn = false;
				input.nextLine();
			}
			else//user didnt enter an integer, get the next line and loop				
			{
				System.out.println(theMenu);
				input.nextLine();
			}//END ELSE BLOCK
		}//END WHILE BLOCK
		
		return userChoice;
	}
	
	public int getNumInput()
	{
		Scanner input = new Scanner(System.in);
		boolean stayIn = true;
		int userChoice = 0;
		
		//parse user input
		while(stayIn)
		{
			//If the user enters an integer, get the input,
			//leave the loop, and get the next line
			if(input.hasNextInt())
			{
				userChoice = input.nextInt();
				stayIn = false;
				input.nextLine();
			}
			else//user didnt enter an integer, get the next line and loop				
			{
				input.nextLine();
			}//END ELSE BLOCK
		}//END WHILE BLOCK
		
		return userChoice;
	}
	
	private boolean menuSwitch(int userChoice) 
	{
		boolean stayIn = true;
		switch(userChoice)
		{
		case 1://do account stuff
			int accInput = 0;
			String accPass;
			boolean authenticated = false;
			
			//allow user to attempt authentication 3 times
			for(int i = 0; i < 3; i++)
			{
				accInput = getNumInput();
			}
			break;
			
		case 2://create account
			//do stuff
			break;
			
		case 3://quit
			stayIn = false;
			break;
			
		default://will only occur if user input is not in range of the menu
			System.out.println("Not a valid choice. Try again...");
		}
		
		return stayIn;
		
	}/**********************************END MENUSWITCH**********************************/
	
}/**********************************END OF CLASS**********************************/
