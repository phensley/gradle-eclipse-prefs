package com.glonk.gradle.eclipse;

import java.util.HashMap;
import java.util.Map;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;


public class EclipsePrefsPlugin implements Plugin<Project> {

  private static final String ECLIPSE_TASK = "eclipse";
  
  private static final String PREFS_EXTENSION = "eclipsePrefs";
  
  @Override
  public void apply(Project project) {
    
    project.apply(dependencies());
    project.getExtensions().create(PREFS_EXTENSION, EclipsePrefs.class);  

    EclipsePrefsAction action = new EclipsePrefsAction();
    Task eclipseTask = project.getTasks().findByName(ECLIPSE_TASK);
    
    if (eclipseTask != null) {
      eclipseTask.doLast(action);
    }
  }

  private static Map<String, ?> dependencies() {
    Map<String, Object> result = new HashMap<>();
    result.put("plugin", "eclipse");
    return result;
  }
  
}
