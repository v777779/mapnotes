## HomePresenter Unit Tests

#### Tested Methods


-  handleNavigationItemClick()
-  showLocationPermissionRationale()
-  showLocationRequirePermissions()
-  checkUser()
-  signOut()

---

### <u>handleNavigationItemClick()</u>

![](images/home_presenter_handle_navigation_item_click.png)

#### Test group 0	

![](images/b.png)  branch B

```
handleNavigationItemClickNavigationAddNoteWithNonNullViewDisplayAddNoteCalledReturnTrue
```

![](images/a.png)	branch A 	view == null

```
handleNavigationItemClickNavigationAddNoteWithNullViewDisplayAddNoteNotCalledReturnFalse
```

![](images/a.png)	branch A	view detached from presenter

```
handleNavigationItemClickNavigationAddNoteWithViewDetachedDisplayAddNoteNotCalledReturnFalse
```

#### Test group 1

 ![](images/c.png)

```
handleNavigationItemClickNavigationMapWithNonNullViewUpdateNavigationStateCalledReturnTrue
```

![](images/a.png) 

```
handleNavigationItemClickNavigationMapWithNullViewUpdateNavigationStateNotCalledReturnFalse
```

![](images/a.png) 

```
handleNavigationItemClickNavigationMapWithViewDetachedUpdateNavigationStateNotCalledReturnFalse
```

#### Test group 2

![](images/d.png) 

```
handleNavigationItemClickNavigationSearchNotesWithNonNullViewDisplaySearchNotesCalledReturnTrue
```

![](images/a.png) 

```
handleNavigationItemClickNavigationSearchNotesWithNullViewDisplaySearchNotesNotCalledReturnFalse
```

![](images/a.png)  

```
handleNavigationItemClickNavigationSearchNotesWithViewDetachedDisplaySearchNotesNotCalledReturnFalse
```
#### Test group3

![](images/e.png) 

```
handleNavigationItemClickUnknownResourceWithNonNullViewThrowIllegalArgumentException
```

![](images/a.png) 

```
handleNavigationItemClickUnknownResourceWithNullViewNotThrowIllegalArgumentExceptionReturnFalse
```

![](images/a.png)  

```
showLocationPermissionRationaleWithNonNullViewShowPermissionExplanationSnackBarCalled
```



### <u>showLocationPermissionRationale()</u>

![](images/home_presenter_show_location_permission_rationale.png)

#### Test group 4

![](images/b.png) 
```
showLocationPermissionRationaleWithNonNullViewShowPermissionExplanationSnackBarCalled
```
![](images/a.png) 
```
showLocationPermissionRationaleWithNullViewShowPermissionExplanationSnackBarNotCalled
```
![](images/a.png) 
```
showLocationPermissionRationaleWithViewDetachedShowPermissionExplanationSnackBarNotCalled
```



### <u>showLocationRequirePermissions()</u>

![](images/home_presenter_show_location_require_permissions.png)

#### Test group 5

![](images/b.png) 
```
showLocationRequirePermissionsWithNonNullViewShowContentWhichRequirePermissionsCalled
```
![](images/a.png) 
```
showLocationRequirePermissionsWithNullViewShowContentWhichRequirePermissionsNotCalled
```
![](images/a.png) 
```
showLocationRequirePermissionsWithViewDetachedShowContentWhichRequirePermissionsNotCalled
```



### <u>checkUser()</u>

![](images/home_presenter_check_user.png)

#### Test group 6

![](images/b.png) 
```
checkUserUserAuthenticatedWithNonNullViewNavigateToLoginScreenNotCalled
```
![](images/a.png) 
```
checkUserUserAuthenticatedWithNullViewNavigateToLoginScreenNotCalled
```
![](images/a.png) 
```
checkUserUserAuthenticatedWithViewDetachedNavigateToLoginScreenNotCalled
```

#### Test group 7

![](images/c.png) 
```
checkUserUserNotAuthenticatedWithNonNullViewNavigateToLoginScreenCalled
```
![](images/a.png) 
```
checkUserUserNotAuthenticatedWithNullViewNavigateToLoginScreenNotCalled
```
![](images/a.png) 
```
checkUserUserNotAuthenticatedWithViewDetachedNavigateToLoginScreenNotCalled
```

### <u>signOut()</u>

![](images/home_presenter_sign_out.png)

#### Test group 8

![](images/b.png) 
```
signOutWithNonNullViewSignOutCalled
```
![](images/a.png) 
```
signOutWithNullViewSignOutNotCalled
```
![](images/a.png) 
```
signOutWithViewDetachedSignOutNotCalled
```

