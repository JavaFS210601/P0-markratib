package com.revature.models;

public class Item 
{
	int item_id;
	String item_name;
	int item_value;
	int item_tier;
	
	
	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	public Item(int item_id, String item_name, int item_value, int item_tier) {
		super();
		this.item_id = item_id;
		this.item_name = item_name;
		this.item_value = item_value;
		this.item_tier = item_tier;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + item_id;
		result = prime * result + ((item_name == null) ? 0 : item_name.hashCode());
		result = prime * result + item_tier;
		result = prime * result + item_value;
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
		Item other = (Item) obj;
		if (item_id != other.item_id)
			return false;
		if (item_name == null) {
			if (other.item_name != null)
				return false;
		} else if (!item_name.equals(other.item_name))
			return false;
		if (item_tier != other.item_tier)
			return false;
		if (item_value != other.item_value)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Item [item_id=" + item_id + ", item_name=" + item_name + ", item_value=" + item_value + ", item_tier="
				+ item_tier + "]";
	}

	public int getItem_id() {
		return item_id;
	}

	public String getItem_name() {
		return item_name;
	}

	public int getItem_value() {
		return item_value;
	}

	public int getItem_tier() {
		return item_tier;
	}
	
	
	
}
