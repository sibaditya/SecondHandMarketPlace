# Kijiji

Are you too busy looking for a wide range of the item to buy? This is the app for you. Here you can buy, sell, or trade almost anything! New and used items, cars, real estate, jobs, services, vacation rentals and more virtually anywhere.

# Architecture

The app is built on Kotlin and follows an MVVM architecture. The android activity and fragments function as views, the ViewModel serves the business logic and holds properties in the form of LiveData which the fragments observe.
The ViewModel works with the repository(Network package) which abstracts out the data source from the ViewModel.
We are using the Navigation component and Data binding to navigate between the views and bind data to those views.
Viewmodels in the app extends the android architecture components ViewModel classes. 

[!Application architecture]
(https://ibb.co/199R4Mm)

# Application Flow

We have 3 major screens in the application.

1) Splash Screen(SplashScreenFragment)

This is the very first screen in the application. We are making a network call to get the initial data before moving to the Ad Listing screen. If we get the data successfully from the network then the user will see the ad listing. If we get an error from the network then the user will see the error screen with a Lottie animation

2) Ad Listing Screen(AdListingFragment)

This screen is showing a list of all ads. We are supporting pagination in this screen. When the user starts scrolling and reaches the end of the screen we are calling the network to fetch more data. Which we are fetching the data we are showing a loading Lottie animation.
When the user clicks on an ad item they will navigate to the Ad detail screen.


3) Ad Detail Screen(AdDetailFragment)

This screen is showing the details of the selected ad. In this screen, the user can see the list of ad images that they can navigate. Along with that, they can also see the item name, price, detail description and contact details which include the address(location) and email id.
When the user clicks on the address and if they have any map application installed on their phone then the map application will get open otherwise we are showing a toast telling that the desire application is not present on the phone. The same behavior will happen for the email.


# Testing efforts
The ViewModel and the Utils have been unit tested.


# Other Libraries used on the project

1)Android architecture components (ViewModel, LiveData, Navigation Component, Data binding)

2)Glide (Image loading, rendering and caching)

3)Square Moshi (JSON parsing)

4)Lottie (To show loading and error state animation)

5)Retrofit (For network calling)

6)MockWebServer(For mocking webserver call)

7)JUnit(For unit testing)


P.S: Screenshots can be found in the screenshots folder.
