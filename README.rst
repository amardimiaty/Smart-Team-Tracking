=======================================
GroupTracking WebApp PHP Modules
=======================================

.. image:: https://github.com/biagiobotticelli/Drupal_Php_Modules/blob/master/images/logo.jpg
   :align: center


GroupTracking is an Android application that wants to allow to create group of friends obtained by Facebook
and tracking their position outside building but also INSIDE! By using Beacons!

.. image:: https://github.com/biagiobotticelli/Drupal_Php_Modules/blob/master/images/beacons.png
   :align: center

With GroupTracking you can create a group of friends, define a range in which your friends will be tracked and 
get their position directly on a map. Friends that are out of the range will NOT be tracked!

.. image:: https://github.com/biagiobotticelli/Drupal_Php_Modules/blob/master/images/map.png
   :align: center


How does GroupTracking make tracking?
-------------------------------------
To tracking people, the GroupTracking Android app uses GPS and bluetooth of the smartphone.
An Android Service searches for beacons through the bluetooth:

- **NO** beacon is found: the app uses the last GPS coordinates to get the position;
- A beacon is found: the app uses the coordinates of the nearest beacon to get the postion;

.. image:: https://github.com/biagiobotticelli/Drupal_Php_Modules/blob/master/images/arch.png
   :align: center


Why do we insert a Drupal WebApp in the system?
-----------------------------------------------
The Android application gets the data that it needs (such us beacon IDs, latitude and longitude, range, etc.)
by REST calls made to an existent webserver.
In order to communicate with the webserver, we develop the Drupal webapp with the following functions:

- **Get a list of Beacons**;
- **Add a Beacon**;
- **Delete an existent Beacon**.


getBeacons.php
--------------
The first function is to get a list of the beacons that are available in the system.

The php module create a button "Get Beacons" and the page is empty:

.. image:: https://github.com/biagiobotticelli/Drupal_Php_Modules/blob/master/images/getBeacon1.png
   :align: center
   
   
when the button "Get Beacons" is clicked: 


.. image:: https://github.com/biagiobotticelli/Drupal_Php_Modules/blob/master/images/getBeacon2.png
   :align: center
   
   
the module makes a GET request to the REST webserver to obtain the list of the available beacons of the system.

Once a response is obtained from the server, the informations of beacons are displayed in the page as a list of values.


.. image:: https://github.com/biagiobotticelli/Drupal_Php_Modules/blob/master/images/getBeacon3.png
   :align: center


addBeacon.php
--------------
This function allows the user to fill a form and insert a new beacon in the system:

.. image:: https://github.com/biagiobotticelli/Drupal_Php_Modules/blob/master/images/addBeacon1.png
   :align: center
   
   
The form has 5 fields that are requider:

- *Name*: name to assign to the beacon;
- *Major*: specific value of the single beacon;
- *Minor*: specific value of the single beacon;
- *Latitude*: latitude of the position of the beacon;
- *Longitude*: longitude of the position of the beacon;
   
when the form is filled with correct values for the fields and the button "Add Beacon" is clicked: 

.. image:: https://github.com/biagiobotticelli/Drupal_Php_Modules/blob/master/images/addBeacon2.png
   :align: center
   
   
the module makes a POST request to the REST webserver to insert the new beacon in the system (if it does NOT exist).

.. image:: https://github.com/biagiobotticelli/Drupal_Php_Modules/blob/master/images/addBeacon3.png
   :align: center



deleteBeacon.php & deleteBeaconRESTCall.php
-------------------------------------------
The last two functions allow the user to select a beacon from the available ones and to delete it from the system.

To get an updated list of the beacons, the *deleteBeacon.php* module creates a button "Get Beacons":

.. image:: https://github.com/biagiobotticelli/Drupal_Php_Modules/blob/master/images/deleteBeacon1.png
   :align: center
   
when the button is clicked (as for the *getBeacons.php*):

.. image:: https://github.com/biagiobotticelli/Drupal_Php_Modules/blob/master/images/deleteBeacon2.png
   :align: center


the module makes a GET request to the REST webserver to obtain the list of the available beacons of the system.

Once a response is obtained from the server, the informations of beacons are displayed and a selection form is created.

.. image:: https://github.com/biagiobotticelli/Drupal_Php_Modules/blob/master/images/deleteBeacon3.png
   :align: center
 
   
The *deleteBeaconRESTCall.php* is not doing anything untill now... it's just waiting for a beaconID:

.. image:: https://github.com/biagiobotticelli/Drupal_Php_Modules/blob/master/images/deleteBeacon4.png
   :align: center


when the user makes it's selection in the form of *deleteBeacon.php* and press the button "Delete Beacon",
the value of the selected beaconID is sent to the *deleteBeaconRESTCall.php*.

.. image:: https://github.com/biagiobotticelli/Drupal_Php_Modules/blob/master/images/deleteBeacon5.png
   :align: center
   

Now, *deleteBeaconRESTCall.php* module makes a REST call (POST) to delete the beacon from the server.
If the beacon is successfully deleted from the system, a message will be displayed:

.. image:: https://github.com/biagiobotticelli/Drupal_Php_Modules/blob/master/images/deleteBeacon6.png
   :align: center

   
Installation instructions
-------------------------------------------

To install one of these PHP modules to make REST requests inside a Drupal working web-application,
you have to follow these steps:

- Inside the Drupal environment, download and enable the *php filter* Drupal module;
- Download the php module from Git;
- Insert the PHP module as *body* of Drupal Content page;
- Publish the Drupal page.
