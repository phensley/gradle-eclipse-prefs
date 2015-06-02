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
    
    // Depend on the "eclipse" plugin
    project.apply(dependencies());
    
    // Hook our preference builder task in after "eclipse" runs
    Task task = project.getTasks().findByName(ECLIPSE_TASK);
    task.doLast(new EclipsePrefsAction());
    
// TODO get subproject tasks working
//    EclipsePrefsTask prefsTask = project.getTasks().create(PREFS_EXTENSION, EclipsePrefsTask.class);
//    prefsTask.mustRunAfter(task);
//    prefsTask.dependsOn(task);

    project.getExtensions().create(PREFS_EXTENSION, EclipsePrefs.class);
  }

  private static Map<String, ?> dependencies() {
    Map<String, Object> result = new HashMap<>();
    result.put("plugin", "eclipse");
    return result;
  }
  
}
