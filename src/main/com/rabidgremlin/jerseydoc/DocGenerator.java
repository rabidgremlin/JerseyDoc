package com.rabidgremlin.jerseydoc;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.ws.rs.core.Response;

import com.rabidgremlin.jerseydoc.model.HttpMethod;
import com.rabidgremlin.jerseydoc.model.RestApplication;
import com.rabidgremlin.jerseydoc.model.RestDocItem;
import com.rabidgremlin.jerseydoc.model.RestMethod;
import com.rabidgremlin.jerseydoc.model.RestMethodResponseStatus;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationValue;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.ParamTag;
import com.sun.javadoc.Parameter;
import com.sun.javadoc.RootDoc;
import com.sun.javadoc.Tag;
import com.sun.javadoc.Type;

public class DocGenerator
{
  private final static Logger LOGGER = Logger.getLogger(DocGenerator.class.getName());

  RootDoc rootDoc;
  String baseUrl;
  String title;
  RestApplication restApplication;

  public DocGenerator(RootDoc rootDoc, String baseUrl, String title)
  {
    this.rootDoc = rootDoc;
    this.baseUrl = baseUrl;
    this.title = title;
  }

  public RestApplication getRestApplication() throws Exception
  {
    if (restApplication != null)
    {
      return restApplication;
    }

    restApplication = new RestApplication();
    restApplication.setName(title);

    ClassDoc[] classes = rootDoc.classes();
    for (int i = 0; i < classes.length; ++i)
    {
      dumpClass(baseUrl, classes[i],true);
    }

    Collections.sort(restApplication.getMethods(), new Comparator<RestMethod>()
    {

      @Override
      public int compare(RestMethod o1, RestMethod o2)
      {
        int comp = o1.getUrl().compareTo(o2.getUrl());

        if (comp != 0)
        {
          return comp;
        }

        return o1.getHttpMethod().compareTo(o2.getHttpMethod());
      }
    });

    return restApplication;
  }

  private boolean isSubResource(ClassDoc classDoc)
  {
    if (classDoc == null)
    {
      return false;
    }

    MethodDoc[] methods = classDoc.methods();
    for (MethodDoc method : methods)
    {
      if (getPath(method.annotations()) != null)
      {
        return true;
      }
    }

    return false;
  }

  private void dumpClass(String parentUrl, ClassDoc classDoc, boolean skipNoPath)
  {
    LOGGER.info("---------------------------------------------------------------------------------------------------------");
    LOGGER.info("Parent Urls: " + parentUrl);
    LOGGER.info("classDoc: " + classDoc.qualifiedTypeName());

    AnnotationDesc[] classAnnotations = classDoc.annotations();

    String path = getPath(classAnnotations);

    if (path == null && skipNoPath)
    {
       LOGGER.info("Skipping " + classDoc.qualifiedName() + " it does not have @Path annotation...");
      // return;
      //path = "";
      return;
    }

    MethodDoc[] methods = classDoc.methods();
    for (MethodDoc method : methods)
    {
      String httpMethod = getMethod(method.annotations());

      if (httpMethod != null)
      {
        String methodPath = getPath(method.annotations());

        RestMethod restMethod = new RestMethod();
        restApplication.getMethods().add(restMethod);

        restMethod.setMethodDoc(method);
        restMethod.setName(method.name());
        restMethod.setComments(method.commentText());
        restMethod.setUrl(buildUrl(parentUrl, path, methodPath));
        restMethod.setHttpMethod(HttpMethod.valueOf(httpMethod));

        Parameter requestParam = getRequestParam(method);
        if (requestParam != null)
        {
          restMethod.setRequestTypeName(requestParam.type().simpleTypeName());
          restMethod.setRequestComments(getParamComment(method.paramTags(), requestParam.name()));
          restMethod.setSampleRequest(generateExample(requestParam.type()));
          restMethod.setRequestClassDoc(requestParam.type().asClassDoc());
        }

        if (!method.returnType().qualifiedTypeName().equals("javax.ws.rs.core.Response")
            && !method.returnType().qualifiedTypeName().equals("void"))
        {
          restMethod.setResponseTypeName(method.returnType().simpleTypeName());
          restMethod.setSampleResponse(generateExample(method.returnType()));
          restMethod.setResponseClassDoc(method.returnType().asClassDoc());
        }

        restMethod.setResponseComments(getReturnTypeComment(method));

        extractQueryParams(method, restMethod);
        extractPathParams(method, restMethod);

        extractResponseStatuses(method, restMethod);

        LOGGER.info("Added " + restMethod.getUrl() + " " + restMethod.getName());
        //LOGGER.info("Method " + restMethod);
      }
      else
      {

        if (isSubResource(method.returnType().asClassDoc()))
        {
          LOGGER.info("%%%%% " + method.returnType().qualifiedTypeName() + " is subresource...");
          String methodPath = getPath(method.annotations());
          String newParentUrl = buildUrl(parentUrl, path, methodPath);

          LOGGER.info("%%%%% methodPath:" + methodPath);
          LOGGER.info("%%%%% newParentUrl:" + newParentUrl);

          dumpClass(newParentUrl, method.returnType().asClassDoc(),false);
        }
        else
        {
          LOGGER.info("%%%%% " + method.returnType().qualifiedTypeName() + " is NOT subresource...");
        }
      }
    }
  }

