# Kotlin SimpleXML RSS

 [![Download](https://api.bintray.com/packages/magneticflux/kotlin-simplexml-rss/kotlin-simplexml-rss/images/download.svg) ](https://bintray.com/magneticflux/kotlin-simplexml-rss/kotlin-simplexml-rss/_latestVersion)
 [![GitHub tag](https://img.shields.io/github/tag/magneticflux-/kotlin-simplexml-rss.svg)](https://github.com/magneticflux-/kotlin-simplexml-rss/tags) 
[![Build Status](https://travis-ci.org/magneticflux-/kotlin-simplexml-rss.svg?branch=master)](https://travis-ci.org/magneticflux-/kotlin-simplexml-rss) 
[![codecov](https://codecov.io/gh/magneticflux-/kotlin-simplexml-rss/branch/master/graph/badge.svg)](https://codecov.io/gh/magneticflux-/kotlin-simplexml-rss) 
[![GitHub issues](https://img.shields.io/github/issues/magneticflux-/kotlin-simplexml-rss.svg)](https://github.com/magneticflux-/kotlin-simplexml-rss/issues) 
[![GitHub PRs](https://img.shields.io/github/issues-pr/magneticflux-/kotlin-simplexml-rss.svg)](https://github.com/magneticflux-/kotlin-simplexml-rss/pulls) 
[![GitHub Contributors](https://img.shields.io/github/contributors/magneticflux-/kotlin-simplexml-rss.svg)](https://github.com/magneticflux-/kotlin-simplexml-rss/graphs/contributors) 
[![GitHub license](https://img.shields.io/github/license/magneticflux-/kotlin-simplexml-rss.svg)](https://github.com/magneticflux-/kotlin-simplexml-rss/blob/master/LICENSE) 

[![Get automatic notifications about new "kotlin-simplexml-rss" versions](https://www.bintray.com/docs/images/bintray_badge_color.png)](https://bintray.com/magneticflux/kotlin-simplexml-rss/kotlin-simplexml-rss?source=watch)

# Usage
Gradle declaration
```Gradle
repositories {
    jcenter()
}

dependencies {
    implementation 'com.github.magneticflux:kotlin-simplexml-rss:$latest_version'
}
```
1. Get a `Persister`
```Kotlin
val persister = createRssPersister()
```
2. Read the input
```Kotlin
val rssFeed = persister.read(Rss::class.java, input)
```
3. ???
```
???
```
4. Profit (Use the input)
```Kotlin
title.setText(rssFeed.channel.title)
viewModel.setItems(rssFeed.channel.items)
```

# Android Support

Proguard rules
```Proguard
-keepattributes *Annotation*
-keepclassmembers class org.simpleframework.xml.** {
    <init>(...);
}
-keep class com.github.magneticflux.rss.** { *; }
-keep class * extends org.simpleframework.xml.convert.Converter
-keep class * extends org.simpleframework.xml.transform.Transform
```
Gradle declaration (More efficient timezone access on Android)
```Gradle
repositories {
    jcenter()
}

dependencies {
    implementation 'com.github.magneticflux:kotlin-simplexml-rss:$latest_version', {
        exclude group: 'org.threeten', module: 'threetenbp'
    }
    implementation 'com.jakewharton.threetenabp:threetenabp:1.0.5'
}
```

---

Copyright 2017 Mitchell Skaggs
