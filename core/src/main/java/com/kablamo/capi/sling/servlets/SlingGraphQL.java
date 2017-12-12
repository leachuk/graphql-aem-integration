package com.kablamo.capi.sling.servlets;

import com.coxautodev.graphql.tools.SchemaParser;
import com.kablamo.capi.graphqlpoc.KabHomepage;
import com.kablamo.capi.graphqlpoc.LinkRepository;
import com.kablamo.capi.graphqlpoc.Query;
import graphql.schema.GraphQLSchema;
import graphql.servlet.DefaultGraphQLSchemaProvider;
import graphql.servlet.GraphQLSchemaProvider;
import graphql.servlet.OsgiGraphQLServlet;
import org.apache.felix.scr.annotations.sling.SlingServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SlingServlet(paths={"/bin/graphql", "/bin/graphql/schema.json"}, methods="GET")
public class SlingGraphQL extends OsgiGraphQLServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = LoggerFactory.getLogger(SlingGraphQL.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("inside OsgiGraphQLServlet sling model doGet");
		
		String path = request.getPathInfo();
		if (path.contentEquals("/bin/graphql/schema.json")) {
		 	super.doGet(replacePathInfo(request, "/bin/graphql/schema.json", "/schema.json"), response);
		} else {
			super.doGet(request, response);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("inside OsgiGraphQLServlet sling model doPost");
		
		String path = request.getPathInfo();
		if (path.contentEquals("/bin/graphql/schema.json")) {
			super.doPost(replacePathInfo(request, "/bin/graphql/schema.json", "/schema.json"), response);
		} else {
			super.doPost(request, response);
		}
	}
	
	private GraphQLSchema buildSchema() {
		LinkRepository linkRepository = new LinkRepository();
		KabHomepage kabHomepage = new KabHomepage();
		return SchemaParser.newParser()
				.file("schema.graphqls")
				.resolvers(new Query(linkRepository, kabHomepage))
				.build()
				.makeExecutableSchema();
	}

	@Override
	protected GraphQLSchemaProvider getSchemaProvider() {
		logger.debug("inside OsgiGraphQLServlet sling model getSchemaProvider");
		return new DefaultGraphQLSchemaProvider(buildSchema());
	}
	
	private static HttpServletRequestWrapper replacePathInfo(HttpServletRequest request, String regex, String replacement) {
		return new HttpServletRequestWrapper(request) {
			public String getPathInfo() {
				return new String(super.getPathInfo().replaceAll(regex, replacement));
			}
		};
	}
}
