package com.rabidgremlin.jerseydoc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.BeforeClass;
import org.junit.Test;

import com.rabidgremlin.jerseydoc.model.HttpMethod;
import com.rabidgremlin.jerseydoc.model.RestApplication;
import com.rabidgremlin.jerseydoc.model.RestMethod;

public class AuditParseTest
{
  protected static RestApplication restApp;

  @BeforeClass
  public static void buildRestApplication() throws Exception
  {
    restApp = Helper.buildRestApplicationFromExample();
  }

  @Test
  public void testAuditData()
  {
    // check that we have some methods
    assertNotNull(restApp.getMethods());

    // this is the data that we display in the audit report
    Object[][] expectedData = { { "TodoListResource", "getTodoLists", HttpMethod.GET, "http://somedomain/rest/lists/" },
        { "TodoResource", "getTodos", HttpMethod.GET, "http://somedomain/rest/lists/{listId}/todos?q=..." },
        { "TodoResource", "createTodo", HttpMethod.PUT, "http://somedomain/rest/lists/{listId}/todos/" },
        { "TodoResource", "getTodo", HttpMethod.GET, "http://somedomain/rest/lists/{listId}/todos/{todoId}/" },
        { "TodoResource", "updateTodo", HttpMethod.POST, "http://somedomain/rest/lists/{listId}/todos/{todoId}/" },
        { "TodoResource", "deleteTodo", HttpMethod.DELETE, "http://somedomain/rest/lists/{listId}/todos/{todoId}/" } };

    // check that we got the expected number of rest methods
    assertEquals(expectedData.length, restApp.getMethods().size());

    // check that the data required for the Audit output is right
    for (int loop = 0; loop < expectedData.length; loop++)
    {
      RestMethod restMethod = restApp.getMethods().get(loop);

      assertEquals(expectedData[loop][0], restMethod.getMethodDoc().containingClass().name());
      assertEquals(expectedData[loop][1], restMethod.getName());
      assertEquals(expectedData[loop][2], restMethod.getHttpMethod());
      assertEquals(expectedData[loop][3], Util.makeUrl(restMethod));
    }

  }
}
