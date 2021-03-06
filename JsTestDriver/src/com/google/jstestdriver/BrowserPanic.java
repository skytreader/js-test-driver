/*
 * Copyright 2009 Google Inc.
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
package com.google.jstestdriver;

import com.google.jstestdriver.Response.ResponseType;

public class BrowserPanic {

  public static final String TYPE_NAME = ResponseType.BROWSER_PANIC.name();
  private BrowserInfo browserInfo;
  private String cause;

  public BrowserPanic() {
  }

  public BrowserPanic(BrowserInfo browserInfo, String cause) {
    this.browserInfo = browserInfo;
    this.cause = cause;
  }

  public BrowserInfo getBrowserInfo() {
    return browserInfo;
  }
  
  public String getCause() {
    return cause;
  }
}
