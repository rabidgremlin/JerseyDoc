package com.rabidgremlin.jerseydoc.output.markdown;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import com.rabidgremlin.jerseydoc.model.RestApplication;
import com.rabidgremlin.jerseydoc.model.RestDocItem;
import com.rabidgremlin.jerseydoc.model.RestMethod;
import com.rabidgremlin.jerseydoc.model.RestMethodResponseStatus;

public class MarkdownWriter
{
  private boolean includeInternals;
  private PrintWriter out;

  public MarkdownWriter(boolean includeInternals)
  {

    this.includeInternals = includeInternals;
  }

  public void writeAsMarkdown(Writer writer, RestApplication application) throws IOException
  {
    out = new PrintWriter(writer);

    writeTitle(application);
    for (RestMethod restMethod : application.getMethods())
    {
      writeMethod(restMethod);
      out.println();
    }

    // clean up
    out.flush();
    out.close();
    out = null;
  }

  private void writeMethod(RestMethod restMethod)
  {
    out.println();
    out.println("## " + restMethod.getName());

    out.println(restMethod.getComments());

    out.println();
    out.println("### URL");
    out.println(restMethod.getUrl());

    out.println();
    out.println("### Method");
    out.println(restMethod.getHttpMethod());

    out.println();
    out.println("### Request");

    if (restMethod.getRequestComments() != null)
    {
      out.println(restMethod.getRequestComments());
    }

    if (restMethod.getPathParams().size() > 0)
    {
      out.println();
      out.println("#### Path parameters");
      out.println();

      for (RestDocItem pp : restMethod.getPathParams())
      {
        out.print("* ");
        out.print(pp.getName());

        out.print(" - ");
        out.print(pp.getComments() != null ? pp.getComments() : "");
        out.println();
      }
    }

    if (restMethod.getQueryParams().size() > 0)
    {
      out.println();
      out.println("#### Query parameters");
      out.println();

      for (RestDocItem qp : restMethod.getQueryParams())
      {
        out.print("* ");
        out.print(qp.getName());

        out.print(" - ");
        out.print(qp.getComments() != null ? qp.getComments() : "");
        out.println();
      }
    }

    if (restMethod.getRequestTypeName() != null)
    {
      out.println();
      out.println("#### Request Type");
      out.println(restMethod.getRequestTypeName());

      // if (restMethod.getRequestClassDoc() != null)
      // {
      // out.println();
      // out.println(restMethod.getRequestClassDoc().commentText());
      // }
    }

    if (restMethod.getSampleRequest() != null)
    {
      out.println();
      out.println("#### Sample Request");
      out.println(restMethod.getSampleRequest());
    }

    out.println();
    out.println("### Response");

    if (restMethod.getResponseComments() != null)
    {
      out.println(restMethod.getResponseComments());
    }

    if (restMethod.getResponseTypeName() != null)
    {
      out.println();
      out.println("#### Response Type");
      out.println(restMethod.getResponseTypeName());

//      if (restMethod.getResponseClassDoc() != null)
//      {
//        out.println();
//        out.println(restMethod.getResponseClassDoc().commentText());
//      }
    }

    if (restMethod.getSampleResponse() != null)
    {
      out.println();
      out.println("#### Sample Response");
      out.println(restMethod.getSampleResponse());
    }

    if (restMethod.getResponseMethodStatuses().size() > 0)
    {
      out.println();
      out.println("#### Response Status Codes");
      out.println();

      for (RestMethodResponseStatus rs : restMethod.getResponseMethodStatuses())
      {
        out.print("* ");
        out.print(rs.getStatus());

        if (rs.getName() != null)
        {
          out.print(" - ");
          out.print(rs.getName());
        }

        out.print(" - ");
        out.print(rs.getComments());
        out.println();
      }
    }

  }

  private void writeTitle(RestApplication application)
  {
    out.println("# " + application.getName());
    out.println();
  }

}
