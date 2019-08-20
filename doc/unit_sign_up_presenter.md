## SignUpPresenter Unit Tests

Tested Methods

-  signUp()



### <u>signUp()</u>



![](unit/sign_in_presenter_sign_up.png)





#### Test group 0	

![](unit/b.png)  branch B

```
singUpWithCorrectEmailPasswordNameWithNonNullViewNavigateToMapScreenCalled
```

![](unit/a.png)	branch A 	view == null

```
singUpWithCorrectEmailPasswordNameWithNullViewNavigateToMapScreenNotCalled
```

![](unit/a.png)	branch A	view detached from presenter

```
singUpWithCorrectEmailPasswordNameWithViewDetachedNavigateToMapScreenNotCalled
```



#### Test group 1

 ![](unit/c.png)

```
singUpWithEmptyEmailCorrectPasswordNameWithNonNullViewDisplayEmailErrorCalled
```

![](unit/a.png) 

```
singUpWithEmptyEmailCorrectPasswordNameWithNullViewDisplayEmailErrorNotCalled
```

![](unit/a.png) 

```
singUpWithEmptyEmailCorrectPasswordNameWithViewDetachedDisplayEmailErrorNotCalled
```



#### Test group 2

![](unit/d.png) 

```
singUpWithIncorrectEmailCorrectPasswordAndNameWithNonNullViewDisplayEmailErrorCalled
```

![](unit/a.png) 

```
singUpWithIncorrectEmailCorrectPasswordAndNameWithNullViewDisplayEmailErrorNotCalled
```

![](unit/a.png)  
```
singUpWithIncorrectEmailCorrectPasswordAndNameWithViewDetachedDisplayEmailErrorNotCalled
```



#### Test group 3

![](unit/e.png) 
```
singUpWithCorrectEmailEmptyPasswordCorrectNameWithNonNullViewDisplayPasswordErrorCalled
```
![](unit/a.png) 
```
singUpWithCorrectEmailEmptyPasswordCorrectNameWithNullViewDisplayPasswordErrorNotCalled
```
![](unit/a.png) 
```
singUpWithCorrectEmailEmptyPasswordCorrectNameWithViewDetachedDisplayPasswordErrorNotCalled
```



#### Test group 4

![](unit/f.png) 
```
singUpWithCorrectEmailPasswordEmptyNameWithNonNullViewDisplayEmptyUserNameErrorCalled
```
![](unit/a.png) 
```
singUpWithCorrectEmailPasswordEmptyNameWithNullViewDisplayEmptyUserNameErrorNotCalled
```
![](unit/a.png) 
```
singUpWithCorrectEmailPasswordEmptyNameWithViewDetachedDisplayEmptyUserNameErrorNotCalled
```



#### Test group 5

![](unit/g.png) 
```
singUpWithCorrectEmailPasswordNameChangeUserNameResultErrorWithNonNullDisplayChangeUserNameErrorCalled
```
![](unit/a.png) 
```
singUpWithCorrectEmailPasswordNameChangeUserNameResultErrorWithNullDisplayChangeUserNameErrorNotCalled
```
![](unit/a.png) 
```
singUpWithCorrectEmailPasswordNameChangeUserNameResultErrorWithViewDetachedDisplayChangeUserNameErrorNotCalled
```



#### Test group 6

![](unit/h.png) 
```
singUpWithCorrectEmailPasswordNameSignUpResultErrorWithNonNullViewDisplaySignUpErrorCalled
```
![](unit/a.png) 
```
singUpWithCorrectEmailPasswordNameSignUpResultErrorWithNullViewDisplaySignUpErrorNotCalled
```
![](unit/a.png) 
```
singUpWithCorrectEmailPasswordNameSignUpResultErrorWithViewDetachedDisplaySignUpErrorNotCalled
```


