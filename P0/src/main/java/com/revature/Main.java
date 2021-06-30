package com.revature;

import com.revature.models.Authentication;
import com.revature.models.Menu;


public class Main 
{

	public static void main(String[] args) 
	{
		String splashScreen = "**********************\n"
							+ "*                    *\n"
							+ "* MY FIRST... Well,  *\n"
							+ "* whatever this is...*\n"
							+ "*                    *\n"
							+ "**********************\n";
		System.out.println(splashScreen);
		
		//THIS IS OLD AND IS NOT USED.
		//create Menu object
		Menu poorMenu = new Menu();
		//Show the Main Menu
		poorMenu.wait(3000);
		poorMenu.mainMenu();
		
	}/**********************************END MAIN**********************************/
	
}
