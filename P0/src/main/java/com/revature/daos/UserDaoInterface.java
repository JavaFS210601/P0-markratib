package com.revature.daos;

import java.util.List;
import com.revature.models.User;

public interface UserDaoInterface 
{
	public List<User> getUsers();
	
	public User getUser(String username);
	
	public boolean addUser(String username, String userpass);
	
	public void delUser(int userId);
	
	public void addToWallet(User curUser);
	
	public void setWallet(User curUser, int amount);
	
	public void updatePassword(User curUser, String newPassword);

}
