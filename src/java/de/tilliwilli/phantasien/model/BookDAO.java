package de.tilliwilli.phantasien.model;

import java.util.Collection;

import com.google.common.base.Optional;

import de.tilliwilli.phantasien.providers.annotations.FromSession;

/**
 * <p>
 * This class allows retrieval of {@link Book} objects by different means.
 * </p>
 * <p>
 * Since books always belong to a {@link User}, instead of supplying a user object with each method
 * call, you must give the user this DAO operates on with one call to {@link #setUser(User)}. If
 * that call is forgotten any retrieval operation that explicitly needs a user throws an
 * {@link IllegalStateException}. The primary exception are methods that get {@link Category}
 * parameters, since these are bound to a user and the category's owner is used anyway.
 * </p>
 * <p>
 * An alternative is to annotate the BookDAO instance with {@link FromSession @SessionUser}, so it
 * automatically gets the user injected from the session.
 */
public interface BookDAO {

	/**
	 * Sets the user this DAO operates on.
	 */
	public void setUser(User user);

	/**
	 * Returns the book with the given <tt>id</tt>.
	 * 
	 * @return an {@link Optional} containing the book object or nothing if there is no book with the
	 *         given <tt>id</tt>
	 */
	public Optional<Book> byId(String id);

	/**
	 * Returns all books that belong to the given category.
	 * <p>
	 * The returned collection is <b>immutable</b>.
	 * </p>
	 */
	public Collection<Book> byCategory(Category cat);

	/**
	 * Returns all books of the user.
	 * <p>
	 * The returned collection is <b>immutable</b>.
	 * </p>
	 */
	public Collection<Book> allFromUser();

	/**
	 * Returns all books that were written by <tt>author</tt>.
	 * <p>
	 * The returned collection is <b>immutable</b>.
	 * </p>
	 */
	public Collection<Book> allByAuthor(String author);

	/**
	 * Returns all books that have the given <tt>state</tt> as their {@link ReadState}.
	 * <p>
	 * The returned collection is <b>immutable</b>.
	 * </p>
	 */
	public Collection<Book> allByReadState(ReadState state);

	/**
	 * Returns all books that contain the given <tt>title</tt>. Looks for both titles and subtitles.
	 * <p>
	 * The returned collection is <b>immutable</b>.
	 * </p>
	 */
	public Collection<Book> findByTitle(String title);

	/**
	 * Returns all books that contain the given <tt>title</tt>. If <tt>withSubtitles</tt> is
	 * <tt>true</tt>, also looks for subtitles, otherwise only looks for {@link Book#getTitle()
	 * titles}.
	 * <p>
	 * The returned collection is <b>immutable</b>.
	 * </p>
	 */
	public Collection<Book> findByTitle(String title, boolean withSubtitles);

	/**
	 * Returns all books that have the given <tt>location</tt> set.
	 * <p>
	 * The returned collection is <b>immutable</b>.
	 * </p>
	 */
	public Collection<Book> findByLocation(String location);

}
