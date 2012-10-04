package de.tilliwilli.phantasien.model.entities.impl;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

import de.tilliwilli.phantasien.model.ReadState;
import de.tilliwilli.phantasien.model.entities.Book;
import de.tilliwilli.phantasien.model.entities.Category;
import de.tilliwilli.phantasien.model.entities.User;

/**
 * Objectify-compliant {@link Book} implementation.
 */
@Entity
@Cache
public class OfyBook implements Book, BaseOfyEntity<OfyBook> {

	/**
	 * The ID of this book in the datastore. Auto-generated.
	 */
	@Id
	private Long id;

	/**
	 * The {@link OfyUser user} this book belongs to.
	 */
	@Parent
	private Ref<OfyUser> user;

	/**
	 * The {@link Set} of {@link OfyCategory categories} that are assigned to this book.<br>
	 */
	@Index
	private Set<Ref<OfyCategory>> categories = Sets.newHashSet();

	/**
	 * The {@link ReadState} of this book.
	 */
	private ReadState readState = ReadState.NOT_READ;

	/**
	 * The book title.
	 */
	private String title;

	/**
	 * The subtitle of the book.
	 */
	private String subTitle;

	/**
	 * Te book author.
	 */
	private String author;

	/**
	 * The number of pages in this book.
	 */
	private Integer pages;

	/**
	 * The person this book came from, for example if it was a present.
	 */
	private String giver;

	/**
	 * The place where this book is located.
	 */
	private String location;

	/**
	 * The ISBN-10 of this book.
	 */
	private String isbn_10;

	/**
	 * The ISBN-13 of this book.
	 */
	private String isbn_13;

	/**
	 * The path of the image of this book, or the {@link BlobKey#getKeyString() keystring} of a
	 * {@link BlobKey blob} in the BlobStore. Determined by {@link #externalImage}.
	 */
	private String image;

	/**
	 * Determines whether the {@link #image} field refers to a web adress or contains a
	 * {@link BlobKey} keystring.
	 */
	private boolean externalImage;

	// Private no-arg contructor for Objectify.
	@SuppressWarnings("unused")
	private OfyBook() {}

	/**
	 * Creates a new book instance. The owning user is the only necessary parameter, since that
	 * relationship is final after creation.
	 * <p>
	 * Categories are initialized as an empty set, and the readstate is set to
	 * {@link ReadState#NOT_READ}. All other fields are empty.
	 * 
	 * @param owner
	 *           the {@link OfyUser user} this book belongs to
	 */
	OfyBook(OfyUser owner) {
		checkNotNull(owner);
		this.user = Ref.create(owner.getKey(), owner);
	}

	/**
	 * Creates a new book instance. The owning user is the only necessary parameter, since that
	 * relationship is final after creation.
	 * <p>
	 * Categories are initialized as an empty set, and the readstate is set to
	 * {@link ReadState#NOT_READ}. All other fields are empty.
	 * 
	 * @param owner
	 *           the {@link Key key} of the {@link OfyUser user} this book belongs to
	 */
	OfyBook(Key<OfyUser> ownerKey) {
		checkNotNull(ownerKey);
		this.user = Ref.create(ownerKey);
	}

	@Override
	public Key<OfyBook> getKey() {
		return Key.create(user.key(), OfyBook.class, id);
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
		ofy().save().entity(this).now();
	}

	@Override
	public void remove() {
		ofy().delete().entity(this).now();
	}

	@Override
	public User getUser() {
		return user.get();
	}

	@Override
	public Collection<Category> getCategories() {
		Collection<OfyCategory> cats = ofy().load().refs(categories).values();
		return Collections.<Category>unmodifiableCollection(cats);
	}

	@Override
	public Book addCategory(Category category) {
		// up to now, we only support objectify'd CategoryImpls
		checkArgument(category instanceof OfyCategory);
		OfyCategory catImpl = (OfyCategory) category;

		// check that the given category belongs to the same user as we do
		checkArgument(catImpl.getUser().equals(this.getUser()));

		categories.add(Ref.create(catImpl.getKey(), catImpl));
		return this;
	}

	@Override
	public Book removeCategory(Category category) {
		// up to now, we only support objectify'd CategoryImpls
		checkArgument(category instanceof OfyCategory);
		OfyCategory catImpl = (OfyCategory) category;

		// since references compare equality only based on their keys, we create a dummy reference
		// to the given category and remove that from our categories set
		categories.remove(Ref.create(catImpl.getKey()));
		return this;
	}

	@Override
	public boolean hasAssigned(Category category) {
		// up to now, we only support objectify'd CategoryImpls
		checkArgument(category instanceof OfyCategory);
		OfyCategory catImpl = (OfyCategory) category;

		// see #removeCategories
		Ref<OfyCategory> ref = Ref.create(catImpl.getKey());
		return categories.contains(ref);
	}

	@Override
	public ReadState getReadState() {
		return readState;
	}

	@Override
	public Book setReadState(ReadState newState) {
		this.readState = checkNotNull(newState);
		return this;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public Book setTitle(String newTitle) {
		this.title = Strings.emptyToNull(newTitle);
		return this;
	}

	@Override
	public String getAuthor() {
		return author;
	}

	@Override
	public Book setAuthor(String newAuthor) {
		this.author = Strings.emptyToNull(newAuthor);
		return this;
	}

	@Override
	public String getSubTitle() {
		return subTitle;
	}

	@Override
	public Book setSubTitle(String newSubTitle) {
		this.subTitle = Strings.emptyToNull(newSubTitle);
		return this;
	}

	@Override
	public Integer getPages() {
		return pages;
	}

	@Override
	public Book setPages(Integer numberOfPages) {
		if (numberOfPages == null) {
			this.pages = null;
		} else {
			checkArgument(numberOfPages > 0);
			this.pages = numberOfPages;
		}
		return this;
	}

	@Override
	public String getGiver() {
		return giver;
	}

	@Override
	public Book setGiver(String giver) {
		this.giver = Strings.emptyToNull(giver);
		return this;
	}

	@Override
	public String getLocation() {
		return location;
	}

	@Override
	public Book setLocation(String newLocation) {
		this.location = Strings.emptyToNull(newLocation);
		return this;
	}

	/**
	 * Returns a String containing the image location as a URL, or as a {@link BlobKey} keystring.
	 */
	@Override
	public String getImageLocation() {
		return image;
	}

	@Override
	public boolean isExternalImage() {
		return externalImage;
	}

	@Override
	public Book setImageLocation(String newImageLoc, boolean isExternal) {
		this.image = Strings.emptyToNull(newImageLoc);
		if (image != null) {
			this.externalImage = isExternal;
		} else {
			this.externalImage = false;
		}
		return this;
	}

	@Override
	public String getISBN10() {
		return isbn_10;
	}

	@Override
	public Book setISBN10(String newISBN) {
		checkArgument(newISBN == null || newISBN.length() == 10);
		this.isbn_10 = Strings.emptyToNull(newISBN);
		return this;
	}

	@Override
	public String getISBN13() {
		return isbn_13;
	}

	@Override
	public Book setISBN13(String newISBN) {
		checkArgument(newISBN == null || newISBN.length() == 13);
		this.isbn_13 = Strings.emptyToNull(newISBN);
		return this;
	}
}
