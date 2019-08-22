## SignInPresenter Unit Tests

#### Tested Methods

-  signIn()

---

### <u>signIn()</u>

![](images/sign_in_presenter_sign_in.png)

#### Test group 0	

![](images/b.png)  branch B

```
singInWithCorrectEmailPasswordWithNonNullViewNavigateToMapScreenCalled
```

![](images/a.png)	branch A 	view == null

```
singInWithCorrectEmailPasswordWithNullViewNavigateToMapScreenNotCalled
```

![](images/a.png)	branch A	view detached from presenter

```
singInWithCorrectEmailPasswordWithViewDetachedNavigateToMapScreenNotCalled
```

#### Test group 1

 ![](images/c.png)

```
singInWithEmptyEmailPasswordWithNonNullViewDisplayEmailErrorCalled
```

![](images/a.png) 

```
singInWithEmptyEmailPasswordWithNullViewDisplayEmailErrorNotCalled
```

![](images/a.png) 

```
singInWithEmptyEmailPasswordWithViewDetachedDisplayEmailErrorNotCalled
```

#### Test group 2

![](images/d.png) 

```
singInWithIncorrectEmailEmptyPasswordWithNonNullViewDisplayEmailErrorCalled
```

![](images/a.png) 

```
singInWithIncorrectEmailEmptyPasswordWithNullViewDisplayEmailErrorNotCalled
```

![](images/a.png)  

```
singInWithIncorrectEmailEmptyPasswordWithViewDetachedDisplayEmailErrorNotCalled
```

#### Test group 3

![](images/e.png) 

```
singInWithCorrectEmailEmptyPasswordWithNonNullViewDisplayPasswordErrorCalled
```

![](images/a.png) 

```
singInWithCorrectEmailEmptyPasswordWithNullViewDisplayPasswordErrorNotCalled
```

![](images/a.png) 

```
singInWithCorrectEmailEmptyPasswordWithViewDetachedDisplayPasswordErrorNotCalled
```

#### Test group 4

![](images/f.png) 
```
singInWithCorrectEmailIncorrectPasswordWithNonNullViewDisplaySignInErrorCalled
```
![](images/a.png) 
```
singInWithCorrectEmailIncorrectPasswordWithNullViewDisplaySignInErrorNotCalled
```
![](images/a.png) 
```
singInWithCorrectEmailIncorrectPasswordWithViewDetachedToPresenterDisplaySignInErrorNotCalled
```




