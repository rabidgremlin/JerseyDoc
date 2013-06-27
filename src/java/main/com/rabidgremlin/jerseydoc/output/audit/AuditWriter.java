package com.rabidgremlin.jerseydoc.output.audit;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import com.rabidgremlin.jerseydoc.model.RestApplication;
import com.rabidgremlin.jerseydoc.model.RestDocItem;
import com.rabidgremlin.jerseydoc.model.RestMethod;

public class AuditWriter
{
  private PrintWriter out;

  public void writeAsCSV(Writer writer, RestApplication application) throws IOException
  {
    out = new PrintWriter(writer);

    for (RestMethod restMethod : application.getMethods())
    {
      writeMethod(restMethod);
    }

    // clean up
    out.flush();
    out.close();
    out = null;
  }

  private void writeMethod(RestMethod restMethod)
  {
    // out.print(restMethod.getResourceClassDoc().name());
    out.print(restMethod.getMethodDoc().containingClass().name());
    out.print(",");

    out.print(restMethod.getName());
    out.print(",");

    out.print(restMethod.getHttpMethod());
    out.print(",");

    out.print(makeUrl(restMethod));

    out.println();
  }

  private String makeUrl(RestMethod restMethod)
  {
    if (restMethod.getQueryParams().size() == 0)
    {
      return restMethod.getUrl();
    }

    String url = restMethod.getUrl();
    if (url.endsWith("/"))
    {
      url = url.substring(0, url.length() - 1) + "?";
    }

    for (RestDocItem queryParam : restMethod.getQueryParams())
    {
        url += queryParam.getName() + "=...&";
    }

    return url = url.substring(0, url.length() - 1);
  }

}
