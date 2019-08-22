## AddNotePresenter Unit Tests

Tested Methods

-  onAttach()
-  getCurrentLocation()
-  addNote()
-  onDetach()

---

### <u>onAttach()</u>

![](images/add_note_presenter_on_attach.png)

#### Test group 0	

![](images/b.png)  branch B

```
onAttachWithNonNullViewStartLocationUpdatesCalled
```

![](images/a.png)	branch A 	view == null

```
onAttachWithNullViewStartLocationUpdatesNotCalled
```


### <u>getCurrentLocation()</u>

![](images/add_note_presenter_get_current_location.png)

#### Test group 1

![](images/b.png) 
```
getCurrentLocationWithNonNullViewDisplayCurrentLocationCalled
```
![](images/a.png) 
```
getCurrentLocationWithNullViewDisplayCurrentLocationNotCalled
```
![](images/a.png) 
```
getCurrentLocationWithViewDetachedDisplayCurrentLocationCalled
```
### <u>addNote()</u>

![](images/add_note_presenter_add_note.png)


#### Test group 2

![](images/b.png) 
```
addNoteUserAuthenticatedWithNonNullViewAddNoteCalled
```
![](images/a.png) 
```
addNoteUserAuthenticatedWithNullViewAddNoteNotCalled
```
![](images/a.png) 
```
addNoteUserAuthenticatedWithViewDetachedAddNoteNotCalled
```

#### Test group 3

![](images/c.png) 
```
addNoteUserNotAuthenticatedWithNonNullViewAddNoteNotCalled
```
![](images/a.png) 
```
addNoteUserNotAuthenticatedWithNullViewAddNoteNotCalled
```
![](images/a.png) 
```
addNoteUserNotAuthenticatedWithViewDetachedAddNoteNotCalled
```
### <u>onDetach()</u>

![](images/add_note_presenter_on_detach.png)

#### Test group 4

![](images/b.png) 
```
onDetachWithNonNullViewStartLocationUpdatesCalled
```
![](images/a.png) 
```
onDettachWithNullViewStartLocationUpdatesNotCalled
```

