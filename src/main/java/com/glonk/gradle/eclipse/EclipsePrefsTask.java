package com.glonk.gradle.eclipse;

import org.gradle.api.internal.AbstractTask;
import org.gradle.api.tasks.TaskAction;


public class EclipsePrefsTask extends AbstractTask {

  @TaskAction
  public void run() throws Exception {
    EclipsePrefs ext = getProject().getExtensions().getByType(EclipsePrefs.class);
    System.err.println("EclipsePrefsTask");
  }
  
}
