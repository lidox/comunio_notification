package de.as.javabot.comunio.entity;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import de.as.javabot.comunio.User;

@Stateless
@LocalBean
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {

	public UserDaoImpl() {
		super(User.class);
	}

	/**
	 * Persist a user in database
	 */
	@Override
	public User persist(User user) {
		return create(user);
	}

	@Override
	public List<User> getUserByName(String userName) {
		try {
			return searchEntity(User.GET_BY_NAME, 0,
					"merchantName", userName);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<User>();
		}
	}

}
