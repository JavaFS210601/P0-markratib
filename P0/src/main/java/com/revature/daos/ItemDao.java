package com.revature.daos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Inventory;
import com.revature.models.Item;
import com.revature.util.ConnectionUtil;

public class ItemDao implements ItemDaoInterface {

	@Override
	public List<Item> getItems() 
	{
		List<Item> itemsList = new ArrayList<>();//create a list that will contain the returned employees
		
		try(Connection conn = ConnectionUtil.getConnection())//try to establish a DB connection
		{
			ResultSet rs = null;//initialize an empty ResultSet that will store the result of our query
			
			String sql = "select * from items;";//write the query, assign it to a string variable
			
			Statement s = conn.createStatement();//This is creating an object to send the query to our DB
			
			rs = s.executeQuery(sql);//execute the query (sql) and put it in our ResultSet
			int i = 0;
			while(rs.next())//While there are results left in rs, continue the loop
			{
				//create a new employee object from each returned record
//				Array temp = rs.getArray("inventory");
//				Integer[] placeholder = (Integer[])temp.getArray();
						
				Item items= new Item(
						rs.getInt("item_id"),
						rs.getString("item_name"),
						rs.getInt("item_value"),
						rs.getInt("item_tier"));
				i++;
				
				itemsList.add(items);//add the new employee to our ArrayList
			}
			
		}catch(SQLException e)
		{
			System.out.println("Something went wrong when trying to access your DB");
			e.printStackTrace();
			System.exit(-1);
		}
		return itemsList;
		
	}

}
