/**
 * 
 */
package de.tilliwilli.phantasien.model.dao;

import org.springframework.stereotype.Repository;

import de.tilliwilli.phantasien.model.BaseDAO;
import de.tilliwilli.phantasien.model.entities.Book;

/**
 * @author Tilman
 */
@Repository
public class BookDAO extends BaseDAO<Book> {

	@Override
	protected Class<Book> getEntityClass() {
		return Book.class;
	}
}
