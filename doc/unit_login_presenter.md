## LoginPresenter Unit Tests

#### Tested Methods

-  openSignIn()
- openSignUp()



### <u>openSignIn()</u>

![](unit/login_presenter_open_sign_in.png) 

#### Test group 0	

![](unit/b.png)  branch B

```
openSignInWithNonNullViewNavigateToSignInCalled
```

![](unit/a.png)	branch A 	view == null

```
openSignInWithNullViewNavigateToSignInNotCalled
```

![](unit/a.png)	branch A	view detached from presenter

```
openSignInWithViewDetachedNavigateToSignInNotCalled
```



### <u>openSignUp()</u>

![](unit/login_presenter_open_sign_up.png) 

#### Test group 1

 ![](unit/b.png)

```
openSignUpWithNonNullViewNavigateToSignUpCalled
```

![](unit/a.png) 

```
openSignUpWithNullViewNavigateToSignUpNotCalled
```

![](unit/a.png) 

```
openSignUpWithViewDetachedNavigateToSignUpNotCalled
```

