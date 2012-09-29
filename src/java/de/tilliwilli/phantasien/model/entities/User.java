package de.tilliwilli.phantasien.model.entities;

import java.util.Collection;
import java.util.Map;

import de.tilliwilli.phantasien.model.ReadState;

/**
 * A User is a single end-user of the application. Users manage {@link Book books}. They can define
 * {@link Category categories} and assign them to books, and can set {@link ReadState read states}
 * of the books they manage.<br>
 * They may also have settings, e.g. what data to show in the frontend. Settings are simple
 * String-to-String mappings.
 */
public interface User extends BaseEntity {

	/**
	 * Returns the value of the setting with name <tt>name</tt>, or <tt>null</tt> if no such setting
	 * was set.
	 * 
	 * @param name
	 *           the name of the setting to retrieve
	 * @return the value of the setting, or <tt>null</tt>
	 */
	public String getSetting(String name);

	/**
	 * Sets a setting with the given <tt>name</tt> to the given <tt>value</tt>. If a setting with
	 * this name already exists, it is overwritten and its old value is returned.
	 * 
	 * @param name
	 *           the name of the setting
	 * @param value
	 *           the value of the setting
	 * @return the old value of the setting, or <tt>null</tt> if no setting existed with that name
	 */
	public String setSetting(String name, String value);

	/**
	 * Returns a {@link Map} containing all settings this user has defined. The map is
	 * <b>immutable</b>, and it might be empty.
	 */
	public Map<String, String> getAllSettings();

	/**
	 * Returns the book with the given <tt>id</tt> that belongs to this user.
	 * 
	 * @param id
	 *           the id of the book
	 * @return the book, or <tt>null</tt> if no book with the given id exists for this user
	 */
	public Book getBook(String id);

	/**
	 * Returns all the books this user manages. The returned collection is <b>immutable</b> and might
	 * be empty.
	 */
	public Collection<Book> getAllBooks();

	/**
	 * Creates a new {@link Book} instance belonging to the user and returns it. The Book is not
	 * persisted yet, so it can be discarded without affecting the data store at all. Users must call
	 * {@link Book#save() save()} on the returned book object to persist it.
	 * 
	 * @return the new {@link Book} instance
	 */
	public Book createBook();

	/**
	 * Returns the category with the given <tt>id</tt> that belongs to this user.
	 * 
	 * @param id
	 *           the id of the category
	 * @return the category, or <tt>null</tt> if no category with the given id exists for this user.
	 */
	public Category getCategory(String id);

	/**
	 * Returns all categories this user has defined. The returned collection is <b>immutable</b> and
	 * might be empty.
	 */
	public Collection<Category> getAllCategories();

	/**
	 * Creates a new {@link Category} instance belonging to the user and returns it. The category is
	 * not persisted yet, so the data store is not affected until {@link Category#save() save()} is
	 * called on the category object.
	 * 
	 * @return the new {@link Category} instance
	 */
	public Category createCategory();

}
