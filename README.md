# TurboAutocompleteButton

[![Twitter](http://img.shields.io/badge/contact-@drmunon-red.svg?style=flat)](http://twitter.com/drmunon)
[![GitHub Issues](http://img.shields.io/github/issues/Mun0n/TurboImageProject.svg?style=flat)](http://github.com/Mun0n/TurboAutocompleteButton/issues)
[![License](https://img.shields.io/badge/license-MIT-yellow.svg?style=flat)](http://opensource.org/licenses/MIT)

TurboAutocompleteButton is the lib that **you need**! 
<br>
It builds an AutoCompleteTextView with an incorporated Mic/Clear button, which lets the user write by voice or clear the written text.
<br>It's the same shit that Google do with their search toolbars!

![TurboAutocompleteButton preview](out.gif)

##Adding to project
To use this lib you will need to add this in your `build.gradle`

```gradle
compile 'com.munon:turboiautocompletebutton:1.0.0'
```

##EASY 
as put this on your XML

```xml
<com.munon.turboautocompletebutton.TurboAutocompleteButton
                android:id="@+id/turboAutocompleteButton"
                android:layout_width="match_parent"
                android:layout_height="?attr/actionBarSize"
                android:background="@android:color/white"
                app:dropDownAnchor="@+id/toolbar"
                app:micWhite="false"
                app:hint="@android:string/search_go"/>
```
<br>
Be careful, if you want to create your **TurboAutocompleteButton** in the good way, we recommend to set all the attributes.

+ dropDownAnchor: to anchor the dropdown menu from the AutoCompleteTextView.
+ micWhite: if you need to set the color of the mic/clear icon to white, or black.
+ hint: set a string hint for the AutoCompleteTextView.

If you want to do something more you can grad the Views with
```java
autoCompleteButton.getAutoCompleteTextView();
autoCompleteButton.getImageButton();
```

So, that's all folks!

Comments, bugs, fixes...anything you wanted, ask an expert. I will be sleeping.

Thanks to [hrules6872](https://github.com/hrules6872)(A.K.A MA MOSES) for the support
