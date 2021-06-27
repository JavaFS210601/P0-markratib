package com.revature.daos;

import java.util.List;
import com.revature.models.Inventory;
import com.revature.models.Item;

public interface InventoryDaoInterface 
{
	public List<Inventory> getInventories();
	
	public Inventory getInventory();
	
	public Inventory addToInventory(Item newItem, Inventory curInv);
	
	public Inventory removeFromInventory(Inventory curInv, int index);
}
