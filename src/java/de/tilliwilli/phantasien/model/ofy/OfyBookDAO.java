package de.tilliwilli.phantasien.model.ofy;

import java.util.Collection;

import de.tilliwilli.phantasien.model.Book;
import de.tilliwilli.phantasien.model.BookDAO;
import de.tilliwilli.phantasien.model.Category;
import de.tilliwilli.phantasien.model.ReadState;
import de.tilliwilli.phantasien.model.User;

class OfyBookDAO implements BookDAO {

	@Override
	public void setUser(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public Book byId(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Book> byCategory(Category cat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Book> allFromUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Book> allByAuthor(String author) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Book> allByReadState(ReadState state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Book> findByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Book> findByTitle(String title, boolean withSubtitles) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Book> findByLocation(String location) {
		// TODO Auto-generated method stub
		return null;
	}

}
