# FloatingOverlayView
Floating view with draw over other apps 
it uses system alert window with the permission `android.permission.SYSTEM_ALERT_WINDOW` under the hood

[![](https://jitpack.io/v/javaherisaber/FloatingOverlayView.svg)](https://jitpack.io/#javaherisaber/FloatingOverlayView)
[![Download](https://img.shields.io/badge/Android%20Arsenal-FloatingOverlayView-green.svg)](https://android-arsenal.com/details/1/8458)

## Demo
<div style="dispaly:flex">
    <img src="/demo.gif" width="30%">
</div>

## Build
##### Step 1. Add the JitPack repository to your build file
```bat
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

##### Step 2. Add the dependency
```bat
dependencies {
    implementation 'com.github.javaherisaber:FloatingOverlayView:1.0.2'
}
```

## Usage
### Layout.xml
##### Step 1. Create layout and don't forget add id (root_container) like this
```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <FrameLayout
        android:id="@+id/root_container"
        android:layout_width="250dp"
        android:layout_height="200dp">

        ....

    </FrameLayout>

</FrameLayout>
```

### Kotlin/Java
##### Step 2. Create FloatingOverlayView
```kotlin
FloatingOverlayView(context, R.layout.view_overlay)
    .setXOffset(800) // move the banner to the right side
    .setOnCreateListener { layout: FloatingOverlayView, view: View ->
        // do something with `view`, maybe a close button
    }
    .setOnCloseListener {
        // do something after the banner is closed
    }
    .create()
```
