## HomePresenter Unit Tests

#### Tested Methods


-  handleNavigationItemClick()
-  showLocationPermissionRationale()
-  showLocationRequirePermissions()
-  checkUser()
-  signOut()



### <u>handleNavigationItemClick()</u>

![](unit/home_presenter_handle_navigation_item_click.png)

#### Test group 0	

![](unit/b.png)  branch B

```
handleNavigationItemClickNavigationAddNoteWithNonNullViewDisplayAddNoteCalledReturnTrue
```

![](unit/a.png)	branch A 	view == null

```
handleNavigationItemClickNavigationAddNoteWithNullViewDisplayAddNoteNotCalledReturnFalse
```

![](unit/a.png)	branch A	view detached from presenter

```
handleNavigationItemClickNavigationAddNoteWithViewDetachedDisplayAddNoteNotCalledReturnFalse
```

#### Test group 1

 ![](unit/c.png)

```
handleNavigationItemClickNavigationMapWithNonNullViewUpdateNavigationStateCalledReturnTrue
```

![](unit/a.png) 

```
handleNavigationItemClickNavigationMapWithNullViewUpdateNavigationStateNotCalledReturnFalse
```

![](unit/a.png) 

```
handleNavigationItemClickNavigationMapWithViewDetachedUpdateNavigationStateNotCalledReturnFalse
```

#### Test group 2

![](unit/d.png) 

```
handleNavigationItemClickNavigationSearchNotesWithNonNullViewDisplaySearchNotesCalledReturnTrue
```

![](unit/a.png) 

```
handleNavigationItemClickNavigationSearchNotesWithNullViewDisplaySearchNotesNotCalledReturnFalse
```

![](unit/a.png)  

```
handleNavigationItemClickNavigationSearchNotesWithViewDetachedDisplaySearchNotesNotCalledReturnFalse
```
#### Test group3

![](unit/e.png) 

```
handleNavigationItemClickUnknownResourceWithNonNullViewThrowIllegalArgumentException
```

![](unit/a.png) 

```
handleNavigationItemClickUnknownResourceWithNullViewNotThrowIllegalArgumentExceptionReturnFalse
```

![](unit/a.png)  

```
showLocationPermissionRationaleWithNonNullViewShowPermissionExplanationSnackBarCalled
```



### <u>showLocationPermissionRationale()</u>

![](unit/home_presenter_show_location_permission_rationale.png)

#### Test group 4

![](unit/b.png) 
```
showLocationPermissionRationaleWithNonNullViewShowPermissionExplanationSnackBarCalled
```
![](unit/a.png) 
```
showLocationPermissionRationaleWithNullViewShowPermissionExplanationSnackBarNotCalled
```
![](unit/a.png) 
```
showLocationPermissionRationaleWithViewDetachedShowPermissionExplanationSnackBarNotCalled
```



### <u>showLocationRequirePermissions()</u>

![](unit/home_presenter_show_location_require_permissions.png)

#### Test group 5

![](unit/b.png) 
```
showLocationRequirePermissionsWithNonNullViewShowContentWhichRequirePermissionsCalled
```
![](unit/a.png) 
```
showLocationRequirePermissionsWithNullViewShowContentWhichRequirePermissionsNotCalled
```
![](unit/a.png) 
```
showLocationRequirePermissionsWithViewDetachedShowContentWhichRequirePermissionsNotCalled
```



### <u>checkUser()</u>

![](unit/home_presenter_check_user.png)

#### Test group 6

![](unit/b.png) 
```
checkUserUserAuthenticatedWithNonNullViewNavigateToLoginScreenNotCalled
```
![](unit/a.png) 
```
checkUserUserAuthenticatedWithNullViewNavigateToLoginScreenNotCalled
```
![](unit/a.png) 
```
checkUserUserAuthenticatedWithViewDetachedNavigateToLoginScreenNotCalled
```

#### Test group 7

![](unit/c.png) 
```
checkUserUserNotAuthenticatedWithNonNullViewNavigateToLoginScreenCalled
```
![](unit/a.png) 
```
checkUserUserNotAuthenticatedWithNullViewNavigateToLoginScreenNotCalled
```
![](unit/a.png) 
```
checkUserUserNotAuthenticatedWithViewDetachedNavigateToLoginScreenNotCalled
```

### <u>signOut()</u>

![](unit/home_presenter_sign_out.png)

#### Test group 8

![](unit/b.png) 
```
signOutWithNonNullViewSignOutCalled
```
![](unit/a.png) 
```
signOutWithNullViewSignOutNotCalled
```
![](unit/a.png) 
```
signOutWithViewDetachedSignOutNotCalled
```

