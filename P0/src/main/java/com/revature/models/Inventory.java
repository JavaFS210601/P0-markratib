package com.revature.models;

import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.revature.daos.InventoryDao;
import com.revature.daos.ItemDao;

public class Inventory 
{
	int inventory_id;
	int user_id_fk;
	Integer[] inventory;
	
	public Inventory() {
		super();
		// TODO Auto-generated constructor stub
	}

	//Integer[] Implementanion
//	public Inventory(int inventory_id, int user_id_fk, Integer[] inventory) {
//		super();
//		this.inventory_id = inventory_id;
//		this.user_id_fk = user_id_fk;
//		this.inventory = inventory;
//	}
//
//	public Inventory(int user_id_fk, Integer[] inventory) {
//		super();
//		this.user_id_fk = user_id_fk;
//		this.inventory = inventory;
//	}
	//all fields constructor
	public Inventory(int inventory_id, int user_id_fk, Array newinventory) 
	{
		super();
		this.inventory_id = inventory_id;
		this.user_id_fk = user_id_fk;
		try {
			this.inventory = (Integer[])newinventory.getArray();
		} catch (SQLException e) {
			System.out.println("Problem in Invetory Constructor...");
			e.printStackTrace();
		}
	}
	//some fields constructor
	public Inventory(int user_id_fk, Array inventory) {
		super();
		this.user_id_fk = user_id_fk;
		try {
			this.inventory = (Integer[])inventory.getArray();
		} catch (SQLException e) {
			System.out.println("Problem in Invetory Constructor...");
			e.printStackTrace();
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((inventory == null) ? 0 : inventory.hashCode());
		result = prime * result + inventory_id;
		result = prime * result + user_id_fk;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Inventory other = (Inventory) obj;
		if (inventory == null) {
			if (other.inventory != null)
				return false;
		} else if (!inventory.equals(other.inventory))
			return false;
		if (inventory_id != other.inventory_id)
			return false;
		if (user_id_fk != other.user_id_fk)
			return false;
		return true;
	}

	

	public int getInventory_id() {
		return inventory_id;
	}

//	public void setInventory_id(int inventory_id) {
//		this.inventory_id = inventory_id;
//	}

	public int getUser_id_fk() {
		return user_id_fk;
	}

//	public void setUser_id_fk(int user_id_fk) {
//		this.user_id_fk = user_id_fk;
//	}

	//Integer[] implementaion
	public Integer[] getInventory() {
		return inventory;
	}

//	public void setInventory(Integer[] inventory) {
//		this.inventory = inventory;
//	}
	
	public Inventory findUserInventory(User curUser)
	{
		InventoryDao id = new InventoryDao();
		Inventory curUserInv = null;
		List<Inventory> allInventories = id.getInventories();
		//go through the list of inventories
		for(Inventory i: allInventories)
		{
			//if the current user_id == the current inventory's user_id_fk...
			if(i.user_id_fk == curUser.user_id)
			{
				curUserInv = i;
			}
		}
	
		return curUserInv;
	}
	//get the item_id at a given index
	public int atIndex(int index) 
	{
		int theThing = 1;
		if(this.getInventory().length >= index)
		{
			theThing = this.inventory[index];
		}
		return theThing;
	}
	//get the value of an item at a given index
	public int valueAt(int index)
	{
		ItemDao itemD = new ItemDao();
		//get a list of the current items in the items table
		List<Item> itemList = itemD.getItems();
		
		int itemId = 0;
		int value = 0;
		//check if index is in range
		if(this.getInventory().length >= index)
		{
			//get the item id at index, because the items table is 1 based,
			//we need to change the returned id to a 0 base because java arrays
			//are 0 based, and our table is 1 based
			itemId = this.inventory[index];
		}
		//loop through the item list
		for(Item i : itemList)
		{
			//compare if itemId is equivalent to the current item
			if(itemId == i.getItem_id())
			{
				//set value equal to the current items value
				value = i.getItem_value();
				break;
			}
		}
		return value;
	}
	//returns true if the inventory is full, there are 10 items present
	public boolean isFull()
	{
		boolean isFull = true;
		for(int i = 0;i<10; i++)
		{
			//if an inventory slot is nothing...
			if(this.atIndex(i) == 1)
			{
				isFull = false;
				break;
			}
		}
		
		return isFull;
	}
	
}
