package com.revature.models;

import java.util.List;
import java.util.Scanner;
import java.util.Random;
import com.revature.daos.InventoryDao;
import com.revature.daos.ItemDao;
import com.revature.daos.UserDao;

/*FUNCTIONS: 
 * protected boolean mainMenuSwitch(int userChoice) 
 * private void printUsernames(List<User> userList)
 * public void printUsers(List<User> userList)
 * public boolean gachaMenuSwitch(int userChoice) 
 * 
 */

public class MenuSwitch 
{
	protected boolean mainMenuSwitch(int userChoice) 
	{
		Menu poorMenu = new Menu();
		//This should only be altered at the quit case
		boolean stayIn = true;
		Scanner input = new Scanner(System.in);
		Authentication auth = new Authentication();
		UserDao ud = new UserDao();
		List<User> userList = ud.getUsers();
		
		switch(userChoice)
		{
		case 1://log in
		{
			String username;
			String userpass;
			
			//allow user to attempt authentication 3 times
			printUsernames(userList);
			
			for(int i = 0; i < 4; i++)
			{
				System.out.println("Attempy " + (i+1) + "/4");
				System.out.print("Please enter your username: ");
				username = input.nextLine();
				System.out.print("Please enter your passowrd: ");
				userpass = input.nextLine();
				//If the user is found in the database...
				if(auth.authenticate(username, userpass))
				{
					//User was found in the database, print the menu to do stuff
					poorMenu.gachaMenu(ud.getUser(username));
					break;
				}
				else//user was not found in the database...
				{
					if(i < 2)
					{
						System.out.println("User was not found, try again...");	
					}
					else// i == 2
					{
						System.out.println("STOP IT");
					}
				}//END IF ELSE
			}
			break;
		}//END OF CASE 1
			
		case 2://create a user
		{

			String newUsername;
			String newUserpass;
			String newUserpass2;
			
			System.out.print("Enter the new username: ");//prompt for the new username
			newUsername = input.nextLine();//get the users input
			do//Get the users password
			{
				System.out.print("Enter your password: ");
				newUserpass = input.nextLine();
				System.out.print("Enter password again: ");
				newUserpass2 = input.nextLine();
				
			}while(newUserpass.compareTo(newUserpass2) != 0);//loop
			
			if(ud.addUser(newUsername, newUserpass))//if the user is added correctly, do stuff
			{
				System.out.println("New user entry is :\n");
				User newUser = ud.getUser(newUsername);
				
				if(newUser != null)//newUser was found and returned a value
				{
					System.out.println(newUser.toString());
				}
				else//newUser is null
				{
					System.out.println("NOT FOUND.");
				}
			}
			else//the user was not added correctly
			{
				System.out.println("User was not created successfully");
	
			}
			break;
		}//END OF CASE 2
			
		case 3://delete a suer
		{
			String username;
			//print out the list of users
			printUsernames(userList);
			for(int i = 0; i < 4; i++)
			{
				System.out.println("Attempy " + (i+1) + "/4");
				System.out.print("Enter the  username you wish to delete: ");//prompt for the username
				username = input.nextLine();//get the users input
//				System.out.println("");//formating
				//check if username exists, so i dont get a null pointer
				if(ud.getUser(username) != null)
				{
					User targetUser = ud.getUser(username);
					ud.delUser(targetUser.getUser_id());//attempt to delete user with requested userId
					System.out.println("Updated users:");
					userList = ud.getUsers();//update the users list
					printUsernames(userList);//print out the list of users
				}
				else//username is not in the database
				{
					System.out.println("User " + username + " not found..."
							+ "\n(HINT: enter the username, not user_id...)");
				}
			}
			break;
		}//END OF CASE 3
			
		case 4://quit
		{
			System.out.println("Goodbye.");
			stayIn = false;
			break;
		}//END OF CASE 4
			
		default://will only occur if user input is not in range of the menu
			{System.out.println("Not a valid choice. Try again...");}
		}//END SWITCH BLOCK
		
		return stayIn;
	}
	/**********************************END menuSwitch**********************************/
	
