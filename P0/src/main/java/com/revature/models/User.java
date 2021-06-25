package com.revature.models;

public class User 
{
	int user_id = -1;
	String user_name;
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
	public User(int user_id, String user_name) {
		super();
		this.user_id = user_id;
		this.user_name = user_name;
	}




	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + user_id;
		result = prime * result + ((user_name == null) ? 0 : user_name.hashCode());
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
		return true;
	}
	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", user_name=" + user_name + "]";
	}
	/*GETTERS AND SETTERS*******************************************/
	public int getUser_id() {
		return user_id;
	}
//	public void setUser_id(int user_id) {
//		this.user_id = user_id;
//	}
	public String getUser_name() {
		return user_name;
	}
//	public void setUser_name(String user_name) {
//		this.user_name = user_name;
//	}
	
	
}
