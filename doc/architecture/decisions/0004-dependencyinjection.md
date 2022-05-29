# 4. DependencyInjection

Date: 2022-05-29

## Status

Accepted

## Context

We want to use dependency injection to handle our dependencies between layers and components

## Decision

We will use Hilt, Hilt is a modern android DI framework for dependency injection. It is merely a 
wrapper around Dagger2.

## Consequences

This will make our app more easy to test was well our components will work with a more abstract
way.
