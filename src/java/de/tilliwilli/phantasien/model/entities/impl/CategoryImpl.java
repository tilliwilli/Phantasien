package de.tilliwilli.phantasien.model.entities.impl;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Collection;

import lombok.Getter;
import lombok.Setter;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Parent;

import de.tilliwilli.phantasien.model.entities.Category;

/**
 * Categories are <em>tags</em> a user can apply to books. Each category entity is owned by the user
 * who created it, and only that user can assign this category to his books. Categories are used by
 * {@link AssociationImpl Associations}.
 * <p>
 * A CategoryImpl a {@link #name} that is a simple String, and an {@link #owner}. The owner is final
 * after creation, but the name can be changed.
 */
@Entity
@Cache
public class CategoryImpl implements Category, BaseOfyEntity<CategoryImpl> {

	/**
	 * The owner of the category. This relation is final after creation, and is enforced in the
	 * datastore.
	 */
	@Parent
	@Getter
	private Ref<UserImpl> owner;

	/**
	 * The ID of this category in the datastore. Auto-generated.
	 */
	@Id
	private Long id;

	/**
	 * The name of the category. This is a simple {@link String} that can be changed.
	 */
	@Getter
	@Setter
	private String name;

	/**
	 * Private no-arg constructor for Objectify.
	 */
	@SuppressWarnings("unused")
	private CategoryImpl() {}

	/**
	 * Creates a new CategoryImpl instance with an owner and a name. The owner must be provided and
	 * is final after creation.
	 */
	public CategoryImpl(UserImpl owner, String name) {
		checkNotNull(owner);
		this.owner = Ref.create(owner.getKey(), owner);
		this.name = name;
	}

	@Override
	public Key<CategoryImpl> getKey() {
		return Key.create(owner.getKey(), CategoryImpl.class, id);
	}

	/**
	 * Returns all book entities (not just references) that are labeled with this category.
	 */
	public Collection<BookImpl> getBooks() {
		return null;
	}

	/**
	 * Retrieve all Categories of the given user.<br>
	 * <code>user</code> must be a {@link Key} or an {@link Entity}.
	 */

}
