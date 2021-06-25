package com.revature.daos;

import java.util.List;
import com.revature.models.User;

public interface UserDaoInterface 
{
	public List<User> getUsers();
	
	public void addUser(String username);
	
	public void delUser(String username);
}
