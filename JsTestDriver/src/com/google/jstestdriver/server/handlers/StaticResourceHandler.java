/*
 * Copyright 2010 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.jstestdriver.server.handlers;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.jstestdriver.SlaveResourceService;
import com.google.jstestdriver.requesthandlers.RequestHandler;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Serves static resources.
 * 
 * @author corbinrsmith@gmail.com
 */
public class StaticResourceHandler implements RequestHandler {
  public static final Map<String, String> MIME_TYPE_MAP = ImmutableMap.<String, String>builder()
      .put("appcache", "text/cache-manifest")
      .put("bmp", "image/bmp")
      .put("css", "text/css")
      .put("csv", "text/csv")
      .put("gif", "image/gif")
      .put("htm", "text/html")
      .put("html", "text/html")
      .put("ico", "image/ico")
      .put("jpe", "image/jpeg")
      .put("jpeg", "image/jpeg")
      .put("jpg", "image/jpeg")
      .put("js", "text/javascript")
      .put("json", "application/json")
      .put("kml", "application/vnd.google-earth.kml+xml")
      .put("kmz", "application/vnd.google-earth.kmz")
      .put("pdf", "application/pdf")
      .put("png", "text/png")
      .put("rdf", "application/rdf+xml")
      .put("tif", "image/tiff")
      .put("tiff", "image/tiff")
      .put("txt", "text/plain")
      .put("wav", "audio/x-wav")
      .put("webp", "image/webp")
      .put("xml", "text/xml")
      .build();

  private final SlaveResourceService service;
  private final HttpServletRequest request;
  private final HttpServletResponse response;

  @Inject
  public StaticResourceHandler(SlaveResourceService service,
      HttpServletRequest request,
      HttpServletResponse response) {
    this.service = service;
    this.request = request;
    this.response = response;
  }

  public void handleIt() throws IOException {
    String pathInfo = request.getPathInfo();
    String mimeType = MIME_TYPE_MAP.get(pathInfo.substring(pathInfo.lastIndexOf(".") + 1));
    if (mimeType != null) {
      response.setContentType(mimeType);
    }
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Cache-Control", "private, no-cache, no-store, max-age=0, must-revalidate");
    response.setHeader("Expires", "Sat, 22 Sep 1984 00:00:00 GMT");
    service.serve(pathInfo, response.getOutputStream());
  }
}
