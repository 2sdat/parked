# Parked

CSE 248 Spring Semester Final Project

The final APK can be found at parked/app/release/app-release.apk

# Implementation

In developing this project I focused heavily on the Android Architecture components, as such
this project uses the Room database library and the Navigation architecture components.

The database is composed of three tables: "users", "tickets", and "spots".
Each table has columns corresponding to the data fields of the class, as annotated in the data model files found in
parked/app/src/main/java/dev/aidaco/parked/Model/Entities.
The two entities ParkingTicketData and SpotData model the result of queries across multiple tables as opposed to modelling the data itself. ParkingTicketData corresponds to a query for a particular row in the "tickets" table and a second query for the row in "users" corresponding to the attendantId in the returned row from "tickets". SpotData works in a similar manner, returning a row from "spots", and a nested ParkingTicketData object containing its respective calls. 

Each screen of the application is implemented as a standalone fragment, with the only link being calls to the Navigation library. The fragments are swapped out of the NavHostFragment in the MainActivity layout.