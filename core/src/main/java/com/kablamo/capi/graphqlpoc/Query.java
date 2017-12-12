package com.kablamo.capi.graphqlpoc;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;

import java.util.List;

public class Query implements GraphQLQueryResolver {
	
	private final LinkRepository linkRepository;
	private final KabHomepage kabHomepage;
	
	public Query(LinkRepository linkRepository, KabHomepage kabHomepage) {
		this.linkRepository = linkRepository;
		this.kabHomepage = kabHomepage;
	}
	
	public List<Link> allLinks() {
		return linkRepository.getAllLinks();
	}
	
	public KabHomepage kabHomepage() {
		return this.kabHomepage;
	}
}
