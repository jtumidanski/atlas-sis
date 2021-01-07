package com.atlas.sis;

import java.net.URI;

import com.atlas.shared.rest.RestServerFactory;
import com.atlas.shared.rest.UriBuilder;
import com.atlas.sis.constant.RestConstants;

public class Server {
   public static void main(String[] args) {
      URI uri = UriBuilder.host(RestConstants.SERVICE).uri();
      RestServerFactory.create(uri, "com.atlas.sis.rest");
   }
}
