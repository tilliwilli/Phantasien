package de.tilliwilli.phantasien.model.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.common.base.Preconditions;

import de.tilliwilli.phantasien.model.dao.base.BaseDAO;
import de.tilliwilli.phantasien.model.entities.impl.UserImpl;

/**
 * @author Tilman
 */
public class UserDAO extends BaseDAO<UserImpl> {

	@Override
	protected Class<UserImpl> getEntityClass() {
		return UserImpl.class;
	}

	public UserImpl getByGoogleUser(com.google.appengine.api.users.User gUser) {
		Preconditions.checkNotNull(gUser);
		return ofy().load().type(UserImpl.class).filter("googleId", gUser.getUserId()).first().get();
	}

}
