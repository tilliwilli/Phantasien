package de.tilliwilli.phantasien.model.entities;

import java.util.Collection;

/**
 * Each user can define Categories he wants to assign to {@link Book books}. Categories have a name
 * and a user they belong to. Once created, the user cannot be changed, but the name can.<br>
 * A Category instance allows retrieval of all books that are labeled with this category.
 */
public interface Category extends BaseEntity {

	/**
	 * Returns the user that owns this category.
	 */
	public User getUser();

	/**
	 * Returns the name of this category. Will never be <tt>null</tt> or empty.
	 */
	public String getName();

	/**
	 * Sets the name of this category to <tt>newName</tt> and returns the old name, if there was one.
	 * 
	 * @param newName
	 *           the new name of this category
	 * @return the old name if there was one, <tt>null</tt> otherwise
	 */
	public String setName(String newName);

	/**
	 * Returns all books that are labeled with this category.
	 * 
	 * @return an <b>immutable</b> collection of books, may be empty
	 */
	public Collection<Book> getBooks();
}
