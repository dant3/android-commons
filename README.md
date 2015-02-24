# android-commons [![Build Status](https://travis-ci.org/dant3/android-commons.svg?branch=master)](https://travis-ci.org/dant3/android-commons)

# Use in your project

* Maven:

```xml
<repositories>
    <repository>
        <id>dant3-bintray</id>
        <name>bintray</name>
        <url>http://dl.bintray.com/dant3/maven</url>
    </repository>
</repositories>
<dependencies>
    <dependency>
        <groupId>com.github.dant3</groupId>
        <artifactId>android-commons</artifactId>
        <version>latest_version</version>
        <type>jar</type>
    </dependency>
</dependencies>
```

* Gradle:

```groovy
repositories {
    maven { url "http://dl.bintray.com/dant3/maven" }
}

dependencies {
    compile "org.github.dant3:android-commons:$latest_version"
}
```

* SBT:

```scala
resolvers += "dant3" at "http://dl.bintray.com/dant3/maven"

libraryDependencies += "org.github.dant3" % "android-commons" % latest_version
```
