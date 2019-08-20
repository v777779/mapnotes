## SearchNotesPresenter Unit Tests

#### Tested Methods

-  getNotes()
-  searchNotes()
-  onPositive()
-  onNegative()

---

### <u>getNotes()</u>

![](unit/search_notes_presenter_get_notes.png)

#### Test group 0	

![](unit/b.png)  branch B

```
getNotesOnlineDefaultUserNameHumanReadableNameListNotesWithNonNullViewDisplayNoteCalled
```

![](unit/a.png)	branch A 	view == null

```
getNotesOnlineDefaultUserNameHumanReadableNameListNotesWithNullViewDisplayNoteNotCalled
```

![](unit/a.png)	branch A	view detached from presenter

```
getNotesOnlineDefaultUserNameHumanReadableNameListNotesWithViewDetachedDisplayNoteNotCalled
```

#### Test group 1

 ![](unit/c.png)

```
getNotesNotOnlineDefaultUserNameHumanReadableNameListNotesWithNonNullViewDisplayNoInternetErrorCalled
```

![](unit/a.png) 

```
getNotesNotOnlineDefaultUserNameHumanReadableNameListNotesWithNullViewDisplayNoInternetErrorNotCalled
```

![](unit/a.png) 

```
getNotesNotOnlineDefaultUserNameHumanReadableNameListNotesWithViewDetachedDisplayNoInternetErrorNotCalled
```

#### Test group 2

![](unit/d.png) 

```
getNotesOnlineNullDefaultUserNameErrorHumanReadableNameListNotesWithNonNullViewDisplayDefaultUserNameErrorCalled
```

![](unit/a.png) 

```
getNotesOnlineNullDefaultUserNameErrorHumanReadableNameListNotesWithNullViewDisplayDefaultUserNameErrorNotCalled
```

![](unit/a.png)  

```
getNotesOnlineNullDefaultUserNameErrorHumanReadableNameListNotesWithViewDetachedDisplayDefaultUserNameErrorNotCalled
```

#### Test group 3

![](unit/e.png) 
```
getNotesOnlineEmptyDefaultUserNameErrorHumanReadableNameListNotesWithNonNullViewDisplayDefaultUserNameErrorCalled
```
![](unit/a.png) 
```
getNotesOnlineEmptyDefaultUserNameErrorHumanReadableNameListNotesWithNonNullViewDisplayDefaultUserNameErrorNotCalled
```
![](unit/a.png) 
```
getNotesOnlineEmptyDefaultUserNameErrorHumanReadableNameListNotesWithViewDetachedDisplayDefaultUserNameErrorNotCalled
```

#### Test group 4

![](unit/f.png) 
```
getNotesOnlineDefaultUserNameNullHumanReadableNameResultSuccessWithNonNullViewDisplayNoteCalled
```
![](unit/a.png) 
```
getNotesOnlineDefaultUserNameNullHumanReadableNameResultSuccessWithNonNullViewDisplayNoteNotCalled
```
![](unit/a.png) 
```
getNotesOnlineDefaultUserNameNullHumanReadableNameResultSuccessWithViewDetachedDisplayNoteNotCalled
```

#### Test group 5

![](unit/g.png) 
```
getNotesOnlineDefaultUserNameEmptyHumanReadableNameResultSuccessWithNonNullViewDisplayNoteCalled
```
![](unit/a.png) 
```
getNotesOnlineDefaultUserNameEmptyHumanReadableNameResultSuccessWithNonNullViewDisplayNoteNotCalled
```
![](unit/a.png) 
```
getNotesOnlineDefaultUserNameEmptyHumanReadableNameResultSuccessWithViewDetachedDisplayNoteNotCalled
```

#### Test group 6

![](unit/h.png) 
```
getNotesOnlineDefaultUserNameHumanReadableNameNullResultSuccessWithNonNullViewDisplayLoadingNotesErrorCalled
```
![](unit/a.png) 
```
getNotesOnlineDefaultUserNameHumanReadableNameNullResultSuccessWithNonNullViewDisplayLoadingNotesErrorNotCalled
```
![](unit/a.png) 
```
getNotesOnlineDefaultUserNameHumanReadableNameNullResultSuccessWithViewDetachedDisplayLoadingNotesErrorNotCalled
```

