package com.glonk.gradle.eclipse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.logging.Logger;


/**
 * Performs changes to the eclipse preferences file.
 */
public class EclipsePrefGenerator {

  private static final String ECLIPSE_SETTINGS = ".settings";
  
  private static final String ECLIPSE_PREFERENCES_VERSION = "eclipse.preferences.version";
  
  private final Project project;
  
  private final Logger log;

  public EclipsePrefGenerator(Task task) {
    this.project = task.getProject();
    this.log = task.getLogger();
  }
  
  public void process(EclipsePrefGroup group) {
    String name = group.name();
    log.info("processing group '" + name + "'");
    File rootDir = project.getRootDir();
    if (!rootDir.exists()) {
      log.error("root directory for project '" + project + "', '" + rootDir + "' does not exist");
      return;
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
      log.error("Failed to update prefs '" + name + "': " + e);
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
  
  // TODO: not sure what our max allowed JDK is yet for gradle plugins.
  private Properties readPrefs(File file) throws IOException {
    FileInputStream input = null;
    try {
      Properties props = new Properties();
      input = new FileInputStream(file);
      props.load(input);
      return props;
      
    } finally {
      if (input != null) {
        input.close();
      }
    }
  }
  
  // TODO: not sure what our max allowed JDK is yet for gradle plugins.
  private void savePrefs(File file, Properties props) throws IOException {
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

  private static Properties buildDefaults() {
    Properties props = new Properties();
    props.setProperty(ECLIPSE_PREFERENCES_VERSION, "1");
    return props;
  }
  
}