  private Parameter getRequestParam(MethodDoc method)
  {
    Parameter[] params = method.parameters();
    for (Parameter param : params)
    {
      // crude assumption that param without an annotation is our request object
      if (param.annotations().length == 0)
      {
        return param;
      }
    }

    return null;
  }

  private String getReturnTypeComment(MethodDoc method)
  {
    Tag[] returnTags = method.tags("return");

    if (returnTags.length > 0)
    {
      return returnTags[0].text();
    }

    return null;

  }

  private void extractQueryParams(MethodDoc method, RestMethod restMethod)
  {
    Parameter[] params = method.parameters();
    for (Parameter param : params)
    {
      AnnotationDesc queryParamAnnotation = getAnnotation(param.annotations(), "QueryParam");

      if (queryParamAnnotation != null)
      {
        RestDocItem di = new RestDocItem();

        di.setName(stripQuotes(queryParamAnnotation.elementValues()[0].value().toString()));
        di.setComment(getParamComment(method.paramTags(), param.name()));

        restMethod.getQueryParams().add(di);
      }
    }
  }

  private void extractPathParams(MethodDoc method, RestMethod restMethod)
  {
    Parameter[] params = method.parameters();
    for (Parameter param : params)
    {
      AnnotationDesc queryParamAnnotation = getAnnotation(param.annotations(), "PathParam");

      if (queryParamAnnotation != null)
      {
        RestDocItem di = new RestDocItem();

        di.setName(stripQuotes(queryParamAnnotation.elementValues()[0].value().toString()));
        di.setComment(getParamComment(method.paramTags(), param.name()));

        restMethod.getPathParams().add(di);
      }
    }
  }

  private String getParamComment(ParamTag[] paramTags, String paramName)
  {
    for (ParamTag tag : paramTags)
    {
      if (tag.parameterName().equals(paramName))
      {
        return tag.parameterComment();
      }
    }

    return null;
  }

  private void extractResponseStatuses(MethodDoc method, RestMethod restMethod)
  {
    Tag[] responseStatusTags = method.tags("response.status");

    if (responseStatusTags.length > 0)
    {
      for (Tag tag : responseStatusTags)
      {
        LOGGER.fine("TAGS: " + tag);

        String[] parts = tag.text().split(" ", 2);

        if (parts.length != 2)
        {
          LOGGER.info(method.qualifiedName() + " has malformed response.status tag: " + tag.toString());
          continue;
        }

        LOGGER.fine("PARTS: " + parts[0] + " - " + parts[1]);

        RestMethodResponseStatus rs = new RestMethodResponseStatus();
        rs.setComments(parts[1]);

        try
        {
          Response.Status status = Response.Status.valueOf(parts[0]);
          rs.setName(status.name());
          rs.setStatus(Integer.toString(status.getStatusCode()));
        }
        catch (IllegalArgumentException e)
        {
          rs.setName(null);
          rs.setStatus(parts[0]);
        }

        restMethod.getResponseMethodStatuses().add(rs);
      }

      LOGGER.fine("Added " + restMethod.getResponseMethodStatuses().size() + " statuses..");
    }

  }

  private String generateExample(Type returnType)
  {
    return generateExample("", returnType);
  }

