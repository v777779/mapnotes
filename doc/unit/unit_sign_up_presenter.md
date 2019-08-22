## SignUpPresenter Unit Tests

#### Tested Methods

-  signUp()

---

### <u>signUp()</u>

![](images/sign_in_presenter_sign_up.png)

#### Test group 0	

![](images/b.png)  branch B

```
singUpWithCorrectEmailPasswordNameWithNonNullViewNavigateToMapScreenCalled
```

![](images/a.png)	branch A 	view == null

```
singUpWithCorrectEmailPasswordNameWithNullViewNavigateToMapScreenNotCalled
```

![](images/a.png)	branch A	view detached from presenter

```
singUpWithCorrectEmailPasswordNameWithViewDetachedNavigateToMapScreenNotCalled
```

#### Test group 1

 ![](images/c.png)

```
singUpWithEmptyEmailCorrectPasswordNameWithNonNullViewDisplayEmailErrorCalled
```

![](images/a.png) 

```
singUpWithEmptyEmailCorrectPasswordNameWithNullViewDisplayEmailErrorNotCalled
```

![](images/a.png) 

```
singUpWithEmptyEmailCorrectPasswordNameWithViewDetachedDisplayEmailErrorNotCalled
```

#### Test group 2

![](images/d.png) 

```
singUpWithIncorrectEmailCorrectPasswordAndNameWithNonNullViewDisplayEmailErrorCalled
```

![](images/a.png) 

```
singUpWithIncorrectEmailCorrectPasswordAndNameWithNullViewDisplayEmailErrorNotCalled
```

![](images/a.png)  
```
singUpWithIncorrectEmailCorrectPasswordAndNameWithViewDetachedDisplayEmailErrorNotCalled
```

#### Test group 3

![](images/e.png) 
```
singUpWithCorrectEmailEmptyPasswordCorrectNameWithNonNullViewDisplayPasswordErrorCalled
```
![](images/a.png) 
```
singUpWithCorrectEmailEmptyPasswordCorrectNameWithNullViewDisplayPasswordErrorNotCalled
```
![](images/a.png) 
```
singUpWithCorrectEmailEmptyPasswordCorrectNameWithViewDetachedDisplayPasswordErrorNotCalled
```

#### Test group 4

![](images/f.png) 
```
singUpWithCorrectEmailPasswordEmptyNameWithNonNullViewDisplayEmptyUserNameErrorCalled
```
![](images/a.png) 
```
singUpWithCorrectEmailPasswordEmptyNameWithNullViewDisplayEmptyUserNameErrorNotCalled
```
![](images/a.png) 
```
singUpWithCorrectEmailPasswordEmptyNameWithViewDetachedDisplayEmptyUserNameErrorNotCalled
```

#### Test group 5

![](images/g.png) 
```
singUpWithCorrectEmailPasswordNameChangeUserNameResultErrorWithNonNullDisplayChangeUserNameErrorCalled
```
![](images/a.png) 
```
singUpWithCorrectEmailPasswordNameChangeUserNameResultErrorWithNullDisplayChangeUserNameErrorNotCalled
```
![](images/a.png) 
```
singUpWithCorrectEmailPasswordNameChangeUserNameResultErrorWithViewDetachedDisplayChangeUserNameErrorNotCalled
```

#### Test group 6

![](images/h.png) 
```
singUpWithCorrectEmailPasswordNameSignUpResultErrorWithNonNullViewDisplaySignUpErrorCalled
```
![](images/a.png) 
```
singUpWithCorrectEmailPasswordNameSignUpResultErrorWithNullViewDisplaySignUpErrorNotCalled
```
![](images/a.png) 
```
singUpWithCorrectEmailPasswordNameSignUpResultErrorWithViewDetachedDisplaySignUpErrorNotCalled
```


