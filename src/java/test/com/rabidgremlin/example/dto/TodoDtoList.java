package com.rabidgremlin.example.dto;

import java.util.ArrayList;
import java.util.List;

public class TodoDtoList
{
  private List<TodoDto> todos = new ArrayList<TodoDto>();

  public List<TodoDto> getTodos()
  {
    return todos;
  }

  public void setTodos(List<TodoDto> todos)
  {
    this.todos = todos;
  }

}
