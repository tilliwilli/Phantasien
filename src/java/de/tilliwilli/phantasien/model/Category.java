package de.tilliwilli.phantasien.model;

/**
 * Categories are <em>tags</em> a user can apply to books. Each category entity is owned by the user
 * who created it, and only that user can assign this category to his books.
 */
public interface Category extends BaseEntity {

	/**
	 * Returns the user that owns this category.
	 */
	public User getUser();

	/**
	 * Returns the name of this category. Will never be <tt>null</tt> or empty if the category was
	 * persisted at least once.
	 */
	public String getName();

	/**
	 * Sets the name of this category to <tt>newName</tt> and returns the old name, if there was one.
	 * 
	 * @param newName
	 *           the new name of this category, must not be <tt>null</tt> or empty
	 * @return the old name
	 */
	public String setName(String newName);

}
