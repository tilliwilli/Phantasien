package de.tilliwilli.phantasien.model;

import de.tilliwilli.phantasien.model.entities.Book;
import de.tilliwilli.phantasien.model.entities.User;

/**
 * Different values of the ReadState enum define the kind of relation a {@link User} has towards a
 * {@link Book}.<br>
 * The following values are possible:
 * <ul>
 * <li>{@link #NOT_OWNED}</li>
 * <li>{@link #NOT_OWNED_BUT_READ}</li>
 * <li>{@link #NOT_READ}</li>
 * <li>{@link #READ}</li>
 * <li>{@link #FAVORITE}</li>
 * </ul>
 */
public enum ReadState {

	/**
	 * The user does not own the book at all and has not read it.
	 */
	NOT_OWNED,

	/**
	 * The user does not own the book but has read it (e.g. he has borrowed it from someone).
	 */
	NOT_OWNED_BUT_READ,

	/**
	 * The user owns the book, but has not read it yet.
	 */
	NOT_READ,

	/**
	 * The user owns the book and has read it.
	 */
	READ,

	/**
	 * The user has {@link #READ read} the book and marked it as one of his favorites.
	 */
	FAVORITE;

}
