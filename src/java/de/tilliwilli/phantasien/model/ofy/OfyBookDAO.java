package de.tilliwilli.phantasien.model.ofy;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.googlecode.objectify.cmd.LoadType;
import com.googlecode.objectify.cmd.Query;

import de.tilliwilli.phantasien.model.Book;
import de.tilliwilli.phantasien.model.BookDAO;
import de.tilliwilli.phantasien.model.Category;
import de.tilliwilli.phantasien.model.ReadState;
import de.tilliwilli.phantasien.model.User;

class OfyBookDAO implements BookDAO {

	private OfyUser user;

	@Override
	public void setUser(User user) {
		checkArgument(user instanceof OfyUser);
		this.user = (OfyUser) user;
	}

	@Override
	public Optional<Book> byId(String id) {
		checkUser();
		Long idL = Long.parseLong(id);
		Book book = book().parent(user).id(idL).get();
		return Optional.fromNullable(book);
	}

	@Override
	public Collection<Book> byCategory(Category cat) {
		checkNotNull(cat);
		// make sure that the category belongs to the same user as this DAO, if there is one
		checkArgument(user == null || cat.getUser().equals(user));
		List<OfyBook> books = book().ancestor(cat.getUser()).filter("categories", cat).list();
		return Collections.<Book>unmodifiableCollection(books);
	}

	@Override
	public Collection<Book> allFromUser() {
		List<OfyBook> books = withUser().list();
		return Collections.<Book>unmodifiableCollection(books);
	}

	@Override
	public Collection<Book> allByAuthor(String author) {
		checkArgument(!Strings.isNullOrEmpty(author));
		return filter("author", author);
	}

	@Override
	public Collection<Book> allByReadState(ReadState state) {
		checkNotNull(state);
		return filter("readState", state);
	}

	@Override
	public Collection<Book> findByTitle(String title) {
		return findByTitle(title, true);
	}

	@Override
	public Collection<Book> findByTitle(String title, boolean withSubtitles) {
		Collection<Book> titles = filter("title", title);
		if (!withSubtitles) {
			return titles;
		}

		Collection<Book> subtitles = filter("subtitle", title);
		Set<Book> all = Sets.newHashSet(titles);
		all.addAll(subtitles);
		return Collections.unmodifiableSet(all);
	}

	@Override
	public Collection<Book> findByLocation(String location) {
		return filter("location", location);
	}

	/**
	 * Returns a {@link Query} that already has the type set to {@link OfyBook} and the ancestor set
	 * to the user of this DAO.
	 */
	private Query<OfyBook> withUser() {
		checkUser();
		return book().ancestor(user);
	}

	/**
	 * Returns a {@link Query} that already has the type set to {@link OfyBook}.
	 */
	private LoadType<OfyBook> book() {
		return ofy().load().type(OfyBook.class);
	}

	/**
	 * Returns an immutable collection containg all books of the user of this DAO, filtered according
	 * to the given condition and value.
	 */
	private Collection<Book> filter(String condition, Object filterTarget) {
		List<OfyBook> books = withUser().filter(condition, filterTarget).list();
		return Collections.<Book>unmodifiableCollection(books);
	}

	/**
	 * Throws an exception if the user of this DAO is not set.
	 */
	private void checkUser() throws IllegalStateException {
		checkState(user != null,
				"Can't call BookDAO methods before the user to operate on is set using setUser().");
	}
}
