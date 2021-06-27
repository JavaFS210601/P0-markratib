package com.revature.models;

import java.util.List;
import java.util.Scanner;
import com.revature.daos.UserDao;
import com.revature.models.MenuSwitch;

/*FUNCTIONS: 
 * public void mainMenu()
 * protected void gachaMenu()
 * public int getNumInput(String theMenu)
 * public int getNumInput()
 */
public class Menu 
{
	public void mainMenu()
	{
		MenuSwitch poorSwitch = new MenuSwitch();
		String menu = "*****************************\n"
				+ "MAIN MENU OPTTIONS:\n"
				+ "*****************************\n"
				+ "1) Log in\n"
				+ "2) Create a user\n"
				+ "3) Delete a user\n"
				+ "4) Quit\n"
				+ "Please enter a number: ";
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
			stayIn = poorSwitch.mainMenuSwitch(userChoice);
		}while(stayIn);
		
	}/********************************END MainMenu********************************/
	
	protected void gachaMenu(User curUser) 
	{
		MenuSwitch poorSwitch = new MenuSwitch();
		String menu = "*****************************\n"
				+ "Current User: " + curUser.getUser_name() + "\n"
				+ "*****************************\n"
				+ "1) Get something\n"
				+ "2) Get something COOL\n"
				+ "3) Look at your inventory\n"
				+ "4) Check your wallet\n"
				+ "5) Sell an item\n"
				+ "6) Change password\n"
				+ "7) Logout to main menu";
		boolean stayIn = true;
		int userChoice = 0;
		do
		{
			System.out.println(menu);
			System.out.print("Please enter a number: ");
			userChoice = getNumInput();
			stayIn = poorSwitch.gachaMenuSwitch(userChoice, curUser);
		}while(stayIn);

	}/********************************END gachaMenu********************************/
	
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
				System.out.print("Invalid input, Enter a number: ");
//				System.out.println(theMenu);
				input.nextLine();
			}//END ELSE BLOCK
		}//END WHILE BLOCK
		
		return userChoice;
	}/********************************END getNumInput********************************/
	
	public int getNumInput()
	{
		//create a scanner for input
		Scanner input = new Scanner(System.in);
		//bool for loop control
		boolean stayIn = true;
		//hold the users input
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
	}/********************************END getNumInput********************************/
	
	
}