	public boolean gachaMenuSwitch(int userChoice, User curUser) 
	{
		Menu poorMenu = new Menu();
		//This should only be altered at the quit case
		boolean stayIn = true;//bool to stay in the loop
		int choice = 0;//int to hold user input
		Scanner input = new Scanner(System.in);//scanner object
		Authentication auth = new Authentication();
		UserDao ud = new UserDao();
		List<User> userList = ud.getUsers();
		InventoryDao id = new InventoryDao();
		List<Inventory> inventoryList =  id.getInventories();
		ItemDao itemD = new ItemDao();
		List<Item> itemList = itemD.getItems();
		Inventory curInv = new Inventory();
		curInv = curInv.findUserInventory(curUser);
		
		switch(userChoice)
		{
		case 1://Get something
			{
				boolean invFull = true;
				
				//check if inventory is full
				invFull = curInv.isFull();
				//if the inventory is not full...
				if(!invFull)
				{
					//roll a random number
					Random rand = new Random();
					int upperbound = 2;
					int randomNumber = 4;
					int randomIndex = 1;
					boolean foundItem = false;
					randomNumber = (rand.nextInt(upperbound) + 3);
					//get an item for the user
					
					while(!foundItem)
					{
						//randomIndex is assigned a value of list size + 1
						randomIndex = (rand.nextInt(itemList.size()));
						if(itemList.get(randomIndex).getItem_tier() >= randomNumber)
						{
							//add the item to the inventory
							System.out.println("Found " + itemList.get(randomIndex).getItem_name() + "!");
							id.addToInventory(itemList.get(randomIndex), curInv);
							System.out.println("Press enter to continue...");
							input.nextLine();
							foundItem = true;
						}
					}
					
				}
				break;
				
			}//END CASE 1
			case 2://Get something COOl
			{
				boolean invFull = true;
				char yn = 'n';
				//check if the user has enough to enter
				if(curUser.getUser_wallet() < 100)
				{
					System.out.println("Insufficient Funds...");
					System.out.println("Press enter to continue...");
					input.nextLine();
				}
				else//user has enough funds
				{
					System.out.print("Your current balance is: " + curUser.getUser_wallet()
							+ "\nYou must spend 100 in order to get something,"
							+ "\nContinue? (y/n) ");
					yn = input.nextLine().charAt(0);
					//if the user wants to spend their hard earned money...
					if(yn == 'y')
					{
						//check if the users inventory is full
						invFull = curInv.isFull();
						//if the inventory is not full...
						if(!invFull)
						{
							//check if the user has the funds...
							if(curUser.getUser_wallet() >= 100)
							{
								//deduct 100 from their wallet
								curUser.addToWallet(-100);
								//roll a random number
								Random rand = new Random();
								int upperbound = 2;
								int randomNumber = 1;
								int randomIndex = 1;
								boolean foundItem = false;
								randomNumber = rand.nextInt(upperbound);
								//get an item for the user
								
								while(!foundItem)
								{
									//randomIndex is assigned a value of list size + 1
									randomIndex = (rand.nextInt(itemList.size()));
									if(itemList.get(randomIndex).getItem_tier() >= randomNumber)
									{
										//add the item to the inventory
										System.out.println("Found " + itemList.get(randomIndex).getItem_name() + "!");
										id.addToInventory(itemList.get(randomIndex), curInv);
										System.out.println("Press enter to continue...");
										input.nextLine();
										foundItem = true;
									}
								}
							}
						}
					}
				}
				break;
			}//END CASE 2	
			case 3://Look at inventory
			{
				printInventory(curUser);
				System.out.println("Press enter to continue...");
				input.nextLine();
				break;
			}//END CASE 3
			case 4://Check your wallet
			{
				System.out.println("You currently have " + curUser.getUser_wallet() + " fun bucks.");
				System.out.println("Press enter to continue...");
				input.nextLine();
				break;
			}//END CASE 4
			case 5://Sell an item
			{
				printInventory(curUser);
				System.out.println("select an item to sell... \n"
						+ "input a number besides 1-10 to quit");
				choice = poorMenu.getNumInput()-1;
				//if choice is not between 1 and 10...
				if(choice < 0 && choice > 9)
				{
					System.out.println("Not a valid choice...");
				}
				else//1<=choice<=10, so choice is in range
				{
					//if curInv for the user exists
					if(curInv != null)
					{
						//check if the array is the correct length for choice and the item at choice is not 0
						if((curInv.getInventory().length >= choice) && (curInv.atIndex(choice) != 1))
						{
							//set the users wallet equal to their current amount + the items value
							curUser.addToWallet(curInv.valueAt(choice));
							id.removeFromInventory(curInv, choice);
						}
						else//choice is out of bounds of the array
						{
							System.out.println("Invalid choice...");
						}
					}
				}
				break;
				
			}//END CASE 5
			case 6://Change password
			{
				System.out.println("CURRENTLY UNDER CONSTRUCTION!!!");
				break;
				
			}//END CASE 6
			case 7://QUit
			{
				System.out.println("Exiting to main menu...");
				System.out.println("Press enter to continue...");
				input.nextLine();
				stayIn = false;
				break;
			}
			default:
			{
				System.out.println("Not a valid choice. Try again...");
			}//END DEFAULT CASE
		}//END SWITCH BLOCK
		return stayIn;
	}
	/**********************************END gachaMenuSwitch**********************************/
	public void printUsernames(List<User> userList) 
	{
		int i = 1;
		System.out.println("\nUser Names: ");
		for(User u: userList)
		{
			System.out.println(i + ". " + u.getUser_name());
			i++;
		}
		
	}
	/**********************************END printUsernames**********************************/
	public void printUsers(List<User> userList)
	{

		for(User u: userList)
		{
			System.out.println(u.toString());
		}
	}
	/**********************************END printUsers**********************************/
	public void printInventory(User curUser)
	{
		Scanner input = new Scanner(System.in);
		InventoryDao id = new InventoryDao();
		//check if we were given a bad variable
		if(curUser == null)
		{
			System.out.println("User does not exist...");
		}
		else//curUser exists
		{
			Inventory curInv = new Inventory();
			//get the inventory for the specifid user
			curInv = curInv.findUserInventory(curUser);
			//if the user had an inventory...
			if(curInv != null)
			{
				//System.out.println(curInv.toString());
				id.printInventory(curInv);
//				System.out.print("press enter to continue...");
//				input.nextLine();
			}
			else//user did not have an inventory assigned
			{
				System.out.println("There was no inventory found for user " + curUser.getUser_name());
			}
		}
	}
	/**********************************END printInventory**********************************/
}
