package com.rabidgremlin.jerseydoc.output.audit;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import com.rabidgremlin.jerseydoc.Util;
import com.rabidgremlin.jerseydoc.model.RestApplication;
import com.rabidgremlin.jerseydoc.model.RestMethod;

public class AuditWriter
{
  private PrintWriter out;

  public void writeAsCSV(Writer writer, RestApplication application) throws IOException
  {
    out = new PrintWriter(writer);
    
    out.println("Resource class,Method name,HTTP Method,Service URL,Request class, Response class");

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
    out.print(restMethod.getMethodDoc().containingClass().qualifiedName());
    out.print(",");

    out.print(restMethod.getName());
    out.print(",");

    out.print(restMethod.getHttpMethod());
    out.print(",");

    out.print(Util.makeUrl(restMethod));
    out.print(",");
    
    out.print(restMethod.getRequestTypeQualifiedName()==null?"":restMethod.getRequestTypeQualifiedName());
    out.print(",");
    
    out.print(restMethod.getResponseTypeQualifiedName()==null?"":restMethod.getResponseTypeQualifiedName());    

    out.println();
  }

}
