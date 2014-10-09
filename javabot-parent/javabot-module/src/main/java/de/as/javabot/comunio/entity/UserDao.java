package de.as.javabot.comunio.entity;

import java.util.List;

import de.as.javabot.comunio.User;

public interface UserDao extends GenericDao<User>{
	
	public User persist(User user);
	
	public List<User> getUserByName(String userName);
}
