package com.kablamo.capi.sling.servlets;

import com.kablamo.capi.graphqlpoc.BigHeader;
import com.kablamo.capi.graphqlpoc.KabHomepage;
import com.kablamo.capi.sling.models.BigHeaderModel;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import java.io.IOException;


@SlingServlet(paths="/bin/slingmodel", methods="GET")
public class SlingModelTest extends SlingAllMethodsServlet{
	private static final long serialVersionUID = 1L;
	Logger logger = LoggerFactory.getLogger(SlingModelTest.class);
	
	@Reference
	ResourceResolverFactory resourceResolverFactory;
	ResourceResolver resourceResolver;

	public void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)throws ServletException,IOException{
		logger.info("inside sling model test servlet");
		response.setContentType("text/html");
		try {
			resourceResolver = resourceResolverFactory.getAdministrativeResourceResolver(null);
			Resource resource = resourceResolver.getResource("/content/capi-poc/kablamo/en/homepage/jcr:content/big_header");
			BigHeaderModel bigHeaderModel = resource.adaptTo(BigHeaderModel.class);

			KabHomepage kabHomepage = new KabHomepage();

			response.getWriter().write("Output from Sling Model is [title]: "+ bigHeaderModel.getTitle() +" [subtitle]: "+ bigHeaderModel.getSubtitle() + ", From Service ["+ kabHomepage.getBigHeader().getTitle() +"]");
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		finally{
			if(resourceResolver.isLive())
				resourceResolver.close();
		}
		
		
	}
	
}
