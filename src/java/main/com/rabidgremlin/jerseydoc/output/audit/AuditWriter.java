package com.rabidgremlin.jerseydoc.output.audit;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import com.rabidgremlin.jerseydoc.Util;
import com.rabidgremlin.jerseydoc.model.RestApplication;
import com.rabidgremlin.jerseydoc.model.RestMethod;
import com.rabidgremlin.jerseydoc.model.RestMethodAuditNote;

public class AuditWriter
{
  private PrintWriter out;

  public void writeAsCSV(Writer writer, RestApplication application) throws IOException
  {
    out = new PrintWriter(writer);

    out.print("Resource class,Method name,HTTP Method,Service URL,Request class,Response class,");

    // get unique list of note IDs across all methods
    HashSet<String> noteIdsSet = new HashSet<String>();
    for (RestMethod restMethod : application.getMethods())
    {
      for (RestMethodAuditNote auditNote : restMethod.getRestMethodAuditNotes())
      {
        noteIdsSet.add(auditNote.getNoteId());
      }
    }

    // get sorted list of ids
    List<String> noteIds = new ArrayList<String>(noteIdsSet);
    Collections.sort(noteIds);

    // TODO do fix up to remove trailing ","
    // write out headers
    for (String noteId : noteIds)
    {
      out.print(noteId);
      out.print(",");
    }

    // finish off header
    out.println();

    for (RestMethod restMethod : application.getMethods())
    {
      writeMethod(noteIds, restMethod);
    }

    // clean up
    out.flush();
    out.close();
    out = null;
  }

  private void writeMethod(List<String> noteIds, RestMethod restMethod)
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

    out.print(restMethod.getRequestTypeQualifiedName() == null ? "" : restMethod.getRequestTypeQualifiedName());
    out.print(",");

    out.print(restMethod.getResponseTypeQualifiedName() == null ? "" : restMethod.getResponseTypeQualifiedName());
    out.print(",");

    // dump out audit notes
    // strip trailing ","
    for (String noteId : noteIds)
    {
      for (RestMethodAuditNote auditNote : restMethod.getRestMethodAuditNotes())
      {
        if (auditNote.getNoteId().equals(noteId))
        {
          out.print(auditNote.getNote());
          break;
        }
      }
      out.print(",");
    }

    out.println();
  }

}
