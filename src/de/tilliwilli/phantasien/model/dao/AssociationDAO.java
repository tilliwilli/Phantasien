/**
 * 
 */
package de.tilliwilli.phantasien.model.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import de.tilliwilli.phantasien.model.BaseDAO;
import de.tilliwilli.phantasien.model.entities.Book;
import de.tilliwilli.phantasien.model.entities.Association;
import de.tilliwilli.phantasien.model.entities.User;

/**
 * @author Tilman
 */
@Repository
public class AssociationDAO extends BaseDAO<Association> {

	@Autowired
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
