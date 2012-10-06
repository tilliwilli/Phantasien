package de.tilliwilli.phantasien.providers.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.google.inject.BindingAnnotation;

/**
 * Annotation that can be used to inject an instance with parameters from the user's session.
 */
@BindingAnnotation
@Target({ FIELD, PARAMETER, METHOD })
@Retention(RUNTIME)
public @interface FromSession {

}
