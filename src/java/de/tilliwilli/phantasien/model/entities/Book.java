package de.tilliwilli.phantasien.model.entities;

import java.util.Collection;

import de.tilliwilli.phantasien.model.ReadState;

/**
 * A Book is the central entity of this application. Books belong to {@link User Users}, and once a
 * book is created, this relationship is immutable. Books can have {@link Category categories}
 * assigned, and have a {@link ReadState} that determines how the user is related to the book.<br>
 * Furthermore, books have a lot of different attributes like a title and the number of pages in the
 * book.
 */
public interface Book extends BaseEntity {

	/**
	 * Returns the {@link User} this book belongs to.
	 */
	public User getUser();

	/**
	 * Returns all {@link Category categories} this book has assigned. The returned collection is
	 * <b>immutable</b>.
	 */
	public Collection<Category> getCategories();

	/**
	 * Adds a category assignment to this book. If the category is already assigned, nothing happens.
	 * 
	 * @return this book for convenience
	 */
	public Book addCategory(Category category);

	/**
	 * Removes a category assignment from this book. If the category is not already assigned to this
	 * book, nothing happens.
	 * 
	 * @return this book for convenience
	 */
	public Book removeCategory(Category category);

	/**
	 * Determines whether this book has the given category assigned or not.
	 * 
	 * @return <tt>true</tt> if this book has the category assigned, <tt>false</tt> otherwise
	 */
	public boolean hasAssigned(Category category);

	/**
	 * Returns the {@link ReadState} of this book.
	 */
	public ReadState getReadState();

	/**
	 * Sets the {@link ReadState} of this book.
	 * 
	 * @return this book for convenience
	 */
	public Book setReadState(ReadState newState);

	/**
	 * Returns the title of this book, or <tt>null</tt> if not set.
	 */
	public String getTitle();

	/**
	 * Sets the title of this book.
	 * 
	 * @return this book for convenience
	 */
	public Book setTitle(String newTitle);

	/**
	 * Returns the subtitle of this book, or <tt>null</tt> if not set.
	 */
	public String getSubTitle();

	/**
	 * Sets the subtitle of this book.
	 * 
	 * @return this book for convenience
	 */
	public Book setSubTitle(String newSubTitle);

	/**
	 * Returns the author of this book, or <tt>null</tt> if not set.
	 */
	public String getAuthor();

	/**
	 * Sets the author of this book.
	 * 
	 * @return this book for convenience
	 */
	public Book setAuthor(String newAuthor);

	/**
	 * Returns the number of pages in this book, or <b>-1</b> if not set.
	 */
	public Integer getPages();

	/**
	 * Sets the number of pages in this book.
	 * 
	 * @return this book for convenience.
	 */
	public Book setPages(Integer numberOfPages);

	/**
	 * Returns the giver of this book, or <tt>null</tt> if not set.
	 */
	public String getGiver();

	/**
	 * Sets the giver of this book.
	 * 
	 * @return this book for convenience
	 */
	public Book setGiver(String giver);

	/**
	 * Returns the location of this book, or <tt>null</tt> if not set.
	 */
	public String getLocation();

	/**
	 * Sets the location of this book.
	 * 
	 * @return this book for convenience
	 */
	public Book setLocation(String newLocation);

	/**
	 * Returns a String containing the location of an image of this book. This string can be a URL,
	 * but it can also be a path in the filesystem or represent any other means of locating an image.
	 * The actual meaning of this string is application specific.<br>
	 * An image location can be either external or internal, determined when the location is set.
	 * Whether the location is external can be queried with {@link #isExternalImage()}.
	 * <p>
	 * Returns <tt>null</tt> if the image location is not set.
	 */
	public String getImageLocation();

	/**
	 * Sets the image location for this book.
	 * 
	 * @param newImageLoc
	 *           the new location
	 * @param isExternal
	 *           <tt>true</tt> if this location somehow refers a resource outside of this
	 *           application, see {@link #isExternalImage()}
	 * @see #getImageLocation()
	 * @return this book for convenience
	 */
	public Book setImageLocation(String newImageLoc, boolean isExternal);

	/**
	 * Determines whether the image location refers to an external image, like an image somewhere on
	 * the web.
	 * 
	 * @return <tt>true</tt> if the image is located outside of the application, or <tt>false</tt> if
	 *         the image location somehow refers an object inside, like a URN
	 */
	public boolean isExternalImage();

	/**
	 * Returns the ISBN-<b>10</b> of this book, or <tt>null</tt> if not set.
	 */
	public String getISBN10();

	/**
	 * Sets the ISBN-<b>10</b> of this book.
	 * 
	 * @return this book for convenience
	 */
	public Book setISBN10(String newISBN);

	/**
	 * Returns the ISBN-<b>13</b> of this book, or <tt>null</tt> if not set.
	 */
	public String getISBN13();

	/**
	 * Sets the ISBN-<b>13</b> of this book.
	 * 
	 * @return this book for convenience
	 */
	public Book setISBN13(String newISBN);

}
