package de.tilliwilli.phantasien.providers;

import static com.google.common.base.Preconditions.checkState;

import javax.servlet.http.HttpSession;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import de.tilliwilli.phantasien.app.Constants;
import de.tilliwilli.phantasien.model.BookDAO;
import de.tilliwilli.phantasien.model.CategoryDAO;
import de.tilliwilli.phantasien.model.User;
import de.tilliwilli.phantasien.providers.annotations.FromSession;
import de.tilliwilli.phantasien.web.filters.UserFilter;

public class ProvidersModule extends AbstractModule implements Constants {

	@Override
	protected void configure() {}

	/**
	 * Simple providing method for {@link BookDAO}s annotated with {@link FromSession}. Takes a
	 * normal BookDAO and sets it's user to the session user set in {@link UserFilter}.
	 */
	@Provides
	@FromSession
	BookDAO provideSessionUserBookDAO(BookDAO bookDAO, HttpSession session) {
		User user = (User) session.getAttribute(USER_SESSION_ATTRIBUTE);
		checkState(user != null,
				"Can't inject session user into BookDAO when the user is not logged in!");
		bookDAO.setUser(user);
		return bookDAO;
	}

	/**
	 * Simple providing method for {@link CategoryDAO}s annotated with {@link FromSession}. Takes a
	 * normal CategoryDAO and sets it's user to the session user set in {@link UserFilter}.
	 */
	@Provides
	@FromSession
	CategoryDAO provideSessionUserCategoryDAO(CategoryDAO catDAO, HttpSession session) {
		User user = (User) session.getAttribute(USER_SESSION_ATTRIBUTE);
		checkState(user != null,
				"Can't inject session user into CategoryDAO when the user is not logged in!");
		catDAO.setUser(user);
		return catDAO;
	}
}