#### Test group 7

![](unit/k.png) 
```
getNotesOnlineDefaultUserNameHumanReadableNameEmptyResultSuccessWithNonNullViewDisplayLoadingNotesErrorCalled
```
![](unit/a.png) 
```
getNotesOnlineDefaultUserNameHumanReadableNameEmptyResultSuccessWithNonNullViewDisplayLoadingNotesErrorNotCalled
```
![](unit/a.png) 
```
getNotesOnlineDefaultUserNameHumanReadableNameEmptyResultSuccessWithViewDetachedDisplayLoadingNotesErrorNotCalled
```

#### Test group 8

![](unit/l.png) 
```
getNotesOnlineDefaultUserNameHumanReadableNameResultErrorWithNonNullViewDisplayLoadingNotesErrorCalled
```
![](unit/a.png) 
```
getNotesOnlineDefaultUserNameHumanReadableNameResultErrorWithViewDetachedDisplayLoadingNotesErrorNotCalled
```
![](unit/a.png) 
```
getNotesOnlineDefaultUserNameHumanReadableNameEmptyResultSuccessWithViewDetachedDisplayLoadingNotesErrorNotCalled
```

### <u>searchNotes()</u>

![](unit/search_notes_presenter_search_notes.png)

#### Test group 9

![](unit/b.png) 
```
searchNotesOnlineTextSearchNotesCategoryDefaultUserNameWithNonNullViewDisplayNotesCalled
```
![](unit/a.png) 
```
searchNotesOnlineTextSearchNotesCategoryDefaultUserNameWithNullViewDisplayNotesNotCalled
```
![](unit/a.png) 
```
searchNotesOnlineTextSearchNotesCategoryDefaultUserNameWithViewDetachedDisplayNotesNotCalled
```
#### Test group 10

![](unit/c.png) 
```
searchNotesNotOnlineTextSearchNotesCategoryDefaultUserNameWithNonNullViewDisplayNoInternetErrorCalled
```
![](unit/a.png) 
```
searchNotesNotOnlineTextSearchNotesCategoryDefaultUserNameWithNullViewDisplayNoInternetErrorNotCalled
```
![](unit/a.png) 
```
searchNotesNotOnlineTextSearchNotesCategoryDefaultUserNameWithViewDetachedDisplayNoInternetErrorNotCalled
```
#### Test group 11

![](unit/d.png) 
```
searchNotesOnlineNullTextSearchNotesCategoryDefaultUserNameWithNonNullViewGetNotesCalled
```
![](unit/a.png) 
```
searchNotesOnlineNullTextSearchNotesCategoryDefaultUserNameWithNullViewGetNotesNotCalled
```
![](unit/a.png) 
```
searchNotesOnlineNullTextSearchNotesCategoryDefaultUserNameWithViewDetachedGetNotesNotCalled
```
#### Test group 12

![](unit/e.png) 
```
searchNotesOnlineEmptyTextSearchNotesCategoryDefaultUserNameWithNonNullViewGetNotesCalled
```
![](unit/a.png) 
```
searchNotesOnlineEmptyTextSearchNotesCategoryDefaultUserNameWithNullViewGetNotesNotCalled
```
![](unit/a.png) 
```
searchNotesOnlineEmptyTextSearchNotesCategoryDefaultUserNameWithViewDetachedGetNotesNotCalled
```
#### Test group 13

![](unit/f.png) 
```
searchNotesOnlineTextSearchUserCategoryDefaultUserNameWithNonNullViewDisplayNoteCalled
```
![](unit/a.png) 
```
searchNotesOnlineTextSearchUserCategoryDefaultUserNameWithNullViewDisplayNoteNotCalled
```
![](unit/a.png) 
```
searchNotesOnlineTextSearchUserCategoryDefaultUserNameWithViewDetachedDisplayNoteNotCalled
```
#### Test group 14

