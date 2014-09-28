package org.on.authentication;

import java.util.List;

import org.hibernate.Query;
import org.on.global.DAO;

public class UserLogin {
	
	private static User user;
	
	private UserLogin() {
		
	}
	
	public static void login(String username,String password) {
		Query result = DAO.getSession().createQuery("FROM User where username = :username and password = :password");
		result.setString("username", username);
		result.setString("password", password);
		user = (User)result.uniqueResult();
		DAO.close();
	}
	
	public static User getInstance() {
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean isInGroup(String groupName) {
		Group group = (Group)DAO.getSession().get(Group.class, groupName);
		Query userQuery = DAO.getSession().createQuery("SELECT ug.user FROM UserGroup ug where ug.group = :group");
		userQuery.setParameter("group", group);
		List<User> users = userQuery.list();
		DAO.close();
		return users.contains(user);
	}
}
