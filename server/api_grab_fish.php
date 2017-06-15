<?php

	require_once('token_auth.php');

	$imageid = $_POST['imageID'];
	$token = $_POST['token'];

	if(verify_jwt($token) != 0){
		grab_fish($imageid);
	}
	else{
		echo 'Nice Try!';
	}


    function grab_fish($imageid) {

		$username = "root";
		$password = "";
		$hostname = "localhost";
		$database = "biggestcatch";

		$dbhandle = mysqli_connect($hostname, $username, $password)
		    or die("Unable to connect to MySQL");
		//echo "Connected to MySQL<br>";

		$selected = mysqli_select_db($dbhandle, $database)
		    or die("Could not selected db2");
		//echo "Connected to db2<br>", "<br>";

		$sql = "SELECT image FROM fish WHERE id = '$imageid'";

		$result = mysqli_query($dbhandle, $sql);
		$array = array();
		$array = $result->fetch_array();

		return $array[0];
		$data = $array[0];

		mysqli_close($dbhandle);
	}




?>