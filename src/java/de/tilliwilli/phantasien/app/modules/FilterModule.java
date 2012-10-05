package de.tilliwilli.phantasien.app.modules;

import static com.google.common.base.Preconditions.checkState;

import javax.servlet.Filter;
import javax.servlet.http.HttpSession;

import com.google.inject.Provides;
import com.google.inject.servlet.ServletModule;
import com.googlecode.objectify.ObjectifyFilter;

import de.tilliwilli.phantasien.filters.CharacterEncodingFilter;
import de.tilliwilli.phantasien.filters.GaeUserFilter;
import de.tilliwilli.phantasien.filters.HiddenHttpMethodFilter;
import de.tilliwilli.phantasien.filters.UserFilter;
import de.tilliwilli.phantasien.model.BookDAO;
import de.tilliwilli.phantasien.model.User;
import de.tilliwilli.phantasien.providers.SessionUser;

/**
 * Simple {@link ServletModule} that sets up all {@link Filter}s needed for our application.
 */
public class FilterModule extends ServletModule {

	@Override
	protected void configureServlets() {
		// CharacterEncodingFilter defaults to use UTF-8, which is what we want
		filter("/*").through(CharacterEncodingFilter.class);

		// automatically clean up the ObjectifyService after usage
		filter("/*").through(ObjectifyFilter.class);

		// make sure to filter through GaeUserFilter _before_ UserFilter
		filter("/*").through(GaeUserFilter.class);
		filter("/*").through(UserFilter.class);

		// automatically change the request method when the associated field is present in a form
		filter("/*").through(HiddenHttpMethodFilter.class);
	}

	/**
	 * Simple providing method for {@link BookDAO}s annotated with @SessionUser. Takes a normal
	 * BookDAO and sets it's user to the session user set in {@link UserFilter}.
	 */
	@Provides
	@SessionUser
	BookDAO provideSessionUserBookDAO(BookDAO bookDAO, HttpSession session) {
		User user = (User) session.getAttribute(UserFilter.SESSION_ATTRIBUTE);
		checkState(user != null,
				"Can't inject @SessionUser BookDAO when the user is not registered in!");
		bookDAO.setUser(user);
		return bookDAO;
	}
}
