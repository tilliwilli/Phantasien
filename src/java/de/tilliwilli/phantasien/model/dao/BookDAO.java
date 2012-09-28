package de.tilliwilli.phantasien.model.dao;

import de.tilliwilli.phantasien.model.dao.base.BaseDAO;
import de.tilliwilli.phantasien.model.entities.impl.BookImpl;

/**
 * @author Tilman
 */
public class BookDAO extends BaseDAO<BookImpl> {

	@Override
	protected Class<BookImpl> getEntityClass() {
		return BookImpl.class;
	}
}
