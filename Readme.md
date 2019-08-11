![java](https://img.shields.io/badge/code-java-blue) 

This is Java/RxJava2/Dagger2  clone of [MapNotes](https://github.com/AlexZhukovich/MapNotes) project created by[ Alex Zhukovich](https://github.com/AlexZhukovich/)

# Map Notes

An Android application which allows you to add/remove notes to the current location 

![screen_001_short](doc\screen_001.png)

![screen_002_short](doc\screen_002.png)

![screen_002_short](doc\screen_003.png)



# Frameworks and Tools

* Android SDK
* Firebase Authentication
* Firebase Realtime Database
* Firebase Crashlytics
* Firebase Analytics
* Google Maps
* Service Location
* Espresso
* Java
* RxJava2 
* Dagger2



# Configuration
* Firebase project should be created, and ```google-services.json``` should be added to the **app** folder.

* Google Maps API key should be stored to **strings.xml** file

  


# Features
* Creating account/Login/ Log out, use email, password and user name

* Adding public notes with current location (notes will be available to other users)

* Removing notes by swipe left, right in search mode (added to original project)

* Search by Notes/Users

* Notes overview on the map

* Interaction mode of the map

* Adding markers to the map by search screen

* Removing all markers by pressing "back"

  

# Setup Project

- Setup of project includes setup application and setup server side of project



## Setup Application

- Clone repository or download zip archive and unpack onto your drive
- Install Android Studio 3.4.2
- Install Build Tools 29.0.0 if necessary
- Setup Server Side of Project
- Build and Run application



## Setup Server Side of Project

- Setup Firebase Project
- Setup Firebase Authentication
- Setup Firebase Realtime Database
- Setup Google Maps
- Setup Firebase Crashlytics
- Setup Firebase Analytics



# Setup Firebase Project

First of all you need Google account to work with [Firebase Console](https://console.firebase.google.com/) and  [Google Cloud Console](https://console.cloud.google.com/). Create new Google Account or user existing one.

Open [Firebase Console](https://console.firebase.google.com/) and create Firebase project, see [documentaition](https://firebase.google.com/docs/android/setup) if necessary

![](doc\screen_004.png) 

Check Setup Firebase Analytics and press "Create project"

![](doc\screen_005.png)

![](doc\screen_006.png)

Open Firebase Project, go to Project Overview, press Add Android application

![](doc\screen_007.png)

To Register application we need package name and SHA-1 key

Open Android Studio Gradle console, double click on app/Tasks/android/signingReport and find SHA-1 below

![](doc\screen_008.png)

Enter package name and SHA-1 key  and register application. 

![](doc\screen_009.png)

Download **google-services.json** file and place it  to **app/** folder of the application

Press Next button several times until Firebase Verification window opens.

Build and Run application, wait until Firebase detects application.

![](doc\screen_010.png)

Firebase Project created and application successfully added



# Setup Firebase Authentication

Open  [Firebase Console](https://console.firebase.google.com/) , go to Authentication, open "Sign-in method" and enable Email/Password authentication

![screen_010](doc\screen_011.png)

![](doc\screen_012.png)

Firebase Authentication setup finished



# Setup Firebase Database

Open  [Firebase Console](https://console.firebase.google.com/) , go to Database, scroll to Realtime Database 

Click on "Create database", select "Test mode". This is demo application, so all users have full access to database. Press Enable to create Database

![cloud](doc/screen_014.png)

Press "Dismiss" button  to hide message. This is demo application, so all users have full access to database. Press Enable to create Database

![cloud](doc/screen_015.png)Database created in Test mode. 

![cloud](doc/screen_016.png)

Setup Firebase Realtime Database is done



# Setup Google Maps

Open [Google Cloud Console](https://console.cloud.google.com/), use your Google account to get access

Select project that you have created in Firebase Console for Map Notes application and press "Open"

![cloud](doc/screen_017.png)

Open Navigation Menu, API& Services, Dashboard

Press "Enable APIs and Services", find Maps SDK for Android

Open "Maps SDK for Android" and press Enable

![key](doc/screen_018.png)

Go to Navigation Menu, API& Services, Credentials

Find "Android API Key" and copy it to the clipboard, this key will be used for Google Maps

![key](doc/screen_019.png)

Place this key to **strings.xml** file instead of "YOUR_API_KEY" words

```xml
<!--google_maps_api_key-->
<string name="google_maps_key" templateMergeStrategy="preserve" translatable="false">
        AIzaSyBHRhcbqML9A_PJWZ-MZ9KH_OfCrsMNvlM
</string>

```

Setup Google Maps is done. 



# Get Started

Build and Run application. Open Sign Up Screen. Enter name, email and password. Press "Sign Up" button

Google Maps should open and show current location of the device.

![key](doc/screen_020.png)

Press Add Note button and enter "Google Plex" label of note and press "**ADD**" button

![key](doc/screen_021.png)

Location marker will appear on Map. Press "Search Notes" button. The list with note "Google Plex" will be displayed. Swipe left or right over note to delete.

At this moment application ready to work.

## Firebase Project Verification

Open Firebase Project Authentication tab to look at the identified users

![key](doc/screen_022.png)

Open Firebase Project Database tab to look at the entered notes and users records

![environment](doc\screen_023.png)

## Setup Firebase Crashlytics

Firebase Crashlytics will track application crash events only.

Open Firebase Project Crashlytics, see [documentation](https://firebase.google.com/docs/crashlytics?authuser=0) if necessary

![environment](doc\screen_024.png)

Press Next, then Build and Run Application. Wait until Crashlytics verification completed

![environment](doc\screen_025.png)

Firebase Crashlytics will track application crash only

![environment](doc\screen_026.png)

To check Crashlytics tracking ability place next code in to onCreate() method of SplashActivity.

Then build and run application. Crashlytics will show crash analytics

```java
Crashlytics.getInstance().crash();
```

Setup Crashlytics is done.

## Setup Firebase Analytics

Firebase Analytics sends in the application sends next information:

-  screen class names in all activities
- image and login events in SignIn and SignUp activities
- location events in GoogleMapFragment.

Firebase Analytics actually was already set during Firebase Project creation procedure.

Firebase Analytics provides long term statistics, fresh data within 30 minutes and live data in debug mode

Open Firebase Analytics Dashboard to look at long term statistics.

Press on StreamView to get analytics within 30 minutes window.

![environment](doc\screen_027.png)

Firebase Analytics can be tested in Debug View mode, where analytics is shown immediately.

To Enable DebugView on the application side send next command in Terminal of Android Studio. Be sure that current folder includes adb.exe tool.

```
adb shell setprop debug.firebase.analytics.app ru.vpcb.map.notes
```

Build and run application. Move over screens, sign in, open notes.

Open Firebase Project Analytics DebugView tab and look at the events in live mode.

![debug](doc\screen_029.png) 

To Disable DebugView mode on the application side send next command in Terminal of Android Studio. 

```
adb shell setprop debug.firebase.analytics.app none
```

Firebase Analytics verification is done.

## Conclusion

At this moment you should have fully functional application.

