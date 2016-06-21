# SmartTeamTracking Server

The SmartTeamTracking Project allows people to connect and reach out easily both Indoor and Outdoor.
Users can simply create their own group, identified by a location and a radius, and then invite their friends to this group, sharing each other their position if they are within the boundaries of the group.


## Technologies used 

How this works?

The main used technologies are:

+ **Estimote**
providing a low-energy bluetooth device for indoor positioning<br />
http://estimote.com/ <br /> https://www.bluetooth.com/

+ **Global Positioning System (GPS)** <br />
http://www.gps.gov/

+ **Neo4j**
a non-relational database for social-network relationship <br />
http://neo4j.com/

+ **Spring**
a framework for simple,fast, flexible and portable development <br />
https://spring.io/


## Overall architecture 


![alt tag](http://i.imgur.com/6Djz7NX.png)

As you can see from the picture, the main components of the project are:

+ **Beacons**
+ **Client (Android)**
+ **Server**
+ **Database (Embedded Neo4j)**

When the Android device where the application is installed comes near a beacon, it will retrieve the beacon’s ID; after this, the Android Client will contact the Server, notifying the association Client-Beacon by the Server REST API. Finally, after getting this request, the Server will save this association in the Embedded Neo4j database, notifying the Android Client when the operation is completed.
In addiction to this one, in the other parts of the application we have just other simple Client-Server-Database interactions, like the last part of the one shown before (3-4-5-6).
Each part of the application has its own README file, so you can check it if you want further explanation on a specific component.


## Installation instructions

Check the README of each component, since each part has different a installation.


## Objectives and next improves

+ [x] Avoid sending unused data by REST calls 
+ [x] Website for adding beacons to the system 
+ [x] Improve and Debug Android Application 
+ [x] Add REST calls for removing groups and to be able to leave a created group 
+ [x] ** **NEW** ** Cleanup of the Server Code (by tools)
+ [x] ** **NEW** ** Show when a friend is not in range


## Additional Informations


You can find us on LinkedIn Profiles:

- *BiagioBotticelli*: https://it.linkedin.com/in/biagio-botticelli-444b87105/en ;
- *Stefano Conoci*: https://it.linkedin.com/in/stefano-conoci-06501844 ; 
- *Davide Meacci*: https://it.linkedin.com/in/davide-meacci-ab065bb7/en ;
- *Salvatore Rivieccio*: https://it.linkedin.com/in/salvatore-rivieccio-653644b7/en .

Presentation on *SlideShare*: http://www.slideshare.net/BiagioBotticelli/smart-team-tracking-project-group-tracking

The project was developed for the course of "Pervasive Systems 2016", 
held by Prof. Ioannis Chatzigiannakis
within the Master of Science in Computer Science of University of Rome "La Sapienza".

Homepage of *Pervasive Systems 2016* course :
http://ichatz.me/index.php/Site/PervasiveSystems2016

Homepage of *Prof. Ioannis Chatzigiannakis*: 
http://ichatz.me/index.php

Homepage of *MSECS "La Sapienza"*:
http://cclii.dis.uniroma1.it/?q=msecs

