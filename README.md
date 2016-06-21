# SmartTeamTracking Android Application

The Android Application is the main client for the SmartTeamTracking project.
From this client, the user is allowed to create his groups and manage them, all in a simple and cool fashion!


## Technologies used 

How this works?

The main used technologies are:

+ **Estimote-SDK**
The library used to interact with estimote beacons.
http://developer.estimote.com

+ **Global Positioning System (GPS)**
http://www.gps.gov/

+ **REST calls**
We all know how a REST call works.
https://en.wikipedia.org/wiki/Representational_state_transfer

+ **Spring**
a framework for simple,fast, flexible and portable development
https://spring.io/


## Overall architecture 


![alt tag](http://i.imgur.com/6Djz7NX.png)

The architecture components are the same of the whole project:

+ **Beacons**
+ **Client (Android)**
+ **Server**
+ **Database (Embedded Neo4j)**

The basic Android behaviour has been already shown in the main README file: when the Android device, where the application is installed, comes near a beacon, it will retrieve the beacon’s ID; after this, the Android Client will contact the Server, notifying the association Client-Beacon by the Server REST API. Finally, after this interaction, the device will wait a response from the Server when the operation is completed.
This is one of the main activities of the application; the lifecycle of the whole application is shown in the image below.

![alt tag](http://i.imgur.com/eWglZKn.png)

When the application is started, the Splash Activity is started: here we do some basic interaction (starting the login activity, for an example), and then we switch into the Main Activity.
From the Main Activity, we can spawn new activities to manage our groups, creating new ones with a specific radius (for privacy reasons) and adding people to them; after having done that, we can check in the minimap where our friends are, when they will associated with a beacon representing a certain area.


## Installation instructions


+ `git clone https://github.com/draugvar/SmartTeamTracking.git` in the branch of the application

+ open the project in Android Studio

+ compile it

+ get the .apk file and install it

Otherwise, you can directly install the .apk file in your device.


## Objectives and next improves

+ Avoid sending unused data by REST calls
+ Website for adding beacons to the system
+ Improve and Debug Android Application
+ Add REST calls for removing groups and to be able to leave a created group
