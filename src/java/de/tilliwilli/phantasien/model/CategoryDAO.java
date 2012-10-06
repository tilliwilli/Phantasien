package de.tilliwilli.phantasien.model;

import com.google.common.base.Optional;

public interface CategoryDAO {

	public void setUser(User user);

	public Optional<Category> byId(String id);

}
