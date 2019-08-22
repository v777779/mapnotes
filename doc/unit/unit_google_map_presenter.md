## GoogleMapPresenter Unit Tests

#### Tested Methods

-  handleInteractionMode()
-  handleMapNote()
-  handleLocationUpdate()
-  checkEnableGpsLocation()
-  openSettings()
-  exit()

---

### <u>handleInteractionMode()</u>

![](images/google_map_presenter_handle_interaction_mode.png)

#### Test group 0	

![](images/b.png)	branch B

```
handleInteractionModeInteractionModeWithNonNullViewAnimateCameraNotCalled
```

![](images/a.png)	branch A 	view == null

```
handleInteractionModeInteractionModeWithNullViewAnimateCameraNotCalled
```

![](images/a.png)	branch A	view detached from presenter

```
handleInteractionModeInteractionModeWithViewDetachedAnimateCameraNotCalled
```

#### Test group 1

 ![](images/c.png)

```
handleInteractionModeNotInteractionModeWithNonNullViewAnimateCameraCalled
```

![](images/a.png) 

```
handleInteractionModeNotInteractionModeWithNullViewAnimateCameraNotCalled
```

![](images/a.png) 
```
handleInteractionModeNotInteractionModeWithViewDetachedAnimateCameraNotCalled
```


### <u>handleMapNote()</u>

![](images/google_map_presenter_handle_map_note.png)

#### Test group 2	

![](images/b.png)  

```
handleMapNoteWithNonNullViewDisplayNoteOnMapCalled
```

![](images/a.png)	

```
handleMapNoteWithNullViewDisplayNoteOnMapNotCalled
```

![](images/a.png)	

```
handleMapNoteWithViewDetachedDisplayNoteOnMapNotCalled
```


### <u>handleLocationUpdate()</u>

![](images/google_map_presenter_handle_location_update.png)

#### Test group 3	

![](images/b.png)  

```
handleLocationUpdateNotInteractionModeNewLocationNotEqualsToCurrentWithNonNullViewAnimateCameraCalled
```

![](images/a.png)	

```
handleLocationUpdateNotInteractionModeNewLocationNotEqualsToCurrentWithNullViewAnimateCameraNotCalled
```

![](images/a.png)	

```
handleLocationUpdateNotInteractionModeNewLocationNotEqualsToCurrentWithViewDetachedAnimateCameraNotCalled
```

#### Test group 4

 ![](images/c.png)

```
handleLocationUpdateNotInteractionModeNewLocationEqualsToCurrentWithNonNullViewAnimateCameraNotCalled
```

![](images/a.png) 

```
handleLocationUpdateNotInteractionModeNewLocationEqualsToCurrentWithNullViewAnimateCameraNotCalled
```

![](images/a.png) 
```
handleLocationUpdateNotInteractionModeNewLocationEqualsToCurrentWithViewDetachedAnimateCameraNotCalled
```

#### Test group 5

![](images/d.png) 

```
handleLocationUpdateInteractionModeNewLocationNotEqualsToCurrentWithNonNullViewAnimateCameraNotCalled
```

![](images/a.png) 

```
handleLocationUpdateInteractionModeNewLocationNotEqualsToCurrentWithNullViewAnimateCameraNotCalled
```

![](images/a.png)  

```
handleLocationUpdateInteractionModeNewLocationNotEqualsToCurrentWithViewDetachedAnimateCameraNotCalled
```

#### Test group 6

![](images/e.png) 
```
handleLocationUpdateInteractionModeNewLocationEqualsToCurrentWithNonNullViewAnimateCameraNotCalled
```
![](images/a.png) 
```
handleLocationUpdateInteractionModeNewLocationEqualsToCurrentWithNullViewAnimateCameraNotCalled
```
![](images/a.png) 
```
handleLocationUpdateInteractionModeNewLocationEqualsToCurrentWithViewDetachedAnimateCameraNotCalled
```


### <u>checkEnableGpsLocation()</u>

![](images/google_map_presenter_check_enable_gps_location.png)

#### Test group 7	

![](images/b.png)  

```
checkEnableGpsLocationLocationAvailableWithNonNullViewShowLocationAlertDialogNotCalled
```

![](images/a.png)	

```
checkEnableGpsLocationLocationAvailableWithNullViewShowLocationAlertDialogNotCalled
```

![](images/a.png)	

```
checkEnableGpsLocationLocationAvailableWithViewDetachedShowLocationAlertDialogNotCalled
```

#### Test group 8

 ![](images/c.png)

```
checkEnableGpsLocationLocationNotAvailableWithNonNullViewShowLocationAlertDialogCalled
```

![](images/a.png) 

```
checkEnableGpsLocationLocationNotAvailableWithNullViewShowLocationAlertDialogNotCalled
```

![](images/a.png) 
```
checkEnableGpsLocationLocationNotAvailableWithViewDetachedShowLocationAlertDialogNotCalled
```


### <u>openSettings()</u>

![](images/google_map_presenter_open_settings.png)

#### Test group 9	

![](images/b.png)  

```
openSettingsWithNonNullViewOpenSettingsCalled
```

![](images/a.png)	

```
openSettingsWithNullViewOpenSettingsNotCalled
```

![](images/a.png)	

```
openSettingsWithViewDetachedOpenSettingsNotCalled
```


### <u>exit()</u>

![](images/google_map_presenter_exit.png)

#### Test group 10	

![](images/b.png)  branch B

```
exitWithNonNullViewExitCalled
```

![](images/a.png)	branch A 	view == null

```
exitWithNullViewExitNotCalled
```

![](images/a.png)	branch A	view detached from presenter

```
exitWithViewDetachedExitCalled
```


