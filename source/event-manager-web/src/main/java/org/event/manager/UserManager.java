package org.event.manager;

import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.event.manager.entities.User;
import org.event.manager.repository.Repository;
import org.event.annotations.annotations.PerformanceLog;

import org.jboss.resteasy.annotations.providers.jaxb.Wrapped;
import org.jboss.resteasy.annotations.providers.jaxb.json.Mapped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;

@Path("/admin")
public class UserManager {

	private static final Logger log = LoggerFactory
			.getLogger(UserManager.class);
	@Context
	HttpServletRequest request;
    
	private final Repository dao;
	private User user;
	
	@Inject
	public UserManager(Repository dao,User user) {
		this.dao = dao;
		this.user = user;
	}

	@POST
	@Path("/login")
	@PerformanceLog
	public String login(@FormParam("name") String name,
			            @FormParam("password") String password) {
		Map<String, Object> params = ImmutableMap.<String, Object>
                of("name", name, "password", password);
		String result = "succesful";
		try {
			user = dao.findUniqueByNamedQuery
                    (User.FIND_BY_CREDENTIALS, params);
			log.debug("getting user with id = {}", user.getId());
			request.getSession().setAttribute("user", user);
		} catch (NoResultException e) {
			result = "fail";
			log.debug("No se pudo loguear le usuario " + name, e);
		}
		return result;
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("id") Long id) {
		return dao.findById(User.class, id);
	}

	@GET
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	@Wrapped(element = "users", prefix = "user")
	public List<User> getUsers() {
		return dao.findAllByClass(User.class);
	}

	@DELETE
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User removeUser(@PathParam("id") Long id) {
		return dao.removeById(User.class, id);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void addUser(@Mapped User user) {
		dao.persistNow(user);
	}
}
