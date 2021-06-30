package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;
/*Functions:
 * public List<User> getUsers() 
 * public void addUser() 
 * public void delUser()
 * 
 * 
 * 
 */

public class UserDao implements UserDaoInterface 
{
	private static final Logger log = LogManager.getLogger(UserDao.class);
	
	@Override
	public List<User> getUsers() 
	{
		List<User> userList = new ArrayList<>();//create a list that will contain the returned employees
		
		try(Connection conn = ConnectionUtil.getConnection())//try to establish a DB connection
		{
			ResultSet rs = null;//initialize an empty ResultSet that will store the result of our query
			
			String sql = "select * from users;";//write the query, assign it to a string variable
			
			Statement s = conn.createStatement();//This is creating an object to send the query to our DB
			
			rs = s.executeQuery(sql);//execute the query (sql) and put it in our ResultSet
			
			while(rs.next())//While there are results left in rs, continue the loop
			{
				//create a new employee object from each returned record
				User u = new User(
						rs.getInt("user_id"),
						rs.getString("user_name"),
						rs.getString("user_pw"),
						rs.getInt("user_wallet"));
				
				userList.add(u);//add the new employee to our ArrayList
			}
			
		}catch(SQLException e)
		{
			System.out.println("Something went wrong when trying to access your DB");
			log.error("getUsers: ", e);
			e.printStackTrace();
			System.exit(-1);
		}
		return userList;
	}/********************************END getUsers********************************/
	
	@Override
	public User getUser(String username) 
	{
		List<User> userList = getUsers();
		User user = null;//create a user object to hold the user we just created
		for(User u: userList)//loop through the list of users, comparing username to each
		{
			//check if the usernames are equivalent
			if(u.getUser_name().compareTo(username) == 0)
			{
				user = u;
				break;
			}
		}
		return user;
	}/********************************END getUser********************************/
	
	@Override
	public boolean addUser(String username, String userpass) 
	{
		boolean success = false;
		try(Connection conn = ConnectionUtil.getConnection())
		{
			log.info("Adding " + username + " to the users table.");
			//check if the username is already in the database
			List<User> userList = getUsers();
			boolean dupe = false;
			//if the user enters an empty string
			if(username.compareTo("") == 0)
			{
				log.error("I dont know how they got here...\nInside addUser username.compateto(\"\")");
				System.out.println("Username cannot be empty...\nAlso, how did you get here???");
			}
			//username is not an empty string
			else
			{
				for(User u: userList)//loop through the list of users, comparing username to each
				{
					//check of the usernames are equivalent
					if(u.getUser_name().compareTo(username) == 0)
					{
						dupe = true;
						break;
					}
				}
				
				if(!dupe)//if this is not a duplicate username...
				{
					//this string will insert the user into our user table
					String sqlInsert1 = "insert into users (user_name, user_pw)"
							+ "values(?, ?);";
					//this string will create an entry in the inventory table, and assign the new users
					//user_id as the foreign key for that inventory
					String sqlInsert2 = "insert into user_inventories (user_id_fk)"
							+ "values(?);";
					//Make the user's inventory not null, becuase that causes problems for some reason :)
					String sqlUpdate = "Update user_inventories set inventory[?] = 1 where user_id_fk = ?;";
					//create the statement for user insertion
					PreparedStatement ps = conn.prepareStatement(sqlInsert1);
					//update the statement with the username for the entry
					ps.setString(1, username);
					ps.setString(2, userpass);
					//execute the sql statement to insert into the users table
					ps.executeUpdate();
					
					userList = getUsers();//get an updated list of users
					User newUser = null;//create a user object to hold the user we just created
					for(User u: userList)//loop through the list of users, comparing username to each
					{
						//check if the usernames are equivalent
						if(u.getUser_name().compareTo(username) == 0)
						{
							newUser = u;
							break;
						}
					}
					//INVENTORY CREATION
					//if, for some reason newUser wasn't assigned a value, check
					if(newUser != null)
					{
						//update the statement with the user_id for the entry
						ps = conn.prepareStatement(sqlInsert2);
						ps.setInt(1, newUser.getUser_id());
						ps.executeUpdate();
						success = true;
					}
					else
					{
						System.out.println("Username wasn't found in addUser...\n"
								+ "Entry in the inventory table was not created...\n"
								+ "Check the database for problems...");
					}
					ps = conn.prepareStatement(sqlUpdate);
					ps.setInt(2, newUser.getUser_id());
					if(newUser != null)
					{
						//set all inventory spaces to 0
						for(int i = 0; i<10; i++)
						{
							ps.setInt(1, i);
							ps.executeUpdate();
						}
					}
					
				}
				else//this is a duplicate username
				{
					success = false;
					System.out.println("This is a duplicate username...");
				}
				if(success)
				{
					log.info("Added " + username + " to the users table.");
				}
			}
		}catch(SQLException e)
		{
			System.out.println("Problem adding user " + username);
			log.error("addUser Exception: ", e);
			e.printStackTrace();
			System.exit(-1);
		}
		return success;
	}/********************************END addUser********************************/

	@Override
	public void delUser(int userId) 
	{
		try(Connection conn = ConnectionUtil.getConnection())
		{
			
			log.info("Removing user with user_id " + userId + ".");
			//check if the username is in the database
			List<User> userList = getUsers();
			boolean present = false;
			
			for(User u: userList)//loop through the list of users, comparing u.user_id to userId
			{
				//check of the userID and u.user_id are equivalent
				if(u.getUser_id() == userId)
				{
					present = true;//userId is in the database
					break;//get out of the loop
				}
			}
			
			if(present)//userId is in the database, continue with deletion
			{
				String sql = "delete fro"
						+ "m users where user_id = "
						+ Integer.toString(userId) + ";";//This might work....
				Statement s = conn.createStatement();
				
				s.execute(sql);
				
			}
			else//userId is not in the database
			{
				System.out.println("Could not find "
						+ userId + " in the database...");
			}
		}catch(SQLException e)
		{
			System.out.println("Problem deleting user with id " + userId);
			log.error("delUser Exception: ", e);
			e.printStackTrace();
			System.exit(-1);
		}
	}/********************************END delUser********************************/

	@Override
	public void addToWallet(User curUser) 
	{
		try(Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "update users set user_wallet = ? where user_id = ?";
			//new amount is current wallet amount + new amount
			//do sql stuff
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, curUser.getUser_wallet());
			ps.setInt(2, curUser.getUser_id());
			ps.executeUpdate();
			
		
		}catch(SQLException e)
		{
			System.out.println("Problem adding to user wallet ");
			log.error("addToWallet Exception: ", e);
			e.printStackTrace();
			System.exit(-1);
		}
		
	}

	@Override
	public void setWallet(User curUser, int amount) 
	{
		try(Connection conn = ConnectionUtil.getConnection())
		{
			
		}catch(SQLException e)
		{
			System.out.println("Problem setting user wallet");
			log.error("setWallet Exception: ", e);
			e.printStackTrace();
			System.exit(-1);
		}
		
	}
	
	@Override
	public void updatePassword(User curUser, String newPassword)
	{
		try(Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "update users set user_pw = ? where user_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, newPassword);
			ps.setInt(2, curUser.getUser_id());
			ps.executeUpdate();
			
		}catch(SQLException e)
		{
			System.out.println("Problem changing user password");
			log.error("update PasswordException: ", e);
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
}
