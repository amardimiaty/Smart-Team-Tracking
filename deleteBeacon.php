<html>
    <body>
        <h2>Delete Beacon</h2>
        <br>
        <form method="post" action="<?php echo htmlspecialchars("/deleteBeacon");?>">
            <h5>Click the button to have an updated list of available beacons: 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="submit" class="button"
              name="get_beacons" value="Get Beacons"> </h5>
        </form>
        <br>

        <?php

            use GuzzleHttp\Psr7;
            use GuzzleHttp\Exception\RequestException;

            $page = $_SERVER['/deleteBeacon'];

            $tab = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
            
            if (isset($_POST['get_beacons'])) {

                // Instantiates a new guzzle client.
                $client = new GuzzleHttp\Client();

                try {
                    // Send POST request to our server with the JSON beacon
                    $response = $client->get('http://amaca.ga:8080/beacon');

                    // DEBUG: 
                    $body = $response->getBody();
                    if($body) {
                        // Convert JSON string to Object
                        $beacon = json_decode($body);
                        
                        echo "<h3>Available Beacons </h3>";

                        // Loop through Objects
                        $ids = array();
                        foreach($beacon as $key => $value) {
                                array_push($ids, $value -> id);

                        echo "<b>ID: " . $value->id . "</b><br>";
                        echo $tab .
                                "Name: " . $value -> name . ",  " . 
                                "Major: " . $value -> major . ",  " .
                                "Minor: " . $value -> minor . ",  " .
                                "Latitude: " . $value -> latBeacon . ",  " .
                                "Longitude: " . $value -> lonBeacon . "<br>";

                        }
                        //DEBUG array
                        //print_r($ids);

                   }
                } catch (RequestException $e) {
                        echo Psr7\str($e->getRequest());
                    if ($e->hasResponse()) {
                        echo Psr7\str($e->getResponse());
                   }
                }

        ?>
        
        <br>
        <h3> Which beacon do you want to remove? </h3>

        <form method="post" action="<?php echo htmlspecialchars("/beaconDelete_post");?>">       
            <select name = "remove" required>
                <option value =""> - Choose ID - </option>
                <?php
                // Iterating through the ids array
                foreach($ids as $item){
                ?>
                <option value="<?php echo $item; ?>"><?php echo $item; ?></option>
                <?php
                }
                ?>
            </select>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="submit" class="button" name="delete_beacon" value="Delete Beacon">
        </form>

        <?php 
        }
        ?>

    </body>
</html>