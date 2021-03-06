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
package com.google.jstestdriver.html;

import junit.framework.TestCase;

import com.google.jstestdriver.FileInfo;

/**
 * @author corysmith@google.com (Cory Smith)
 *
 */
public class InlineHtmlProcessorTest extends TestCase {
  public void testProcessHtmlCreate() throws Exception {
    ScriptBuilder script = new ScriptBuilder()
        .line("TestCase.prototype.setUp = function(){")
        .line("  this.foo = 1;")
        .test("  /*:DOC bar = <div></div>*/")
        .expect("  this. bar  = jstestdriver.toHtml(' <div></div>',window.document);")
        .line("};");
    
    doScriptTest(script);
  }
  public void testProcessHtmlToBody() throws Exception {
    ScriptBuilder script = new ScriptBuilder()
    .line("TestCase.prototype.setUp = function(){")
    .line("  this.foo = 1;")
    .test("  /*:DOC += <div></div>*/")
    .expect("  jstestdriver.appendHtml(' <div></div>',window.document);")
    .line("};");
    
    doScriptTest(script);
  }

  private void doScriptTest(ScriptBuilder script) {
    FileInfo test = new FileInfo("foo.js", 20, -1, false, false, script.buildTest(), "foo.js");
    FileInfo expected = new FileInfo(test.getFilePath(),
                                     test.getTimestamp(),
                                     -1,
                                     test.isPatch(),
                                     test.isServeOnly(),
                                     script.buildExpect(),
                                     test.getDisplayPath());
    FileInfo actual = new InlineHtmlProcessor(new HtmlDocParser(),
        new HtmlDocLexer()).process(test);
    assertEquals(expected.getFilePath(), actual.getFilePath());
    assertEquals(expected.getTimestamp(), actual.getTimestamp());
    assertEquals(expected.isPatch(), actual.isPatch());
    assertEquals(expected.isServeOnly(), actual.isServeOnly());
    assertEquals(expected.getData(), actual.getData());
  }
  
  static class ScriptBuilder {
    private StringBuilder script = new StringBuilder();
    private StringBuilder expected = new StringBuilder();
    public ScriptBuilder line(String line) {
      script.append(line);
      expected.append(line);
      return this;
    }
    
    public ScriptBuilder expect(String line) {
      expected.append(line);
      return this;
    }
    
    public ScriptBuilder test(String line) {
      script.append(line);
      return this;
    }
    
    public String buildTest() {
      return script.toString();
    }
    
    public String buildExpect() {
      return expected.toString();
    }
  }
}
