package com.atlas.sis;

import java.net.URI;

import com.atlas.shared.rest.RestServerFactory;
import com.atlas.shared.rest.RestService;
import com.atlas.shared.rest.UriBuilder;

public class Server {
   public static void main(String[] args) {
      URI uri = UriBuilder.host(RestService.SKILL_INFORMATION).uri();
      RestServerFactory.create(uri, "com.atlas.sis.rest");
   }
}
