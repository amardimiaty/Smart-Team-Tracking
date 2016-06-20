<?php 
    
    if (isset($_POST['delete_beacon'])) {
        $remove = intval($_POST["remove"]);

        // DEBUG
        //var_dump($remove);

        try {
            // Instantiates a new guzzle client.
            $client = new GuzzleHttp\Client();

            // Sending application/x-www-form-urlencoded POST requests requires
            // that you specify the POST fields as an array in the 'form_params' 
            // request options.
            $response = $client->request('POST', 'http://amaca.ga:8080/beacon/delete', [
                'form_params' => ['beaconID' => $remove]
            ]);

            $body = $response->getBody();
            if($body) {
                $done = "<p> <h3> The Beacon with ID $remove is successfully removed!</h2>";
                echo $done;

                // Refresh the page
                header("Refresh: 10; URL=/deleteBeacon");

            }
        } catch (RequestException $e) {
            echo Psr7\str($e->getRequest());
            if ($e->hasResponse()) {
                echo Psr7\str($e->getResponse());
            }
        }
    }
    else {
        echo "<br>";
        echo "<h5> Waiting for a request...</h5>";
    }
?>