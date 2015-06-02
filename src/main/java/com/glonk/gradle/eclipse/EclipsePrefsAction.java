package com.glonk.gradle.eclipse;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.Task;


public class EclipsePrefsAction implements Action<Task> {
  
  @Override
  public void execute(Task task) {
    Project project = task.getProject();
    EclipsePrefs prefs = project.getExtensions().getByType(EclipsePrefs.class);
    EclipsePrefGenerator generator = new EclipsePrefGenerator(task);
    for (EclipsePrefGroup group : prefs.groups().values()) {
      generator.process(group);
    }
  }

}
