package com.kablamo.capi.sling.component;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(
name="SlingGraphQLComponent",
immediate=true)
@Service(SlingGraphQLComponent.class)
public class SlingGraphQLComponent {
	Logger logger = LoggerFactory.getLogger(SlingGraphQLComponent.class);
	private String hello;
	
	public SlingGraphQLComponent() {
		this.hello = "Hello Foo";
	}
	
	@Activate
	private void onActivate(ComponentContext context) {
		logger.debug("onActivate SlingGraphQLComponent");
		this.hello = "Hello Foo Activated";
	}
	
	public String getHello() {
		return hello;
	}
}
