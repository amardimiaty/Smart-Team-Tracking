# SmartTeamTracking Android Application

The Android Application is the main client for the SmartTeamTracking project.
From this client, the user is allowed to create his groups and manage them, all in a simple and cool fashion!


## Technologies used 

How this works?

The main used technologies are:

+ **Estimote-SDK**
The library used to interact with estimote beacons.<br />
http://developer.estimote.com

+ **Global Positioning System (GPS)**<br />
http://www.gps.gov/

+ **REST calls**
We all know how a REST call works.<br />
https://en.wikipedia.org/wiki/Representational_state_transfer

+ **Spring**
a framework for simple,fast, flexible and portable development<br />
https://spring.io/


## Overall architecture 


![alt tag](http://i.imgur.com/6Djz7NX.png)

The architecture components are the same of the whole project:

+ **Beacons**
+ **Client (Android)**
+ **Server**
+ **Database (Embedded Neo4j)**

The basic Android behaviour has been already shown in the main README file: when the Android device, where the application is installed, comes near a beacon, it will retrieve the beacon’s ID; after this, the Android Client will contact the Server, notifying the association Client-Beacon by the Server REST API. Finally, after this interaction, the device will wait a response from the Server when the operation is completed.<br />
This is one of the main activities of the application; the lifecycle of the whole application is shown in the image below.

![alt tag](http://i.imgur.com/eWglZKn.png)

<br />
When the application is started, the Splash Activity is started:
<br /><br /><br />
<br />

<img src="https://github.com/draugvar/Smart-Team-Tracking/blob/android_app/screenshots/splash.png" width="20%">
<img src="https://github.com/draugvar/Smart-Team-Tracking/blob/android_app/screenshots/login.png" width="20%">
<img src="https://github.com/draugvar/Smart-Team-Tracking/blob/android_app/screenshots/create_groups.png" width="20%">
<img src="http://i.imgur.com/rPzXdSU.jpg" width="20%">

<br />
here we do some basic interaction (starting the login activity, for an example), and then we switch into the Main Activity.<br />
From the Main Activity, we can spawn new activities to manage our groups, creating new ones with a specific radius (for privacy reasons) and adding people to them; 
after having done that, we can check in the minimap where our friends are, when they will associated with a beacon representing a certain area.





## Installation instructions


+ `git clone https://github.com/draugvar/SmartTeamTracking.git` in the branch of the application

+ open the project in Android Studio
+ set the IP address of your server machine (if you want to customize it)

+ compile it

+ get the .apk file and install it

Otherwise, you can directly install the .apk file in your device.
