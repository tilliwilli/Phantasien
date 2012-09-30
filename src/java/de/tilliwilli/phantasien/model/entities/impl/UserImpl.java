package de.tilliwilli.phantasien.model.entities.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Map;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import de.tilliwilli.phantasien.model.entities.Book;
import de.tilliwilli.phantasien.model.entities.Category;
import de.tilliwilli.phantasien.model.entities.User;

/**
 * Objectify-Implementation of a {@link User}.
 */
@Entity
@Cache
public class UserImpl implements User, BaseOfyEntity<UserImpl> {

	/**
	 * The ID in the datastore. For Users, this is set to the ID of the
	 * {@link com.google.appengine.api.users.User GoogleUser}, which is said to be unique and stable
	 * even if users change their e-mail or other stuff.
	 */
	@Id
	private String id;

	private Map<String, String> settings = Maps.newHashMap();

	/**
	 * Private no-arg constructor for Objectify.
	 */
	@SuppressWarnings("unused")
	private UserImpl() {}

	/**
	 * Creates a new User based on a {@link com.google.appengine.api.users.User GAE user}.
	 */
	public UserImpl(com.google.appengine.api.users.User googleUser) {
		checkNotNull(googleUser);
		this.id = googleUser.getUserId();
	}

	@Override
	public Key<UserImpl> getKey() {
		return Key.create(UserImpl.class, id);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void save() {
		ofy().save().entity(this).now();
	}

	@Override
	public void remove() {
		ofy().delete().entity(this).now();
	}

	@Override
	public String getSetting(String name) {
		checkArgument(!Strings.isNullOrEmpty(name));
		return settings.get(name);
	}

	@Override
	public String setSetting(String name, String value) {
		checkArgument(!Strings.isNullOrEmpty(name));
		return settings.put(name, value);
	}

	@Override
	public Map<String, String> getAllSettings() {
		return ImmutableMap.copyOf(settings);
	}

	@Override
	public Book getBook(String id) {
		checkNotNull(id);
		Long idL = null;
		try {
			idL = Long.valueOf(id);
		} catch (NumberFormatException e) {
			return null;
		}
		Key<BookImpl> key = Key.create(this.getKey(), BookImpl.class, idL);
		return ofy().load().key(key).get();
	}

	@Override
	public Iterable<BookImpl> getAllBooks() {
		Iterable<BookImpl> books = ofy().load().type(BookImpl.class).ancestor(this).iterable();
		return Iterables.unmodifiableIterable(books);
	}

	@Override
	public Book createBook() {
		return new BookImpl(this);
	}

	@Override
	public Category getCategory(String id) {
		checkNotNull(id);
		Long idL = null;
		try {
			idL = Long.valueOf(id);
		} catch (NumberFormatException e) {
			return null;
		}
		Key<CategoryImpl> key = Key.create(this.getKey(), CategoryImpl.class, idL);
		return ofy().load().key(key).get();
	}

	@Override
	public Iterable<? extends Category> getAllCategories() {
		Iterable<CategoryImpl> cats = ofy().load().type(CategoryImpl.class).ancestor(this).iterable();
		return Iterables.unmodifiableIterable(cats);
	}

	@Override
	public Category createCategory() {
		return new CategoryImpl(this);
	}

}
