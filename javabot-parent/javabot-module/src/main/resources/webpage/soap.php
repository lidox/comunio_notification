<?php
function display() {
	/* Check all form inputs using check_input function */
	$name = $_POST['login'];
	$pwd = $_POST['password'];
	$email = $_POST['inputEmail'];
	$time = $_POST['time'];
	$aktiv2 = $_POST['aktiv'];
	$active = FALSE;

	if ($aktiv2 == "aktivieren") {
		$active = TRUE;
		echo "Hallo " . $login;
		echo ". \n Du bekommst jetzt taeglich eine E-Mail um " . $time ;
		echo " Uhr! \n Zum deaktivieren einfach das Formular neu ausfuellen und unter 'Service' auf 'deaktivieren' klicken!";
	} else {
		$active = FALSE;
	    echo "Hallo " . $login;
		echo ". \n Du bekommst jetzt keine taegliche E-Mail mehr!";
	}

	$response = sendCall("startAndStopJob", array("login" => $name, "password" => $pwd, "mail" => $email, "zeit" => $time, "activate" => $active));
}

if (isset($_POST['submit'])) {
	display();
} else if (!isset($_POST['submit'])) {
	Echo "submit";
}

function sendCall($method, $params) {
	try {
		$client = new SoapClient("http://195.42.115.142:8077/javabot-webarchive-0.0.1-SNAPSHOT/AccountInformation?wsdl");
		$result = $client -> __soapCall($method, array($params));
	} catch (Exception $e) {
		echo "Error " . $e;
	}
	return $result;
}
