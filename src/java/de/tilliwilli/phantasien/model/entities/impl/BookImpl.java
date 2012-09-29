package de.tilliwilli.phantasien.model.entities.impl;

import lombok.Getter;
import lombok.Setter;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import de.tilliwilli.phantasien.model.entities.Book;

/**
 * Objectify-compliant {@link Book} implementation.
 */
@Entity
@Cache
public class BookImpl implements Book, BaseOfyEntity<BookImpl> {

	/**
	 * The ID of this book in the datastore. Auto-generated.
	 */
	@Id
	private Long id;

	@Getter
	@Setter
	private String title;

	@Getter
	@Setter
	private String author;

	@Getter
	@Setter
	private int pages;

	@Getter
	@Setter
	private String ISBN_10;

	@Getter
	@Setter
	private String ISBN_13;

	@Getter
	@Setter
	private String imageURL;

	@Override
	public Key<BookImpl> getKey() {
		return Key.create(BookImpl.class, id);
	}

}
