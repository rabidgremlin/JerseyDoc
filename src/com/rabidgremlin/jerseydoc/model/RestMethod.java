package com.rabidgremlin.jerseydoc.model;

import java.util.ArrayList;
import java.util.List;

import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;

public class RestMethod
{
  private String name;
  private String url;
  private HttpMethod httpMethod;
  private String comments;

  private ClassDoc resourceClassDoc;
  private MethodDoc methodDoc;

  private String sampleRequest;
  private String sampleResponse;

  private String responseComments;
  private String requestComments;

  private String requestTypeName;
  private String responseTypeName;

  private ClassDoc responseClassDoc;
  private ClassDoc requestClassDoc;

  private List<RestMethodResponseStatus> responseMethodStatuses = new ArrayList<RestMethodResponseStatus>();

  private List<RestDocItem> pathParams = new ArrayList<RestDocItem>();
  private List<RestDocItem> queryParams = new ArrayList<RestDocItem>();

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getUrl()
  {
    return url;
  }

  public void setUrl(String url)
  {
    this.url = url;
  }

  public HttpMethod getHttpMethod()
  {
    return httpMethod;
  }

  public void setHttpMethod(HttpMethod httpMethod)
  {
    this.httpMethod = httpMethod;
  }

  public String getComments()
  {
    return comments;
  }

  public void setComments(String comments)
  {
    this.comments = comments;
  }

  public ClassDoc getResourceClassDoc()
  {
    return resourceClassDoc;
  }

  public void setResourceClassDoc(ClassDoc resourceClassDoc)
  {
    this.resourceClassDoc = resourceClassDoc;
  }

  public MethodDoc getMethodDoc()
  {
    return methodDoc;
  }

  public void setMethodDoc(MethodDoc methodDoc)
  {
    this.methodDoc = methodDoc;
  }

  public String getSampleRequest()
  {
    return sampleRequest;
  }

  public void setSampleRequest(String sampleRequest)
  {
    this.sampleRequest = sampleRequest;
  }

  public String getSampleResponse()
  {
    return sampleResponse;
  }

  public void setSampleResponse(String sampleResponse)
  {
    this.sampleResponse = sampleResponse;
  }

  public ClassDoc getResponseClassDoc()
  {
    return responseClassDoc;
  }

  public void setResponseClassDoc(ClassDoc responseClassDoc)
  {
    this.responseClassDoc = responseClassDoc;
  }

  public List<RestMethodResponseStatus> getResponseMethodStatuses()
  {
    return responseMethodStatuses;
  }

  public void setResponseMethodStatuses(List<RestMethodResponseStatus> responseMethodStatuses)
  {
    this.responseMethodStatuses = responseMethodStatuses;
  }

  public List<RestDocItem> getPathParams()
  {
    return pathParams;
  }

  public void setPathParams(List<RestDocItem> pathParams)
  {
    this.pathParams = pathParams;
  }

  public List<RestDocItem> getQueryParams()
  {
    return queryParams;
  }

  public void setQueryParams(List<RestDocItem> queryParams)
  {
    this.queryParams = queryParams;
  }

  public String getResponseComments()
  {
    return responseComments;
  }

  public void setResponseComments(String responseComments)
  {
    this.responseComments = responseComments;
  }

  public String getRequestComments()
  {
    return requestComments;
  }

  public void setRequestComments(String requestComments)
  {
    this.requestComments = requestComments;
  }

  public String getRequestTypeName()
  {
    return requestTypeName;
  }

  public void setRequestTypeName(String requestTypeName)
  {
    this.requestTypeName = requestTypeName;
  }

  public String getResponseTypeName()
  {
    return responseTypeName;
  }

  public void setResponseTypeName(String responseTypeName)
  {
    this.responseTypeName = responseTypeName;
  }

  public ClassDoc getRequestClassDoc()
  {
    return requestClassDoc;
  }

  public void setRequestClassDoc(ClassDoc requestClassDoc)
  {
    this.requestClassDoc = requestClassDoc;
  }

  @Override
  public String toString()
  {
    return "RestMethod [name=" + name + ", url=" + url + ", httpMethod=" + httpMethod + ", comments=" + comments
        + ", resourceClassDoc=" + resourceClassDoc + ", methodDoc=" + methodDoc + ", sampleRequest=" + sampleRequest
        + ", sampleResponse=" + sampleResponse + ", responseComments=" + responseComments + ", requestComments="
        + requestComments + ", requestTypeName=" + requestTypeName + ", responseTypeName=" + responseTypeName
        + ", responseClassDoc=" + responseClassDoc + ", requestClassDoc=" + requestClassDoc + ", responseMethodStatuses="
        + responseMethodStatuses + ", pathParams=" + pathParams + ", queryParams=" + queryParams + "]";
  }

  
  
}
