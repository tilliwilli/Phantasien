package de.tilliwilli.phantasien.model.entities;

import lombok.Getter;
import lombok.Setter;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import de.tilliwilli.phantasien.model.entities.base.ActiveRecord;

/**
 * @author Tilman
 */

@Entity
@Cache
public class Book extends ActiveRecord<Book> {

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
	public Key<Book> getKey() {
		return Key.create(Book.class, id);
	}

}
