# My Bitso Dream
Bitso's Techincal Assignment by Mateo Hervas. 

## The Assignment
Build an Android application that shows the available order books and their detailed information. 
It has to run on Android 5.1 Lollipop or later
### Main screen:
In the first screen of the app you need to show a list of all available books in Bitso and their corresponding
price information (displaying only the last price is OK).
- Show an animation when loading resources or performing long operations.
- Enable pull to refresh to get updated price information.
- If the initial request fails, display an error message and a retry option.

### Details screen:
Selecting any book from the list should take the user to a second screen that shows additional
information.
- The details to show from each book are: bid price, ask price, day low, day high, 24h volume and
spread (difference between the last bid and ask prices)
- Show an animation when loading resources or performing long operations.


## The Execution
In order to accomplish the assignment, the following parts where taken care of:
### Language
The App is fully developed in Kotlin
### Architecture
The use of MVVM with Live Data, Coroutines and Flow was the Architecture chosen for this application as it is suggested from Google itself to create Apps based with this Architecture
### Binding
View Binding was used in this project to bind the views.

### Dependency Injection 
The dependency injection framework used in this case was Koin because of its simplicity when configurating dependencies.

### Remote Connections
The library Square okhttp was used for API RESTFUL operations 
### Flavors
Thinking of Scalability, when configuring the app, two flavors were created. For the time being, there is no difference between them.

### Connectivity Manager
The Android’s connectivity manager class was used in this assignment to monitor network changes during the apps lifetime.

### Animations
Both, the Splash animation and the Loader animation where implemented with the use of Airbnb’s Lottie library 
For the Animated App bar in the Product detail Activity, Android Motion Layout was used. 

###Kotlin Extension Functions

In order to make the code more mantainable, Kotlin extension functions were used. For instance, to show or hide a view.
 
### Testing
Unit Tests where written during the development of the app

## Extra Credits
### Charts 
For the Extra credit, MPAndroidChart library was used to create the chart that shows the change of the value of the past month

## The Result in a gif
![](demo.gif)
