/**
 * 
 */
package de.tilliwilli.phantasien.model.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import javax.inject.Inject;

import de.tilliwilli.phantasien.model.BaseDAO;
import de.tilliwilli.phantasien.model.entities.Association;
import de.tilliwilli.phantasien.model.entities.Book;
import de.tilliwilli.phantasien.model.entities.User;

/**
 * @author Tilman
 */
public class AssociationDAO extends BaseDAO<Association> {

	@Inject
	private User user;

	@Override
	protected Class<Association> getEntityClass() {
		return Association.class;
	}

	public Association getForBook(Book b) {
		return ofy().load().type(Association.class).ancestor(user).filter("book", b.getKey()).first()
				.get();
	}

	public List<Association> getAllBooks() {
		return ofy().load().type(Association.class).ancestor(user).list();
	}

}
