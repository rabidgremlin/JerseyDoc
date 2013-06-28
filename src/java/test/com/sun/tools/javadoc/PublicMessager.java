package com.sun.tools.javadoc;

import com.sun.tools.javac.util.Context;
import java.io.PrintWriter;

/** * Protected constructors prevent the world from exploding! */
public class PublicMessager extends Messager
{
  public PublicMessager(Context context, String s)
  {
    super(context, s);
  }

  public PublicMessager(Context context, String s, PrintWriter printWriter, PrintWriter printWriter1, PrintWriter printWriter2)
  {
    super(context, s, printWriter, printWriter1, printWriter2);
  }
}
