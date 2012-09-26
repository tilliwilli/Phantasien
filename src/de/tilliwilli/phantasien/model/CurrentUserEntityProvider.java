/**
 * 
 */
package de.tilliwilli.phantasien.model;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import de.tilliwilli.phantasien.model.dao.UserDAO;
import de.tilliwilli.phantasien.model.entities.User;

/**
 * @author Tilman
 */
public class CurrentUserEntityProvider implements FactoryBean<User> {

	@Autowired
	private UserDAO userDAO;

	@Override
	public User getObject() throws Exception {
		UserService userService = UserServiceFactory.getUserService();
		com.google.appengine.api.users.User googleUser = userService.getCurrentUser();

		User user = userDAO.getByGoogleUser(googleUser);
		return user;
	}

	@Override
	public Class<?> getObjectType() {
		return User.class;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}
}
