## LoginPresenter Unit Tests

#### Tested Methods

-  openSignIn()
- openSignUp()

---

### <u>openSignIn()</u>

![](images/login_presenter_open_sign_in.png) 

#### Test group 0	

![](images/b.png)  branch B

```
openSignInWithNonNullViewNavigateToSignInCalled
```

![](images/a.png)	branch A 	view == null

```
openSignInWithNullViewNavigateToSignInNotCalled
```

![](images/a.png)	branch A	view detached from presenter

```
openSignInWithViewDetachedNavigateToSignInNotCalled
```



### <u>openSignUp()</u>

![](images/login_presenter_open_sign_up.png) 

#### Test group 1

 ![](images/b.png)

```
openSignUpWithNonNullViewNavigateToSignUpCalled
```

![](images/a.png) 

```
openSignUpWithNullViewNavigateToSignUpNotCalled
```

![](images/a.png) 

```
openSignUpWithViewDetachedNavigateToSignUpNotCalled
```

