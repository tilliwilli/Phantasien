package de.tilliwilli.phantasien;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import lombok.extern.apachecommons.CommonsLog;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;

import com.googlecode.objectify.ObjectifyService;

/**
 * @author Tilman
 */
@CommonsLog
public class EntityScanner implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		log.info("Scanning for Entities to objectify.");

		StopWatch watch = new StopWatch();
		watch.start();

		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		String packages = "de.tilliwilli.phantasien";

		classes.addAll(doScan(packages));

		for (Class<?> clazz : classes) {
			ObjectifyService.register(clazz);
			log.info("Registered entity class [" + clazz.getName() + "]");
		}

		watch.stop();
		long millis = watch.getLastTaskTimeMillis();
		log.info("Registering of Entities completed in " + millis + " ms.");
	}

	protected List<Class<?>> doScan(String packages) {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		String[] basePackages = StringUtils.tokenizeToStringArray(packages,
			ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
		for (String basePackage : basePackages) {
			if (log.isInfoEnabled()) {
				log.info("Scanning package [" + basePackage + "]");
			}
			ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(
					false);
			scanner.addIncludeFilter(new AnnotationTypeFilter(
					com.googlecode.objectify.annotation.Entity.class));
			Set<BeanDefinition> candidates = scanner.findCandidateComponents(basePackage);
			for (BeanDefinition candidate : candidates) {
				Class<?> clazz = ClassUtils.resolveClassName(candidate.getBeanClassName(),
					ClassUtils.getDefaultClassLoader());
				classes.add(clazz);
			}
		}
		return classes;
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// do nothing
	}

}
