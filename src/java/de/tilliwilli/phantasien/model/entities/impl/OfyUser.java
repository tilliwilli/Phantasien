package de.tilliwilli.phantasien.model.entities.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
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
public class OfyUser implements User, BaseOfyEntity<OfyUser> {

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
	private OfyUser() {}

	/**
	 * Creates a new User based on a {@link com.google.appengine.api.users.User GAE user}.
	 */
	public OfyUser(com.google.appengine.api.users.User googleUser) {
		checkNotNull(googleUser);
		this.id = googleUser.getUserId();
	}

	@Override
	public Key<OfyUser> getKey() {
		return Key.create(OfyUser.class, id);
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
		Key<OfyBook> key = Key.create(this.getKey(), OfyBook.class, idL);
		return ofy().load().key(key).get();
	}

	@Override
	public Collection<Book> getAllBooks() {
		List<OfyBook> books = ofy().load().type(OfyBook.class).ancestor(this).list();
		return Collections.<Book>unmodifiableCollection(books);
	}

	@Override
	public Book createBook() {
		return new OfyBook(this);
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
		Key<OfyCategory> key = Key.create(this.getKey(), OfyCategory.class, idL);
		return ofy().load().key(key).get();
	}

	@Override
	public Collection<Category> getAllCategories() {
		List<OfyCategory> cats = ofy().load().type(OfyCategory.class).ancestor(this).list();
		return Collections.<Category>unmodifiableCollection(cats);
	}

	@Override
	public Category createCategory() {
		return new OfyCategory(this);
	}

}
