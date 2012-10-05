package de.tilliwilli.phantasien.model.ofy;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Map;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Cache;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import de.tilliwilli.phantasien.model.User;

/**
 * Objectify-compliant implementation of a {@link User}.
 */
@Entity
@Cache
class OfyUser implements User, OfyBaseEntity<OfyUser> {

	/**
	 * The ID in the datastore. For Users, this is set to the ID of the
	 * {@link com.google.appengine.api.users.User GoogleUser}, which is said to be unique and stable
	 * even if users change their e-mail or other stuff.
	 */
	@Id
	private String id;

	private Map<String, Object> settings = Maps.newHashMap();

	/**
	 * Flag indicating that this user's profile should be publicly accessible.
	 */
	private boolean publicProfile;

	/**
	 * Private no-arg constructor for Objectify.
	 */
	@SuppressWarnings("unused")
	private OfyUser() {}

	/**
	 * Creates a new User based on a {@link com.google.appengine.api.users.User GAE user}.
	 */
	public OfyUser(com.google.appengine.api.users.User googleUser) {
		checkNotNull(googleUser);
		this.id = googleUser.getUserId();
	}

	@Override
	public Key<OfyUser> getKey() {
		return Key.create(OfyUser.class, id);
	}

	@Override
	public String getId() {
		return id;
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
	public Optional<Object> get(String name) {
		checkArgument(!Strings.isNullOrEmpty(name));
		return Optional.fromNullable(settings.get(name));
	}

	@Override
	public User set(String name, Object value) {
		checkArgument(!Strings.isNullOrEmpty(name));
		checkNotNull(value);
		settings.put(name, value);
		return this;
	}

	@Override
	public Map<String, Object> getAllSettings() {
		return ImmutableMap.copyOf(settings);
	}

	@Override
	public boolean isPublicProfile() {
		return publicProfile;
	}

	@Override
	public User setPublicProfile(boolean publicProfile) {
		this.publicProfile = publicProfile;
		return this;
	}

}
