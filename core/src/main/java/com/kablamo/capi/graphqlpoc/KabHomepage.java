package com.kablamo.capi.graphqlpoc;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KabHomepage {
	Logger logger = LoggerFactory.getLogger(KabHomepage.class);

	BigHeader bigHeaderComponent;

	public KabHomepage() {
		logger.debug("constructor KabHomepage");

		this.bigHeaderComponent =  new BigHeader();
	}

	public BigHeader getBigHeader() {
		return bigHeaderComponent;
	}

}
