package com.kablamo.capi.graphqlpoc;

import com.kablamo.capi.sling.models.BigHeaderModel;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BigHeader {
	Logger logger = LoggerFactory.getLogger(BigHeader.class);

	private BigHeaderModel bigHeaderModel;

	ResourceResolver resourceResolver;
	Resource resource;

	public BigHeader() {
		this.bigHeaderModel = fetchModel();
	}

	public String getTitle() {
		return bigHeaderModel.getTitle();
	}

	public String getSubtitle() {
		return bigHeaderModel.getSubtitle();
	}

	private BigHeaderModel fetchModel() {
		BigHeaderModel bigHeaderModel = null;

		try {
			resourceResolver = getResourceResolverFactory().getAdministrativeResourceResolver(null);
			resource = resourceResolver.getResource("/content/capi-poc/kablamo/en/homepage/jcr:content/big_header");

			bigHeaderModel = resource.adaptTo(BigHeaderModel.class);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		finally{
			if(resourceResolver.isLive())
				resourceResolver.close();
		}

		return bigHeaderModel;
	}

	private static ResourceResolverFactory getResourceResolverFactory() {
		ResourceResolverFactory resourceResolverFactory = null;
		BundleContext bundleContext = FrameworkUtil.getBundle(ResourceResolverFactory.class).getBundleContext();
		ServiceReference factoryRef = bundleContext.getServiceReference(ResourceResolverFactory.class.getName());
		resourceResolverFactory =  (ResourceResolverFactory)bundleContext.getService(factoryRef);

		return resourceResolverFactory;
	}
}
