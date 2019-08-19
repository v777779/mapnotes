![java](https://img.shields.io/badge/code-java-blue) 

# Map Notes

An Android application which allows you to add/remove notes to the current location 

![](doc/images/screen_020.png)

![](doc/images/screen_021.png)



# Frameworks and Tools

* Android SDK
* Firebase Authentication
* Firebase Realtime Database
* Firebase Crashlytics
* Firebase Analytics
* Google Maps
* Service Location
* Robolectric
* Mockito
* Espresso
* RxJava2 
* Dagger2





# Configuration
* Configuration requires Firebase Project and Google Maps SDK, see [installation guide](doc/installation_guide.md) for the details.





# Features
* Creating account/Login/ Log out, use email, password and user name

* Adding public notes with current location (notes will be available to other users)

* Removing notes by swipe left, right in search mode (added to original project)

* Search by Notes/Users

* Notes overview on the map

* Interaction mode of the map

* Adding markers to the map by search screen

* Removing all markers by pressing "back"

  



# Get Started

Setup Firebase Project and Google Maps SDK for Android according to [installation guide](doc/installation_guide)

Build and run application. 

Open Sign Up Screen. Enter name, email and password. Press "Sign Up" button

Google Maps will open and show current location of the device.

Press "Add Note" button and enter "Google Plex" label of note and press "**ADD**" button

Location marker will appear on Map. Press "Search Notes" button. The list of notes will be displayed.

Swipe note right or left to delete.





# Unit Tests
Unit tests based on Robolectric and Mockito frameworks added to application, see [unit tests guide](doc/unit_tests_guide.md) for the details.





## Copyrights

This project is java/rxJava2/dagger2 clone of [MapNotes](https://github.com/AlexZhukovich/MapNotes) project based on kotlin/coroutines/koin created by [Alex Zhukovich](https://github.com/AlexZhukovich/)

