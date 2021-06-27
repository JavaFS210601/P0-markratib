package com.revature.models;

import com.revature.daos.UserDao;

public class User 
{
	int user_id = -1;
	int user_wallet = 0;
	String user_name;
	String user_pw;
	//no args constructor
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	//constructor with only the username
	public User(String user_name) {
		super();
		this.user_name = user_name;
	}
	//all args constructor
	public User(int user_id, String user_name, String user_pw, int user_wallet) {
		super();
		this.user_id = user_id;
		this.user_name = user_name;
		this.user_pw = user_pw;
		this.user_wallet = user_wallet;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + user_id;
		result = prime * result + ((user_name == null) ? 0 : user_name.hashCode());
		result = prime * result + ((user_pw == null) ? 0 : user_pw.hashCode());
		result = prime * result + user_wallet;
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
		User other = (User) obj;
		if (user_id != other.user_id)
			return false;
		if (user_name == null) {
			if (other.user_name != null)
				return false;
		} else if (!user_name.equals(other.user_name))
			return false;
		if (user_pw == null) {
			if (other.user_pw != null)
				return false;
		} else if (!user_pw.equals(other.user_pw))
			return false;
		if (user_wallet != other.user_wallet)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", user_wallet=" + user_wallet + ", user_name=" + user_name + "]";
	}
	
	public int getUser_id() {
		return user_id;
	}
	
//	public void setUser_id(int user_id) {
//		this.user_id = user_id;
//	}
	
	public int getUser_wallet() {
		return user_wallet;
	}
	
	public void setUser_wallet(int user_wallet) {
		this.user_wallet = user_wallet;
	}
	
	public String getUser_name() {
		return user_name;
	}
	
//	public void setUser_name(String user_name) {
//		this.user_name = user_name;
//	}
	
	public String getUser_pw() {
		return user_pw;
	}
	
	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}
	
	public int addToWallet(int amount)
	{
		UserDao ud = new UserDao();
		//add amount to wallet
		user_wallet += amount;
		ud.addToWallet(this);
		//return the new wallet amount
		return this.user_wallet;
	}
}
