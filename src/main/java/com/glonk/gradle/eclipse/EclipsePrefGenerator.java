package com.glonk.gradle.eclipse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.logging.Logger;
import org.gradle.api.tasks.TaskExecutionException;


/**
 * Performs changes to the eclipse preferences file.
 */
public class EclipsePrefGenerator {

  private static final String ECLIPSE_SETTINGS = ".settings";
  
  private static final String ECLIPSE_PREFERENCES_VERSION = "eclipse.preferences.version";

  private final Task task;

  private final Project project;
  
  private final Logger log;

  public EclipsePrefGenerator(Task task) {
    this.task = task;
    this.project = task.getProject();
    this.log = task.getLogger();
  }
  
  public void process(EclipsePrefGroup group) {
    String name = group.name();
    log.info("{} processing group '{}'", task, name);
    File rootDir = project.getProjectDir();
    if (!rootDir.exists()) {
      throw newError("project directory does not exist: " + rootDir);
    }
    
    File settingsDir = new File(rootDir, ECLIPSE_SETTINGS);
    if (!settingsDir.exists()) {
      settingsDir.mkdir();
    }

    File prefsFile = new File(settingsDir, name);
    try {
      if (prefsFile.exists()) {
        update(prefsFile, group);
      } else {
        create(prefsFile, group);
      }
    } catch (IOException e) {
      throw new TaskExecutionException(task, e);
    }
  }

  private void update(File file, EclipsePrefGroup group) throws IOException {
    Properties props = readPrefs(file);
    props.putAll(group.settings());
    savePrefs(file, props);
  }
  
  private void create(File file, EclipsePrefGroup group) throws IOException {
    Properties props = buildDefaults();
    props.putAll(group.settings());
    savePrefs(file, props);
  }
  
  private TaskExecutionException newError(String message) {
    return new TaskExecutionException(task, new Exception(message));
  }
  
  // TODO: not sure what our max allowed JDK is yet for gradle plugins.
  private Properties readPrefs(File file) throws IOException {
    log.info("{} loading prefs from {}", task, file);
    FileInputStream input = null;
    try {
      Properties props = new Properties();
      input = new FileInputStream(file);
      props.load(input);
      dumpDebug("read", props);
      return props;
      
    } finally {
      if (input != null) {
        input.close();
      }
    }
  }
  
  // TODO: not sure what our max allowed JDK is yet for gradle plugins.
  private void savePrefs(File file, Properties props) throws IOException {
    log.info("{} saving prefs to {}", task, file);
    dumpDebug("write", props);
    FileOutputStream out = null;
    try {
      out = new FileOutputStream(file);
      props.store(out, "");
      
    } finally {
      if (out != null) {
        out.close();
      }
    }
  }

  private void dumpDebug(String operation, Properties props) {
    if (log.isDebugEnabled()) {
      for (Object key : props.keySet()) {
        log.debug("{} {}: {} = {}", task, operation, key, props.get(key));
      }
    }
  }
  
  private static Properties buildDefaults() {
    Properties props = new Properties();
    props.setProperty(ECLIPSE_PREFERENCES_VERSION, "1");
    return props;
  }
  
}
