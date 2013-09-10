version-injection-plugin
========================

Gradle plugin used by Hibernate to inject project version into compiled classes

To use you'd simply apply the plugin (after making sure it is added to your buildscript classpath):
```
buildscript {
    ...
    dependencies {
        classpath 'org.hibernate.build.gradle:version-injection-plugin:${theVersionToUse}'
    }
}

apply plugin: 'version-injection'
```

Then, you'd configure it.  Configuration mainly involves naming the Class member to inject the project version into:
```
versionInjection {
    into( 'com.acme.Version', 'getVersionString' )
}
```

The above would instruct the plugin to over-write the body of `com.acme.Version.getVersionString()` method with the project's current version.  The plugin does this through bytecode manipulation.  The injection is performed every time the compileJava task is performed (as a doLast action).
