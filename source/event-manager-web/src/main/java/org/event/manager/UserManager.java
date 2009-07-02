package org.event.manager;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.event.manager.dao.Dao;
import org.event.manager.user.User;
import org.jboss.resteasy.annotations.providers.jaxb.Wrapped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

@Path("/user")
public class UserManager {

	private static final Logger log = LoggerFactory.getLogger(UserManager.class);
	private final Dao dao;
    
	@Inject
	public UserManager(Dao dao) {
		this.dao = dao;
	}
	
	@GET @Path("/users") @Produces(MediaType.APPLICATION_JSON)
	@Wrapped(element="list",prefix="user")
	public List<User> getBooks() {
		log.info("getBooks() : begin");
		return dao.findAllByClass(User.class);
	}
}
