## SignInPresenter Unit Tests

Tested Methods

-  signIn()



### <u>signIn()</u>

![](unit/sign_in_presenter_sign_in.png)

#### Test group 0	

![](unit/b.png)  branch B

```
singInWithCorrectEmailPasswordWithNonNullViewNavigateToMapScreenCalled
```

![](unit/a.png)	branch A 	view == null

```
singInWithCorrectEmailPasswordWithNullViewNavigateToMapScreenNotCalled
```

![](unit/a.png)	branch A	view detached from presenter

```
singInWithCorrectEmailPasswordWithViewDetachedNavigateToMapScreenNotCalled
```

#### Test group 1

 ![](unit/c.png)

```
singInWithEmptyEmailPasswordWithNonNullViewDisplayEmailErrorCalled
```

![](unit/a.png) 

```
singInWithEmptyEmailPasswordWithNullViewDisplayEmailErrorNotCalled
```

![](unit/a.png) 

```
singInWithEmptyEmailPasswordWithViewDetachedDisplayEmailErrorNotCalled
```

#### Test group 2

![](unit/d.png) 

```
singInWithIncorrectEmailEmptyPasswordWithNonNullViewDisplayEmailErrorCalled
```

![](unit/a.png) 

```
singInWithIncorrectEmailEmptyPasswordWithNullViewDisplayEmailErrorNotCalled
```

![](unit/a.png)  

```
singInWithIncorrectEmailEmptyPasswordWithViewDetachedDisplayEmailErrorNotCalled
```

#### Test group 3

![](unit/e.png) 

```
singInWithCorrectEmailEmptyPasswordWithNonNullViewDisplayPasswordErrorCalled
```

![](unit/a.png) 

```
singInWithCorrectEmailEmptyPasswordWithNullViewDisplayPasswordErrorNotCalled
```

![](unit/a.png) 

```
singInWithCorrectEmailEmptyPasswordWithViewDetachedDisplayPasswordErrorNotCalled
```

#### Test group 4

![](unit/f.png) 
```
singInWithCorrectEmailIncorrectPasswordWithNonNullViewDisplaySignInErrorCalled
```
![](unit/a.png) 
```
singInWithCorrectEmailIncorrectPasswordWithNullViewDisplaySignInErrorNotCalled
```
![](unit/a.png) 
```
singInWithCorrectEmailIncorrectPasswordWithViewDetachedToPresenterDisplaySignInErrorNotCalled
```




