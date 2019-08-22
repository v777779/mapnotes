## SearchNotesPresenter Unit Tests

#### Tested Methods

-  getNotes()
-  searchNotes()
-  onPositive()
-  onNegative()

---

### <u>getNotes()</u>

![](images/search_notes_presenter_get_notes.png)

#### Test group 0	

![](images/b.png)  branch B

```
getNotesOnlineDefaultUserNameHumanReadableNameListNotesWithNonNullViewDisplayNoteCalled
```

![](images/a.png)	branch A 	view == null

```
getNotesOnlineDefaultUserNameHumanReadableNameListNotesWithNullViewDisplayNoteNotCalled
```

![](images/a.png)	branch A	view detached from presenter

```
getNotesOnlineDefaultUserNameHumanReadableNameListNotesWithViewDetachedDisplayNoteNotCalled
```

#### Test group 1

 ![](images/c.png)

```
getNotesNotOnlineDefaultUserNameHumanReadableNameListNotesWithNonNullViewDisplayNoInternetErrorCalled
```

![](images/a.png) 

```
getNotesNotOnlineDefaultUserNameHumanReadableNameListNotesWithNullViewDisplayNoInternetErrorNotCalled
```

![](images/a.png) 

```
getNotesNotOnlineDefaultUserNameHumanReadableNameListNotesWithViewDetachedDisplayNoInternetErrorNotCalled
```

#### Test group 2

![](images/d.png) 

```
getNotesOnlineNullDefaultUserNameErrorHumanReadableNameListNotesWithNonNullViewDisplayDefaultUserNameErrorCalled
```

![](images/a.png) 

```
getNotesOnlineNullDefaultUserNameErrorHumanReadableNameListNotesWithNullViewDisplayDefaultUserNameErrorNotCalled
```

![](images/a.png)  

```
getNotesOnlineNullDefaultUserNameErrorHumanReadableNameListNotesWithViewDetachedDisplayDefaultUserNameErrorNotCalled
```

#### Test group 3

![](images/e.png) 
```
getNotesOnlineEmptyDefaultUserNameErrorHumanReadableNameListNotesWithNonNullViewDisplayDefaultUserNameErrorCalled
```
![](images/a.png) 
```
getNotesOnlineEmptyDefaultUserNameErrorHumanReadableNameListNotesWithNonNullViewDisplayDefaultUserNameErrorNotCalled
```
![](images/a.png) 
```
getNotesOnlineEmptyDefaultUserNameErrorHumanReadableNameListNotesWithViewDetachedDisplayDefaultUserNameErrorNotCalled
```

#### Test group 4

![](images/f.png) 
```
getNotesOnlineDefaultUserNameNullHumanReadableNameResultSuccessWithNonNullViewDisplayNoteCalled
```
![](images/a.png) 
```
getNotesOnlineDefaultUserNameNullHumanReadableNameResultSuccessWithNonNullViewDisplayNoteNotCalled
```
![](images/a.png) 
```
getNotesOnlineDefaultUserNameNullHumanReadableNameResultSuccessWithViewDetachedDisplayNoteNotCalled
```

#### Test group 5

![](images/g.png) 
```
getNotesOnlineDefaultUserNameEmptyHumanReadableNameResultSuccessWithNonNullViewDisplayNoteCalled
```
![](images/a.png) 
```
getNotesOnlineDefaultUserNameEmptyHumanReadableNameResultSuccessWithNonNullViewDisplayNoteNotCalled
```
![](images/a.png) 
```
getNotesOnlineDefaultUserNameEmptyHumanReadableNameResultSuccessWithViewDetachedDisplayNoteNotCalled
```

#### Test group 6

![](images/h.png) 
```
getNotesOnlineDefaultUserNameHumanReadableNameNullResultSuccessWithNonNullViewDisplayLoadingNotesErrorCalled
```
![](images/a.png) 
```
getNotesOnlineDefaultUserNameHumanReadableNameNullResultSuccessWithNonNullViewDisplayLoadingNotesErrorNotCalled
```
![](images/a.png) 
```
getNotesOnlineDefaultUserNameHumanReadableNameNullResultSuccessWithViewDetachedDisplayLoadingNotesErrorNotCalled
```

