package com.nabenik.todo.rest;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import com.nabenik.todo.dao.ItemDao;
import com.nabenik.todo.model.Item;

@RequestScoped
@Path("/items")
@Produces("application/json")
@Consumes("application/json")
public class ItemEndpoint {
	
	@Inject
	ItemDao itemService;

	@POST
	public Response create(Item item) {
		itemService.create(item);
		return Response.created(UriBuilder.fromResource(ItemEndpoint.class).path(String.valueOf(item.getId())).build())
                        .entity(item)
                        .build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	public Response findById(@PathParam("id") final Long id) {
		Item item = itemService.findById(id);
		if (item == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(item).build();
	}

	@GET
	public List<Item> listAll(@QueryParam("start") final Integer startPosition,
			@QueryParam("max") final Integer maxResult) {
		final List<Item> items = itemService.listAll(startPosition, maxResult);
		return items;
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	public Response update(@PathParam("id") Long id, final Item item) {
		itemService.update(item);
		return Response.noContent().build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") final Long id) {
		itemService.deleteById(id);
		return Response.noContent().build();
	}

}
