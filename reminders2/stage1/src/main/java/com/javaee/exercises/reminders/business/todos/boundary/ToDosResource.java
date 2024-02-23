package com.javaee.exercises.reminders.business.todos.boundary;

import com.javaee.exercises.reminders.business.todos.entity.ToDo;

import java.net.URI;
import java.util.List;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Stateless
@Path("todos")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ToDosResource {
    @Inject
    ToDoManager manager;

    @GET
    public List<ToDo> all() {
        return manager.all();
    }

    @Path("{id}")
    public ToDoResource find(@PathParam("id") long id) {
        return new ToDoResource(id, manager);
    }

    @POST
    public Response save(@Valid ToDo todo, @Context UriInfo info) {
        ToDo saved = manager.save(todo);
        long id = saved.getId();
        URI uri = info.getAbsolutePathBuilder().path("/" + id).build();
        return Response.created(uri).build();
    }
}