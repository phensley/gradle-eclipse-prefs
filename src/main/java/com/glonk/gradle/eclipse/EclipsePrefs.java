package com.glonk.gradle.eclipse;

import java.util.LinkedHashMap;
import java.util.Map;

import groovy.lang.Closure;


public class EclipsePrefs {

  private final Map<String, EclipsePrefGroup> prefGroups = new LinkedHashMap<>();
  
  public void group(String name, Closure<?> closure) {
    EclipsePrefGroup group = prefGroups.get(name);
    if (group == null) {
      group = new EclipsePrefGroup(name);
      prefGroups.put(name, group);
    }
    
    closure.setDelegate(group);
    closure.setResolveStrategy(Closure.DELEGATE_ONLY);
    closure.call();
  }
  
  public Map<String, EclipsePrefGroup> groups() {
    return prefGroups;
  }
  
  @Override
  public String toString() {
    return prefGroups.toString();
  }
  
}
