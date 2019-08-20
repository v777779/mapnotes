# Unit Tests Guide

Unit tests used for local testing on JVM to verify that the login of individual units is correct.

Unit tests  written for activity presenters, fragment presenters, and user classes.

Test method names include tested method name, state of input parameters and output result description

For example:

```
startWithPlayServicesAvailableUserNotAuthenticatedNonNullViewNavigateToLoginCalled
```

- method name 		start()

- playServices			available

- user					 	not authenticated

- view						non null

- result		       		navigateToLogin() method called

#### Tested Activity Presenters:  

- SplashPresenter, LoginPresenter, SignInPresenter, SignUpPresenter, HomePresenter
- Every method tested with  three variants  of view that represents AppCompatActivity
  - view is non null
  - view is null
  - view is non null but detached from presenters

#### Tested Fragment Presenters:

- AddNotePresenter,  GoogleMapPresenter,  SearchNotesPresenter
- Every method tested with  three variants of view, that represents Fragment Activity
  - view is non null
  - view is null
  - view is non null but detached from presenters

#### Tested user classes:

- ValidationExt, CoordinateFormatter



## Activity Presenter Tests

#### SplashPresenter Tests

Tested Methods

-  start()
- startMapNotes()
- playMarketResults()
- onPositive()
- onNegative()

See [source](../app/src/test/java/ru/vpcb/map/notes/activity/splash/SplashPresenterTests.java) for test methods and  [splash_presenter_tests](unit_splash_presenter.md) for the description.

#### LoginPresenter Tests

Tested Methods

- openSignIn()
- openSignup()

See [source](../app/src/test/java/ru/vpcb/map/notes/activity/login/LoginPresenterTests.java) for test methods and  [login_presenter_tests](unit_login_presenter.md) for the description.

#### SignInPresenter Tests

Tested Methods

- signIn()

See [source](../app/src/test/java/ru/vpcb/map/notes/activity/login/signin/SIgnInPresenterTests.java) for test methods and  [sign_in_presenter_tests](unit_sign_in_presenter.md) for the description.

#### SignUpPresenter Tests

Tested Methods

- signUp()

See [source](../app/src/test/java/ru/vpcb/map/notes/activity/login/signup/SignUpPresenterTests.java) for test methods and  [sign_up_presenter_tests](unit_sign_up_presenter.md) for the description.

#### HomePresenter Tests

Tested Methods

- handleNavigationItemClick()
- showLocationPermissionRationale()
- showLocationRequirePermissions()
- checkUser()
- signOut()

See [source](../app/src/test/java/ru/vpcb/map/notes/activity/home/HomePresenterTests.java) for test methods and  [home_presenter_tests](unit_home_presenter.md) for the description.



## Fragment Presenters Tests

#### AddNotePresenter Tests

Tested Methods

- onAttach()
- getCurrentLocation()
- addNote()
- onDetach()


See [source](../app/src/test/java/ru/vpcb/map/notes/fragments/add/AddNotePresenterTests.java) for test methods and  [add_note_presenter_tests](unit_add_note_presenter.md) for the description.

#### GoogleMapPresenter Tests

Tested Methods

- handleInteractionMode()
- handleMapNote()
- handleLocationUpdate()
- checkEnableGpsLocation()
- open Settings()
- exit()

See [source](../app/src/test/java/ru/vpcb/map/notes/fragments/map/GoogleMapPresenterTests.java) for test methods and  [google_map_presenter_tests](unit_google_map_presenter.md) for the description.

#### SearchNotesPresenter Tests

Tested Methods

- getNotes()
- searchNotes()
- onPositive()
- onNegative()

See [source](../app/src/test/java/ru/vpcb/map/notes/fragments/search/SearchNotesPresenterTests.java) for test methods and  [search_notes_presenter_tests](unit_search_notes_presenter.md) for the description.



## User Classes Tests

#### ValidationExt Tests

Tested Methods

- isValidEmail()

See [source](../app/src/test/java/ru/vpcb/map/notes/ext/ValidationExtTests.java) for test methods and  [validation_ext_tests](unit_validation_ext.md) for the description.

#### CoordinateFormatter Tests

Tested Methods

- format()

See [source](../app/src/test/java/ru/vpcb/map/notes/data/formatter/CoordinateFormatterTests.java) for test methods and  [coordinate_formatter_tests](unit_coordinate_formatter.md) for the description.









