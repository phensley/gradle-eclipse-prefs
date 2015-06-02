
gradle-eclipse-prefs
--------------------

Eclipse preferences settings for Gradle projects.

```
allprojects {
    eclipsePrefs {
        group("org.eclipse.jdt.ui.prefs") {
            set "editor_save_participant_org.eclipse.jdt.ui.postsavelistener.cleanup", true
            set ""sp_cleanup.remove_trailing_whitespaces", true
        }
    }
}
```

