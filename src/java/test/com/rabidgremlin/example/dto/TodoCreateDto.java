package com.rabidgremlin.example.dto;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

public class TodoCreateDto
{

  /** The due date. */
  private Date dueDate;

  /** The task. */
  private String task;

  public Date getDueDate()
  {
    return dueDate;
  }

  public void setDueDate(Date dueDate)
  {
    this.dueDate = dueDate;
  }

  public String getTask()
  {
    return task;
  }

  public void setTask(String task)
  {
    this.task = task;
  }

}
