package com.rabidgremlin.example.dto;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

public class TodoUpdateDto
{

  /** The due date. */
  private Date dueDate;

  /** The task. */
  private String task;

  /** The completed. */
  private boolean completed;

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

  public boolean isCompleted()
  {
    return completed;
  }

  public void setCompleted(boolean completed)
  {
    this.completed = completed;
  }

}
