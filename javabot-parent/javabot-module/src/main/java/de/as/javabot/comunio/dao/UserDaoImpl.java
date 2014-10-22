//package de.as.javabot.comunio.dao;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.ejb.LocalBean;
//import javax.ejb.Stateless;
//import de.as.javabot.comunio.User2;
//import de.as.javabot.comunio.entity.GenericDaoImpl;
//
//@Stateless
//@LocalBean
//public class UserDaoImpl extends GenericDaoImpl<User2> implements UserDao {
//
//	public UserDaoImpl() {
//		super(User2.class);
//	}
//
//	/**
//	 * Persist a user in database
//	 */
//	@Override
//	public User2 persist(User2 user) {
//		return create(user);
//	}
//
//	@Override
//	public List<User2> getUserByName(String userName) {
//		try {
//			return searchEntity(User2.GET_BY_NAME, 0,
//					"name", userName);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return new ArrayList<User2>();
//		}
//	}
//
//}
