package de.tilliwilli.phantasien.model.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import javax.inject.Inject;

import de.tilliwilli.phantasien.model.dao.base.BaseDAO;
import de.tilliwilli.phantasien.model.entities.impl.AssociationImpl;
import de.tilliwilli.phantasien.model.entities.impl.BookImpl;
import de.tilliwilli.phantasien.model.entities.impl.UserImpl;

/**
 * @author Tilman
 */
public class AssociationDAO extends BaseDAO<AssociationImpl> {

	@Inject
	private UserImpl user;

	@Override
	protected Class<AssociationImpl> getEntityClass() {
		return AssociationImpl.class;
	}

	public AssociationImpl getForBook(BookImpl b) {
		return ofy().load().type(AssociationImpl.class).ancestor(user).filter("book", b.getKey()).first()
				.get();
	}

	public List<AssociationImpl> getAllBooks() {
		return ofy().load().type(AssociationImpl.class).ancestor(user).list();
	}

}
