package com.kablamo.capi.sling.filter;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.sling.SlingFilter;
import org.apache.felix.scr.annotations.sling.SlingFilterScope;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Map;

@SlingFilter(
	label = "CAPI CORS Filter - Enable CORS for Sling requests",
	metatype = true, // Required for config to appear in configMgr
	description = "Allow third party requests",
	generateComponent = true,
	generateService = true,
	order = 0,
	scope = SlingFilterScope.REQUEST) // REQUEST, INCLUDE, FORWARD, ERROR, COMPONENT (REQUEST, INCLUDE, COMPONENT)
@Properties(value = {
	@Property(name = "sling.name.externalDomain", description = "The allowed external domain. E.g. https://test.com")
})
public class CorsFilter implements Filter {
	private static final Logger log = LoggerFactory.getLogger(CorsFilter.class);
	
	private String allowedDomain;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.debug("Initialising CorsFilter");
	}
	
	@Activate
	public void activate(final ComponentContext componentContext) throws Exception {
		final Map<String, String> properties = (Map<String, String>) componentContext.getProperties();
		this.allowedDomain = properties.get("sling.name.externalDomain");
	}
	
	@Override
	public final void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
																																																				 ServletException {
		final SlingHttpServletResponse slingResponse = (SlingHttpServletResponse) response;
		final SlingHttpServletRequest slingRequest = (SlingHttpServletRequest) request;
		final Resource resource = slingRequest.getResource();
		
		if(this.allowedDomain != ""){
			slingResponse.setHeader("Access-Control-Allow-Origin", this.allowedDomain);
			slingResponse.setHeader("Access-Control-Allow-Credentials", "true");
		}
		
		chain.doFilter(request, response);
	}
	
	@Override
	public void destroy() {
		log.debug("Destroying CorsFilter");
	}
}