  private String generateExample(String indent, Type returnType)
  {
    indent += "\t";

    // Logger.fine.println(returnType.qualifiedTypeName() + " " + returnType.asClassDoc());

    ClassDoc classDoc = returnType.asClassDoc();

    if (classDoc == null)
    {
      return "";
    }

    Set<String> jsonIgnores = getJsonIgnores(classDoc);

    StringBuilder sb = new StringBuilder();

    sb.append("\n" + indent + "{");

    MethodDoc[] methods = classDoc.methods();
    for (MethodDoc method : methods)
    {
      if (method.name().startsWith("get") && method.name().length() > 3)
      {
        // Logger.fine.println("********* " + method.name() + " " + classDoc.qualifiedName());

        String prop = getMethodAsProperty(method);

        if (jsonIgnores.contains(prop))
        {
          continue;
        }

        if (!method.returnType().qualifiedTypeName().startsWith("java"))
        {
          LOGGER.fine("********* " + method.name() + " " + method.returnType());

          sb.append(generateExample(indent + "\t", method.returnType()));
        }
        else
        {
          if (method.returnType().qualifiedTypeName().startsWith("java.util.List"))
          {

            if (method.returnType().asParameterizedType().typeArguments().length > 0)
            {
              // sb.append("\n" + indent + "\t\"" + prop + "\": [" + " (" + method.returnType().qualifiedTypeName() + "<"
              // + method.returnType().asParameterizedType().typeArguments()[0].qualifiedTypeName() + ">)");
              // sb.append(generateExample(indent + "\t", method.returnType().asParameterizedType().typeArguments()[0]));

              sb.append("\n" + indent + "\t\"" + prop + "\": [");
              sb.append(generateExample(indent + "\t", method.returnType().asParameterizedType().typeArguments()[0]));
            }
            else
            {
              // sb.append("\n" + indent + "\t\"" + prop + "\": [" + " (" + method.returnType().qualifiedTypeName() + ")");
              sb.append("\n" + indent + "\t\"" + prop + "\": [");
            }

            sb.append(" ...]");
          }
          else
          {
            // sb.append("\n" + indent + "\t\"" + prop + "\": ..." + " (" + method.returnType().qualifiedTypeName() + ")");
            sb.append("\n" + indent + "\t\"" + prop + "\": ...");
          }
        }
      }
    }

    sb.append("\n" + indent + "}");

    return sb.toString();

  }

  private Set<String> getJsonIgnores(ClassDoc classDoc)
  {
    HashSet<String> jsonIgnoresProperties = new HashSet<String>();

    AnnotationDesc jsonIgnores = getAnnotation(classDoc.annotations(), "JsonIgnoreProperties");

    if (jsonIgnores != null)
    {
      AnnotationValue[] values = ((AnnotationValue[]) jsonIgnores.elementValues()[0].value().value());

      for (AnnotationValue value : values)
      {
        jsonIgnoresProperties.add(stripQuotes(value.toString()));
      }

      // Logger.fine.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$: " + [0].value() );
    }

    return jsonIgnoresProperties;
  }

  private String getMethodAsProperty(MethodDoc method)
  {
    String prop = ("" + method.name().charAt(3)).toLowerCase() + method.name().substring(4);
    return prop;
  }

  private String buildUrl(String basePath, String classPath, String methodPath)
  {
    // LOGGER.info("bp:" + basePath + " cp:" + classPath + " mp:" + methodPath);

    if (!basePath.endsWith("/"))
    {
      basePath += "/";
    }

    if (classPath != null)
    {

      if (classPath.startsWith("/"))
      {
        classPath = classPath.substring(1);
      }

      if (!classPath.endsWith("/"))
      {
        classPath += "/";
      }
    }
    else
    {
      classPath = "";
    }

    if (methodPath == null)
    {
      return basePath + classPath;
    }

    if (methodPath.startsWith("/"))
    {
      methodPath = methodPath.substring(1);
    }

    if (!methodPath.endsWith("/"))
    {
      methodPath += "/";
    }

    return basePath + classPath + methodPath;
  }

  private String getMethod(AnnotationDesc[] annotations)
  {
    for (AnnotationDesc annotationDesc : annotations)
    {
      if (annotationDesc.annotationType().name().equals("GET") || annotationDesc.annotationType().name().equals("PUT")
          || annotationDesc.annotationType().name().equals("POST") || annotationDesc.annotationType().name().equals("DELETE"))
      {
        return annotationDesc.annotationType().name();
      }
    }
    return null;
  }

  private String getPath(AnnotationDesc[] annotations)
  {
    for (AnnotationDesc annotationDesc : annotations)
    {
      if (annotationDesc.annotationType().name().equals("Path"))
      {
        return stripQuotes(annotationDesc.elementValues()[0].value().toString());
      }
    }
    return null;
  }

  private AnnotationDesc getAnnotation(AnnotationDesc[] annotations, String annotationName)
  {
    for (AnnotationDesc annotationDesc : annotations)
    {
      if (annotationDesc.annotationType().name().equals(annotationName))
      {
        return annotationDesc;
      }
    }
    return null;
  }

  private String stripQuotes(String stringToStrip)
  {
    return stringToStrip.replaceAll("\"", "");
  }

}
