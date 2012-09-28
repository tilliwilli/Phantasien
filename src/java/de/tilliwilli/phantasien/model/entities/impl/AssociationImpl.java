package de.tilliwilli.phantasien.model.entities.impl;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

import com.google.common.collect.Sets;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;
import com.googlecode.objectify.annotation.Parent;

import de.tilliwilli.phantasien.model.ReadState;
import de.tilliwilli.phantasien.model.entities.Association;

/**
 * Associations link {@link UserImpl Users} to {@link BookImpl Books}. For each BookImpl a UserImpl owns (or is
 * connected to in any other way) there has to be an AssociationImpl instance. In the datastore,
 * Associations are child elements of the Users they belong to. <br>
 * This class has access to both it's owning UserImpl and the BookImpl it describes. It has fields
 * containing the {@link ReadState} of the BookImpl for that user, and the {@link CategoryImpl Categories}
 * that this UserImpl has assigned to the book.
 */
@Entity
@Cache
public class AssociationImpl implements Association {

	/**
	 * The ID of this association in the datastore. Auto-generated.
	 */
	@Id
	private Long id;

	@Parent
	@Load
	private Ref<UserImpl> user;

	@Load
	private Ref<BookImpl> book;

	private Set<Ref<CategoryImpl>> categories;

	@Getter
	@Setter
	private ReadState state;

	/**
	 * Private no-arg constructor for Objectify.
	 */
	@SuppressWarnings("unused")
	private AssociationImpl() {}

	/**
	 * Creates a new AssociationImpl instance. Needs the book and the user, as these are final after
	 * creation.<br>
	 * State is set to NOT_OWNED, and categories are empty.
	 */
	public AssociationImpl(UserImpl user, BookImpl book) {
		checkNotNull(user);
		checkNotNull(book);

		this.user = Ref.create(user.getKey(), user);
		this.book = Ref.create(book.getKey(), book);

		this.categories = Sets.newHashSet();
		state = ReadState.NOT_OWNED;
	}

	public Key<AssociationImpl> getKey() {
		return Key.create(user.key(), AssociationImpl.class, id);
	}

	/**
	 * Returns a {@link Collection} of all {@link CategoryImpl Categories} that are assigned within this
	 * association.
	 */
	public Collection<CategoryImpl> getCategories() {
		Map<Key<CategoryImpl>, CategoryImpl> map = ofy().load().refs(categories);
		return map.values();
	}

	/**
	 * Adds a category to the list of categories of this association. A new {@link Ref} with the
	 * {@link Key} of the category and the category itself as the ref value is created.
	 */
	public void addCategory(CategoryImpl cat) {
		checkNotNull(cat);
		categories.add(Ref.create(cat.getKey(), cat));
	}

	public void removeCategory(CategoryImpl cat) {
		Key<CategoryImpl> catKey = cat.getKey();

		Iterator<Ref<CategoryImpl>> it = categories.iterator();
		while (it.hasNext()) {
			Ref<CategoryImpl> ref = it.next();
			if (catKey.equals(ref.key())) {
				it.remove();
				break;
			}
		}
	}

	/**
	 * Returns the BookImpl that is described by this association.
	 * 
	 * @return the actual {@link BookImpl} instance, not the {@link Ref}
	 */
	public BookImpl getBook() {
		return book.get();
	}

	/**
	 * Returns the UserImpl that owns this association.
	 * 
	 * @return the actual {@link UserImpl} instance, not the {@link Ref}
	 */
	public UserImpl getUser() {
		return user.get();
	}
}
