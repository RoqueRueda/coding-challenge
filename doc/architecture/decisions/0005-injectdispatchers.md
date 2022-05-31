# 5. InjectDispatchers

Date: 2022-05-30

## Status

Accepted

## Context

We want to use the best practices for the work with coroutines and in order to make it
more scalable and testable.

## Decision

Don't hardcode dispatchers when creating coroutines or calling withContext. Instead we will inject
the dispatchers as constructors or function arguments. ViewModel classes should create coroutines.
This will make an easy to unit test our ViewModels and validate business logic.
Don't expose mutable types to other classes, Data and Business Layer should expose suspend functions
and flows. Make coroutines cancellable.

All details are covered here: https://developer.android.com/kotlin/coroutines/coroutines-best-practices

## Consequences

This will make our coroutines easy to change during testing.
