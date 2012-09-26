/**
 * 
 */
package de.tilliwilli.phantasien.model.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import org.springframework.stereotype.Repository;

import com.google.common.base.Preconditions;

import de.tilliwilli.phantasien.model.BaseDAO;
import de.tilliwilli.phantasien.model.entities.User;

/**
 * @author Tilman
 */
@Repository
public class UserDAO extends BaseDAO<User> {

	@Override
	protected Class<User> getEntityClass() {
		return User.class;
	}

	public User getByGoogleUser(com.google.appengine.api.users.User gUser) {
		Preconditions.checkNotNull(gUser);
		return ofy().load().type(User.class).filter("googleId", gUser.getUserId()).first().get();
	}

}
