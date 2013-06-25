package com.rabidgremlin.jerseydoc.model;

import java.util.ArrayList;
import java.util.List;

public class RestApplication
{
  private String name;
  private List<RestMethod> methods = new ArrayList<RestMethod>();

  public List<RestMethod> getMethods()
  {
    return methods;
  }

  public void setMethods(List<RestMethod> methods)
  {
    this.methods = methods;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

}
