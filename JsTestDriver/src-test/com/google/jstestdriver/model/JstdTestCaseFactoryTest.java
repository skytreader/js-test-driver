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

package com.google.jstestdriver.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.jstestdriver.FileInfo;
import com.google.jstestdriver.hooks.JstdTestCaseProcessor;
import com.google.jstestdriver.hooks.ResourceDependencyResolver;
import com.google.jstestdriver.util.NullStopWatch;

import junit.framework.TestCase;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Tests the creation of JstdTestCases.
 * 
 * @author corbinrsmith@gmail (Cory Smith)
 */
public class JstdTestCaseFactoryTest extends TestCase {

  public void testCreateWithTests() throws Exception {
    List<FileInfo> files = Lists.newArrayList();
    FileInfo one = new FileInfo("one.js", 1234, -1, false, false, null, "one.js");
    FileInfo two = new FileInfo("two.js", 1234, -1, false, false, null, "two.js");
    FileInfo three = new FileInfo("three.js", 1234, -1, false, false, null, "three.js");
    files.add(one);
    files.add(two);
    files.add(three);

    FileInfo testOne = new FileInfo("oneTest.js", 1234, -1, false, false, null, "oneTest.js");
    FileInfo testTwo = new FileInfo("twoTest.js", 1234, -1, false, false, null, "twoTest.js");
    FileInfo testThree = new FileInfo("threeTest.js", 1234, -1, false, false, null, "threeTest.js");
    List<FileInfo> tests = Lists.newArrayList(testOne, testTwo, testThree);
    final JstdTestCaseFactory testCaseFactory = new JstdTestCaseFactory(
        Collections.<JstdTestCaseProcessor> emptySet(),
        Collections.<ResourceDependencyResolver>emptySet(), new NullStopWatch());

    List<JstdTestCase> testCases = testCaseFactory.createCases(
        Collections.<FileInfo>emptyList(), files, tests);
    assertEquals(1, testCases.size());
    JstdTestCase jstdTestCase = testCases.get(0);
    assertEquals(tests, jstdTestCase.getTests());
    assertEquals(Lists.newArrayList(files), jstdTestCase.getDependencies());
  }

  public void testCreateWithOutTests() throws Exception {
    List<FileInfo> fileSet = Lists.newArrayList();
    FileInfo one = new FileInfo("one.js", 1234, -1, false, false, null, "one.js");
    FileInfo two = new FileInfo("two.js", 1234, -1, false, false, null, "two.js");
    FileInfo three = new FileInfo("three.js", 1234, -1, false, false, null, "three.js");
    FileInfo testOne = new FileInfo("oneTest.js", 1234, -1, false, false, null, "oneTest.js");
    FileInfo testTwo = new FileInfo("twoTest.js", 1234, -1, false, false, null, "twoTest.js");
    FileInfo testThree = new FileInfo("threeTest.js", 1234, -1, false, false, null, "threeTest.js");
    fileSet.add(one);
    fileSet.add(two);
    fileSet.add(three);
    fileSet.add(testOne);
    fileSet.add(testTwo);
    fileSet.add(testThree);

    final JstdTestCaseFactory testCaseFactory =
        new JstdTestCaseFactory(
            Collections.<JstdTestCaseProcessor> emptySet(),
            Collections.<ResourceDependencyResolver>emptySet(), new NullStopWatch());

    List<JstdTestCase> testCases =
        testCaseFactory.createCases(
            Collections.<FileInfo>emptyList(),
            fileSet,
            Lists.<FileInfo>newArrayList());
    assertEquals(1, testCases.size());
    JstdTestCase jstdTestCase = testCases.get(0);
    assertTrue(jstdTestCase.getTests().isEmpty());
    assertEquals(Lists.newArrayList(fileSet), jstdTestCase.getDependencies());
  }

  public void testCreateWithOutTestsAndDeps() throws Exception {
    List<FileInfo> fileSet = Lists.newArrayList();
    FileInfo one = new FileInfo("one.js", 1234, -1, false, false, null, "one.js");
    FileInfo two = new FileInfo("two.js", 1234, -1, false, false, null, "two.js");
    FileInfo three = new FileInfo("three.js", 1234, -1, false, false, null, "three.js");
    FileInfo testOne = new FileInfo("oneTest.js", 1234, -1, false, false, null, "oneTest.js");
    FileInfo testTwo = new FileInfo("twoTest.js", 1234, -1, false, false, null, "twoTest.js");
    FileInfo testThree = new FileInfo("threeTest.js", 1234, -1, false, false, null, "threeTest.js");
    fileSet.add(one);
    fileSet.add(two);
    fileSet.add(three);
    fileSet.add(testOne);
    fileSet.add(testTwo);
    fileSet.add(testThree);
    
    final JstdTestCaseFactory testCaseFactory =
      new JstdTestCaseFactory(
          Collections.<JstdTestCaseProcessor> emptySet(),
          Collections.<ResourceDependencyResolver>emptySet(), new NullStopWatch());
    
    List<JstdTestCase> testCases =
      testCaseFactory.createCases(
          fileSet,
          Collections.<FileInfo>emptyList(),
          Collections.<FileInfo>emptyList());
    assertEquals(0, testCases.size());
  }
  
  public void testUpdateTestCasesFromRunData() throws Exception {
    FileInfo plugin = new FileInfo("plugin.js", 1234, -1, false, false, null, "plugin.js");
    List<FileInfo> files = Lists.newArrayList();
    FileInfo one = new FileInfo("one.js", 1234, -1, false, false, null, "one.js");
    FileInfo two = new FileInfo("two.js", 1234, -1, false, false, null, "two.js");
    FileInfo three = new FileInfo("three.js", 1234, -1, false, false, null, "three.js");
    files.add(one);
    files.add(two);
    files.add(three);

    FileInfo testOne = new FileInfo("oneTest.js", 1234, -1, false, false, null, "oneTest.js");
    FileInfo testTwo = new FileInfo("twoTest.js", 1234, -1, false, false, null, "twoTest.js");
    FileInfo testThree = new FileInfo("threeTest.js", 1234, -1, false, false, null, "threeTest.js");
    List<FileInfo> tests = Lists.newArrayList(testOne, testTwo, testThree);
    final JstdTestCaseFactory testCaseFactory = new JstdTestCaseFactory(
        Collections.<JstdTestCaseProcessor> emptySet(),
        Collections.<ResourceDependencyResolver>emptySet(), new NullStopWatch());

    final List<JstdTestCase> testCases =
        testCaseFactory.createCases(Collections.<FileInfo>emptyList(), files, tests);

    final Set<FileInfo> fileSet = new RunData(null, testCases, null).getFileSet();
    final Set<FileInfo> updatedFileSet = Sets.newLinkedHashSet();
    updatedFileSet.add(plugin);
    updatedFileSet.addAll(fileSet);

    List<JstdTestCase> updatedTestCases =
        testCaseFactory.updateCases(updatedFileSet, testCases);
    assertEquals(1, updatedTestCases.size());
    JstdTestCase jstdTestCase = updatedTestCases.get(0);
    assertEquals(tests, jstdTestCase.getTests());
    final List<FileInfo> expected = Lists.newArrayList(plugin);
    expected.addAll(files);
    assertEquals(expected, jstdTestCase.getDependencies());
  }
}
