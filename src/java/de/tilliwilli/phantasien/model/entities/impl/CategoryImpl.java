package de.tilliwilli.phantasien.model.entities.impl;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

import de.tilliwilli.phantasien.model.entities.Category;
import de.tilliwilli.phantasien.model.entities.User;

/**
 * Objectify-compliant {@link Category} implementation
 */
@Entity
@Cache
public class CategoryImpl implements Category, BaseOfyEntity<CategoryImpl> {

	/**
	 * The owner of the category. This relation is final after creation, and is enforced in the
	 * datastore.
	 */
	@Parent
	private Ref<UserImpl> owner;

	/**
	 * The ID of this category in the datastore. Auto-generated.
	 */
	@Id
	private Long id;

	/**
	 * The name of the category. This is a simple {@link String} that can be changed.
	 */
	private String name;

	// Private no-arg constructor for Objectify.
	@SuppressWarnings("unused")
	private CategoryImpl() {}

	/**
	 * Creates a new CategoryImpl instance with an owner and a name. The owner must be provided and
	 * is final after creation.
	 * 
	 * @param owner
	 *           the {@link UserImpl user} this category belongs to.
	 */
	CategoryImpl(UserImpl owner) {
		checkNotNull(owner);
		this.owner = Ref.create(owner.getKey(), owner);
	}

	CategoryImpl(Key<UserImpl> ownerKey) {
		checkNotNull(ownerKey);
		this.owner = Ref.create(ownerKey);
	}

	@Override
	public Key<CategoryImpl> getKey() {
		return Key.create(owner.getKey(), CategoryImpl.class, id);
	}

	@Override
	public String getId() {
		if (id == null) { return null; }
		return id.toString();
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
	public User getUser() {
		return owner.get();
	}

	@Override
	public Iterable<BookImpl> getBooks() {
		//@formatter:off
		Iterable<BookImpl> books = ofy().load()
			.type(BookImpl.class)
			.ancestor(owner)
			.filter("categories", this)
			.iterable();
		//@formatter:on

		return Iterables.unmodifiableIterable(books);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String setName(String newName) {
		String oldName = Strings.emptyToNull(name);
		this.name = Strings.emptyToNull(newName);
		return oldName;
	}
}
