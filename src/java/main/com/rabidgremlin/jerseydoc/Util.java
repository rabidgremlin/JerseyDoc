package com.rabidgremlin.jerseydoc;

import com.rabidgremlin.jerseydoc.model.RestDocItem;
import com.rabidgremlin.jerseydoc.model.RestMethod;

public class Util
{

  private Util()
  {

  }

  public static String makeUrl(RestMethod restMethod)
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
