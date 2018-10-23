# sample-android-clean

This project explain how to implement clean architecture at android application project.

## Table of Contents

- [Dependencies](#dependencies)
- [Structure](#structure)
- [How To Run](#how-to-run)

## Dependencies

This project dependencies:

* [dagger 2](https://github.com/google/dagger) is a dependency to handle dependency injection.
* [Jackson Json](https://github.com/FasterXML/jackson) is a dependency to handle map object from json.
* [retrofit 2](https://github.com/square/retrofit) is a dependency to handle network transaction.
* [android arch lifecycle](https://developer.android.com/topic/libraries/architecture/livedata) is a dependency to handle lifecycle android application using MVVM.
* [android arch room](https://developer.android.com/topic/libraries/architecture/room) is a dependency to handle local database.

## Structure

This project structure:

```
data/
    feature/
        interactor
            FeatureUseCase.kt
        store/
            cloud/
                response/
                    FeatureResponse.kt
                FeatureApi.kt
                FeatureCloudStore.kt
            database/
                FeatureDao.kt
                FeatureDatabaseStore.kt
                FeatureTable.kt
            FeatureStoreFactory.kt
        FeatureEntity.kt
        FeatureRepository.kt
        FeatureRepositoryImpl.kt
di/
    AppComponent.kt
    AppModule.kt
ui/
    feature/
        FeatureActivity.kt
        FeatureFragment.kt
        FeatureViewModel.kt
```

## How To Run

* Put your google.service.json at folder /app
* Run backend application [Sample Backend](https://github.com/lukma/sample-go-clean).
* Run this android application by `./gradlew clean build`.