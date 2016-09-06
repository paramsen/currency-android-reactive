# Cewlrency, a currency converter built in Android
_[Built on RxJava, fueled by awesomness and 🤓-iness]_  

__Google Play: [Cewlrency](https://play.google.com/store/apps/details?id=com.amsen.par.cewlrency "Cewlrency")__  
__This is a rewrite of my no-3rd-party-libs currency app [HERE](https://github.com/paramsen/currency-android-vanilla "HERE")__

## Features
* Cool concurrency fueled by Rx. The kind that you just can't build with AsyncTasks, the kind without callback hell. Yup.
* Custom views and aggressive encapsulation. React -like architecture with View components.
* Material Design, or at least the newest com.android.design lib & styles
* Architecture loosely based on Fowlers Uncle Bobs Clean Architecture.
* Functional approach to the problems of asynchronisity in Android. Not keen on the functional paradigm? Just take a look at the "CurrencyActivity" and let your heart speak for you. You might be familiar with the code from your backend systems, or even you React frontend. Well this is native Android, and for the first time, it rocks. It's fun to work with. No more horrific all knowing monolithic Android Activities. 🤓
* Vanilla Android version without 3rd party libs of this project; https://github.com/paramsen/currency-android-vanilla [Yes, you will find AsyncTasks in there you HR person from a larger agency and yes I do know them, in fact I know them so well I rather avoid them in favor of Rx because reasons]

## Look at
* __[CurrencyActivity](https://github.com/paramsen/currency-android-reactive/blob/master/app/src/main/java/com/amsen/par/cewlrency/view/activity/CurrencyActivity.java):__ Doesn't look like much, it's in charge of loading the initial data and showing a loader when it's slow. Also handles Network errors in an extremely graceful way.
* __[CurrencyFragment](https://github.com/paramsen/currency-android-reactive/blob/master/app/src/main/java/com/amsen/par/cewlrency/view/fragment/CurrencyFragment.java):__ It contains all the Views one will see when opening the app, visually speaking. Notice that the Views are implemented in their own encapsulations, like CurrencyPickerViewPager, kind of like React.
* __[CurrencyPickerViewPager](https://github.com/paramsen/currency-android-reactive/blob/master/app/src/main/java/com/amsen/par/cewlrency/view/view/CurrencyPickerViewPager.java):__ Encapsulates the quite complex behavior behind the "pickers", relies on a heavily hacked (and tested) ViewPager.
* __[CurrencySource](https://github.com/paramsen/currency-android-reactive/blob/master/app/src/main/java/com/amsen/par/cewlrency/source/CurrencySource.java):__ Now we're talking. It's the glue between View and Data. It handles offline caching, interacts with the API layer etc.
* __[AbstractEventStream](https://github.com/paramsen/currency-android-reactive/blob/master/app/src/main/java/com/amsen/par/cewlrency/view/view/CurrencyPickerViewPager.java):__ My own adaptation of otto.EventBus that uses RxJava instead. Think EventBus, but streamable and awesome. I have used this in many different projects over the past year, it's in apps used by __~1 million monthly users__ successfully.
* __[Icon](https://github.com/paramsen/currency-android-reactive/blob/master/app/src/main/res/mipmap-xxxhdpi/ic_launcher.png):__ Designed it myself using Roman Nuriks tool, awesome dude.

## Architectural info
Inspired by DDD, Fowler and React.
* __Activity__: Manages current View/Scene state, decides which View is showing when, reacts to events. Orchestrates Fragments.
* __Fragment:__ View compoents. Orchestrates Views.
* __Views:__ More encapsulation of View logic, think React. Views are the smallest components. Orchestrates themselves.
* __Source:__ The Single Source of Truth for data, i.e. the data layer and the only entry for accessing data. Sources are the glue of this architecture.
* __Dependency Injection__: A Graph of dependencies, used by Dagger2. Provides inversion of control, a View that needs a Source is not coupled with the instantiation of the Source. The View merely asks for a CurrencySource and gets it. Singletons and hard coupling, good bye.