# Givex SDK for Android

Implementation of the Givex API as an SDK for the Android platform.
Givex don't currently offer an SDK to interface with their API from an Android application,
we're offering SDK as a quick way of implementing their service and avoiding a lengthy
implementation process.

## Features

All features are available by instantiating the `Givex` object and calling the corresponding method.

### Activate
Activate a card with a given balance.

### Increment
Top up a Givex card with a specified balance.

### Redemption
Redeem a specified amount from a specified card number.

### Cancel
Cancel an activation, redemption, or increment request using the `transactionReference` from the specified transaction.

## Usage

First, add the LUSH Maven repo:
To URL to our repo is `"https://artifacts.platformserviceaccount.com/repository/maven-public/"`

Second, add the dependency in build.gradle:
`compile 'com.lush.library:givex:1.2.0'`

Then just execute the appropriate method on a `Givex` instance.
