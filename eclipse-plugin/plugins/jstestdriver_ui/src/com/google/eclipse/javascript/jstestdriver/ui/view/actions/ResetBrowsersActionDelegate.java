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
package com.google.eclipse.javascript.jstestdriver.ui.view.actions;

import java.util.logging.Logger;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import com.google.eclipse.javascript.jstestdriver.core.JstdTestRunner;
import com.google.eclipse.javascript.jstestdriver.core.ServiceLocator;
import com.google.eclipse.javascript.jstestdriver.ui.view.JsTestDriverView;
import com.google.eclipse.javascript.jstestdriver.ui.view.TestResultsPanel;

/**
 * Resets captured browsers so that they all return to a clean state.
 *
 * @author shyamseshadri@gmail.com (Shyam Seshadri)
 */
public class ResetBrowsersActionDelegate implements IViewActionDelegate {
  private static final Logger logger =
      Logger.getLogger(ResetBrowsersActionDelegate.class.getName());

  private final JstdTestRunner jstdTestRunner;

  private TestResultsPanel view;

  public ResetBrowsersActionDelegate() {
    this(ServiceLocator.getService(JstdTestRunner.class));
  }

  public ResetBrowsersActionDelegate(JstdTestRunner jstdTestRunner) {
    this.jstdTestRunner = jstdTestRunner;
  }

  @Override
  public void init(IViewPart view) {
    if (view instanceof JsTestDriverView) {
      this.view = ((JsTestDriverView) view).getTestResultsPanel();
    }
  }

  @Override
  public void run(IAction action) {
    if (view.getLastLaunchConfiguration() == null) {
      return;
    }
    try {
      jstdTestRunner.resetTest(view.getLastLaunchConfiguration());
    } catch (CoreException e) {
      logger.severe(e.toString());
    }
  }

  @Override
  public void selectionChanged(IAction action, ISelection selection) {
  }
}