package com.rabidgremlin.example.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.rabidgremlin.example.dto.TodoListDto;
import com.sun.jersey.api.core.ResourceContext;

/**
 * Example top-level resource. This code is designed to expose features of the doclet and is not necessary "best-practice" code
 * for Jersey resource classes.
 * 
 */
@Path("lists")
public class TodoListResource
{
  @Context
  private ResourceContext resourceContext;

  /**
   * This service returns a list of todo lists.
   * 
   * @return The list of todo lists.
   * 
   * @response.status OK If the service call was successful.
   * @response.status INTERNAL_SERVER_ERROR Returned if there is a critical failure.
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public List<TodoListDto> getTodoLists()
  {
    // don't actually do anything
    return null;
  }

  /**
   * This method creates a sub-resource. For these types of methods the Javadoc for parameters and any @response.status lines
   * will be "passed" into the documentation for the services exposed by the sub-resource.
   * 
   * @param listId The ID of the todo list.
   * @return The Todo
   * 
   * @response.status NOT_FOUND If list cannot be found.
   */
  @Path("{listId}/todos")
  public TodoResource getTodoResource(@PathParam("listId") String listId)
  {
    // do nothing. Real code would look something like this:

    // if(!validListIf(listId)
    // {
    // throw new WebApplicationException(Status.NOT_FOUND);
    // }
    //
    // TodoResource todoResource = resourceContext.getResource(TodoResource.class);
    // todoResource.setListId(listId);
    // return todoResource;

    return null;
  }
}
