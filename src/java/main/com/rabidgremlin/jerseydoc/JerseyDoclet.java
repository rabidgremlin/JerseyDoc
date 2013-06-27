package com.rabidgremlin.jerseydoc;

import java.io.File;
import java.io.FileWriter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import com.rabidgremlin.jerseydoc.model.RestApplication;
import com.rabidgremlin.jerseydoc.output.audit.AuditWriter;
import com.rabidgremlin.jerseydoc.output.markdown.MarkdownWriter;
import com.sun.javadoc.LanguageVersion;
import com.sun.javadoc.RootDoc;

public class JerseyDoclet
{
  private final static Logger LOGGER = Logger.getLogger(JerseyDoclet.class.getName());

  public void go(RootDoc rootDoc) throws Exception
  {
    try
    {
      LogManager.getLogManager().reset();
      LogManager.getLogManager().readConfiguration(Thread.currentThread().getContextClassLoader().getResourceAsStream("com/rabidgremlin/jerseydoc/logging.properties"));

      String baseUrl = readOptions(rootDoc.options(), "-baseUrl");
      String title = readOptions(rootDoc.options(), "-title");
      String type = readOptions(rootDoc.options(), "-type");
      if (type == null)
      {
        type = "docs";
      }

      DocGenerator docGenerator = new DocGenerator(rootDoc, baseUrl, title);
      RestApplication restApplication = docGenerator.getRestApplication();

      FileWriter out = new FileWriter(new File(readOptions(rootDoc.options(), "-o")));

      if ("audit".equalsIgnoreCase(type))
      {
        AuditWriter writer = new AuditWriter();
        writer.writeAsCSV(out, restApplication);
      }
      else
      {

        MarkdownWriter writer = new MarkdownWriter(true);
        writer.writeAsMarkdown(out, restApplication);
      }
    }
    catch (Exception e)
    {
      LOGGER.severe(e.toString());
      throw e;
    }
  }

  public static boolean start(RootDoc rootDoc)
  {
    try
    {
      JerseyDoclet jd = new JerseyDoclet();
      jd.go(rootDoc);

      return true;
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return false;
    }
  }

  private String readOptions(String[][] options, String option)
  {
    String tagName = null;
    for (int i = 0; i < options.length; i++)
    {
      String[] opt = options[i];
      if (opt[0].equals(option))
      {
        tagName = opt[1];
      }
    }
    return tagName;
  }

  public static int optionLength(String option)
  {
    if (option.equals("-o"))
    {
      return 2;
    }
    if (option.equals("-baseUrl"))
    {
      return 2;
    }
    if (option.equals("-title"))
    {
      return 2;
    }
    if (option.equals("-type"))
    {
      return 2;
    }

    return 0;
  }

  public static LanguageVersion languageVersion()
  {
    return LanguageVersion.JAVA_1_5;
  }
}
