package de.tilliwilli.phantasien.model.ofy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.tilliwilli.phantasien.model.BookDAO;
import de.tilliwilli.phantasien.model.CategoryDAO;
import de.tilliwilli.phantasien.model.UserDAO;

@Configuration
public class ObjectifyConfiguration {

	@Bean
	public UserDAO userDao() {
		return new OfyUserDAO();
	}

	@Bean
	public BookDAO bookDao() {
		return new OfyBookDAO();
	}

	@Bean
	public CategoryDAO categoryDao() {
		return new OfyCategoryDAO();
	}

}
