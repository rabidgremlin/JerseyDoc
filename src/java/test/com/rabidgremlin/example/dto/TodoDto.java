package com.rabidgremlin.example.dto;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Simple model holding Todos. Code is 'twisted' to test code doc generator.
 */
@JsonIgnoreProperties(value = { "dontSerializeThis" })
public class TodoDto
{
  private long id;

  /** The due date. */
  private Date dueDate;

  /** The task. */
  private String task;

  /** The completed. */
  private boolean completed;

  /** An internal field. */
  private int dontSerializeThis = 100;

  public long getId()
  {
    return id;
  }

  public void setId(long id)
  {
    this.id = id;
  }

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

  public int getDontSerializeThis()
  {
    return dontSerializeThis;
  }

  public void setDontSerializeThis(int dontSerializeThis)
  {
    this.dontSerializeThis = dontSerializeThis;
  }

}
