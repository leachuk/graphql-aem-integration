package com.kablamo.capi.sling;

import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.ResourceResolver;

public interface SampleService {

    // PROP_NAME is used by the SampleMultiReferenceServiceImpl
    String PROP_NAME = "service-name";

    String helloWorld();

    void doWork(ResourceResolver resourceResolver) throws PersistenceException;
}
