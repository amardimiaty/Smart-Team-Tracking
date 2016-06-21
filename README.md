# SmartTeamTracking Server

The SmartTeamTracking Project allows people to connect and reach out easily both Indoor and Outdoor.
Users can simply create their own group, identified by a location and a radius, and then invite their friends to this group, sharing each other their position if they are within the boundaries of the group.


## Technologies used 

How this works?

The main used technologies are:

+ **Estimote**
providing a low-energy bluetooth device for indoor positioning
http://estimote.com/ https://www.bluetooth.com/

+ **Global Positioning System (GPS)**
http://www.gps.gov/

+ **Neo4j**
a non-relational database for social-network relationship
http://neo4j.com/

+ **Spring**
a framework for simple,fast, flexible and portable development
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


## Installation instructions

`git clone https://github.com/draugvar/Smart-Team-Tracking.git`

`mvn clean`

`mvn install`

`java -jar “MavenOutput”.jar`

For the Client Part:
Simply download the .apk file and install it in your application.

Finally, you will have to setup the IP address of your machine in the Client code.
