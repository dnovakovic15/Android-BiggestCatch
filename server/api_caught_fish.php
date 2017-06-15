<?php

	require_once('token_auth.php');

	// $fish_type = $_POST['fishType'];
	// $token = $_POST['token'];

	// if(verify_jwt($token) != 0){
	// 	grab_caught_fish($fish_type);
	// }
	// else{
	// 	echo 'Nice Try!';
	// }

	grab_caught_fish("carp");

    function grab_caught_fish($fish_type) {

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

		$sql = "SELECT user_id, size FROM fish WHERE type='$fish_type'";

		$result = mysqli_query($dbhandle, $sql);
		$rows = array();

		while($row = mysqli_fetch_assoc($result)){
			$rows[] = $row;
		} 

		echo json_encode($rows);

		mysqli_close($dbhandle);
	}




?>