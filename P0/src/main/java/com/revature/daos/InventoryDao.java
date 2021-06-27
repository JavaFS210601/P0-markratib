package com.revature.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.revature.models.Inventory;
import com.revature.models.Item;
import com.revature.models.User;
import com.revature.util.ConnectionUtil;
import java.sql.Array;

public class InventoryDao implements InventoryDaoInterface 
{
	@Override
	public List<Inventory> getInventories() 
	{
		List<Inventory> inventoryList = new ArrayList<>();//create a list that will contain the returned employees
		
		try(Connection conn = ConnectionUtil.getConnection())//try to establish a DB connection
		{
			ResultSet rs = null;//initialize an empty ResultSet that will store the result of our query
			
			String sql = "select * from user_inventories;";//write the query, assign it to a string variable
			
			Statement s = conn.createStatement();//This is creating an object to send the query to our DB
			
			rs = s.executeQuery(sql);//execute the query (sql) and put it in our ResultSet
			int i = 0;
			while(rs.next())//While there are results left in rs, continue the loop
			{
				//create a new employee object from each returned record
//				Array temp = rs.getArray("inventory");
//				Integer[] placeholder = (Integer[])temp.getArray();
						
				Inventory inv = new Inventory(
						rs.getInt("inventory_id"),
						rs.getInt("user_id_fk"),
//						(Integer[])rs.getArray(3).getArray());
						rs.getArray("inventory"));
				i++;
				
				inventoryList.add(inv);//add the new employee to our ArrayList
			}
			
		}catch(SQLException e)
		{
			System.out.println("Something went wrong when trying to access your DB");
			e.printStackTrace();
			System.exit(-1);
		}
		return inventoryList;
	}

	@Override
	public Inventory getInventory() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Inventory addToInventory(Item newItem, Inventory curInv) 
	{
		try(Connection conn = ConnectionUtil.getConnection())//try to establish a DB connection
		{
			String sql = "update user_inventories set inventory[?] = ? where user_id_fk = ?;";
			//find which index has nothing
			for(int i = 0; i<10; i++)
			{
				//if the current inventory position is nothing
				if(1 == curInv.getInventory()[i])
				{
					PreparedStatement ps = conn.prepareStatement(sql);
					//set the index of the update
					ps.setInt(1, i);
					//set the item_id for the update
					ps.setInt(2, newItem.getItem_id());
					//set the user_id_fk for the update
					ps.setInt(3, curInv.getUser_id_fk());
					ps.executeUpdate();
					break;
					
				}
			}
			
		}catch(SQLException e)
		{
			System.out.println("Something went wrong when trying to access your DB");
			e.printStackTrace();
			System.exit(-1);
		}
		return null;
	}


	@Override
	public Inventory removeFromInventory(Inventory curInv, int index) 
	{
		
		try(Connection conn = ConnectionUtil.getConnection())//try to establish a DB connection
		{
			String sql = "update user_inventories set inventory[?] = 1 where user_id_fk = ?;";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, index);
			ps.setInt(2, curInv.getUser_id_fk());
			
			ps.executeUpdate();
			
		}catch(SQLException e)
		{
			System.out.println("Something went wrong when trying to access your DB");
			e.printStackTrace();
			System.exit(-1);
		}
		return null;
	}
	//NEEDS UPDATING
	public void printInventory(Inventory curInv)
	{
		try(Connection conn = ConnectionUtil.getConnection())
		{
			String sql = "SELECT item_name, item_value FROM user_inventories LEFT JOIN items ON inventory[?] = item_id WHERE user_id_fk =?;";
			String nameResult;
			int valueResult;
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = null;
			int counter = 1;
			System.out.println("Inventory: ");
			//THIS LOOP MAKES NO SENSE
			for(Integer i: curInv.getInventory())
			{
				if(i == 1)
				{
					System.out.print("");
				}
				else
				{
					ps.setInt(1, (counter-1));
					ps.setInt(2, curInv.getUser_id_fk());
					rs = ps.executeQuery();
					rs.next();
					nameResult = rs.getString("item_name");
					valueResult = rs.getInt("item_value");
					System.out.println(counter + ") " + nameResult + ", " + valueResult);
				}
				counter++;
			}
		}
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
