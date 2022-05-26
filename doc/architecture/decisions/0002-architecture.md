# 2. Architecture

Date: 2022-05-26

## Status

Accepted

## Context

In order to make this project with a proper separation of concerns we need to define some 
Architecture rules.

## Decision

Architecture for this project are as follows
- Single Activity, multiple fragments
- MVVM
- Coroutines
- Navigation Component
- LiveData to communicate with the Vies from the ViewModel
- Pragmatic Clean Architecture (data, domain, app/presentation layers)
- Jetpack for as much as possible

## Pragmatic Clean Architecture

A simple, layered app approach where *Data* layer defines repository implementations, database, and
networking APIs, data transfer objects, and converters to domain objects. *Domain* layer provides 
model object definitions, use cases (using coroutines), and repository interface declarations. And 
finally the *App* or *Presentation* layer, which provides ViewModel and View (mainly Fragment) 
implementations.

## Navigation

Navigation is provided by the Navigation Architecture Component. The navigation graphs are in the
App Layer.

## Coroutines

Coroutines will be used for background execution.

## MVVM

Model View View Model architecture using the Jetpack framework.

LiveData should be used to communicate with the View (Fragment) from the ViewModel. Using a single 
UI State data object is preferred over breaking the state down into separate objects, as much as 
possible.

## Consequences

We will have defined layers that once the project becomes bigger it will not be easy to change
this architecture to something else
