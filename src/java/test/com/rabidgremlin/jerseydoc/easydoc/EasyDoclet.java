package com.rabidgremlin.jerseydoc.easydoc;

import com.sun.javadoc.RootDoc;
import com.sun.javadoc.ClassDoc;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Options;
import com.sun.tools.javadoc.JavadocTool;
import com.sun.tools.javadoc.ModifierFilter;
import com.sun.tools.javadoc.PublicMessager;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/** See http://planet.jboss.org/post/testing_java_doclets0 */
public class EasyDoclet
{
  final private Logger log = Logger.getLogger(EasyDoclet.class.getName());
  final private File sourceDirectory;
  final private String[] packageNames;
  final private File[] fileNames;
  final private RootDoc rootDoc;

  public EasyDoclet(File sourceDirectory, String... packageNames)
  {
    this(sourceDirectory, packageNames, new File[0]);
  }

  public EasyDoclet(File sourceDirectory, File... fileNames)
  {
    this(sourceDirectory, new String[0], fileNames);
  }

  protected EasyDoclet(File sourceDirectory, String[] packageNames, File[] fileNames)
  {
    this.sourceDirectory = sourceDirectory;
    this.packageNames = packageNames;
    this.fileNames = fileNames;
    Context context = new Context();
    Options compOpts = Options.instance(context);
    if (getSourceDirectory().exists())
    {
      log.fine("Using source path: " + getSourceDirectory().getAbsolutePath());
      compOpts.put("-sourcepath", getSourceDirectory().getAbsolutePath());
    }
    else
    {
      log.info("Ignoring non-existant source path, check your source directory argument");
    }
    ListBuffer javaNames = new ListBuffer();
    for (File fileName : fileNames)
    {
      log.fine("Adding file to documentation path: " + fileName.getAbsolutePath());
      javaNames.append(fileName.getPath());
    }
    ListBuffer subPackages = new ListBuffer();
    for (String packageName : packageNames)
    {
      log.fine("Adding sub-packages to documentation path: " + packageName);
      subPackages.append(packageName);
    }
    new PublicMessager(context, getApplicationName(), new PrintWriter(new LogWriter(Level.SEVERE), true), new PrintWriter(
        new LogWriter(Level.WARNING), true), new PrintWriter(new LogWriter(Level.FINE), true));
    JavadocTool javadocTool = JavadocTool.make0(context);
    try
    {
      rootDoc = javadocTool.getRootDocImpl("", null, new ModifierFilter(ModifierFilter.ALL_ACCESS), javaNames.toList(),
          new ListBuffer().toList(), false, subPackages.toList(), new ListBuffer().toList(), false, false, false);
    }
    catch (Exception ex)
    {
      throw new RuntimeException(ex);
    }
    if (log.isLoggable(Level.FINEST))
    {
      for (ClassDoc classDoc : getRootDoc().classes())
      {
        log.finest("Parsed Javadoc class source: " + classDoc.position() + " with inline tags: " + classDoc.inlineTags().length);
      }
    }
  }

  public File getSourceDirectory()
  {
    return sourceDirectory;
  }

  public String[] getPackageNames()
  {
    return packageNames;
  }

  public File[] getFileNames()
  {
    return fileNames;
  }

  public RootDoc getRootDoc()
  {
    return rootDoc;
  }

  protected class LogWriter extends Writer
  {
    Level level;

    public LogWriter(Level level)
    {
      this.level = level;
    }

    public void write(char[] chars, int offset, int length) throws IOException
    {
      String s = new String(Arrays.copyOf(chars, length));
      if (!s.equals("\n"))
        log.log(level, s);
    }

    public void flush() throws IOException
    {
    }

    public void close() throws IOException
    {
    }
  }

  protected String getApplicationName()
  {
    return getClass().getSimpleName() + " Application";
  }
}
