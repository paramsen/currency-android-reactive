# Android project
_[Vanilla Android, no 3rd party libraries or frameworks]_
## Currency Converter

## Features
* Custom views and aggressive encapsulation. React -like architecture with View components.
* Architecture loosely based on Fowlers Uncle Bobs Clean Architecture.
* Functional approach to the problems of asynchronisity in Android. Think RxJava, in fact, use RxJava. I would if this wasn't a vanilla Android project.
* Why haven't I just used RxJava? Because everyone in Australia is like "Oh, so you don't know Android??? You use new cool frameworks huh? You use libraries that promotes testable, clean asynchronous code? That must be bad!". Seriously Aussies, when you catch up with Europe, look at this repo instead;

## Architectural info
Inspired by DDD, Fowler and React.
* __Activity__: Manages current View/Scene state, decides which View is showing when, reacts to events. Orchestrates Fragments.
* __Fragment:__ View compoents. Orchestrates Views.
* __Views:__ More encapsulation of View logic, think React. Views are the smallest components. Orchestrates themselves.
* __Source:__ The Single Source of Truth for data, i.e. the data layer and the only entry for accessing data.
* __Dependency/Graph__: A Graph of dependencies, like Guice or Dagger. To avoid Singletons.

## Look at
* __CurrencyActivity:__ Doesn't look like much, it's in charge of loading the initial data and showing a loader (if there was one)
* __CurrencySource:__ Now we're talking. Async stuff is an implementation detail, it shouldn't be part of business logic. It's complex. Here it's the layer between View and Data (Source), but very loosely coupled. The logic "below" is encapsulated from the complex async logic and is testable on its own, in a synchronous maner.
* __CurrencyFragment:__ It contains all the Views one will see when opening the app. Notice that the Views are implemented in their own encapsulations, like CurrencyEditText, kind of like React.
* __APK:__ Its in the root of the project!
* __Icon:__ Designed it myself

## Do not look at
* The UI style is quite "raw", can't work on it more now :)
