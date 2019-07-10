# MaskedEditText
[![Release](https://jitpack.io/v/lucasselani/MaskedEditText.svg)](https://jitpack.io/#lucasselani/MaskedEditText)

### Description

This is a really simple, one file, library!
Use this masked edit text (which is a AppCompatEditText) to masks your texts like:
  - phone number: +##(##)#####-#### or
  - document number: ###.###.###-## or
  - whatever you like!
  
The lib is written in Kotlin and the component comes with a companion object with 2 masks to use, but you can create your own.
There's a limitation though, the kind of text you want to mask must be filled from left to right (e.g.: cellphone number, date, etc),  it won't work if the text is filled from right to left (e.g.: money).

### How to use

The use is very simple, you need to pass to the lib a list of the masks you want to apply, so the MaskedEditText can change from one mask to another when the texts grows (you can use a list with a single element too if you don't want the mask to change).
The # character will be replaced by whatever the user is typing and any other character in the mask will be inserted in the text:
````Kotlin
val list = listOf("###.###.###-##", "##.###.###/####-##")
edittext.setMasks(list)
````

If you want a line break in your mask, you can use the * character to insert it in your masked text:
````Kotlin
val bankSlip = listOf("#####.#####*#####.#####*#####.##### #*##############", "###########-#*###########-#*###########-#*###########-#")
result { 
  23791.11103
  60000.000103
  0100.222206 1
  486220000000000

  or

  83620000001-3
  38350138000-0
  18168932011-5
  08081757497-5
}
````

You can retrieve the data by accessing 2 variables:
````Kotlin
edittext.masked //Will retrieve the text with the applied mask
edittext.raw // Will retrieve the text without any mask
````

Also it's possible to set a callback to notify you when the text has changed, so you can have more control:
````Kotlin
edittext.setOnTextChangedCallback { /* Do something */ }
````

### How to implement 
On your app's gradle file, you should import the jitpack.io and this repo's url (you should override the word tag on the dependencies for the one you want, e.g.: 1.1, master-SNAPSHOT, etc. You can found more about it by clicking on the jitpack's icon):
````Gradle
   repositories {
        jcenter()
        maven { url "https://jitpack.io" }
   }
   dependencies {
         implementation 'com.github.lucasselani:MaskedEditText:Tag'
   }
````