#### Test group 7

![](images/k.png) 
```
getNotesOnlineDefaultUserNameHumanReadableNameEmptyResultSuccessWithNonNullViewDisplayLoadingNotesErrorCalled
```
![](images/a.png) 
```
getNotesOnlineDefaultUserNameHumanReadableNameEmptyResultSuccessWithNonNullViewDisplayLoadingNotesErrorNotCalled
```
![](images/a.png) 
```
getNotesOnlineDefaultUserNameHumanReadableNameEmptyResultSuccessWithViewDetachedDisplayLoadingNotesErrorNotCalled
```

#### Test group 8

![](images/l.png) 
```
getNotesOnlineDefaultUserNameHumanReadableNameResultErrorWithNonNullViewDisplayLoadingNotesErrorCalled
```
![](images/a.png) 
```
getNotesOnlineDefaultUserNameHumanReadableNameResultErrorWithViewDetachedDisplayLoadingNotesErrorNotCalled
```
![](images/a.png) 
```
getNotesOnlineDefaultUserNameHumanReadableNameEmptyResultSuccessWithViewDetachedDisplayLoadingNotesErrorNotCalled
```

### <u>searchNotes()</u>

![](images/search_notes_presenter_search_notes.png)

#### Test group 9

![](images/b.png) 
```
searchNotesOnlineTextSearchNotesCategoryDefaultUserNameWithNonNullViewDisplayNotesCalled
```
![](images/a.png) 
```
searchNotesOnlineTextSearchNotesCategoryDefaultUserNameWithNullViewDisplayNotesNotCalled
```
![](images/a.png) 
```
searchNotesOnlineTextSearchNotesCategoryDefaultUserNameWithViewDetachedDisplayNotesNotCalled
```
#### Test group 10

![](images/c.png) 
```
searchNotesNotOnlineTextSearchNotesCategoryDefaultUserNameWithNonNullViewDisplayNoInternetErrorCalled
```
![](images/a.png) 
```
searchNotesNotOnlineTextSearchNotesCategoryDefaultUserNameWithNullViewDisplayNoInternetErrorNotCalled
```
![](images/a.png) 
```
searchNotesNotOnlineTextSearchNotesCategoryDefaultUserNameWithViewDetachedDisplayNoInternetErrorNotCalled
```
#### Test group 11

![](images/d.png) 
```
searchNotesOnlineNullTextSearchNotesCategoryDefaultUserNameWithNonNullViewGetNotesCalled
```
![](images/a.png) 
```
searchNotesOnlineNullTextSearchNotesCategoryDefaultUserNameWithNullViewGetNotesNotCalled
```
![](images/a.png) 
```
searchNotesOnlineNullTextSearchNotesCategoryDefaultUserNameWithViewDetachedGetNotesNotCalled
```
#### Test group 12

![](images/e.png) 
```
searchNotesOnlineEmptyTextSearchNotesCategoryDefaultUserNameWithNonNullViewGetNotesCalled
```
![](images/a.png) 
```
searchNotesOnlineEmptyTextSearchNotesCategoryDefaultUserNameWithNullViewGetNotesNotCalled
```
![](images/a.png) 
```
searchNotesOnlineEmptyTextSearchNotesCategoryDefaultUserNameWithViewDetachedGetNotesNotCalled
```
#### Test group 13

![](images/f.png) 
```
searchNotesOnlineTextSearchUserCategoryDefaultUserNameWithNonNullViewDisplayNoteCalled
```
![](images/a.png) 
```
searchNotesOnlineTextSearchUserCategoryDefaultUserNameWithNullViewDisplayNoteNotCalled
```
![](images/a.png) 
```
searchNotesOnlineTextSearchUserCategoryDefaultUserNameWithViewDetachedDisplayNoteNotCalled
```
#### Test group 14

