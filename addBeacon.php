<!DOCTYPE HTML>  
<html>
<head>
<style>
.error {color: #FF0000;}
</style>
</head>
<body>

<?php

// define variables and set to empty values
$major = $minor = 0;
$name = "";
$latBeacon = $lonBeacon = 0.000000;

$majorErr = $minorErr = $nameErr = $latBeaconErr = $lonBeaconErr = "";

if ($_SERVER["REQUEST_METHOD"] == "POST") {
  	if (empty($_POST["name"])) {
    	$nameErr = "Name is required";
  	} else {
    	$name = test_input($_POST["name"]);
  	}
  
  	if (empty($_POST["major"])) {
    	$majorErr = "Major is required";
  	} else {
    	$major = test_input($_POST["major"]);
  	}
    
  	if (empty($_POST["minor"])) {
    	$minorErr = "Minor is required";
  	} else {
    	$minor = test_input($_POST["minor"]);
  	}

  	if (empty($_POST["latBeacon"])) {
    	$latBeaconErr = "latBeacon is required";
  	} else {
    	$latBeacon = test_input($_POST["latBeacon"]);
  	}

  	if (empty($_POST["lonBeacon"])) {
    	$lonBeaconErr = "lonBeacon is required";
  	} else {
    	$lonBeacon = test_input($_POST["lonBeacon"]);
  	}
}

function test_input($data) {
  	$data = trim($data);
  	$data = stripslashes($data);
  	$data = htmlspecialchars($data);
  	return $data;
}
?>

<h2>Add Beacon</h2>
<p>
<h5> Insert the values for the Beacon that you want to insert in the Web Server: </h5>
<p>
<form method="post" action="<?php echo htmlspecialchars("/addBeacon");?>"> 
 
  	Name: <input type="text" name="name">
  	<span class="error">* <?php echo $nameErr;?> 

  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

 	* Required Fields</span>
  	<br><br> 

  	Major: <input type="number" min = 1 name="major">
  	<span class="error">* <?php echo $majorErr;?></span>
  	<br><br>

  	Minor: <input type="number" min = 1 name="minor">
  	<span class="error">* <?php echo $minorErr;?></span>
  	<br><br>

  	Latitude ° : <input type="number" min = -90 max = 90 step="0.000001" name="latBeacon">
  	<span class="error">* <?php echo $latBeaconErr;?></span>
  	<br><br>

  	Longitude ° : <input type="number" min = -180 max = 180 step="0.000001" name="lonBeacon">
  	<span class="error">* <?php echo $lonBeaconErr;?></span>
  	<br><br>

	<input type="submit" class="button"
          name="add_beacon" value="Add Beacon">
</form>

<?php

	use GuzzleHttp\Psr7;
	use GuzzleHttp\Exception\RequestException;

	if (isset($_POST['add_beacon'])) {
   	
      // Instantiates a new guzzle client.
      $client = new GuzzleHttp\Client();
      
      
      
      try {
      	// Send POST request to our server with the JSON beacon
    		$response = $client->request('POST', 'http://amaca.ga:8080/beacon', 
    		['json' => 
    			[
    			"major"   => intval($major),
      		"minor" => intval($minor),
      		"name" => $name,
      		"latBeacon" => floatval($latBeacon),
      		"lonBeacon" => floatval($lonBeacon)
    			]
    		]);
    		
    		$body = $response->getBody();
      	if($body) {
         	$done = "<p> <h3> The Beacon $name is successfully added to the server!</h2>";
          	echo $done;
         }
    		
		} catch (RequestException $e) {
    		        echo Psr7\str($e->getRequest());
    			if ($e->hasResponse()) {
        			echo Psr7\str($e->getResponse());
    			}
		}
}

?>
</body>
</html>