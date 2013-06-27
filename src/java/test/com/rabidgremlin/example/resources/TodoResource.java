package com.rabidgremlin.example.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rabidgremlin.example.dto.TodoCreateDto;
import com.rabidgremlin.example.dto.TodoDto;
import com.rabidgremlin.example.dto.TodoDtoList;
import com.rabidgremlin.example.dto.TodoUpdateDto;

/**
 * Example sub-resource. This code is designed to expose features of the doclet and is not necessary "best-practice" code for
 * Jersey resource classes.
 * 
 */
public class TodoResource
{

  /**
   * This method sets the list id that this resource will manage todos for.
   * 
   * @param listId The ID of the list
   */
  public void setListId(String listId)
  {
    // do nothing it's test code
  }

  /**
   * This service returns the list of todos for the specified todo list. The list can be optionally filtered by passing filter
   * text in the 'q' query parameter.
   * 
   * @param filterText The text used to filter the list of todos returned.
   * @return The list of todos.
   * 
   * @response.status OK If the service call was successful.
   */
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public TodoDtoList getTodos(@QueryParam("q") String filterText)
  {
    // don't actually do anything
    return null;
  }

  /**
   * This service returns the details of the specified todo.
   * 
   * @param todoId The ID of the todo.
   * @return The details of the todo.
   * 
   * @response.status OK If the service call was successful.
   * @response.status NOT_FOUND If the todo cannot be found.
   */
  @GET
  @Path("{todoId}")
  @Produces(MediaType.APPLICATION_JSON)
  public TodoDto getTodo(@PathParam("todoId") String todoId)
  {
    // don't actually do anything
    return null;
  }

  /**
   * This service deletes the specified todo.
   * 
   * @param todoId The ID of the todo.
   * 
   * @response.status NO_CONTENT If the todo was successfully deleted.
   * @response.status NOT_FOUND If the todo cannot be found.
   */
  @DELETE
  @Path("{todoId}")
  @Produces(MediaType.APPLICATION_JSON)
  public void deleteTodo(@PathParam("todoId") String todoId)
  {
    // don't actually do anything
    // JAX-RS spec states that void will be mapped to 204 (NO CONTENT) response code
  }

  /**
   * This service creates a todo in the specified todo list.
   * 
   * @param todoCreateRequest The todo create request.
   * @return CREATED if the new todo has been successfully created. Location header will provide URL of newly created todo.
   * 
   * @response.status CREATED If the new todo has been successfully created.
   * @response.status INTERNAL_SERVER_ERROR Returned if there is a critical failure.
   * @response.status 460 If some business rule is broken.
   */
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createTodo(TodoCreateDto todoCreateRequest)
  {
    // don't actually do anything
    // code would be something like
    // return Response.created(new URI(newTodoId)).build();
    return null;
  }

  /**
   * This service updates the details of the specified todo.
   * 
   * @param todoId The ID of the todo to update.
   * @param todoUpdateRequest The todo update request.
   * @return OK if the todo was successfully updated.
   * 
   * @response.status OK if the todo was successfully updated.
   * @response.status NOT_FOUND If the todo cannot be found.
   * @response.status INTERNAL_SERVER_ERROR Returned if there is a critical failure.
   * 
   */
  @POST
  @Path("{todoId}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response updateTodo(@PathParam("todoId") String todoId, TodoUpdateDto todoUpdateRequest)
  {
    // don't actually do anything
    // code would actually return something like
    // return Response.ok();
    return null;
  }

}
