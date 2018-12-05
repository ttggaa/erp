package com.stosz.store.engine;

import com.stosz.plat.engine.CrosFilter;
import com.stosz.plat.engine.PagingFilter;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {

		return new Class<?>[] { SpringRootConfig.class };
		// return new Class<?>[]{};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		// return new Class<?>[]{};
		return new Class<?>[] { SpringWebMvcConfig.class, StoreProvider.class };
	}

	@Override
	protected String[] getServletMappings() {

		return new String[] { "/" };
	}

	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		return new Filter[] { characterEncodingFilter, new CrosFilter(), new PagingFilter() };
	}

	@Override
	protected void customizeRegistration(Dynamic registration) {
		super.customizeRegistration(registration);
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		servletContext.setInitParameter("spring.profiles.default", "local");
		servletContext.addListener(RequestContextListener.class);
		servletContext.getSessionCookieConfig().setMaxAge(60 * 60 * 12);
		super.onStartup(servletContext);
	}

}