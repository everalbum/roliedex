# Roliedex

An Android View to show independently spinning digits.

![Demo](/roliedex_demo.gif "roliedex demo")

## Usage

Take a look at the same app included in this repo for usages. A typical case may look like this:

    ```
    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        ... />
        
        <com.everalbum.roliedex.RoliedexLayout
            android:id="@+id/counter"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:gravity="center_vertical"
            android:orientation="horizontal"
            app:numberOfDigits="3"
            app:numberOfDecimals="2"
            app:textColor="#008eff"
            app:textSize="40"
            />
            
    </LinearLayout>
    ```
    
Below is also documentation on the individual attributes we support.

## Including Library

Simply imported it into your gradle project like so:

```groovy
compile 'com.everalbum.roliedex:roliedex:1.0.3'
```

Or if you like using [Jitpack.io](https://jitpack.io) you can include it in your gradle file like so:

```groovy
repositories {
  maven { url "https://jitpack.io" }
}

dependencies {
  compile 'com.everalbum.roliedex:roliedex:1.0.3'
}
```

Be sure to visit [release page to verify latest version](https://github.com/everalbum/roliedex/releases)... sometimes new versions are released without update to this page (Readme.md).

## Supported attributes (with default values)

 - **LinearLayout Attributes**
 
    ```RoliedexLayout``` extends ```LinearLayout```, so any of attributes of ```LinearLayout``` can be used.
    
    ```
    android:gravity="center_vertical"
    android:orientation="horizontal"
    ```

 - **Animation Duration**
 
    Time the numbers will spin before they show the final.
    
    ```
    app:animDuration="500"
    ```
     
 - **Text Size**
 
    ```
    app:textSize="16"
    ```

 - **Text Color**
 
    ```
    app:textColor="android:color/black"
    ```
    
 - **Number of digits before decimal**
 
    ```
    app:numberOfDigits="3"
    ```
     
 - **Number of decimals**
 
    ```
    app:numberOfDecimals="2"
    ```
    
 - **Slide In Animation**
 
    ```
    app:slideInAnimation="@anim/slide_in_from_bottom"
    ```
 
 - **Slide Out Animation**
 
    ```
    app:slideOutAnimation="@anim/slide_out_to_top"
    ```

## Limitations & Planned Improvements

 - We only support numbers. Having spinning chars would be cool.
 - The animation spinning the digits is random. It would be nice to have it synchronized. So that you can have a countdown timer, or clock, or increasing ticker.
 - We only support positive, greater than zero values.
 - We currently show all trailing decimal digits. So if the number is `0.1` and you pass `app:numberOfDecimals="2"` we will display `0.10`.
 - We currently do not support commas or dots to format large numbers.
 - No localization support :(
 
## Releasing

Run gradle command:
```
./gradlew clean build generateRelease
```

Packages to upload will be under:
roliedex/build/release-<versiob>.zip

Upload these:
1) Go to http://bintray.com/everalbum/maven/roliedex
2) Click "New Version"
3) Click into new version
4) Click "Upload Files""
5) Drag zip file to upload
6) Make sure to check box "Explode this archive"
7) Click "Save Changes"

Check out this guide: https://medium.com/@tigr/how-to-publish-your-android-studio-library-to-jcenter-5384172c4739#.s2ixju5xl