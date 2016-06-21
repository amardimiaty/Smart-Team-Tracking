# SmartTeamTracking Server

The Server part of this project is meant to be a connection between the Neo4j embedded database and the Web/Android clients.
<br /> In fact, almost all REST calls made from the Client side to the Server are going to interact with the Neo4j database, implementing basically CRUD operations on every possible object of the application. We can create and remove beacons, we can create, update and remove groups, all made from a simple REST call that will make these changes persistent by updating the Neo4j Database.


## Technologies used 

How this works?

The main used technologies are:

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

As shown in the picture, after being started, the Server is going to wait for any REST call coming from the Clients, making the required changes persistent by interacting with the Neo4j database. Almost every object has CRUD operations available by REST API.
The relations between objects are shown in the ER diagram below:

![alt tag](http://i.imgur.com/ikM0bNH.png)


## Installation instructions

`git clone https://github.com/draugvar/Smart-Team-Tracking.git`

`mvn clean`

`mvn install`

`java -jar “MavenOutput”.jar`
