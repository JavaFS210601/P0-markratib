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
						rs.getString("user_name"));
				
				userList.add(u);//add the new employee to our ArrayList
			}
			
		}catch(SQLException e)
		{
			System.out.println("Something went wrong when trying to access your DB");
			e.printStackTrace();
			System.exit(-1);
		}
		return userList;
	}/********************************END getUsers********************************/
	
	@Override
	public void addUser(String username) 
	{
		try(Connection conn = ConnectionUtil.getConnection())
		{
			//check if the username is already in the database
			List<User> userList = getUsers();
			boolean dupe = false;
			
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
				String sqlInsert1 = "insert into users (user_name)"
						+ "values(?);";
				String sqlInsert2 = "insert into user_inventories (user_id_fk)"
						+ "values(?);";
				PreparedStatement ps = conn.prepareStatement(sqlInsert1);
				ps.setString(1, username);
				
				ps.executeUpdate();//execute the sql statement
				
				userList = getUsers();//get an updated list of users
				User newUser = null;
				for(User u: userList)//loop through the list of users, comparing username to each
				{
					//check of the usernames are equivalent
					if(u.getUser_name().compareTo(username) == 0)
					{
						newUser = u;
						break;
					}
				}
				//if, for some reason newUser wasn't assigned a value, check
				if(newUser != null)
				{
					ps = conn.prepareStatement(sqlInsert2);
					ps.setInt(1, newUser.getUser_id());
					ps.executeUpdate();
				}
				else
				{
					System.out.println("Username wasn't found in addUser...");
				}
			}
			else//this is a duplicate username
			{
				System.out.println("Duplicate username..."
						+ " Not adding: " + username);
			}

			
		}catch(SQLException e)
		{
			
		}
	}

	@Override
	public void delUser(String username) 
	{
		
	}

}
