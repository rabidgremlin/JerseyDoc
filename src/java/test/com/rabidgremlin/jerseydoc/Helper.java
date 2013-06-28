package com.rabidgremlin.jerseydoc;

import java.io.File;

import com.rabidgremlin.jerseydoc.easydoc.EasyDoclet;
import com.rabidgremlin.jerseydoc.model.RestApplication;
import com.sun.javadoc.RootDoc;

public class Helper
{

  public static RestApplication buildRestApplicationFromExample() throws Exception
  {
    EasyDoclet doclet = new EasyDoclet(getTestSourcePath(), "com.rabidgremlin.example.resources");
    RootDoc rootDoc = doclet.getRootDoc();

    DocGenerator docGenerator = new DocGenerator(rootDoc, "http://somedomain/rest/", "Example services");
    return docGenerator.getRestApplication();
  }

  public static File getTestSourcePath()
  {
    String testSourcePath = System.getProperty("test.source.path");
    
    if (testSourcePath == null)
    {
      testSourcePath = "src/java/test";
    }

    return new File(testSourcePath);
  }
}
