## AddNotePresenter Unit Tests

Tested Methods

-  onAttach()
-  getCurrentLocation()
-  addNote()
-  onDetach()

---

### <u>onAttach()</u>

![](unit/add_note_presenter_on_attach.png)

#### Test group 0	

![](unit/b.png)  branch B

```
onAttachWithNonNullViewStartLocationUpdatesCalled
```

![](unit/a.png)	branch A 	view == null

```
onAttachWithNullViewStartLocationUpdatesNotCalled
```


### <u>getCurrentLocation()</u>

![](unit/add_note_presenter_get_current_location.png)

#### Test group 1

![](unit/b.png) 
```
getCurrentLocationWithNonNullViewDisplayCurrentLocationCalled
```
![](unit/a.png) 
```
getCurrentLocationWithNullViewDisplayCurrentLocationNotCalled
```
![](unit/a.png) 
```
getCurrentLocationWithViewDetachedDisplayCurrentLocationCalled
```
### <u>addNote()</u>

![](unit/add_note_presenter_add_note.png)


#### Test group 2

![](unit/b.png) 
```
addNoteUserAuthenticatedWithNonNullViewAddNoteCalled
```
![](unit/a.png) 
```
addNoteUserAuthenticatedWithNullViewAddNoteNotCalled
```
![](unit/a.png) 
```
addNoteUserAuthenticatedWithViewDetachedAddNoteNotCalled
```

#### Test group 3

![](unit/c.png) 
```
addNoteUserNotAuthenticatedWithNonNullViewAddNoteNotCalled
```
![](unit/a.png) 
```
addNoteUserNotAuthenticatedWithNullViewAddNoteNotCalled
```
![](unit/a.png) 
```
addNoteUserNotAuthenticatedWithViewDetachedAddNoteNotCalled
```
### <u>onDetach()</u>

![](unit/add_note_presenter_on_detach.png)

#### Test group 4

![](unit/b.png) 
```
onDetachWithNonNullViewStartLocationUpdatesCalled
```
![](unit/a.png) 
```
onDettachWithNullViewStartLocationUpdatesNotCalled
```