![](unit/g.png) 
```
searchNotesOnlineTextUnknownCategorySearchUserDefaultUserNameWithNonNullViewThrowException
```
![](unit/a.png) 
```
searchNotesOnlineTextUnknownCategorySearchUserDefaultUserNameWithNullViewNotThrowException
```
![](unit/a.png) 
```
searchNotesOnlineTextUnknownCategorySearchUserDefaultUserNameWithViewDetachedNotThrowException
```
#### Test group 15

![](unit/h.png) 
```
searchNotesOnlineTextSearchNotesCategoryNullDefaultUserNameWithNonNullViewDisplayDefaultUserNameErrorCalled
```
![](unit/a.png) 
```
searchNotesOnlineTextSearchNotesCategoryNullDefaultUserNameWithNullViewDisplayDefaultUserNameErrorCalled
```
![](unit/a.png) 
```
searchNotesOnlineTextSearchNotesCategoryNullDefaultUserNameWithViewDetachedDisplayDefaultUserNameErrorCalled
```
#### Test group 16

![](unit/k.png) 
```
searchNotesOnlineTextSearchNotesCategoryEmptyDefaultUserNameWithNonNullViewDisplayDefaultUserNameErrorCalled
```
![](unit/a.png) 
```
searchNotesOnlineTextSearchNotesCategoryEmptyDefaultUserNameWithNullViewDisplayDefaultUserNameErrorNotCalled
```
![](unit/a.png) 
```
searchNotesOnlineTextSearchNotesCategoryEmptyDefaultUserNameWithViewDetachedDisplayDefaultUserNameErrorNotCalled
```

### <u>onPositive()</u>

![](unit/search_notes_presenter_on_positive.png)

#### Test group 17

![](unit/b.png) 
```
onPositiveOnlineNoteRemoveNoteSuccessWithNonNullViewRefreshFragmentCalled
```
![](unit/a.png) 
```
onPositiveOnlineNoteRemoveNoteSuccessWithNullViewRefreshFragmentNotCalled
```
![](unit/a.png) 
```
onPositiveOnlineNoteRemoveNoteSuccessWithViewDetachedRefreshFragmentNotCalled
```
#### Test group 18

![](unit/c.png) 
```
onPositiveNotOnlineNoteRemoveNoteSuccessWithNonNullViewDisplayNoInternetErrorCalled
```
![](unit/a.png) 
```
onPositiveNotOnlineNoteRemoveNoteSuccessWithNullViewDisplayNoInternetErrorNotCalled
```
![](unit/a.png) 
```
onPositiveNotOnlineNoteRemoveNoteSuccessWithViewDetachedDisplayNoInternetErrorNotCalled
```

#### Test group 19

![](unit/d.png) 
```
onPositiveOnlineNullNoteRemoveNoteSuccessWithNonNullViewDisplayNoteDataErrorCalled
```
![](unit/a.png) 
```
onPositiveOnlineNullNoteRemoveNoteSuccessWithNullViewDisplayNoteDataErrorNotCalled
```
![](unit/a.png) 
```
onPositiveOnlineNullNoteRemoveNoteSuccessWithViewDetachedDisplayNoteDataErrorNotCalled
```

#### Test group 20

![](unit/e.png) 
```
onPositiveOnlineNoteRemoveNoteErrorWithNonNullViewDisplayRemoveNoteErrorCalled
```
![](unit/a.png) 
```
onPositiveOnlineNoteRemoveNoteErrorWithNullViewDisplayRemoveNoteErrorNotCalled
```
![](unit/a.png) 
```
onPositiveOnlineNoteRemoveNoteErrorWithViewDetachedDisplayRemoveNoteErrorNotCalled
```

### <u>onNegative()</u>

![](unit/search_notes_presenter_on_pnegative.png)

#### Test group 21

![](unit/b.png) 
```
onNegativeWithNonNullViewRefreshAdapterCalled
```
![](unit/a.png) 
```
onNegativeWithNullViewRefreshAdapterNotCalled
```
![](unit/a.png) 
```
onNegativeWithViewDetachedRefreshAdapterNotCalled
```
