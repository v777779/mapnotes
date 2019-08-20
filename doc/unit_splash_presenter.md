## SplashPresenter Unit Tests

#### Tested Methods

-  start()
- startMapNotes()
- playMarketResults()
- onPositive()
- onNegative()

### <u>start()</u>

![](unit/splash_presenter_start.png)

#### Test group 0	

![](unit/b.png)  branch B

```
startWithPlayServicesAvailableUserAuthenticatedNonNullViewNavigateToHomeCalled
```

![](unit/a.png)	branch A 	view == null

```
startWithPlayServicesAvailableUserAuthenticatedNullViewStartNavigateToHomeNotCalled
```

![](unit/a.png)	branch A	view detached from presenter

```
startWithPlayServicesAvailableUserAuthenticatedWithViewDetachedNavigateToHomeNotCalled
```

#### Test group 1

 ![](unit/c.png)

```
startWithPlayServicesAvailableUserNotAuthenticatedNonNullViewNavigateToLoginCalled
```

![](unit/a.png) 

```
startWithPlayServicesAvailableUserNotAuthenticatedNullViewNavigateToLoginNotCalled
```

![](unit/a.png) 

```
startWithPlayServicesAvailableUserNotAuthenticatedWithViewDetachedNavigateToLoginNotCalled
```

#### Test group 2

![](unit/d.png) 

```
startWithPlayServicesNotAvailablePlayMarketInstalledNonNullViewGetErrorDialog
```

![](unit/a.png) 

```
startWithPlayServicesNotAvailablePlayMarketInstalledNullViewViewGetErrorDialogNotCalled
```

![](unit/a.png)  

```
startWithPlayServicesNotAvailablePlayMarketInstalledWithViewDetachedViewGetErrorDialogNotCalled
```

#### Test group 3

![](unit/e.png) 

```
startWithPlayServicesNotAvailablePlayMarketNotInstalledNonNullViewGetAlertDialog
```

![](unit/a.png) 

```
startWithPlayServicesNotAvailablePlayMarketNotInstalledNullViewGetAlertDialogNotCalled
```

![](unit/a.png) 

```
startWithPlayServicesNotAvailablePlayMarketNotInstalledWithViewDetachedGetAlertDialogNotCalled
```



### <u>startmapNotes()</u>



![](unit/splash_presenter_start_map_notes.png)

#### Test group 4

![](unit/b.png) 

```
startMapNotesWithUserAuthenticatedWithNonNullViewNavigateToHomeCalled
```

![](unit/a.png) 

```
startMapNotesWithUserAuthenticatedWithNullViewNavigateToHomeNotCalled
```

![](unit/a.png) 

```
startMapNotesWithUserAuthenticatedWithViewDetachedNavigateToHomeNotCalled
```

#### Test group 5

![](unit/c.png) 

```
startMapNotesWithUserNotAuthenticatedWithNonNullViewNavigateToLoginCalled
```

![](unit/a.png) 

```
startMapNotesWithUserNotAuthenticatedWithNonNullViewNavigateToLoginCalled
```

  ![](unit/a.png) 

```
startMapNotesWithUserNotAuthenticatedWithNullViewNavigateToLoginNotCalled
```



### <u>playMarketResults()</u>



![](unit/splash_presenter_play_market_results.png)

#### Test group 6

![](unit/b.png) 

```
playMarketResultsWithGPSRequestCodeUserAuthenticatedPlayServiceAvailableWithNonNullViewNavigateToHomeCalled
```

![](unit/a.png) 

```
playMarketResultsWithGPSRequestCodeUserAuthenticatedPlayServiceAvailableWithNullViewNavigateToHomeNotCalled
```

![](unit/a.png) 

```
playMarketResultsWithGPSRequestCodeUserAuthenticatedPlayServiceAvailableWithViewDetachedNavigateToHomeNotCalled
```

#### Test group 7

![](unit/c.png) 

```
playMarketResultsWithRequestGPSRequestCodeUserNotAuthenticatedPlayServiceAvailableWithNonNullViewNavigateToLoginCalled
```

![](unit/a.png) 

```
playMarketResultsWithGPSRequestCodeUserNotAuthenticatedPlayServiceAvailableWithNullViewNavigateToLoginCalledNotCalled
```

![](unit/a.png) 

```
playMarketResultsWithGPSRequestCodeUserNotAuthenticatedPlayServiceAvailableWithViewDetachedNavigateToLoginCalledNotCalled
```

#### Test group 8

![](unit/d.png) 

```
playMarketResultsWithGPSRequestCodePlayServiceNotAvailableWithNonNullViewFinishActivityCalled
```

![](unit/a.png) 

```
playMarketResultsWithGPSRequestCodePlayServiceNotAvailableWithNullViewFinishActivityNotCalled
```

![](unit/a.png) 

```
playMarketResultsWithGPSRequestCodePlayServiceNotAvailableWithViewDetachedFinishActivityNotCalled
```

#### Test group 9

![](unit/e.png) 

```
playMarketResultsWithNotGPSRequestCodeWithNonNullViewFinishActivityCalled
```

![](unit/a.png) 

```
playMarketResultsWithNotGPSRequestCodeWithNullViewFinishActivityNotCalled
```

![](unit/a.png) 

```
playMarketResultsWithNotGPSRequestCodeWithViewDetachedFinishActivityNotCalled
```



### <u>onPositive()</u>



![](unit/splash_presenter_on_positive.png)

#### Test group 10

![](unit/b.png) 

```
onPositiveWithNonNullViewNavigateToPlayMarketCalled
```

![](unit/a.png) 

```
onPositiveWithNullViewNavigateToPlayMarketNotCalled
```

![](unit/a.png) 

```
onPositiveWithViewDetachedNavigateToPlayMarketNotCalled
```



### <u>onNegative()</u>



![](unit/splash_presenter_on_negative.png)

#### Test group 11

![](unit/b.png) 

```
onNegativeWithNonNullViewFinishActivityCalled
```

![](unit/a.png) 

```
onNegativeWithNullViewFinishActivityNotCalled
```

![](unit/a.png) 

```
onNegativeWithViewDetachedFinishActivityNotCalled
```

