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

See [source](../app/src/test/java/ru/vpcb/map/notes/activity/splash/SplashPresenterTests.java) for test methods and  [unit_splash_presenter](unit_splash_presenter.md) for the description.

#### LoginPresenter Tests

Tested Methods

- openSignIn()
- openSignup()
- See [source](../app/src/test/java/ru/vpcb/map/notes/activity/login/LoginPresenterTests.java) for test methods and  [unit_login_presenter](unit_login_presenter.md) for the description.

#### SignInPresenter Tests

Tested Methods

- signIn()
- See [source](../app/src/test/java/ru/vpcb/map/notes/activity/login/signin/SIgnInPresenterTests.java) for test methods and  [unit_sign_in_presenter](unit_sign_in_presenter.md) for the description.

#### SignUpPresenter Tests

Tested Methods

- signUp()

See [source](../app/src/test/java/ru/vpcb/map/notes/activity/login/signup/SignUpPresenterTests.java) for test methods and  [unit_sign_up_presenter](unit_sign_up_presenter.md) for the description.

#### HomePresenter Tests

Tested Methods

- start()
- startMapNotes()
- playMarketResults()
- onPositive()
- onNegative()

See [source](../app/src/test/java/ru/vpcb/map/notes/activity/splash/SplashPresenterTests.java) for test methods and  [unit home_presenter_doc](unit_home_presenter_.md) for the description.



## Fragment Presenters Tests

#### AddNotePresenter Tests

Tested Methods

- start()
- startMapNotes()
- playMarketResults()
- onPositive()
- onNegative()

See [source](../app/src/test/java/ru/vpcb/map/notes/activity/splash/SplashPresenterTests.java) for test methods and  [unit_add_note_presenter_doc](unit_add_note_presenter.md) for the description.

#### GoogleMapPresenter Tests

Tested Methods

- start()
- startMapNotes()
- playMarketResults()
- onPositive()
- onNegative()

See [source](../app/src/test/java/ru/vpcb/map/notes/activity/splash/SplashPresenterTests.java) for test methods and  [unit_google_map_presenter_doc](unit_google_map_presenter.md) for the description.

#### SearchNotesPresenter Tests

Tested Methods

- start()
- startMapNotes()
- playMarketResults()
- onPositive()
- onNegative()

See [source](../app/src/test/java/ru/vpcb/map/notes/activity/splash/SplashPresenterTests.java) for test methods and  [unit_search_notes_presenter](unit_search_notes_presenter.md) for the description.



## User Classes Tests

#### ValidationExt Tests

Tested Methods

- start()
- startMapNotes()
- playMarketResults()
- onPositive()
- onNegative()

See [source](../app/src/test/java/ru/vpcb/map/notes/activity/splash/SplashPresenterTests.java) for test methods and  [unit_validation_ext](unit_validation_ext.md) for the description.

#### CoordinateFormatter Tests

Tested Methods

- start()
- startMapNotes()
- playMarketResults()
- onPositive()
- onNegative()

See [source](../app/src/test/java/ru/vpcb/map/notes/activity/splash/SplashPresenterTests.java) for test methods and  [unit_coordinate_formatter](unit_coordinate_formatter.md) for the description.









