package de.tilliwilli.phantasien.model.entities;

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
import de.tilliwilli.phantasien.model.entities.base.ActiveRecord;

/**
 * Associations link {@link User Users} to {@link Book Books}. For each Book a User owns (or is
 * connected to in any other way) there has to be an Association instance. In the datastore,
 * Associations are child elements of the Users they belong to. <br>
 * This class has access to both it's owning User and the Book it describes. It has fields
 * containing the {@link ReadState} of the Book for that user, and the {@link Category Categories}
 * that this User has assigned to the book.
 */
@Entity
@Cache
public class Association extends ActiveRecord<Association> {

	/**
	 * The ID of this association in the datastore. Auto-generated.
	 */
	@Id
	private Long id;

	@Parent
	@Load
	private Ref<User> user;

	@Load
	private Ref<Book> book;

	private Set<Ref<Category>> categories;

	@Getter
	@Setter
	private ReadState state;

	/**
	 * Private no-arg constructor for Objectify.
	 */
	@SuppressWarnings("unused")
	private Association() {}

	/**
	 * Creates a new Association instance. Needs the book and the user, as these are final after
	 * creation.<br>
	 * State is set to NOT_OWNED, and categories are empty.
	 */
	public Association(User user, Book book) {
		checkNotNull(user);
		checkNotNull(book);

		this.user = Ref.create(user.getKey(), user);
		this.book = Ref.create(book.getKey(), book);

		this.categories = Sets.newHashSet();
		state = ReadState.NOT_OWNED;
	}

	@Override
	public Key<Association> getKey() {
		return Key.create(user.key(), Association.class, id);
	}

	/**
	 * Returns a {@link Collection} of all {@link Category Categories} that are assigned within this
	 * association.
	 */
	public Collection<Category> getCategories() {
		Map<Key<Category>, Category> map = ofy().load().refs(categories);
		return map.values();
	}

	/**
	 * Adds a category to the list of categories of this association. A new {@link Ref} with the
	 * {@link Key} of the category and the category itself as the ref value is created.
	 */
	public void addCategory(Category cat) {
		checkNotNull(cat);
		categories.add(Ref.create(cat.getKey(), cat));
	}

	public void removeCategory(Category cat) {
		Key<Category> catKey = cat.getKey();

		Iterator<Ref<Category>> it = categories.iterator();
		while (it.hasNext()) {
			Ref<Category> ref = it.next();
			if (catKey.equals(ref.key())) {
				it.remove();
				break;
			}
		}
	}

	/**
	 * Returns the Book that is described by this association.
	 * 
	 * @return the actual {@link Book} instance, not the {@link Ref}
	 */
	public Book getBook() {
		return book.get();
	}

	/**
	 * Returns the User that owns this association.
	 * 
	 * @return the actual {@link User} instance, not the {@link Ref}
	 */
	public User getUser() {
		return user.get();
	}
}
