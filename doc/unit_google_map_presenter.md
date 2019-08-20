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

![](unit/google_map_presenter_handle_interaction_mode.png)

#### Test group 0	

![](unit/b.png)	branch B

```
handleInteractionModeInteractionModeWithNonNullViewAnimateCameraNotCalled
```

![](unit/a.png)	branch A 	view == null

```
handleInteractionModeInteractionModeWithNullViewAnimateCameraNotCalled
```

![](unit/a.png)	branch A	view detached from presenter

```
handleInteractionModeInteractionModeWithViewDetachedAnimateCameraNotCalled
```

#### Test group 1

 ![](unit/c.png)

```
handleInteractionModeNotInteractionModeWithNonNullViewAnimateCameraCalled
```

![](unit/a.png) 

```
handleInteractionModeNotInteractionModeWithNullViewAnimateCameraNotCalled
```

![](unit/a.png) 
```
handleInteractionModeNotInteractionModeWithViewDetachedAnimateCameraNotCalled
```


### <u>handleMapNote()</u>

![](unit/google_map_presenter_handle_map_note.png)

#### Test group 2	

![](unit/b.png)  

```
handleMapNoteWithNonNullViewDisplayNoteOnMapCalled
```

![](unit/a.png)	

```
handleMapNoteWithNullViewDisplayNoteOnMapNotCalled
```

![](unit/a.png)	

```
handleMapNoteWithViewDetachedDisplayNoteOnMapNotCalled
```


### <u>handleLocationUpdate()</u>

![](unit/google_map_presenter_handle_location_update.png)

#### Test group 3	

![](unit/b.png)  

```
handleLocationUpdateNotInteractionModeNewLocationNotEqualsToCurrentWithNonNullViewAnimateCameraCalled
```

![](unit/a.png)	

```
handleLocationUpdateNotInteractionModeNewLocationNotEqualsToCurrentWithNullViewAnimateCameraNotCalled
```

![](unit/a.png)	

```
handleLocationUpdateNotInteractionModeNewLocationNotEqualsToCurrentWithViewDetachedAnimateCameraNotCalled
```

#### Test group 4

 ![](unit/c.png)

```
handleLocationUpdateNotInteractionModeNewLocationEqualsToCurrentWithNonNullViewAnimateCameraNotCalled
```

![](unit/a.png) 

```
handleLocationUpdateNotInteractionModeNewLocationEqualsToCurrentWithNullViewAnimateCameraNotCalled
```

![](unit/a.png) 
```
handleLocationUpdateNotInteractionModeNewLocationEqualsToCurrentWithViewDetachedAnimateCameraNotCalled
```

#### Test group 5

![](unit/d.png) 

```
handleLocationUpdateInteractionModeNewLocationNotEqualsToCurrentWithNonNullViewAnimateCameraNotCalled
```

![](unit/a.png) 

```
handleLocationUpdateInteractionModeNewLocationNotEqualsToCurrentWithNullViewAnimateCameraNotCalled
```

![](unit/a.png)  

```
handleLocationUpdateInteractionModeNewLocationNotEqualsToCurrentWithViewDetachedAnimateCameraNotCalled
```

#### Test group 6

![](unit/e.png) 
```
handleLocationUpdateInteractionModeNewLocationEqualsToCurrentWithNonNullViewAnimateCameraNotCalled
```
![](unit/a.png) 
```
handleLocationUpdateInteractionModeNewLocationEqualsToCurrentWithNullViewAnimateCameraNotCalled
```
![](unit/a.png) 
```
handleLocationUpdateInteractionModeNewLocationEqualsToCurrentWithViewDetachedAnimateCameraNotCalled
```


### <u>checkEnableGpsLocation()</u>

![](unit/google_map_presenter_check_enable_gps_location.png)

#### Test group 7	

![](unit/b.png)  

```
checkEnableGpsLocationLocationAvailableWithNonNullViewShowLocationAlertDialogNotCalled
```

![](unit/a.png)	

```
checkEnableGpsLocationLocationAvailableWithNullViewShowLocationAlertDialogNotCalled
```

![](unit/a.png)	

```
checkEnableGpsLocationLocationAvailableWithViewDetachedShowLocationAlertDialogNotCalled
```

#### Test group 8

 ![](unit/c.png)

```
checkEnableGpsLocationLocationNotAvailableWithNonNullViewShowLocationAlertDialogCalled
```

![](unit/a.png) 

```
checkEnableGpsLocationLocationNotAvailableWithNullViewShowLocationAlertDialogNotCalled
```

![](unit/a.png) 
```
checkEnableGpsLocationLocationNotAvailableWithViewDetachedShowLocationAlertDialogNotCalled
```


### <u>openSettings()</u>

![](unit/google_map_presenter_open_settings.png)

#### Test group 9	

![](unit/b.png)  

```
openSettingsWithNonNullViewOpenSettingsCalled
```

![](unit/a.png)	

```
openSettingsWithNullViewOpenSettingsNotCalled
```

![](unit/a.png)	

```
openSettingsWithViewDetachedOpenSettingsNotCalled
```


### <u>exit()</u>

![](unit/google_map_presenter_exit.png)

#### Test group 10	

![](unit/b.png)  branch B

```
exitWithNonNullViewExitCalled
```

![](unit/a.png)	branch A 	view == null

```
exitWithNullViewExitNotCalled
```

![](unit/a.png)	branch A	view detached from presenter

```
exitWithViewDetachedExitCalled
```


