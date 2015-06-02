package com.glonk.gradle.eclipse;

import java.util.LinkedHashMap;
import java.util.Map;


public class EclipsePrefGroup {
  
  private final String name;

  private final Map<String, String> prefs = new LinkedHashMap<>();
  
  public EclipsePrefGroup(String name) {
    this.name = name;
  }

  public String name() {
    return name;
  }
  
  public void set(String key, Object val) {
    prefs.put(key, val.toString());
  }
  
  public Map<String, String> settings() {
    return prefs;
  }
  
  @Override
  public String toString() {
    return "EclipsePref(" + name + ", " + prefs + ")";
  }
  
}
