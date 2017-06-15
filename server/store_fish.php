<?php

	require_once('token_auth.php');
	require_once('api_grab_fish.php');

	$email = $_POST['email'];
	$image = $_POST['image'];
	$size  = $_POST['size'];
	$type  = $_POST['type'];
	$token = $_POST['token'];


	if(verify_jwt($token) != 0){
		base64ToImage($image);
	}
	else{
		echo 'Nice Try!';
	}

    function store_fish($email, $size, $type, $image) {

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

		$name = $email."_".$type."_".$size.".jpeg"

		$sql = "INSERT INTO fish (user_id, type, size, image) SELECT user_id, '$type', '$size', '$name' FROM users WHERE email='$email'";

		if (mysqli_query($dbhandle, $sql)) {
		    echo $image;
		} else {
		    echo "Error updating record: " . mysqli_error($dbhandle);
		}

		mysqli_close($dbhandle);
	}

	 function base64ToImage($base64_string) {
	 	$content = base64_decode(str_replace(array('-', '_'), array('+', '/'), $base64_string));
	 	$file = fopen("C:/wamp64/www/biggestCatch/".$email."_".$type."_".$size.".jpeg", "wb");
	 	fwrite($file, $content);
		fclose($file);

		store_fish($email, $size, $type, $image);
	 }
	     




?>