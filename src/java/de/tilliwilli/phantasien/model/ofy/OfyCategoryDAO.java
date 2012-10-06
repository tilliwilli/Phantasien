package de.tilliwilli.phantasien.model.ofy;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;
import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.common.base.Optional;

import de.tilliwilli.phantasien.model.Category;
import de.tilliwilli.phantasien.model.CategoryDAO;
import de.tilliwilli.phantasien.model.User;

/**
 * Objectify-compliant implementation of {@link CategoryDAO}.
 */
public class OfyCategoryDAO implements CategoryDAO {

	/**
	 * The user this DAO operates on.
	 */
	private OfyUser user;

	@Override
	public void setUser(User user) {
		checkArgument(user instanceof OfyUser);
		this.user = (OfyUser) user;
	}

	@Override
	public Optional<Category> byId(String id) {
		checkUser();
		Long idL = Long.parseLong(id);
		Category category = ofy().load().type(OfyCategory.class).parent(user).id(idL).get();
		return Optional.fromNullable(category);
	}

	/**
	 * Throws an exception if the user of this DAO is not set.
	 */
	private void checkUser() throws IllegalStateException {
		checkState(user != null,
				"Can't call BookDAO methods before the user to operate on is set using setUser().");
	}
}
