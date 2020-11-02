# INSTAFLIX

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Flow](#flow)
* [Improvements](#improvements)

## General info
app for listing movies and series, it implements MVVM

## Technologies
Project is created with:
* MVVM architecture
* Koin for dependency injection
* Retrofit for api comsuption
* Navigation components
* RxJava
* Glide for image loading

## Flow
The application has a home activity with a BottomNavigationView with two options, Movies and Series
![Image of Nav](https://drive.google.com/file/d/1K0-JSJInqYJf9QBOAbtv1s6mCuGmkTGz/view)
This is the home view
![Image of Home](https://drive.google.com/file/d/1UIHDNnKsK6TDD0YH3W63W2fehx4TbYBf/view)
And this is the detail view of a Series or a Movies
![Image of Detail](https://drive.google.com/file/d/1yXQE9bQqlIqHbe0kSgF6yiGl8EWQGPsk/view)

## Improvements
This app can implement a persistence framework like Room