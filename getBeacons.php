<html>
    <body>
        <h2>Get Beacons </h2>
        <br>
        <form method="post" action="<?php echo htmlspecialchars("/getBeacons");?>">
           <h5> Click the button to have an updated list of available beacons: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="submit" class="button"
              name="get_beacons" value="Get Beacons"> </h5>
        </form>

        <?php

            use GuzzleHttp\Psr7;
            use GuzzleHttp\Exception\RequestException;

            $tab = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";

            $page = $_SERVER['/getBeacons'];

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

                        // Loop through Objects
                        foreach($beacon as $key => $value) {
                            echo "<p><h4> Beacon " . $value->id . "</h4></p>";
                            echo "<p>" . $tab ."Name: " . $value->name . "</p>";
                            echo "<p>" . $tab ."Major: " . $value->major . $tab ."Minor: " . $value->minor . "</p>";
                            echo "<p>" . $tab ."Latitude: " . $value-> latBeacon . $tab ."Longitude: " . $value-> lonBeacon . "</p>";
                        }
                   }
                } catch (RequestException $e) {
                    echo Psr7\str($e->getRequest());
                    if ($e->hasResponse()) {
                        echo Psr7\str($e->getResponse());
                    }
                }

                // Last update done
                date_default_timezone_set('Europe/Rome');
                $date = date('j/M/Y H:i:s');

                echo "Last Update: " . $date;

                // Refresh the page after 2 minutes
                header("Refresh: 120; URL=$page");
            }
        ?>

    </body>
</html>