![](images/g.png) 
```
searchNotesOnlineTextUnknownCategorySearchUserDefaultUserNameWithNonNullViewThrowException
```
![](images/a.png) 
```
searchNotesOnlineTextUnknownCategorySearchUserDefaultUserNameWithNullViewNotThrowException
```
![](images/a.png) 
```
searchNotesOnlineTextUnknownCategorySearchUserDefaultUserNameWithViewDetachedNotThrowException
```
#### Test group 15

![](images/h.png) 
```
searchNotesOnlineTextSearchNotesCategoryNullDefaultUserNameWithNonNullViewDisplayDefaultUserNameErrorCalled
```
![](images/a.png) 
```
searchNotesOnlineTextSearchNotesCategoryNullDefaultUserNameWithNullViewDisplayDefaultUserNameErrorCalled
```
![](images/a.png) 
```
searchNotesOnlineTextSearchNotesCategoryNullDefaultUserNameWithViewDetachedDisplayDefaultUserNameErrorCalled
```
#### Test group 16

![](images/k.png) 
```
searchNotesOnlineTextSearchNotesCategoryEmptyDefaultUserNameWithNonNullViewDisplayDefaultUserNameErrorCalled
```
![](images/a.png) 
```
searchNotesOnlineTextSearchNotesCategoryEmptyDefaultUserNameWithNullViewDisplayDefaultUserNameErrorNotCalled
```
![](images/a.png) 
```
searchNotesOnlineTextSearchNotesCategoryEmptyDefaultUserNameWithViewDetachedDisplayDefaultUserNameErrorNotCalled
```

### <u>onPositive()</u>

![](images/search_notes_presenter_on_positive.png)

#### Test group 17

![](images/b.png) 
```
onPositiveOnlineNoteRemoveNoteSuccessWithNonNullViewRefreshFragmentCalled
```
![](images/a.png) 
```
onPositiveOnlineNoteRemoveNoteSuccessWithNullViewRefreshFragmentNotCalled
```
![](images/a.png) 
```
onPositiveOnlineNoteRemoveNoteSuccessWithViewDetachedRefreshFragmentNotCalled
```
#### Test group 18

![](images/c.png) 
```
onPositiveNotOnlineNoteRemoveNoteSuccessWithNonNullViewDisplayNoInternetErrorCalled
```
![](images/a.png) 
```
onPositiveNotOnlineNoteRemoveNoteSuccessWithNullViewDisplayNoInternetErrorNotCalled
```
![](images/a.png) 
```
onPositiveNotOnlineNoteRemoveNoteSuccessWithViewDetachedDisplayNoInternetErrorNotCalled
```

#### Test group 19

![](images/d.png) 
```
onPositiveOnlineNullNoteRemoveNoteSuccessWithNonNullViewDisplayNoteDataErrorCalled
```
![](images/a.png) 
```
onPositiveOnlineNullNoteRemoveNoteSuccessWithNullViewDisplayNoteDataErrorNotCalled
```
![](images/a.png) 
```
onPositiveOnlineNullNoteRemoveNoteSuccessWithViewDetachedDisplayNoteDataErrorNotCalled
```

#### Test group 20

![](images/e.png) 
```
onPositiveOnlineNoteRemoveNoteErrorWithNonNullViewDisplayRemoveNoteErrorCalled
```
![](images/a.png) 
```
onPositiveOnlineNoteRemoveNoteErrorWithNullViewDisplayRemoveNoteErrorNotCalled
```
![](images/a.png) 
```
onPositiveOnlineNoteRemoveNoteErrorWithViewDetachedDisplayRemoveNoteErrorNotCalled
```

### <u>onNegative()</u>

![](images/search_notes_presenter_on_pnegative.png)

#### Test group 21

![](images/b.png) 
```
onNegativeWithNonNullViewRefreshAdapterCalled
```
![](images/a.png) 
```
onNegativeWithNullViewRefreshAdapterNotCalled
```
![](images/a.png) 
```
onNegativeWithViewDetachedRefreshAdapterNotCalled
```
