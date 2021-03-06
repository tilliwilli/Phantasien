package de.tilliwilli.phantasien.model.ofy;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.common.base.Strings;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

import de.tilliwilli.phantasien.model.Category;
import de.tilliwilli.phantasien.model.User;

/**
 * Objectify-compliant {@link Category} implementation.
 */
@Entity
@Cache
class OfyCategory implements Category, OfyBaseEntity<OfyCategory> {

	/**
	 * The owner of the category. This relation is final after creation, and is enforced in the
	 * datastore.
	 */
	@Parent
	private Ref<OfyUser> owner;

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
	private OfyCategory() {}

	/**
	 * Creates a new OfyCategory instance with an owner and a name. The owner must be provided and is
	 * final after creation.
	 * 
	 * @param owner
	 *           the {@link OfyUser user} this category belongs to.
	 */
	OfyCategory(OfyUser owner) {
		checkNotNull(owner);
		this.owner = Ref.create(owner.getKey());
	}

	OfyCategory(Key<OfyUser> ownerKey) {
		checkNotNull(ownerKey);
		this.owner = Ref.create(ownerKey);
	}

	@Override
	public Key<OfyCategory> getKey() {
		return Key.create(owner.getKey(), OfyCategory.class, id);
	}

	@Override
	public String getId() {
		if (id == null) {
			return null;
		}
		return id.toString();
	}

	@Override
	public void save() {
		checkState(!Strings.isNullOrEmpty(name));
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
	public String getName() {
		return name;
	}

	@Override
	public String setName(String newName) {
		checkArgument(!Strings.isNullOrEmpty(newName));
		String oldName = name;
		this.name = newName;
		return oldName;
	}
}
