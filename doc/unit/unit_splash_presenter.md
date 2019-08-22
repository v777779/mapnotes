## SplashPresenter Unit Tests

#### Tested Methods

-  start()
- startMapNotes()
- playMarketResults()
- onPositive()
- onNegative()

---

### <u>start()</u>

![](images/splash_presenter_start.png)

#### Test group 0	

![](images/b.png)  branch B

```
startWithPlayServicesAvailableUserAuthenticatedNonNullViewNavigateToHomeCalled
```

![](images/a.png)	branch A 	view == null

```
startWithPlayServicesAvailableUserAuthenticatedNullViewStartNavigateToHomeNotCalled
```

![](images/a.png)	branch A	view detached from presenter

```
startWithPlayServicesAvailableUserAuthenticatedWithViewDetachedNavigateToHomeNotCalled
```

#### Test group 1

 ![](images/c.png)

```
startWithPlayServicesAvailableUserNotAuthenticatedNonNullViewNavigateToLoginCalled
```

![](images/a.png) 

```
startWithPlayServicesAvailableUserNotAuthenticatedNullViewNavigateToLoginNotCalled
```

![](images/a.png) 

```
startWithPlayServicesAvailableUserNotAuthenticatedWithViewDetachedNavigateToLoginNotCalled
```

#### Test group 2

![](images/d.png) 

```
startWithPlayServicesNotAvailablePlayMarketInstalledNonNullViewGetErrorDialog
```

![](images/a.png) 

```
startWithPlayServicesNotAvailablePlayMarketInstalledNullViewViewGetErrorDialogNotCalled
```

![](images/a.png)  

```
startWithPlayServicesNotAvailablePlayMarketInstalledWithViewDetachedViewGetErrorDialogNotCalled
```

#### Test group 3

![](images/e.png) 

```
startWithPlayServicesNotAvailablePlayMarketNotInstalledNonNullViewGetAlertDialog
```

![](images/a.png) 

```
startWithPlayServicesNotAvailablePlayMarketNotInstalledNullViewGetAlertDialogNotCalled
```

![](images/a.png) 

```
startWithPlayServicesNotAvailablePlayMarketNotInstalledWithViewDetachedGetAlertDialogNotCalled
```



### <u>startmapNotes()</u>



![](images/splash_presenter_start_map_notes.png)

#### Test group 4

![](images/b.png) 

```
startMapNotesWithUserAuthenticatedWithNonNullViewNavigateToHomeCalled
```

![](images/a.png) 

```
startMapNotesWithUserAuthenticatedWithNullViewNavigateToHomeNotCalled
```

![](images/a.png) 

```
startMapNotesWithUserAuthenticatedWithViewDetachedNavigateToHomeNotCalled
```

#### Test group 5

![](images/c.png) 

```
startMapNotesWithUserNotAuthenticatedWithNonNullViewNavigateToLoginCalled
```

![](images/a.png) 

```
startMapNotesWithUserNotAuthenticatedWithNonNullViewNavigateToLoginCalled
```

  ![](images/a.png) 

```
startMapNotesWithUserNotAuthenticatedWithNullViewNavigateToLoginNotCalled
```



### <u>playMarketResults()</u>



![](images/splash_presenter_play_market_results.png)

#### Test group 6

![](images/b.png) 

```
playMarketResultsWithGPSRequestCodeUserAuthenticatedPlayServiceAvailableWithNonNullViewNavigateToHomeCalled
```

![](images/a.png) 

```
playMarketResultsWithGPSRequestCodeUserAuthenticatedPlayServiceAvailableWithNullViewNavigateToHomeNotCalled
```

![](images/a.png) 

```
playMarketResultsWithGPSRequestCodeUserAuthenticatedPlayServiceAvailableWithViewDetachedNavigateToHomeNotCalled
```

#### Test group 7

![](images/c.png) 

```
playMarketResultsWithRequestGPSRequestCodeUserNotAuthenticatedPlayServiceAvailableWithNonNullViewNavigateToLoginCalled
```

![](images/a.png) 

```
playMarketResultsWithGPSRequestCodeUserNotAuthenticatedPlayServiceAvailableWithNullViewNavigateToLoginCalledNotCalled
```

![](images/a.png) 

```
playMarketResultsWithGPSRequestCodeUserNotAuthenticatedPlayServiceAvailableWithViewDetachedNavigateToLoginCalledNotCalled
```

#### Test group 8

![](images/d.png) 

```
playMarketResultsWithGPSRequestCodePlayServiceNotAvailableWithNonNullViewFinishActivityCalled
```

![](images/a.png) 

```
playMarketResultsWithGPSRequestCodePlayServiceNotAvailableWithNullViewFinishActivityNotCalled
```

![](images/a.png) 

```
playMarketResultsWithGPSRequestCodePlayServiceNotAvailableWithViewDetachedFinishActivityNotCalled
```

#### Test group 9

![](images/e.png) 

```
playMarketResultsWithNotGPSRequestCodeWithNonNullViewFinishActivityCalled
```

![](images/a.png) 

```
playMarketResultsWithNotGPSRequestCodeWithNullViewFinishActivityNotCalled
```

![](images/a.png) 

```
playMarketResultsWithNotGPSRequestCodeWithViewDetachedFinishActivityNotCalled
```



### <u>onPositive()</u>



![](images/splash_presenter_on_positive.png)

#### Test group 10

![](images/b.png) 

```
onPositiveWithNonNullViewNavigateToPlayMarketCalled
```

![](images/a.png) 

```
onPositiveWithNullViewNavigateToPlayMarketNotCalled
```

![](images/a.png) 

```
onPositiveWithViewDetachedNavigateToPlayMarketNotCalled
```



### <u>onNegative()</u>



![](images/splash_presenter_on_negative.png)

#### Test group 11

![](images/b.png) 

```
onNegativeWithNonNullViewFinishActivityCalled
```

![](images/a.png) 

```
onNegativeWithNullViewFinishActivityNotCalled
```

![](images/a.png) 

```
onNegativeWithViewDetachedFinishActivityNotCalled
```

